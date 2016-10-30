/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.system;

import java.util.Arrays;
import java.util.LinkedList;
import org.martin.electroList.structure.ElectroList;

/**
 *
 * @author martin
 */
public class PossibleOption {
    private final ElectroList<String> similarOptions;
    
    public PossibleOption(String options) {
        this.similarOptions = new ElectroList<>("similarOptions");
        similarOptions.addAll(Arrays.asList(options.split("/")));
    }

    public boolean isEquals(PossibleOption po){
        int listSize = similarOptions.size();
        for (int i = 0; i < listSize; i++)
            if (!getOption(i).equals(po.getOption(i)))
                return false;
        return true;
    }
    
    public boolean isPossibleOption(PossibleOption po){
        return similarOptions.anyMatch(op -> op.equals(po.getInsertedOption()));
    }
    
    public boolean isPossibleOption(String option){
        return similarOptions.anyMatch(op->op.equals(option));
    }
    
    public String getInsertedOption(){
        return similarOptions.element();
    }
    
    public String getOption(int index){
        return similarOptions.get(index);
    }

    @Override
    public String toString() {
        String strOptions = "";
        for (String option : similarOptions)
            strOptions = strOptions.concat(option+"/");
        return strOptions.substring(0, strOptions.length()-1);
    }
    
}
