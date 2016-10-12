/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.model;

/**
 *
 * @author martin
 */
public class DuplicatedPrimaryKeyException extends Exception {

    /**
     * Creates a new instance of <code>DuplicatedPrimaryKeyException</code>
     * without detail message.
     */
    public DuplicatedPrimaryKeyException() {
    }

    /**
     * Constructs an instance of <code>DuplicatedPrimaryKeyException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicatedPrimaryKeyException(String msg) {
        super(msg);
    }
}
