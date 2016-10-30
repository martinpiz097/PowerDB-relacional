/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.model.dataTypes;

import java.io.BufferedOutputStream;
import java.util.Arrays;
import org.martin.electroList.structure.ElectroList;

/**
 *
 * @author martin
 */
public class Blob {
    private ElectroList<byte[]> bytes;

    public Blob(String strBytes) {
        bytes = new ElectroList<>();
        loadBytes(strBytes.substring(1, strBytes.length()-1));
    }

    private void loadBytes(String strBytes) {
        String[] splitBytes = strBytes.split("/");
        String[] commaSplit;
        final int splitLen = splitBytes.length;
        int commaSplitLen;
        byte[] b;
        
        for (int i = 0; i < splitLen; i++) {
            commaSplit = splitBytes[i].split(", ");
            commaSplitLen = commaSplit.length;
            b = new byte[commaSplitLen];
            for (int j = 0; j < commaSplitLen; j++)
                b[j] = Byte.parseByte(commaSplit[j]);
            bytes.add(b);
        }
        commaSplit = splitBytes = null;
        b = null;
    }
    
    @Override
    public String toString(){
        StringBuilder sBuilder = new StringBuilder();
        String strArray;
        final int listSize = bytes.size();
        
        int counter = 0;
        for (byte[] b : bytes) {
            strArray = Arrays.toString(b);
            strArray = strArray.substring(1, strArray.length()-1);
            sBuilder.append(strArray);
            if (counter < listSize-1)
                sBuilder.append('/');
            counter++;
            b = null;
        }
        
        // Reutilizo el objeto String para dejar null el StringBuilder.
        strArray = sBuilder.toString();
        sBuilder = null;
        return strArray;
    }
    
}
