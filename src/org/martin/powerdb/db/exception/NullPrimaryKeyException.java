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
public class NullPrimaryKeyException extends Exception {

    /**
     * Creates a new instance of <code>NullPrimaryKeyException</code> without
     * detail message.
     */
    public NullPrimaryKeyException() {
    }

    /**
     * Constructs an instance of <code>NullPrimaryKeyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NullPrimaryKeyException(String msg) {
        super(msg);
    }
}
