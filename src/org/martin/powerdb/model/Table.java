/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.powerdb.db.TableManager;

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
        this.relatedDb = relatedDb;
        this.name = name;
        this.columns = columns;
        instanceManager();
        updateTable();
    }

    public void instanceManager(){
        this.tableManager = new TableManager(relatedDb, name);
    }
    
    public void setName(String newName){
        this.name = newName;
        updateTable();
    }
    
    public List<Object[]> getRecords() {
        return tableManager.getRecords();
    }

    public TableManager getTableManager() {
        return tableManager;
    }
    
    public void updateTable() {
        try {
            tableManager.storeTable(this);
        } catch (IOException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void drop(){
        tableManager.deleteAll();
    }
    
    public void addRecord(Object... record){
        try {
            tableManager.addRecord(record);
        } catch (IOException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    @Override
    public String toString() {
        return "Table{" + "relatedDb=" + relatedDb + ", name=" + name + ", columns=" + columns + ", tableManager=" + tableManager + '}';
    }

}
