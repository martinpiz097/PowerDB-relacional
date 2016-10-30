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
import java.nio.file.Files;

/**
 *
 * @author martin
 */
public class DataWriter extends BufferedWriter{
    private final File fileToWrite;
    private static final StringBuilder sBuild = new StringBuilder();
    
    public DataWriter(File fileToWrite) throws IOException {
        this(fileToWrite, true);
    }
    
    public DataWriter(File fileToWrite, boolean append) throws IOException{
        super(new FileWriter(fileToWrite, append));
        this.fileToWrite = fileToWrite;
    }

    public static void clearStringBuilder(){
        sBuild.delete(0, sBuild.length());
    }
    
    public void clearFile() throws IOException{
        fileToWrite.delete();
        fileToWrite.createNewFile();
    }
    
    // Misma consideracion que reader para la encriptación

    public void writeRecord(String str) throws IOException{
        sBuild.append(str);
        sBuild.append('~');
        write(sBuild.toString(), 0, sBuild.length());
        clearStringBuilder();
    }
    
    public void writeRecordAndFlush(String str) throws IOException{
        writeRecord(str);
        flush();
    }
    
}
