package com.meti.feature;

public class NativeInfixTest extends FeatureTest{
    @Override
    protected int expectedExit() {
        return 420;
    }

    @Override
    protected String source() {
        return """
                native def +(value0 : Int, value1 : Int) : Int;
                
                def main() : Int => {
                    return 400 + 20;
                }
                """;
    }

    @Override
    protected String expectedOutput() {
        return "";
    }

    @Override
    protected String compile() {
        return "int main(){return 400+20;}";
    }
}
