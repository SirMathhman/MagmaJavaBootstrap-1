package com.meti.feature;

public class InvokeTest extends FeatureTest {
	@Override
	protected int expectedExit() {
		return 420;
	}

	@Override
	protected String source() {
		return """
				def value() : Int => {
					return 420;
				}
				def main() : Int => {
					return value();
				}
				""";
	}

	@Override
	protected String expectedOutput() {
		return "";
	}

	@Override
	protected String compile() {
		return "int value(){return 420;}int main(){return value();}";
	}
}
