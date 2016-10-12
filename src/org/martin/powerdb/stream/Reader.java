/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.stream;

import com.sun.nio.zipfs.ZipPath;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author martin
 */
public class Reader {
    private final Path filePath;
    //private final File fileToRead;
    
    public Reader(File fileToRead) {
        this.filePath = fileToRead.toPath();
    }

    public List<String> readLines() throws IOException{
        String fileLine = Files.readAllLines(filePath).get(0);
        fileLine = fileLine.substring(0, fileLine.length()-1);
        return Arrays.asList(fileLine.split("~"));
    }
    
}
