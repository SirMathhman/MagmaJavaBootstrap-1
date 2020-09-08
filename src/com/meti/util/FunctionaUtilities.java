package com.meti.util;

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionaUtilities {
    public static Predicate<Boolean> IdentityPredicate = value -> value;

    public static <T> BinaryOperator<T> SelectLast() {
        return (oldValue, newValue) -> newValue;
    }

    public static <T> Function<T, Boolean> toFunction(Predicate<T> predicate) {
        return predicate::test;
    }
}
