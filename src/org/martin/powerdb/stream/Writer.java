/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.stream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author martin
 */
public class Writer extends BufferedWriter{
    private final File fileToWrite;

    public Writer(File fileToWrite) throws IOException {
        this(fileToWrite, true);
    }
    
    public Writer(File fileToWrite, boolean append) throws IOException{
        super(new FileWriter(fileToWrite, append));
        this.fileToWrite = fileToWrite;
    }

    public void clearFile() throws IOException{
        fileToWrite.delete();
        fileToWrite.createNewFile();
    }
    
    // Misma consideracion que reader para la encriptación

    public void writeRecord(String str) throws IOException{
        write(str.concat("~"), 0, str.length()+1);
    }
    
    public void writeRecordAndFlush(String str) throws IOException{
        writeRecord(str);
        flush();
    }
    
}
