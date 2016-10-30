/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import org.martin.powerdb.model.Table;
import org.martin.powerdb.model.interfaces.Readable;

/**
 *
 * @author martin
 */
public class ResultsPointer {
    private final Table table;
    private int indexPointer;
    
    public ResultsPointer(Table table) {
        this.table = table;
        indexPointer = -1;
    }

    private int getRecordsCount(){
        return table.getRecordsCount();
    }
    
    public boolean hasNext(){
        indexPointer++;
        return indexPointer < table.getRecordsCount();
    }
    
    private void checkIndex(int index){
        if (index >= table.getRecordsCount() || index < 0)
            throw new IndexOutOfBoundsException(index+"");
    }

    public void setPointerPosition(int position){
        checkIndex(position);
        indexPointer = position;
    }
    
    public Number getNumber(int column) {
        return table.getNumber(indexPointer, column);
    }

    public Long getLong(int column) {
        return table.getLong(indexPointer, column);
    }

    public Integer getInt(int column) {
        return table.getInt(indexPointer, column);
    }

    public Short getShort(int column) {
        return table.getShort(indexPointer, column);
    }

    public Byte getByte(int column) {
        return table.getByte(indexPointer, column);
    }

    public Float getFloat(int column) {
        return table.getFloat(indexPointer, column);
    }

    public Double getDouble(int column) {
        return table.getDouble(indexPointer, column);
    }

    public String getString(int column) {
        return table.getString(indexPointer, column);
    }
    
}
