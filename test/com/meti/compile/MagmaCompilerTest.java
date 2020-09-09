package com.meti.compile;

import com.meti.util.Monad;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static com.meti.util.Monad.Monad;
import static org.junit.jupiter.api.Assertions.*;

class MagmaCompilerTest {

    @Test
    void compile() {
        Monad(new MagmaCompiler())
                .with("def main() : Int => {return 0;}")
                .map(MagmaCompiler::compile)
                .with("int main(){return 0;}")
                .reverse()
                .accept(Assertions::assertEquals);
    }
}