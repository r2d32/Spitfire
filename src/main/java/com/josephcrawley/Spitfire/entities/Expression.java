package com.josephcrawley.Spitfire.entities;

import com.josephcrawley.util.Log;

/**
 * An expression.
 */
public abstract class Expression extends Entity {

    // As the language is statically typed, we can compute and store the type at compile time.
    Type type;

    /**
     * Returns the type of this expression.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns whether this expression is compatible with (that is, "can be assigned to an object
     * of") a given type.
     */
    public boolean isCompatibleWith(Type thatType) {
        return type.canBeAssignedTo(thatType);
    }

    /**
     * Returns whether this expression can be assigned to.  False by default; it will be
     * overwritten only in those particular cases where it should return true.
     */
    boolean isWritableLValue() {
        return false;
    }

    /**
     * Returns whether this expression's type is an array type.
     */
    public boolean isArray() {
        return type instanceof ArrayType;
    }

    /**
     * Returns whether this expression's type is an array type or the string type.
     */
    public boolean isArrayOrString() {
        return isArray() || type == Type.STRING;
    }

    // Helpers for semantic analysis, called from the analyze methods of other expressions.  These
    // are by no means necessary, but they are very convenient.

    void assertAssignableTo(Type otherType, Log log, String errorKey) {
        if (!this.isCompatibleWith(otherType)) {
            log.error(errorKey, otherType, this.type);
        }
    }

    void assertArithmetic(String context, Log log) {
        if (type != Type.NUMBER) {
            log.error("non_arithmetic", context);
        }
    }


    void assertInteger(String context, Log log) {
        if (!(type == Type.NUMBER)) {
            log.error("non_integer", context, type);
        }
    }

    void assertBoolean(String context, Log log) {
        if (!(type == Type.BOOLEAN)) {
            log.error("non_boolean", context, type);
        }
    }

    void assertChar(String context, Log log) {
        if (!(type == Type.CHARACTER)) {
            log.error("non_char", context);
        }
    }

    void assertArray(String context, Log log) {
        if (!(type instanceof ArrayType)) {
            log.error("non_array", context);
        }
    }

    void assertString(String context, Log log) {
        if (!(type == Type.STRING)) {
            log.error("non_string", context);
        }
    }

    void assertArrayOrString(String context, Log log) {
        if (!(isArrayOrString())) {
            log.error("non_array_or_string", context);
        }
    }

}