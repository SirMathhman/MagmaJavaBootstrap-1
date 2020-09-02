package com.meti.feature;

public class StructureDeclareTest extends FeatureTest{
    @Override
    protected int expectedExit() {
        return 0;
    }

    @Override
    protected String source() {
        return """
                struct Wrapper {
                    value : Int;
                }
                def main() : Int => {
                    const wrapper : Wrapper = <Wrapper>{
                        value : 420;
                    };
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
        return "struct Wrapper{int value;};int main(){struct Wrapper wrapper={420};return 0;}";
    }
}
