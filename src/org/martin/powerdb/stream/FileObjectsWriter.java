///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.martin.powerdb.stream;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author martin
// */
//public class FileObjectsWriter<T> {
//    private final OOS outputStream;
//    private final File serialFile;
//    
//    private 
//    
//    /**
//     * Serializa un objeto en el stream para luego trasladarlo al archivo.
//     * @param object Objeto a escribir.
//     * @throws IOException En caso de errores al escribir el objeto.
//     */
//    public synchronized void serialize(T object) throws IOException{
//        writeObject(object);
//        writeIntoFile();
//    }
//    
//    /**
//     * Serializa los objetos de la lista entregada al stream para luego trasladarlo al archivo.
//     * @param objects Objetos a serializar.
//     * @throws IOException En caso de errores al serializar.
//     */
//    public synchronized void serialize(List<T> objects) throws IOException{
//        for (T object : objects)
//            writeObject(object);
//        writeIntoFile();
//    }
//    
//    /**
//     * Serializa todos los objetos del stream en el archivo.
//     * @throws IOException En caso de errores al serializar objetos.
//     */
//    public synchronized void writeIntoFile() throws IOException{
//        baos.writeTo(fileWriter);
//        baos.reset();
//    }
//
//    public synchronized void clearFile(File file){
//        try {
//            fileWriter.close();
//            fileWriter = new FileOutputStream(file);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FileObjectsWriter.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(FileObjectsWriter.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//}
