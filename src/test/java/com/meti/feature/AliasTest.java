package com.meti.feature;

public class AliasTest extends FeatureTest {
	@Override
	protected int expectedExit() {
		return 420;
	}

	@Override
	protected String source() {
		return """
				def main() : Int => {
					const x : Char = 'x';
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
		return "int main(){char x='x';int x0_=420;return x0_;}";
	}
}
