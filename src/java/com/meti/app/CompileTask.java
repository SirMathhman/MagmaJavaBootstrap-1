package com.meti.app;

import com.meti.CompileException;
import com.meti.compile.MagmaCompiler;
import com.meti.log.Recorder;
import com.meti.task.WarningTask;
import com.meti.util.Binding;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class CompileTask extends WarningTask {
	public static final String MAIN_CLASS = "Main-Class";
	public static final String SOURCE_DIRECTORY = "Source-Directory";
	public static final Path TARGET = Paths.get(".", "target");
	private final Binding<? extends Properties> binding;

	protected CompileTask(Recorder recorder, Binding<? extends Properties> binding) {
		super(recorder);
		this.binding = binding;
	}

	@Override
	protected void performImpl() {
		Properties properties = binding.get();
		Path sourceDirectory = getSourceDirectory(properties);
		Path targetDirectory = getTargetDirectory();
		Path mainClass = getMainClass(sourceDirectory, properties);
		String mainOutput = compilePath(mainClass);
		outputMain(targetDirectory, mainOutput);
	}

	static Path getSourceDirectory(Properties properties) {
		if (!properties.containsKey(SOURCE_DIRECTORY)) {
			throw new CompileException("Source directory was not stored in properties.");
		}
		String directory = properties.getProperty(SOURCE_DIRECTORY);
		Path path = Paths.get(".", directory);
		ensureDirectory(path);
		return path;
	}

	static Path getTargetDirectory() {
		ensureDirectory(TARGET);
		return TARGET;
	}

	static Path getMainClass(Path source, Properties properties) {
		if (properties.containsKey(MAIN_CLASS)) {
			Path mainClass = source.resolve(properties.getProperty("Main-Class"));
			if (Files.exists(mainClass)) return mainClass;
			throw new CompileException("Main class at " + mainClass.toAbsolutePath() + " doesn't exist. " +
			                           "Nothing will be compiled.");
		} else {
			throw new CompileException("Main class was not stored in properties.");
		}
	}

	static String compilePath(Path mainClass) {
		String mainContent;
		try {
			mainContent = Files.readString(mainClass);
		} catch (IOException e) {
			throw new CompileException("Failed to read content from " + mainClass.toAbsolutePath() + ".", e);
		}
		return MagmaCompiler.INSTANCE.compile(mainContent);
	}

	static Path outputMain(Path targetDirectory, String mainOutput) {
		Path output = targetDirectory.resolve("out.c");
		ensureDirectory(targetDirectory);
		ensure(output);

		try {
			Files.writeString(output, mainOutput);
			return output;
		} catch (IOException e) {
			throw new CompileException(String.format("Unable to write output of length %d to %s",
					mainOutput.length(),
					output.toAbsolutePath()), e);
		}
	}

	static void ensureDirectory(Path path) {
		try {
			if (!Files.exists(path)) {
				Files.createDirectory(path);
			}
		} catch (IOException e) {
			throw new CompileException("Failed to create directory.", e);
		}
	}

	private static void ensure(Path output) {
		try {
			if (!Files.exists(output)) {
				Files.createFile(output);
			}
		} catch (IOException e) {
			throw new CompileException("Failed to create file.", e);
		}
	}

	@Override
	protected String failureMessage() {
		return "Failed to compile.";
	}
}
