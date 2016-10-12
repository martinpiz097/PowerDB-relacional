/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.martin.powerdb.model.Column;
import org.martin.powerdb.model.Table;
import org.martin.powerdb.stream.Reader;
import org.martin.powerdb.stream.Writer;
import org.martin.powerdb.system.SysInfo;
import org.martin.powerdb.test.GeneralCounter;

/**
 *
 * @author martin
 */
public final class TableManager implements Serializable{
    private File tableDir;
    private File recordsFile;
    
    // Usados para gestionar como objeto los datos de la tabla(nombre, columnas, clase)
    // Ver más adelante una posible migracion a texto.
    private transient TableProperties tblProperties;
    
    // Usados para los gestionar registros como texto.
    private transient Writer dataWriter;
    private transient Reader dataReader;
    
    private transient Column[] tblColumns;
    private transient List<Object[]> records;
    //private transient ExecutorService threadsExecutor;
    private transient boolean autoSave;
    
    private class SAVE_TYPE{
        static final boolean APPEND = true;
        static final boolean NOT_APPEND = false;
    }
    
    public TableManager(String relatedDb, String tableName, Column[] tblColumns) {
        try {
            tableDir = new File(SysInfo.ROOT_DIR.getCanonicalPath()+"/"+relatedDb, tableName);
            
            if (!tableDir.exists())
                tableDir.mkdir();

            tblProperties = new TableProperties(tableDir, tableName);
            this.tblColumns = tblColumns;
            
            recordsFile = new File(tableDir, "records.db");
            
            if(!recordsFile.exists())
                recordsFile.createNewFile();

            openConnection();
            records = getDeserializedRecords();
            //threadsExecutor = Executors.newFixedThreadPool(1000);
            verifyWriter();
            enableAutoSave();
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setProperties(File file, boolean bool){
        file.setReadable(bool);
        file.setReadable(bool);
        file = null;
    }
    
    private void lockRecordsFile(){
        setProperties(recordsFile, false);
    }
    
    private void unlockRecordsFile(){
        setProperties(recordsFile, true);
    }

    private boolean isWriterClosed(){
        return dataWriter == null;
    }
    
    private boolean isReaderClosed(){
        return dataReader == null;
    }
    
    private void verifyWriter() {
        if (isWriterClosed()) 
            try {
                dataWriter = new Writer(recordsFile);
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void verifyReader() {
        if (isReaderClosed())
            dataReader = new Reader(recordsFile);
    }

    private void closeWriter(){
        try {
            dataWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataWriter = null;
    }
    
    private void closeReader(){
        dataReader = null;
    }
    
    private Object[] getTransformedObject(String str){
        String[] split = str.substring(1, str.length()-1).split(", ");
        Object[] array = new Object[split.length];
        System.arraycopy(split, 0, array, 0, split.length);
        split = null;
        return array;
    }
    
//    public Table getTable() throws IOException, ClassNotFoundException{
//        return tblProperties.getTable();
//    }
    
    public boolean hasRecords(){
        return !records.isEmpty();
    }
    
    public void storeTable(Table t) throws IOException{
        tblProperties.setRelatedDB(t.getRelatedDB());
        tblProperties.setName(t.getName());
        tblProperties.setColumns(t.getColumns());
    }

    private void clearFile() throws IOException {
        recordsFile.delete();
        recordsFile.createNewFile();
    }

    private void clearAndReboot() throws IOException{
        clearFile();
        closeWriter();
        verifyWriter();
    }
    
    private void updateRecords(){
        try {
            clearAndReboot();
            for (Object[] r : records)
                dataWriter.writeRecord(Arrays.toString(r));
            
            dataWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void openConnection(){
        unlockRecordsFile();
    }
    
    public void shutdown(){
        lockRecordsFile();
    }
    
    public void saveChanges(){
        updateRecords();
    }
    
    public void flushRecordsFile() {
        try {
            dataWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addRecord(Object[] record) throws IOException{
        records.add(record);
        dataWriter.writeRecord(Arrays.toString(record));
        if(autoSave)
            flushRecordsFile();
        
    }
    
    public Object[] getRecordAt(int index){
        return records.isEmpty() ? null : records.get(index);
    }
    
    public Object[] getRecord(int columnIndex, Object valueToFind){
        Object[] r;
        long ti = System.currentTimeMillis();
        for (Object[] record : records){
//            if (valueToFind instanceof Number) {
//                if (Long.parseLong(record[columnIndex].toString()) == ((Number) valueToFind).longValue())
//                    return record;
//            }
//            else
            
            if (record[columnIndex] == valueToFind)
                r = record;
        }
        GeneralCounter.foreachCount++;
        GeneralCounter.milisecForeach+=(System.currentTimeMillis()-ti);
        
        ti = System.currentTimeMillis();
        r = records.stream().filter(rec -> rec[columnIndex].equals(valueToFind))
                .findAny().orElse(null);
        GeneralCounter.milisecFilter+=(System.currentTimeMillis()-ti);
        GeneralCounter.filterCount++;
        return r;
    }
    
    public List<Object[]> getRecordsBy(int columnIndex, Object valueToFind){
        List<Object[]> listResults = new LinkedList<>();
        
        for (Object[] record : records) 
            if (record[columnIndex].equals(valueToFind))
                listResults.add(record);
        return listResults;
    }
    
    private List<Object[]> getDeserializedRecords(){
        List<Object[]> listRecords = new LinkedList<>();
        try {
            verifyReader();
            for (String line : dataReader.readLines()){
                listRecords.add(getTransformedObject(line));
                
            }
            closeReader();
        } catch (IOException ex) {
            closeReader();
        } finally {
            return listRecords;
        }
    }
    
//    public List<Object[]> getAllRecords(){
//        if (records != null)
//            return records;
//        else
//            return getDeserializedRecords();
//    }

    public int getRecordsCount(){
        return records.size();
    }
    
    public List<Object[]> getRecords() {
        return records;
    }
    
    public void setRecord(int index, Object... record) {
        records.set(index, record);
        if(autoSave) updateRecords();
    }
    
    public void enableAutoSave(){
        autoSave = true;
    }
    
    public void disableAutoSave(){
        autoSave = false;
    }
    
    public void setRecord(Object object, int row, int column){
        Object[] get = records.get(row);
        get[column] = object;
        records.set(row, get);
        if(autoSave) updateRecords();
        get = null;
    }
    
    public void deleteRecordAt(int index){
        try {
            records.remove(index);
            
            verifyWriter();
            clearFile();
            
            // Ver si concatenar String y despues insertar en el archivo es más
            // rápido que escribir en el archivo por cada línea.
            
            for (Object[] r : records)
                dataWriter.writeRecord(Arrays.toString(r));
            
            dataWriter.flush();
            closeWriter();
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteRecord(Object[] record){
        try {
            records.remove(record);
            verifyWriter();
            clearFile();
            for (Object[] r : records)
                dataWriter.writeRecord(Arrays.toString(r));
                
            dataWriter.flush();
            // Ver ese tema para editar directamente el archivo con los registros.
            //RandomAccessFile raf = new RandomAccessFile(tableDir, "rw");
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteRecords(){
        try {
            clearFile();
            records.clear();
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAll(){
        tblProperties.deleteLogFile();
        recordsFile.delete();
        tableDir.delete();
    }
}
