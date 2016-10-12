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
 * @param <T>
 */
public class Column<T> implements Serializable{
    private final Class<T> dataClass;
    private String name;
    private boolean isAutoIncrement;
    private boolean isPK;
    //private boolean isFK;
    private boolean isEditable;

    public Column(Class<T> dataClass, String name, boolean isPK, 
            boolean isAutoIncrement) {
        this.dataClass = dataClass;
        this.name = name;
        this.isAutoIncrement = isAutoIncrement;
        this.isPK = isPK;
        this.isEditable = false;
    }
    
    public Column(Class<T> dataClass, String name, boolean isEditable) {
        this.dataClass = dataClass;
        this.name = name;
        this.isAutoIncrement = false;
        this.isEditable = isEditable;
    }

    public Column(Class<T> dataClass, String name, boolean isAutoIncrement, boolean isPK, boolean isEditable) {
        this.dataClass = dataClass;
        this.name = name;
        this.isAutoIncrement = isAutoIncrement;
        this.isPK = isPK;
        this.isEditable = isEditable;
    }

    // Retorna si la clase de sus datos es un número o no
//    private boolean isDataClassNumber(){
//        
//    }
    
    public String getColumnDataType(){
        return dataClass.getName();
    }

    public Class<T> getDataClass() {
        return dataClass;
    }

    public String getName() {
        return name;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public boolean isPK() {
        return isPK;
    }

    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public String toString() {
        return "["+dataClass.getName()+"~"+name+"~"+isAutoIncrement+"~"+isPK+
                "~"+isEditable+"]";
    }
    
}
