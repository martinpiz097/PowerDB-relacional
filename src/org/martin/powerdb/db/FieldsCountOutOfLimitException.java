/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

/**
 *
 * @author martin
 */
public class FieldsCountOutOfLimitException extends Exception {

    /**
     * Creates a new instance of <code>FieldsCountOutOfLimitException</code>
     * without detail message.
     */
    public FieldsCountOutOfLimitException() {
    }

    /**
     * Constructs an instance of <code>FieldsCountOutOfLimitException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public FieldsCountOutOfLimitException(String msg) {
        super(msg);
    }
}
