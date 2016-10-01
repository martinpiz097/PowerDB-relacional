/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

/**
 *
 * @author martin
 */
public class OIS extends ObjectInputStream{

    public OIS(InputStream in) throws IOException {
        super(in);
    }

    public OIS() throws IOException, SecurityException {
        super();
    }

    @Override
    protected void readStreamHeader() throws IOException, StreamCorruptedException {
    }
    
}
