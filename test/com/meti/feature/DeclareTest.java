package com.meti.feature;

public class DeclareTest extends FeatureTest {
	@Override
	protected int expectedExit() {
		return 420;
	}

	@Override
	protected String source() {
		return """
				def main() : Int => {
					const x : Int = 420;
					return x;
				}
				""";
	}

	@Override
	protected String expectedOutput() {
		return "";
	}

	@Override
	protected String compile() {
		return "int main(){int x=420;return x;}";
	}
}
