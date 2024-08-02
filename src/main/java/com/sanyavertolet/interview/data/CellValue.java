package com.sanyavertolet.interview.data;

import com.sanyavertolet.interview.exceptions.ValueTypeException;

public class CellValue {

    public enum CellValueType {
        INT,
        DOUBLE,
        BOOLEAN,
        STRING,
        ERROR,
        ;
    }

    private final CellValueType type;
    private final String value;

    public CellValue(CellValueType type, String value) {
        this.type = type;
        this.value = value;
    }

    public CellValueType getType() {
        return type;
    }

    public Integer asInt() {
        if (type == CellValueType.INT) {
            return Integer.parseInt(value);
        }
        throw new ValueTypeException("Value is " + type + ", not Integer");
    }

    public Double asDouble() {
        if (type == CellValueType.DOUBLE) {
            return Double.parseDouble(value);
        }
        throw new ValueTypeException("Value is " + type + ", not Double");
    }

    public Boolean asBoolean() {
        if (type == CellValueType.BOOLEAN) {
            return Boolean.valueOf(value);
        }
        throw new ValueTypeException("Value is " + type + ", not Boolean");
    }

    public String asString() {
        return value;
    }
}
