package com.bulatmain.conference.domain.common.value;

import java.util.Objects;

/**
 * This is StringObject interface that represents objects
 * that are representable as string records
 */
public interface StringObject {
    String get();

    default boolean equals(StringObject other) {
        return Objects.equals(this.get(), other.get());
    }
}
