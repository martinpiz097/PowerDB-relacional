/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.db;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.martin.powerdb.stream.Writer;

/**
 *
 * @author martin
 */
public class TInsert implements Runnable {
    private Writer writer;
    private Object[] record;

    public TInsert(Writer writer, Object[] record) {
        this.writer = writer;
        this.record = record;
    }
    
    @Override
    public void run() {
        try {
            writer.writeRecordAndFlush(Arrays.toString(record));
            record = null;
            writer = null;
        } catch (IOException ex) {
            Logger.getLogger(TInsert.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
