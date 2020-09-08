package com.meti.util;

import java.util.List;

public class CollectiveUtilities {
    /*
    Other languages, such as Scala, have support for immutable data structures.
    Java doesn't have this so this method has to be here as an easy adapter.
     */
    public static <T, R extends List<T>> R join(R fields, T field) {
        fields.add(field);
        return fields;
    }
}
