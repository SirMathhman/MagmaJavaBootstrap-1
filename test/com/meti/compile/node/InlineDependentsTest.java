package com.meti.compile.node;

import com.meti.compile.type.Field;
import com.meti.util.CollectiveUtilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.meti.compile.type.FieldBuilder.create;

class InlineDependentsTest {
    @Test
    void streamChildren() {
        InlineDependents.ofChild(new ValueToken("test")).streamChildren()
                .reduceToMonad(new ArrayList<Token>(), CollectiveUtilities::join)
                .map(list -> list.get(0))
                .append(new ValueToken("test"))
                .accept(Assertions::assertEquals);
    }

    @Test
    void toFields() {
    }

    @Test
    void testToFields() {
    }

    @Test
    void ofChild() {
    }

    @Test
    void ofChildren() {
    }

    @Test
    void of() {
    }

    @Test
    void ofSingleton() {
    }

    @Test
    void testOfChildren() {
    }

    @Test
    void apply() {
    }

    @Test
    void copyChildren() {
    }

    @Test
    void copyFields() {
    }

    @Test
    void streamChildrenNatively() {
    }

    @Test
    void streamFieldsNatively() {
    }

    @Test
    void identityWithChildren() {
        InlineDependents.ofChild(new ValueToken("test"))
                .identity()
                .streamChildren()
                .reduceToMonad(new ArrayList<Token>(), CollectiveUtilities::join)
                .map(ArrayList::isEmpty)
                .accept(Assertions::assertTrue);
    }

    @Test
    void identityWithFields(){
        InlineDependents.toFields(create().build())
                .identity()
                .streamFields()
                .reduceToMonad(new ArrayList<Field>(), CollectiveUtilities::join)
                .map(ArrayList::isEmpty)
                .accept(Assertions::assertTrue);
    }

    @Test
    void append() {
    }

    @Test
    void streamFields() {
        InlineDependents.toFields(create().build()).streamFields()
                .reduceToMonad(new ArrayList<Field>(), CollectiveUtilities::join)
                .map(list -> list.get(0))
                .append(create().build())
                .accept(Assertions::assertEquals);
    }

    @Test
    void applyToProperties() {
    }

    @Test
    void copyProperties() {
    }

    @Test
    void withoutFields() {
    }
}