/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
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
    
    private void loadDB(){
        tables = manager.getAllTables();
    }

    public String getName(){
        return name;
    }
    
    public void createTable(Table table){
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
   
    public Table getTable(String name){
        if(tables.isEmpty()) return null;
        
        for (Table table : tables) 
            if (table.getName().equals(name))
                return table;
        
        return null;
    }
    
}
