/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.model;

import java.io.Serializable;

/**
 *
 * @author martin
 */
public class ForeignKey<T> extends Column<T> implements Serializable{
    // Tabla relacionada con la foreign key
    private final Table relatedTable;

    public ForeignKey(Table relatedTable, Class<T> dataClass, String name) {
        super(dataClass, name, false);
        this.relatedTable = relatedTable;
    }

    public Table getRelatedTable() {
        return relatedTable;
    }

    @Override
    public String toString() {
        return super.toString()+" ["+getRelatedTable().toString()+"]";
    }
    
}
