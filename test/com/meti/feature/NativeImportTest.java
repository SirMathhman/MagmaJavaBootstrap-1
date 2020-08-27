package com.meti.feature;

public class NativeImportTest extends FeatureTest {
	@Override
	protected int expectedExit() {
		return 0;
	}

	@Override
	protected String source() {
		return """
				import native stdio;
								
				def main() : Int => {
					return 0;
				}
				""";
	}

	@Override
	protected String expectedOutput() {
		return "";
	}

	@Override
	protected String compile() {
		return "#include <stdio.h>\nint main(){return 0;}";
	}
}
