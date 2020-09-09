package com.meti.feature;

public class IntTest extends FeatureTest {
	@Override
	protected int expectedExit() {
		return 420;
	}

	@Override
	protected String source() {
		return "def main() : Int => {return 420;}";
	}

	@Override
	protected String expectedOutput() {
		return "";
	}

	@Override
	protected String compile() {
		return "int main(){return 420;}";
	}
}
