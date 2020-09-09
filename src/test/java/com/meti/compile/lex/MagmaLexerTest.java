package com.meti.compile.lex;

import com.meti.compile.node.Token;
import com.meti.compile.node.block.*;
import com.meti.compile.node.primitive.IntToken;
import com.meti.compile.instance.primitive.PrimitiveType;
import com.meti.util.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.meti.util.Monad.Monad;

class MagmaLexerTest {

    @Test
    void parseToOption() {
        Monad(new MagmaLexer())
                .append("def main() : Int => {return 0;}")
                .map(MagmaLexer::parseToOption)
                .append(createFunction())
                .flatMapStart(Option::toMonadOrThrow)
                .accept(Assertions::assertEquals);
    }

    private Token createFunction() {
        return new FunctionNodeBuilder()
                .withName("main")
                .withReturnType(PrimitiveType.Int)
                .withValue(createContent())
                .build();
    }

    private Token createContent() {
        return Monad(0)
                .map(IntToken::new)
                .map(ReturnToken::new)
                .apply(BlockToken::new);
    }
}