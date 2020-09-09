package com.meti.compile.lex.parse.structure;

import com.meti.compile.lex.Tokenizer;
import com.meti.util.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.meti.util.Monad.Monad;

class StructDeclareFactoryTest {
    @Test
    void invalidateFunction() {
        Monad("def main() : Int => {return 0;}")
                .map(StructDeclareFactory::create)
                .map(Tokenizer::evaluate)
                .map(Option::isEmpty)
                .accept(Assertions::assertTrue);
    }
}