package com.meti.feature;

public class StructureTest extends FeatureTest{
    @Override
    protected int expectedExit() {
        return 0;
    }

    @Override
    protected String source() {
        return """
                struct Value {
                    x : Int;
                }
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
        return "struct Value{int x;};int main(){return 0;}";
    }
}
