/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.powerdb.model.Column;

/**
 *
 * @author martin
 */
public final class TableProperties {
    private final File logFile;
    private final Properties p;
    private FileOutputStream os;

    public TableProperties(File logFile) throws IOException {
        this.logFile = logFile;
        p = new Properties();
        
        if (!logFile.exists()) saveConfigs();
        else loadConfigs();
    }

    public TableProperties(File rootDir, String tableName) throws IOException {
        logFile = new File(rootDir, tableName+".xml");
        p = new Properties();
        
        if (!logFile.exists()) saveConfigs();
        else loadConfigs();
    }

    public File getLogFile() {
        return logFile;
    }
    
    public void deleteLogFile(){
        logFile.delete();
    }

    public void addProperty(String key, String value){
        p.put(key, value);
        try {
            saveConfigs();
        } catch (IOException ex) {
            Logger.getLogger(TableProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getRelatedDB(){
        return p.getProperty("relatedDB");
    }
    
    public void setRelatedDB(String dbName){
        addProperty("relatedDB", dbName);
    }
    
    public String getName(){
        return p.getProperty("name");
    }
    
    public void setName(String tblName){
        addProperty("name", tblName);
    }
    
    public String getColumns(){
        return p.getProperty("columns");
    }
    
    public void setColumns(Column[] columns){
        addProperty("columns", Arrays.toString(columns));
    }
    
    public void deleteSettings() throws IOException{
        p.clear();
        saveConfigs();
    }

    public void loadConfigs() throws IOException{
        FileInputStream is = new FileInputStream(logFile);
        p.loadFromXML(is);
        is.close();
        is = null;
    }
    
    public void saveConfigs() throws IOException{
        os = new FileOutputStream(logFile);
        p.storeToXML(os, "Información de estructura de la tabla");
        os.close();
        os = null;
    }
}