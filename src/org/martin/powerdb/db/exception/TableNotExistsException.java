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
public class TableNotExistsException extends Exception {

    /**
     * Creates a new instance of <code>TableNotExistsException</code> without
     * detail message.
     */
    public TableNotExistsException() {
    }

    /**
     * Constructs an instance of <code>TableNotExistsException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TableNotExistsException(String msg) {
        super(msg);
    }
}
