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
public class UnknownColumnException extends Exception {

    /**
     * Creates a new instance of <code>UnknownColumnException</code> without
     * detail message.
     */
    public UnknownColumnException() {
    }

    /**
     * Constructs an instance of <code>UnknownColumnException</code> with the
     * specified detail message.
     *
     * @param columnName the detail message.
     */
    public UnknownColumnException(String columnName, String tblName) {
        super("La columna "+columnName+"en la tabla "+tblName+" no existe");
    }
}
