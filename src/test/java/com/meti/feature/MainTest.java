package com.meti.feature;

public class MainTest extends FeatureTest {

	@Override
	protected int expectedExit() {
		return 0;
	}

	@Override
	protected String expectedOutput() {
		return "";
	}

	@Override
	protected String source() {
		return "def main() : Int => {return 0;}";
	}

	@Override
	protected String compile() {
		return "int main(){return 0;}";
	}
}
