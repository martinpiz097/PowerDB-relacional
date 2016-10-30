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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author martin
 */
public class DataReader extends BufferedReader{
    private final String filePath;
    //private static final StringBuilder sBuilder = new StringBuilder();
    
    public DataReader(File fileToRead) throws FileNotFoundException, IOException {
        super(new FileReader(fileToRead));
        filePath = fileToRead.getCanonicalPath();
    }
//    
//    private void appendToBuilder(String str){
//        sBuilder.append(str);
//    }
//    
//    private void clearStringBuilder(){
//        sBuilder.delete(0, sBuilder.length());
//    }
    
    public List<String> readLines() throws IOException{
        String fileLine = readLine();
        fileLine = fileLine.substring(0, fileLine.length()-1);
        return Arrays.asList(fileLine.split("~"));
    }
    
}
