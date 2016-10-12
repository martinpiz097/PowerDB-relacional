/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.test;

import java.io.IOException;
import java.util.List;
import org.martin.powerdb.db.Database;
import org.martin.powerdb.db.FieldsCountOutOfLimitException;
import org.martin.powerdb.db.exception.NullPrimaryKeyException;
import org.martin.powerdb.db.exception.TableNotExistsException;
import org.martin.powerdb.model.Column;
import org.martin.powerdb.model.DuplicatedPrimaryKeyException;
import org.martin.powerdb.model.IncompatibleObjectTypeException;
import org.martin.powerdb.model.NullForeignKeyException;
import org.martin.powerdb.model.Table;

/**
 *
 * @author martin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IncompatibleObjectTypeException, 
            IOException, TableNotExistsException, FieldsCountOutOfLimitException, 
            NullForeignKeyException, NullPrimaryKeyException, DuplicatedPrimaryKeyException {
        // Sin autoSave --> 322.000 elementos por segundo.
        // Con autoSave --> 250.000 elementos por segundo(y menos tiempo).
        
        Database db = new Database("dbTest");
        
        System.out.println("DBName: "+db.getName());
        
        db.createTable("table1", new Column(Integer.class, "id", true, true), 
                new Column(String.class, "name", true));
        
        Table table = db.getLastCreatedTable();
        String tblName = table.getName();
        
        int limit = 10000;
        long ti = start();
        db.selectFirst(tblName);
        System.out.println("selectFirst: "+finish(ti));
        
        ti = start();
        db.selectLast(tblName);
        System.out.println("selectLast: "+finish(ti));
        
        int recordCount = table.getRecordsCount();
        ti = start();
        for (int i = 0; i < limit; i++){
            db.insert(tblName, i, "nom"+(i+100));
        }
        System.out.println("Insert de "+limit+" elementos: "+finish(ti));
        System.out.println("foreach: "+GeneralCounter.getPromedioForeach());
        System.out.println("filter: "+GeneralCounter.getPromedioFilter());
        
        ti = start();
        List<Object[]> allRecords = table.getTableManager().getRecords();
        System.out.println("Deserializar todo: "+finish(ti));
        System.out.println("Cantidad de elementos: "+allRecords.size());
        table.getTableManager().shutdown();
        
//
//        for (int i = 0; i < 1000; i++)
//            table.addRecord(i+1, "nom"+(i+100));
//
//        int recordCount = table.getTableManager().getRecordsCount();
//        TableManager tm = table.getTableManager();
//        
//        ti = start();
//        for (int i = 0; i < recordCount; i++) {
//            tm.setRecord(i, i, "xd");
//        }
//        System.out.println("Con autoSave: "+finish(ti));
//    
//        tm.disableAutoSave();
//        ti = start();
//        for (int i = 0; i < recordCount; i++)
//            tm.setRecord(i, i, "xd");
//        
//        tm.saveChanges();
//        System.out.println("Sin autoSave: "+finish(ti));
//        tm.enableAutoSave();
//        
//        tm.deleteRecords();
    }
    
    public static long start(){
        return System.currentTimeMillis();
    }
    
    public static long finish(long ti){
        return System.currentTimeMillis()-ti;
    }
}
