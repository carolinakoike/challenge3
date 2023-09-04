package com.compass.challenge3.exceptions;

import java.util.Objects;

public final class Error extends Throwable {
    private final String s;

    public Error(String s) {
        this.s = s;
    }

    public String getMessage() {
        return null;
    }

    public String s() {
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Error) obj;
        return Objects.equals(this.s, that.s);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s);
    }

    @Override
    public String toString() {
        return "Error[" +
                "s=" + s + ']';
    }

}
