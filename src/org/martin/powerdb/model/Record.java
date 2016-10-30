///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.martin.powerdb.model;
//
//import java.util.Arrays;
//
///**
// *
// * @author martin
// */
//public class Record {
//    private final Object[] fields;
//
//    public Record(Object[] fields) {
//        this.fields = fields;
//    }
//
//    private void checkIndex(int index){
//        if(index >= fields.length || index < 0) 
//            throw new IndexOutOfBoundsException(index+"");
//    }
//    
//    public Object getField(int index){
//        checkIndex(index);
//        return fields[index];
//    }
//    
//    public void setField(int index, Object obj){
//        checkIndex(index);
//        fields[index] = obj;
//    }
//
//    @Override
//    public String toString() {
//        return Arrays.toString(fields);
//    }
//    
//}
