package com.meti;

import com.meti.compile.lex.RootLexer;
import com.meti.compile.lex.parse.MagmaLexRule;
import com.meti.compile.lex.resolve.primitive.IntResolveRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
	public static final Path MAIN = Paths.get(".", "main.txt");

	public static void main(String[] args) {
		try {
			List<String> packageList = findMainPackage();
			compile(packageList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String> findMainPackage() throws IOException {
		if (!Files.exists(MAIN)) Files.createFile(MAIN);
		String mainText = Files.readString(MAIN).trim();
		String[] packages = mainText.split("\\.");
		return List.of(packages);
	}

	public static void compile(List<String> packageList) throws IOException {
		Path directoryPath = packageList
				.subList(0, packageList.size() - 1)
				.stream()
				.reduce(Paths.get("."), Path::resolve, (oldPath, newPath) -> newPath);
		String sourceName = packageList.get(packageList.size() - 1);
		Path sourcePath = directoryPath.resolve("%s.magma".formatted(sourceName));
		String content = Files.readString(sourcePath);
		String contentToWrite = new RootLexer(new MagmaLexRule(), new IntResolveRule())
				.parse(content)
				.render();
		String fileName = sourcePath.getFileName().toString();
		String newFileName = fileName.replace(".magma", ".c");
		Path targetPath = sourcePath.resolveSibling(newFileName);
		Files.writeString(targetPath, contentToWrite);
	}

}
