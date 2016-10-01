/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

/**
 *
 * @author martin
 */
public class Reader extends BufferedReader{
    private final File fileToRead;
    
    public Reader(File fileToRead) throws FileNotFoundException{
        super(new FileReader(fileToRead));
        this.fileToRead = fileToRead;
    }

    // Por ahora seran los métodos normales pero despues la idea es ocupar la deserializacion
    // a la hora de rescatar los datos.
    @Override
    public Stream<String> lines() {
        return super.lines(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reset() throws IOException {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String readLine() throws IOException {
        return super.readLine(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int read() throws IOException {
        return super.read(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
