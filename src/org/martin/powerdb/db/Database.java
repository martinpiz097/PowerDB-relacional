/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import org.martin.powerdb.db.exception.FieldsCountOutOfLimitException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import org.martin.electroList.structure.ElectroList;
import org.martin.powerdb.db.exception.NullPrimaryKeyException;
import org.martin.powerdb.db.exception.TableNotExistsException;
import org.martin.powerdb.db.exception.UnknownColumnException;
import org.martin.powerdb.model.Column;
import org.martin.powerdb.db.exception.DuplicatedPrimaryKeyException;
import org.martin.powerdb.db.exception.IncompatibleObjectTypeException;
import org.martin.powerdb.db.exception.NullForeignKeyException;
import org.martin.powerdb.db.exception.SQLSyntaxErrorException;
import org.martin.powerdb.model.Table;

/**
 *
 * @author martin
 */
public class Database implements Serializable{
    // Ver la opcion de crear triggers como listeners
    private final String name;
    private ElectroList<Table> tables;
    private final DBManager manager;

    public Database(String name) {
        this.name = name;
        this.manager = new DBManager(name);
        loadDB();
    }
    
    public boolean hasTables(){
        return !tables.isEmpty();
    }
    
    public boolean hasTable(String tblName){
        return tables.anyMatch(tbl->tbl.getName().equals(tblName));
//        boolean hasTable = false;
//        
//        for (Table table : tables)
//            if (table.getName().equals(name)) {
//                hasTable = true;
//                break;
//            }
//        
//        return hasTable;
    }
    
    private void loadDB(){
        tables = manager.getAllTables();
    }
    
    private Table verifyTable(String tblName) throws TableNotExistsException{
        Table tbl = getTable(tblName);
        if(tbl == null)
            throw new TableNotExistsException();
        return tbl;
    }

    private void execQuery(String query) throws SQLSyntaxErrorException {
        String[] orders = query.split(" ");
        String[] fields;
        String strFields;
        
        if(orders.length == 0 && orders.length == 1) return;
        
        if (orders[0].equals("create")) {
            if (orders.length >= 3) {
                if (orders[1].equals("table")) {
                    strFields = query.substring(orders[0].length()+orders[1].length()+
                            orders[2].length()+2, query.length()-1);
                    fields = strFields.split(", ");
                    
                    LinkedList<Column> columns = new LinkedList<>();
                    String[] fieldSplit;
                    Class<?> clazz;
                    
                    for (String field : fields) {
                        fieldSplit = field.split(" ");
                        switch(fieldSplit[1].toLowerCase()){
                            case "int":
                                clazz = Integer.class;
                                break;
                            case "varchar":
                                clazz = String.class;
                                break;
                            default:
                                clazz = Object.class;
                                break;
                        }
                        columns.add(new Column(clazz, fieldSplit[0], true));
                    }
                    
                    Column[] columnsArray = new Column[columns.size()];
                    createTable(orders[2], columns.toArray(columnsArray));
                }
            }
            else
                throw new SQLSyntaxErrorException("Consulta create con errores de sintaxis");
        }
        else if (orders[0].equals("insert")) {
            
        }
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
    
    public ResultsPointer getResultsPointer(String tblName) throws TableNotExistsException{
        return new ResultsPointer(verifyTable(tblName));
    }
    
    public ResultsPointer getResultsPointer(Table tbl){
        return new ResultsPointer(tbl);
    }
    
    public List<Object[]> select(String tblName, String... wheres) throws TableNotExistsException, 
            UnknownColumnException{
        // Corregir busquedas con mas de un where.
        Table tbl = verifyTable(tblName);
        List<Object[]> listResults = new LinkedList<>();
        
        String[] splitWhere;
        for (String where : wheres) {
            splitWhere = where.split("=");
            listResults.addAll(tbl.getRecordsBy(splitWhere[0], splitWhere[1]));
        }
        tbl = null;
        splitWhere = null;
        return listResults;
    }
    
    public Object[] selectFirst(String tblName) throws TableNotExistsException{
        Table tbl = verifyTable(tblName);
        return tbl.getFirst();
    }
    
    public Object[] selectLast(String tblName) throws TableNotExistsException{
        Table tbl = verifyTable(tblName);
        return tbl.getFirst();
    }
    
    public List<Object[]> selectAll(String tblName) throws TableNotExistsException{
        Table tbl = verifyTable(tblName);
        return tbl.getRecords();
    }
    
    public void insert(String tblName, Object... record) throws TableNotExistsException, FieldsCountOutOfLimitException, IncompatibleObjectTypeException, IOException, NullForeignKeyException, NullPrimaryKeyException, DuplicatedPrimaryKeyException{
        Table tbl = verifyTable(tblName);
        
        if (record == null || record.length == 0)
            throw new NullPointerException("Los campos a agregar no son válidos");
        if (record.length > tbl.getColumnCount())
            throw new FieldsCountOutOfLimitException(record.length+"");
        
        tbl.addRecord(record);
        tbl = null;
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
        if(!hasTables()) return null;
        
        return tables.findFirst(tbl->tbl.getName().equals(tableName));
        //return tables.filter(tbl->tbl.getName().equals(tableName)).findFirst().orElse(null);
        
//        for (Table table : tables) 
//            if (table.getName().equals(tableName))
//                return table;
//        
//        return null;
    }
    
    public Table getLastCreatedTable(){
        if(tables.isEmpty()) return null;
        return tables.getLast();
    }

    public ElectroList<Table> getTables() {
        return tables;
    }
    
}
