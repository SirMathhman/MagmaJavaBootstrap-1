package com.meti.feature;

public class ParameterTest extends FeatureTest{
    @Override
    protected int expectedExit() {
        return 420;
    }

    @Override
    protected String source() {
        return """
                def pass(value : Int) : Int => {
                    return value;
                }
                
                def main() : Int => {
                    return pass(420);
                }
                """;
    }

    @Override
    protected String expectedOutput() {
        return "";
    }

    @Override
    protected String compile() {
        return "int pass(int value){return value;}int main(){return pass(420);}";
    }
}
