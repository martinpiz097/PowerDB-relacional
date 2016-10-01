/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.powerdb.model.Table;
import org.martin.powerdb.stream.OIS;
import org.martin.powerdb.stream.OOS;
import org.martin.powerdb.stream.Reader;
import org.martin.powerdb.stream.Writer;
import org.martin.powerdb.system.SysInfo;

/**
 *
 * @author martin
 */
public final class TableManager implements Serializable{
    private File tableDir;
    private File tableRecordsFile;
    private File serializedTableFile;
    
    // Usados para gestionar como objeto los datos de la tabla(nombre, columnas, clase)
    // Ver más adelante una posible migracion a texto.
    private transient OOS outputStream;
    private transient OIS inputStream;

    // Usados para los gestionar registros como texto.
    private transient Writer dataWriter;
    private transient Reader dataReader;
    private transient List<Object[]> records;
    
    public TableManager(String relatedDb, String tableName) {
        try {
            tableDir = new File(SysInfo.ROOT_DIR.getCanonicalPath()+"/"+relatedDb, tableName);
            
            if (!tableDir.exists())
                tableDir.mkdir();
            
            serializedTableFile = new File(tableDir, tableName+".db");
            if(!serializedTableFile.exists())
                serializedTableFile.createNewFile();

            tableRecordsFile = new File(tableDir, "records.db");
            
            if(!tableRecordsFile.exists())
                tableRecordsFile.createNewFile();
                
            records = getDeserializedRecords();
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean isOutputStreamClosed(){
        return outputStream == null;
    }
    
    private boolean isInputStreamClosed(){
        return inputStream == null;
    }
    
    private boolean isWriterClosed(){
        return dataWriter == null;
    }
    
    private boolean isReaderClosed(){
        return dataReader == null;
    }
    
    private void verifyOutputStream() {
        if (isOutputStreamClosed()) 
            try {
                outputStream = new OOS(new FileOutputStream(serializedTableFile));
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void verifyInputStream() {
        if (isInputStreamClosed()) 
            try {
                inputStream = new OIS(new FileInputStream(serializedTableFile));
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void verifyWriter() {
        if (isWriterClosed()) 
            try {
                dataWriter = new Writer(tableRecordsFile);
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void verifyReader() {
        if (isReaderClosed()) 
            try {
                dataReader = new Reader(tableRecordsFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeOutputStream(){
        try {
            outputStream.close();
            outputStream = null;
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void closeInputStream(){
        try {
            inputStream.close();
            inputStream = null;
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            dataReader.close();
            dataReader = null;
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Object[] getTransformedObject(String str){
        String[] split = str.substring(1, str.length()-1).split(", ");
        Object[] array = new Object[split.length];
        System.arraycopy(split, 0, array, 0, split.length);
        split = null;
        return array;
    }
    
    public Table getTable() throws IOException, ClassNotFoundException{
        verifyInputStream();
        Table t = (Table) inputStream.readObject();
        closeInputStream();
        return t;
    }
    
    public void storeTable(Table t) throws IOException{
        verifyOutputStream();
        outputStream.writeObject(t);
        closeOutputStream();
    }

    private void clearFile() throws IOException{
        dataWriter.clearFile();
    }
    
    private void updateRecords(){
        try {
            verifyWriter();
            clearFile();
            for (Object[] r : records)
                dataWriter.writeLine(Arrays.toString(r));
            
            dataWriter.flush();
            closeWriter();
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addRecord(Object[] record) throws IOException{
        System.out.println(Arrays.toString(record));
        records.add(record);
        verifyWriter();
        dataWriter.writeLineAndFlush(Arrays.toString(record));
        closeWriter();
    }
    
    public Object[] getRecordAt(int index){
        return records.isEmpty() ? null : records.get(index);
    }
    
    public Object[] getRecord(int columnIndex, Object valueToFind){
        for (Object[] record : records) 
            if (record[columnIndex].equals(valueToFind))
                return record;
        return null;
    }
    
    private List<Object[]> getDeserializedRecords(){
        List<Object[]> listRecords = new LinkedList<>();
        try {
            String line;
            verifyReader();
            while ((line = dataReader.readLine()) != null)
                listRecords.add(getTransformedObject(line));
            
            closeReader();
        } catch (IOException ex) {
            closeReader();
        } finally {
            return listRecords;
        }
    }
    
    public List<Object[]> getAllRecords(){
        if (records != null)
            return records;
        else{
            return getDeserializedRecords();
        }
    }

    public List<Object[]> getRecords() {
        return records;
    }
    
    public void setRecord(int index, Object[] record) {
        records.set(index, record);
        updateRecords();
    }
    
    public void setRecord(Object object, int row, int column){
        Object[] get = records.get(row);
        get[column] = object;
        records.set(row, get);
        updateRecords();
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
                dataWriter.writeLine(Arrays.toString(r));
            
            dataWriter.flush();
            closeWriter();
        } catch (IOException ex) {
            Logger.getLogger(TableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteAll(){
        serializedTableFile.delete();
        tableRecordsFile.delete();
        tableDir.delete();
    }
}
