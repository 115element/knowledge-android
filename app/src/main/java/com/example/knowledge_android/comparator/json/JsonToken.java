/*
 * Copyright 2003-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.knowledge_android.comparator.json;

import static com.example.knowledge_android.comparator.json.JsonTokenType.FALSE;
import static com.example.knowledge_android.comparator.json.JsonTokenType.NULL;
import static com.example.knowledge_android.comparator.json.JsonTokenType.NUMBER;
import static com.example.knowledge_android.comparator.json.JsonTokenType.STRING;
import static com.example.knowledge_android.comparator.json.JsonTokenType.TRUE;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * A JSON token, with a type, line / column information, and the text of that token.
 *
 * @author Guillaume Laforge
 * @since 1.8.0
 */
public class JsonToken {
    private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
    private static final BigInteger MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
    private static final BigInteger MAX_INTEGER = BigInteger.valueOf(Integer.MAX_VALUE);
    private static final BigInteger MIN_INTEGER = BigInteger.valueOf(Integer.MIN_VALUE);
    private static final BigDecimal MAX_DOUBLE = new BigDecimal(String.valueOf(Double.MAX_VALUE));
    private static final BigDecimal MIN_DOUBLE = MAX_DOUBLE.negate();
    private static final BigDecimal MAX_FLOAT = new BigDecimal(String.valueOf(Float.MAX_VALUE));
    private static final BigDecimal MIN_FLOAT = MAX_FLOAT.negate();

    /**
     * Start line position
     */
    private long startLine;
    /**
     * End line position
     */
    private long endLine;
    /**
     * Start column position
     */
    private long startColumn;
    /**
     * End column position
     */
    private long endColumn;

    /**
     * The type of the token
     */
    private JsonTokenType type;

    /**
     * The text of that token
     */
    private String text;

    /**
     * Return the value represented by this token (ie. a number, a string, a boolean or null).
     * For numbers, the most appropriate type is returned (Float, Double, BigDecimal for decimal numbers,
     * and Integer, Long and BigInteger for integral numbers).
     *
     * @return the represented value
     */
    public Object getValue() {
        if (type == STRING) {
            if (text.length() == 2) {
                return "";
            } else {
                return text.substring(1, text.length() - 1);
            }
        } else if (type == NUMBER) {
            if (text.contains(".") || text.contains("e") || text.contains("E")) {
                // a decimal number
                return new BigDecimal(text);
            } else {
                // an integer number
                BigInteger v = new BigInteger(text);
                if (v.compareTo(MAX_INTEGER) <= 0 && v.compareTo(MIN_INTEGER) >= 0) {
                    return v.intValue();
                } else if (v.compareTo(MAX_LONG) <= 0 && v.compareTo(MIN_LONG) >= 0) {
                    return v.longValue();
                } else {
                    return v;
                }
            }
        } else if (type == TRUE) {
            return true;
        } else if (type == FALSE) {
            return false;
        } else if (type == NULL) {
            return null;
        } else {
            throw new JsonException(
                    "No appropriate value represented by '" + text +
                            "' on line: " + startLine + ", column: " + startColumn
            );
        }
    }

    public String toString() {
        return text + " (" + type + ") [" + startLine + ":" + startColumn + "-" + endLine + ":" + endColumn + "]";
    }

    public long getStartLine() {
        return startLine;
    }

    public void setStartLine(long startLine) {
        this.startLine = startLine;
    }

    public long getEndLine() {
        return endLine;
    }

    public void setEndLine(long endLine) {
        this.endLine = endLine;
    }

    public long getStartColumn() {
        return startColumn;
    }

    public void setStartColumn(long startColumn) {
        this.startColumn = startColumn;
    }

    public long getEndColumn() {
        return endColumn;
    }

    public void setEndColumn(long endColumn) {
        this.endColumn = endColumn;
    }

    public JsonTokenType getType() {
        return this.type;
    }

    public void setType(JsonTokenType type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
