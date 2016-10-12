
package org.martin.powerdb.dataStructures;

import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author martin
 */
public class Array<T> {
    private T[] array;
    private T firstElement;
    private T lastElement;
    
    // Cantidad de elementos
    private int size;

    public Array() {
        array = (T[]) new Object[10];
        firstElement = null;
        lastElement = null;
        size = 0;
    }
    
    private void increaseSize(){
        T[] newArray = (T[]) new Object[array.length+10];
        System.arraycopy(array, 0, newArray, 0, array.length);
        array = newArray;
    }
    
    private void addElement(T object){
        int indexNull = 0;
         
        // Buscar el primer indice con objeto nulo del array
        if (!isEmpty()) {
            while (array[indexNull] != null)
                indexNull++;
        }

        array[indexNull] = object;
        lastElement = array[indexNull];
        if (indexNull == 0)
            firstElement = lastElement;
        size++;
    }
    
    private void goBack(int fromIndex){
        for (int i = fromIndex; i < size; i++) {
            if (i == 0) {
                array[i] = array[i+1];
                firstElement = array[i];
            }
            else if (size == i-1) {
                array[i] = null;
                lastElement = array[i-1];
            }
            else
                array[i] = array[i+1];
        }
    }
    
    private void openSpaceAt(int index){
        if (size == array.length)
            increaseSize();

        if (index+1 == size) 
            array[index+1] = array[index];
        
        else{
            T current;
            T next = null;
            for (int i = index; i < size; i++) {
                if (i == index) {
                    next = array[i+1];
                    array[i+1] = array[i];
                }
                else {
                    current = next;
                    next = array[i + 1];
                    array[i + 1] = current;
                }
            }
        }
        size++;
    }
    
    public boolean isEmpty(){
        return size == 0;
    }
    
    public int size(){
        return size;
    }
    
    public void add(T object){
        if (size == array.length)
            increaseSize();
        
        addElement(object);
    }
    
    public void add(T object, int index){
        openSpaceAt(index);
        set(index, object);
    }

    public T get(int index){
        if(index >= size || index < 0) return null;
        
        if (index == 0) return firstElement;
        else if(index == size-1) return lastElement;
        else return array[index];
    }
    
    public void set(int index, T newObject){
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("El índice al que intenta acceder está "
                    + "fuera del rango de la lista");
        
        array[index] = newObject;
        if (index == 0)
            firstElement = array[index];
        
        else if(index == size-1)
            lastElement = array[index];
        
    }
    
    public void removeAt(int index){
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException("El índice al que intenta acceder está "
                    + "fuera del rango de la lista");
        
        goBack(index);
        size--;
    }
    
    public void remove(T object){
        if(object == null) throw new NullPointerException();
        
        for (int i = 0; i < size; i++)
            if (array[i].equals(object)) {
                goBack(i);
                size--;
                break;
            }
    }

    public T[] toArray(){
        T[] newArray = (T[]) new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        return newArray;
    }
    
}