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
        super(new FileWriter(fileToWrite, true));
        this.fileToWrite = fileToWrite;
    }

    public void clearFile() throws IOException{
        fileToWrite.createNewFile();
    }
    
    // Misma consideracion que reader para la encriptación

    public void writeLine(String str) throws IOException{
        write(str+"\n", 0, str.length()+1);
    }
    
    public void writeLineAndFlush(String str) throws IOException{
        writeLine(str);
        flush();
    }
    
    @Override
    public void close() throws IOException {
        super.close(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void flush() throws IOException {
        super.flush(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void newLine() throws IOException {
        super.newLine(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void write(String s, int off, int len) throws IOException {
        super.write(s, off, len); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        super.write(cbuf, off, len); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
