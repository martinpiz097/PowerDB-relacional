/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.test;

import java.util.List;
import org.martin.powerdb.db.Database;
import org.martin.powerdb.model.Column;
import org.martin.powerdb.model.Table;

/**
 *
 * @author martin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Database db = new Database("dbTest");
        db.createTable("table1", new Column(Integer.class, "id", true));
        Table table = db.getTable("table1");
        table.addRecord(1);
        List<Object[]> records = table.getRecords();
        System.out.println(records.toString());
        System.exit(0);
        table.addRecord(1);
    }
}
