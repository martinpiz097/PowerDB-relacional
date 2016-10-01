/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.test;

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
        Column[] columns = new Column[2];
        columns[0] = new Column(Integer.class, "id", true, true);
        columns[1] = new Column(String.class, "name", false, false, true);
        Table t = new Table(db.getName(), "tabla1", columns);
        db.createTable(t);
        t.addRecord(new Object[]{1, "nom1"});
        Object valueAt = t.getValueAt(0, 1);
        System.out.println(valueAt);
    }
    
}
