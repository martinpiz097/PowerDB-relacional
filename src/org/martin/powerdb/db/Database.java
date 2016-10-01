/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.martin.powerdb.model.Column;
import org.martin.powerdb.model.Table;

/**
 *
 * @author martin
 */
public class Database implements Serializable{
    // Ver la opcion de crear triggers como listeners
    private final String name;
    private LinkedList<Table> tables;
    private final DBManager manager;

    public Database(String name) {
        this.name = name;
        this.manager = new DBManager(name);
        loadDB();
    }
    
    public boolean hasTables(){
        return !tables.isEmpty();
    }
    
    public boolean hasTable(String name){
        boolean hasTable = false;
        
        for (Table table : tables)
            if (table.getName().equals(name)) {
                hasTable = true;
                break;
            }
        
        return hasTable;
    }
    
    private void loadDB(){
        tables = manager.getAllTables();
    }

    public String getName(){
        return name;
    }
    
    public boolean createTable(String tableName, Column... columns){
        if(hasTable(tableName)) 
            return false;
        
        if(columns == null) return false;
        
        Table t = new Table(this.name, tableName, columns);
        addTable(t);
        return true;
    }
    
    public void addTable(Table table){
        tables.add(table);
    }
    
    public void dropTable(String name){
        for (Table table : tables) {
            if (table.getName().equals(name)) {
                table.drop();
                tables.remove(table);
            }
        }
    }
    
    public void dropAllTables(){
        while (!tables.isEmpty())            
            tables.poll().drop();
    }
   
    public Table getTable(String tableName){
        if(tables.isEmpty()) return null;
        
        for (Table table : tables) 
            if (table.getName().equals(tableName))
                return table;
        
        return null;
    }

    public LinkedList<Table> getTables() {
        return tables;
    }
    
}
