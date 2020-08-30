package com.meti.feature;

public class NativeFunctionTest extends FeatureTest {

    @Override
    protected int expectedExit() {
        return 0;
    }

    @Override
    protected String source() {
        return """
                import native stdio;
                
                native def printf(format : String, value : Any) => Void;
                
                def main() : Int => {
                    printf("%s", "Hello World!");
                    return 0;
                }
                """;
    }

    @Override
    protected String expectedOutput() {
        return "Hello World!";
    }

    @Override
    protected String compile() {
        return "#include <stdio.h>\nint main(){printf(\"%s\",\"Hello World!\");return 0;}";
    }
}
