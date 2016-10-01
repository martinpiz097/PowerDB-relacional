/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.stream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author martin
 */
public class FileObjectsReader<T> extends ObjectInputStream{
    private final FileInputStream fis;
    
    public FileObjectsReader(FileInputStream fis) throws IOException {
        super(fis);
        this.fis = fis;
        fis = null;
    }

    @Override
    protected void readStreamHeader() throws IOException, StreamCorruptedException {
    }
    
    public List<T> readObjects(){
        List<T> objects = null;
        try {
            objects = new LinkedList<>();
            
            for(;;)
                objects.add((T) readObject());
            
        } catch (IOException | ClassNotFoundException ex) {
            return objects;
        }
    }
}
