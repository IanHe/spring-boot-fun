package com.ian.exceptions;

import java.util.Set;

public class BookUnsupportedFieldPatchException extends RuntimeException {
    public BookUnsupportedFieldPatchException(Set<String> keys) {
        super("Fields: " + keys.toString() + " update is not allowed");
    }
}
