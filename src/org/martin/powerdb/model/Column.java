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

    // Al crear la tabla en la clase Database se debe comprobar que no deben 
    // haber dos columnas como primary key
    
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

    public T castField(String str){
        if (dataClass.getSimpleName().equals(String.class.getTypeName()))
            return (T) str;
        else if (dataClass.getTypeName().equals(Long.class.getSimpleName()))
            return (T) new Long(str);
        
        else if (dataClass.getTypeName().equals(Integer.class.getTypeName()))
            return (T) new Integer(str);
        
        else if (dataClass.getTypeName().equals(Short.class.getTypeName()))
            return (T) new Short(str);
        
        else if (dataClass.getTypeName().equals(Byte.class.getTypeName()))
            return (T) new Byte(str);
        
        else if (dataClass.getTypeName().equals(Float.class.getTypeName()))
            return (T) new Float(str);
        
        else if (dataClass.getTypeName().equals(Double.class.getTypeName()))
            return (T) new Double(str);
        
        else
            return (T) new Character(str.charAt(0));
        
        
//        else if(/*Para blobs*/true){
//            
//        }
        
    }
    
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
