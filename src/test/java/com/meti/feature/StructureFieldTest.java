package com.meti.feature;

public class StructureFieldTest extends FeatureTest{
    @Override
    protected int expectedExit() {
        return 420;
    }

    @Override
    protected String source() {
        return """
                struct Wrapper {
                    value : Int;
                }
                def main() : Int => {
                    const wrapper : Wrapper = <Wrapper>{420};
                    return wrapper->value;
                }
                """;
    }

    @Override
    protected String expectedOutput() {
        return "";
    }

    @Override
    protected String compile() {
        return "struct Wrapper{int value;};int main(){struct Wrapper wrapper={420};return wrapper.value;}";
    }
}
