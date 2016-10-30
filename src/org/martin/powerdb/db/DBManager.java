/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.electroList.structure.ElectroList;
import org.martin.powerdb.model.Column;
import org.martin.powerdb.model.ForeignKey;
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
    
    private Column[] getColumnsFromString(String strColumns) throws ClassNotFoundException{
        strColumns = strColumns.substring(1, strColumns.length()-1);
        String[] columnSplit = strColumns.split(", ");
        String[] fieldsSplit;
        LinkedList<Column> listColumns = new LinkedList<>();
        
        Class<?> fieldClass;
        Column[] columns;
        Table relatedTbl;
        String columnName;
        
        boolean isAutoIncrement, isPK, isEditable;
        
        for (String column : columnSplit) {
            fieldsSplit = column.substring(1, column.length()-1).split("~");
            
            fieldClass = Class.forName(fieldsSplit[0]);
            columnName = fieldsSplit[1];
            
            // Es columna normal
            if (fieldsSplit.length == 5) {
                isAutoIncrement = Boolean.valueOf(fieldsSplit[2]);
                isPK = Boolean.valueOf(fieldsSplit[3]);
                isEditable = Boolean.valueOf(fieldsSplit[4]);
                listColumns.add(new Column(fieldClass, columnName, isAutoIncrement, isPK, isEditable));
            }
            // Es foreign key --> tiene 6 campos
            else{
                relatedTbl = getTableAt(new File(dbDir, fieldsSplit[5]+".xml"));
                listColumns.add(new ForeignKey(relatedTbl, fieldClass, columnName));
            }
        }
        
        columns = new Column[listColumns.size()];
        return listColumns.toArray(columns);
    }
    
    private Table getTableAt(File tblFile){
        try {
            //long ti = System.currentTimeMillis();
            TableProperties properties = new TableProperties(tblFile);
            Column[] columns = getColumnsFromString(properties.getColumns());
            Table tbl = new Table(properties.getRelatedDB(), properties.getName(), columns, false);
            
            columns = null;
            properties = null;
            //System.out.println("Cargar tabla: "+(System.currentTimeMillis()-ti));
            return tbl;
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void addFoundTablesTo(ElectroList<Table> listTables){
        File[] tblDirs = dbDir.listFiles(File::isDirectory);
        
        if (tblDirs != null) {
            File[] infoFiles;
            for (File tblDir : tblDirs) {
                infoFiles = tblDir.listFiles(f->!f.isDirectory());
                
                if (infoFiles != null) {
                    for (File infoFile : infoFiles) {
                        if (infoFile.getName().contains(tblDir.getName()) && 
                                infoFile.getName().endsWith(".xml")){
                            System.out.println("infoFile: "+infoFile);
                            System.out.println("infoFileExiste: "+infoFile.exists());
                            listTables.add(getTableAt(infoFile));
                            break;
                        }
                    }
                }
                
            }
        }
    }
    
    public ElectroList<Table> getAllTables(){
        ElectroList<Table> listTables = new ElectroList<>();
        addFoundTablesTo(listTables);
        return listTables;
    }
}
