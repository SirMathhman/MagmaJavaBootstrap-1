package com.meti.feature.core;

public class MainTest extends FeatureTest {

	@Override
	protected String output() {
		return "";
	}

	@Override
	protected String source() {
		return "def main() : Int => {return 0;}";
	}

	@Override
	protected String compiled() {
		return "int main(){return 0;}";
	}
}
