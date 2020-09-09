package com.meti.feature;

public class InvalidNameTest extends FeatureTest{
    @Override
    protected int expectedExit() {
        return 420;
    }

    @Override
    protected String source() {
        return """
                def main() : Int => {
                    const # : Int = 420;
                    return #;
                }
                """;
    }

    @Override
    protected String expectedOutput() {
        return "";
    }

    @Override
    protected String compile() {
        return "int main(){int __35__=420;return __35__;}";
    }
}
