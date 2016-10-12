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
public class SQLSyntaxExcepcion extends Exception {

    /**
     * Creates a new instance of <code>SQLSyntaxExcepcion</code> without detail
     * message.
     */
    public SQLSyntaxExcepcion() {
    }

    /**
     * Constructs an instance of <code>SQLSyntaxExcepcion</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SQLSyntaxExcepcion(String msg) {
        super(msg);
    }
}
