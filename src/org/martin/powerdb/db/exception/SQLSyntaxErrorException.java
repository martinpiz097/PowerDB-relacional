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
public class SQLSyntaxErrorException extends Exception {

    /**
     * Creates a new instance of <code>SQLSyntaxErrorException</code> without
     * detail message.
     */
    public SQLSyntaxErrorException() {
    }

    /**
     * Constructs an instance of <code>SQLSyntaxErrorException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SQLSyntaxErrorException(String msg) {
        super(msg);
    }
    
}
