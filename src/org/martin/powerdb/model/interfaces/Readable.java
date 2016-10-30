/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.model.interfaces;

/**
 *
 * @author martin
 */
public interface Readable {
    Number getNumber(int row, int column);
    
    Long getLong(int row, int column);
    
    Integer getInt(int row, int column);
    
    Short getShort(int row, int column);
    
    Byte getByte(int row, int column);
    
    Float getFloat(int row, int column);
    
    Double getDouble(int row, int column);
    
    String getString(int row, int column);
    
}
