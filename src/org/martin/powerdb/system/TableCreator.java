/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.system;

import org.martin.electroList.structure.ElectroList;
import org.martin.powerdb.db.Database;
import org.martin.powerdb.model.Column;
import org.martin.powerdb.model.ForeignKey;
import org.martin.powerdb.model.Table;

/**
 *
 * @author martin
 */
public class TableCreator {
    private final Database db;
    private String tblName;
    private final ElectroList<Column<?>> tblColumns;
    private boolean isTableCreated;
    
    public TableCreator(String dbName){
        this(new Database(dbName));
    }
    
    public TableCreator(Database db) {
        this.db = db;
        tblColumns = new ElectroList<>();
        isTableCreated = false;
    }
    
    public TableCreator(String dbName, String tblName){
        this(new Database(dbName), tblName);
    }
    
    public TableCreator(Database db, String tblName){
        this(db);
        this.tblName = tblName;
        isTableCreated = false;
    }
    
    private boolean hasColumnName(String colName){
        return tblColumns.anyMatch(c->c.getName().equalsIgnoreCase(colName));
    }
    
    private boolean hasPrimaryKey(){
        if(tblColumns.isEmpty()) return false;
        
        return tblColumns.anyMatch(c->c.isPK());
    }
    
    public Table getCreatedTable(){
        return db.getTable(tblName);
    }

    public String getTblName() {
        return tblName;
    }
    
    public void setTableName(String tblName){
        this.tblName = tblName;
    }
    
    public boolean addColumn(Class<?> type, String name, boolean isAutoIncrement, 
            boolean isEditable){
        if(hasColumnName(name)) 
            return false;
        
        Column<?> c = new Column<>(type, name, isAutoIncrement, false, isEditable);
        return tblColumns.add(c);
    }
    
    public boolean addPrimaryKey(Class<?> type, String name, boolean isAutoIncrement){
        if(hasColumnName(name)) 
            return false;
        
        Column<?> c = new Column<>(type, name, isAutoIncrement, true);
        return tblColumns.add(c);
    }
    
    public boolean addForeignKey(String relatedTable, Class<?> type, String name){
        if(hasColumnName(name))
            return false;
        
        Table relatedTbl = db.getTable(tblName);
        ForeignKey<?> c = new ForeignKey<>(relatedTbl, type, name);
        return tblColumns.add(c);
    }

    private void createTable(){
        Column[] columns = new Column[tblColumns.size()];
        tblColumns.copyTo(columns);
        
        Table tbl = new Table(db.getName(), tblName, columns);
        db.addTable(tbl);
        
        columns = null;
        tbl = null;
        tblColumns.clear();
    }
    
    public boolean addToDatabase(){
        if(tblColumns.isEmpty()) return false;

        if (db.hasTable(tblName)) return false;

        if(isTableCreated) return false;
        
        createTable();
        return (isTableCreated = true);
    }
    
}
