/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.powerdb.model.Table;
import org.martin.powerdb.stream.OIS;
import org.martin.powerdb.system.SysInfo;

/**
 *
 * @author martin
 */
public class DBManager implements Serializable{
    private final File dbDir;
    private OIS inputStream;
    
    public DBManager(String dbName) {
        dbDir = new File(SysInfo.ROOT_DIR, dbName);
        if (!dbDir.exists())
            dbDir.mkdir();
        
    }
    
    private Table getTableAt(File tblFile){
        try {
            Table readedTable;
            inputStream = new OIS(new FileInputStream(tblFile));
            readedTable = (Table) inputStream.readObject();
            inputStream.close();
            inputStream = null;
            return readedTable;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void addFoundTablesTo(LinkedList<Table> listTables){
        File[] tblDirs = dbDir.listFiles(File::isDirectory);
        
        if (tblDirs != null) {
            File[] infoFiles;
            for (File tblDir : tblDirs) {
                infoFiles = tblDir.listFiles(f->!f.isDirectory());
                
                if (infoFiles != null) {
                    for (File infoFile : infoFiles) {
                        if (infoFile.getName().contains(tblDir.getName()) && 
                                infoFile.getName().endsWith(".db")){
                            listTables.add(getTableAt(infoFile));
                            break;
                        }
                    }
                }
                
            }
        }
    }
    
    public LinkedList<Table> getAllTables(){
        LinkedList<Table> listTables = new LinkedList<>();
        addFoundTablesTo(listTables);
        return listTables;
    }
}
