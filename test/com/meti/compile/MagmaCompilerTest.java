package com.meti.compile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.meti.util.Monad.Monad;

class MagmaCompilerTest {

    @Test
    void compile() {
        Monad(new MagmaCompiler())
                .append("def main() : Int => {return 0;}")
                .map(MagmaCompiler::compile)
                .append("int main(){return 0;}")
                .reverse()
                .accept(Assertions::assertEquals);
    }
}