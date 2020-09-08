package com.meti.compile.node;

import com.meti.util.CollectiveUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class InlineDependentsTest {

    @Test
    void streamChildren() {
        InlineDependents.ofChild(new ValueToken("test")).streamChildren()
                .reduceToMonad(new ArrayList<Token>(), CollectiveUtilities::join)
                .map(list -> list.get(0))
                .with(new ValueToken("test"))
                .accept(Assertions::assertEquals);
    }
}