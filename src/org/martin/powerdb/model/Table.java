/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.powerdb.db.TableManager;
import org.martin.powerdb.db.exception.NullPrimaryKeyException;
import org.martin.powerdb.db.exception.UnknownColumnException;

/**
 *
 * @author martin
 */
public final class Table implements Serializable, TableModel{
    private final String relatedDb;
    private String name;
    private final Column[] columns;
    //private final LinkedList<Object[]> records;
    private transient TableManager tableManager;
    
    public Table(String relatedDb, String name, Column[] columns) {
        this(relatedDb, name, columns, true);
    }
    
    public Table(String relatedDb, String name, Column[] columns, boolean store) {
        this.relatedDb = relatedDb;
        this.name = name;
        this.columns = columns;
        instanceManager();
        if(store) storeTable();
    }
    
    private boolean isPkAlreadyUsed(Object pk, byte columnIndex){
        return tableManager.getRecord(columnIndex, pk) != null;
    }

    public boolean hasRecords(){
        return tableManager.hasRecords();
    }
    
    public void instanceManager(){
        this.tableManager = new TableManager(relatedDb, name, columns);
    }
    
    public void setName(String newName){
        this.name = newName;
        storeTable();
    }

    public int selectCount(){
        return tableManager.getRecordsCount();
    }
    
    public List<Object[]> getRecords() {
        return tableManager.getRecords();
    }

    public TableManager getTableManager() {
        return tableManager;
    }
    
    public void storeTable() {
        try {
            tableManager.storeTable(this);
        } catch (IOException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void drop(){
        tableManager.deleteAll();
    }

    public boolean delete(String columnNames, Object... valuesToFind){
        String[] columnsSplit = columnNames.split(",");
        boolean columnFinded = false, toDeleted = false, fieldFinded = false;
        String colName;
        
        for (int i = 0; i < columnsSplit.length; i++){
            columnsSplit[i] = columnsSplit[i].trim();
            colName = columnsSplit[i];
            for (Column column : columns) {
                if (column.getName().equals(colName)) {
                    columnFinded = true;
                    for (Object[] record : tableManager.getRecords()) {
                        for (int j = 0; j < record.length; j++) {
                            if (record[j].equals(valuesToFind[i])) {
                                toDeleted = true;
                                fieldFinded = true;
                                break;
                            }
                            else{
                                toDeleted = false;
                                fieldFinded = false;
                            }
                        }
                        if (fieldFinded && toDeleted) 
                            tableManager.deleteRecord(record);
                        
                    }
                    break;
                }
            }
            if (!columnFinded)
                return false;
            else
                columnFinded = false;
        }
        
        return true;
    }
    
    public int getRecordsCount(){
        return tableManager.getRecordsCount();
    }
    
    public Object[] getFirst(){
        if (hasRecords()) return null;

        return tableManager.getRecordAt(0);
    }
    
    public Object[] getLast(){
        if(!hasRecords()) return null;
        
        return tableManager.getRecordAt(tableManager.getRecordsCount()-1);
    }
    
    public void addRecord(Object... record) throws IncompatibleObjectTypeException, IOException, 
            NullForeignKeyException, NullPrimaryKeyException, DuplicatedPrimaryKeyException{
        byte counter = 0;
        for (Object object : record) {
            if (!object.getClass().getName().equals(columns[counter].getColumnDataType()))
                throw new IncompatibleObjectTypeException();
            if(record[counter] == null && (columns[counter].isPK() || columns[counter] instanceof ForeignKey)){
                if(columns[counter].isPK())
                    throw new NullPrimaryKeyException();
                else
                    throw new NullForeignKeyException();
            }
            if (columns[counter].isPK() && isPkAlreadyUsed(object, counter))
                throw new DuplicatedPrimaryKeyException(object.toString());
            
            counter++;
        }
        counter = 0;
        tableManager.addRecord(record);
    }
    
    public String getRelatedDB(){
        return relatedDb;
    }

    public Column[] getColumns() {
        return columns;
    }
    
    public List<Object[]> getRecordsBy(String columnName, Object valueToFind) 
            throws UnknownColumnException{
        boolean columnExists = false;
        int colIndex = 0;
        for (Column column : columns){
            if (column.getName().equals(columnName)) {
                columnExists = true;
                break;
            }
            colIndex++;
        }
        if (!columnExists) 
            throw new UnknownColumnException(columnName, getName());
            
        return tableManager.getRecordsBy(colIndex, valueToFind);
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex].getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns[columnIndex].getDataClass();
    }

    @Override
    public boolean isFieldEditable(int columnIndex) {
        return columns[columnIndex].isEditable();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getRecords().get(rowIndex)[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        tableManager.setRecord(aValue, rowIndex, columnIndex);
    }
//
//    public String toSerialString(){
//        return "db="+relatedDb+"\nname="+name+"columns="+Arrays.toString(columns);
//    }
//    
    @Override
    public String toString() {
        return "Table{" + "relatedDb=" + relatedDb + ", name=" + name + ", columns=" + columns + ", tableManager=" + tableManager + '}';
    }

}
