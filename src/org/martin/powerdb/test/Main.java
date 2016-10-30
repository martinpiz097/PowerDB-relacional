///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.martin.powerdb.test;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import org.martin.powerdb.db.Database;
//import org.martin.powerdb.db.exception.FieldsCountOutOfLimitException;
//import org.martin.powerdb.db.ResultsPointer;
//import org.martin.powerdb.db.exception.NullPrimaryKeyException;
//import org.martin.powerdb.db.exception.TableNotExistsException;
//import org.martin.powerdb.db.exception.UnknownColumnException;
//import org.martin.powerdb.model.Column;
//import org.martin.powerdb.db.exception.DuplicatedPrimaryKeyException;
//import org.martin.powerdb.db.exception.IncompatibleObjectTypeException;
//import org.martin.powerdb.db.exception.NullForeignKeyException;
//import org.martin.powerdb.model.Table;
//import org.martin.powerdb.system.TableCreator;
//
///**
// *
// * @author martin
// */
//public class Main {
//
//    /**
//     * @param args the command line arguments
//     * @throws java.io.IOException
//     */
//    public static void main(String[] args) throws IncompatibleObjectTypeException, 
//            IOException, TableNotExistsException, FieldsCountOutOfLimitException, 
//            NullForeignKeyException, NullPrimaryKeyException, DuplicatedPrimaryKeyException, UnknownColumnException {
//        // Sin autoSave --> 322.000 elementos por segundo.
//        // Con autoSave --> 250.000 elementos por segundo(y menos tiempo).
//        
//        execTest();
//        execTestTableCreator();
//        Database db = new Database("dbTest");
//        
//        System.out.println("DBName: "+db.getName());
//        
//        db.createTable("table1", new Column(Integer.class, "id", true, true), 
//                new Column(String.class, "name", true));
//        
//        Table table = db.getLastCreatedTable();
//        String tblName = table.getName();
//        
//        int limit = 1;
//        long ti = start();
//        db.selectFirst(tblName);
//        System.out.println("selectFirst: "+finish(ti));
//        
//        ti = start();
//        db.selectLast(tblName);
//        System.out.println("selectLast: "+finish(ti));
//        
//        int recordCount = table.getRecordsCount();
//        ti = start();
//        for (int i = 0; i < limit; i++){
//            db.insert(tblName, i, "nom"+(i+100));
//        }
//        System.out.println("Insert de "+limit+" elementos: "+finish(ti));
//        
//        ti = start();
//        List<Object[]> allRecords = table.getTableManager().getRecords();
//        System.out.println("Deserializar todo: "+finish(ti));
//        System.out.println("Cantidad de elementos: "+allRecords.size());
//        for (Object[] record : allRecords) {
//            for (int i = 0; i < record.length; i++) {
//                if (i == record.length-1) {
//                    System.out.print(record[i]);
//                }
//                else{
//                    System.out.print(record[i]+"-");
//                }
//            }
//            System.out.println("");
//        }
//        //table.getTableManager().shutdown();
//        
////
////        for (int i = 0; i < 1000; i++)
////            table.addRecord(i+1, "nom"+(i+100));
////
////        int recordCount = table.getTableManager().getRecordsCount();
////        TableManager tm = table.getTableManager();
////        
////        ti = start();
////        for (int i = 0; i < recordCount; i++) {
////            tm.setRecord(i, i, "xd");
////        }
////        System.out.println("Con autoSave: "+finish(ti));
////    
////        tm.disableAutoSave();
////        ti = start();
////        for (int i = 0; i < recordCount; i++)
////            tm.setRecord(i, i, "xd");
////        
////        tm.saveChanges();
////        System.out.println("Sin autoSave: "+finish(ti));
////        tm.enableAutoSave();
////        
////        tm.deleteRecords();
//    }
//    
//    private static void execTest() throws TableNotExistsException, FieldsCountOutOfLimitException, 
//            IncompatibleObjectTypeException, IOException, NullForeignKeyException, 
//            NullPrimaryKeyException, DuplicatedPrimaryKeyException{
//        Database db = new Database("pruebas");
//        Column[] columns = new Column[2];
//        columns[0] = new Column(Integer.class, "id", true, true);
//        columns[1] = new Column(String.class, "name", true);
//        db.addTable(new Table(db.getName(), "tabla1", columns));
//        db.insert("tabla1", 1, "nom1");
//        db.insert("tabla1", 2, "nom2");
//        for (Object[] record : db.selectAll("tabla1")) {
//            for (Object object : record) {
//                System.out.print(object.getClass().getSimpleName()+"-");
//            }
//            System.out.println("");
//        }
//        
//        System.exit(0);
//    }
//    
//    private static void execTestTableCreator() throws TableNotExistsException, FieldsCountOutOfLimitException, IncompatibleObjectTypeException, IOException, NullForeignKeyException, NullPrimaryKeyException, DuplicatedPrimaryKeyException, UnknownColumnException{
//        Database db = new Database("pruebaTableCreator");
////        TableCreator tc = new TableCreator(db, "tabla4");
////        tc.addPrimaryKey(Integer.class, "id", true);
////        tc.addColumn(String.class, "name", false, true);
////        boolean createTable = tc.createTable();
////        System.out.println(createTable ? "Tabla creada" : "Tabla no creada");
//
//        //Table createdTable = tc.getCreatedTable();
//        Table tbl = db.getTable("tabla4");
//        //tbl.toString();
//        
//        System.exit(0);
//        //db.insert(createdTable.getName(), null, "nom1");
//        //db.insert(createdTable.getName(), null, "nom2");
//        
//        tbl.deleteAll();
//        
//        ResultsPointer res = db.getResultsPointer(tbl);
//            
//        int counter = 0;
//        while (res.hasNext()){
//            System.out.println(res.getInt(0)+"|"+res.getString(1));
//            counter++;
//            if (counter % 5 == 0) {
//                res.setPointerPosition(0);
//            }
//        }
//    }
//    
//    public static long start(){
//        return System.currentTimeMillis();
//    }
//    
//    public static long finish(long ti){
//        return System.currentTimeMillis()-ti;
//    }
//}
