package com.meti.feature;

public class InfixTest extends FeatureTest{
    @Override
    protected int expectedExit() {
        return 420;
    }

    @Override
    protected String source() {
        return """
                infix def select(first : Int, second : Int) : Int => {
                    return first;
                }
                def main() : Int => {
                    return 420 select 0;
                }
                """;
    }

    @Override
    protected String expectedOutput() {
        return "";
    }

    @Override
    protected String compile() {
        return "int select(int first,int second){return first;}int main(){return select(420,0);}";
    }
}
