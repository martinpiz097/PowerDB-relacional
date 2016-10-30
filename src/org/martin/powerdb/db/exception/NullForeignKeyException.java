/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db.exception;

/**
 *
 * @author martin
 */
public class NullForeignKeyException extends Exception {

    /**
     * Creates a new instance of <code>NullForeignKeyException</code> without
     * detail message.
     */
    public NullForeignKeyException() {
    }

    /**
     * Constructs an instance of <code>NullForeignKeyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NullForeignKeyException(String msg) {
        super(msg);
    }
}
