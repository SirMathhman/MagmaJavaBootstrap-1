package com.meti;

import com.meti.compile.*;
import com.meti.compile.Compiler;
import com.meti.compile.name.*;
import com.meti.compile.node.Node;
import com.meti.compile.parse.*;
import com.meti.compile.unit.StringUnit;
import com.meti.compile.unit.StructUnit;
import com.meti.compile.unit.Unit;
import com.meti.compile.value.CastValueResolver;
import com.meti.compile.value.PointerValueResolver;
import com.meti.compile.value.QuantityValueResolver;
import com.meti.compile.value.VariableValueResolver;
import com.meti.load.Loader;
import com.meti.load.LoaderFactory;
import com.meti.util.PathLoaderFactory;
import com.meti.util.Scopes;
import com.meti.util.Structures;
import com.meti.util.TreeScopes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.meti.util.DepthSplitter.BRACKET;

public class Main {
	public static final Path CONFIG = Paths.get(".", "config");
	public static final Path OUTPUT = Paths.get(".", "output.c");
	private static final Properties PROPERTIES = new Properties();
	private static final Path SOURCE = Paths.get(".", "source");
	public static final LoaderFactory FACTORY = new PathLoaderFactory(SOURCE);
	private static final Logger logger = Logger.getLogger("Main");
	private static final Scopes scopes = new TreeScopes();
	private static final Unit stringUnit = new StringUnit();
	private static final Structures structures = new Structures();
	private static final Unit structUnit = new StructUnit(structures);
	private static final Compiler compiler = new UnitCompiler(
			List.of(new QuantityParser(),
					new DereferenceParser(),
					new IntParser(),
					new ReturnParser(),
					new InvocationParser(),
					new BlockParser(scopes),
					new FunctionParser(scopes),
					structUnit,
					new FieldParser(structures),
					new AssignParser(),
					new CastParser(),
					new DeclareParser(scopes),
					stringUnit,
					new VariableParser(scopes)
			),
			List.of(
					new PointerNameResolver(),
					new AnyNameResolver(),
					new FunctionNameResolver(),
					new IntNameResolver(),
					new CharNameResolver(),
					stringUnit,
					new BoolNameResolver(),
					structUnit
			), List.of(
			new QuantityValueResolver(),
			new PointerValueResolver(),
			new CastValueResolver(),
			new VariableValueResolver(scopes)
	));

	public static void main(String[] args) {
		Loader loader = FACTORY.create();
		ensureConfig();
		loadProperties();
		if (ensureMain()) {
			processContent(loader);
		}
		storeProperties();
	}

	private static void ensureConfig() {
		if (!Files.exists(CONFIG)) {
			try {
				logger.log(Level.WARNING, "Failed to locate config file, it will be created.");
				Files.createFile(CONFIG);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Failed to create config file.", e);
			}
		}
	}

	private static void loadProperties() {
		try (InputStream input = Files.newInputStream(CONFIG)) {
			PROPERTIES.load(input);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to load properties.", e);
		}
	}

	private static boolean ensureMain() {
		if (!PROPERTIES.containsKey("Main")) {
			logger.log(Level.WARNING, "Loaded properties did not have main source specified, " +
			                          "will be set to \"insert name here\".");
			PROPERTIES.setProperty("Main", "insert name here");
			return false;
		} else {
			return true;
		}
	}

	private static void processContent(Loader loader) {
		String name = PROPERTIES.getProperty("Main");
		String content = loader.load(name);
		compileContent(content);
	}

	private static void storeProperties() {
		try (OutputStream output = Files.newOutputStream(CONFIG)) {
			PROPERTIES.store(output, "");
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to store properties.", e);
		}
	}

	private static void compileContent(String content) {
		if (content.isBlank()) {
			logger.log(Level.WARNING, "Content cannot be blank.");
		} else {
			String output = compile(content);
			writeOutputChecked(output);
		}
	}

	private static String compile(CharSequence content) {
		return BRACKET.split(content).stream()
				.filter(s -> !s.isBlank())
				.map(String::trim)
				.map(compiler::parse)
				.map(Node::render)
				.collect(Collectors.joining());
	}

	private static void writeOutputChecked(String output) {
		if (output.isBlank()) {
			logger.log(Level.WARNING, "Output is blank.");
		} else {
			writeOutput(output);
		}
	}

	private static void writeOutput(CharSequence output) {
		try {
			Files.writeString(OUTPUT, output);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Failed to write output.", e);
		}
	}
}
