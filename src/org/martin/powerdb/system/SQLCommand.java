/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.system;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author martin
 */
public class SQLCommand implements Serializable{
//    public static final SQLCommand insert = new SQLCommand("insert");
//    public static final SQLCommand select = new SQLCommand("select");
//    public static final SQLCommand update = new SQLCommand("update");
//    public static final SQLCommand delete = new SQLCommand("delete");
    
    private final String order;
    private final PossibleOption[] options;

    public SQLCommand(String strCommand) {
        this(strCommand.split(" "));
    }
    
    public SQLCommand(String... cmd){
        this.order = cmd[0];
        this.options = new PossibleOption[cmd.length-1];
    
        for (int i = 1; i < cmd.length; i++)
            options[i-1] = new PossibleOption(cmd[i]);
    }
    
    public boolean hasOptions(){
        return options != null && options.length > 0;
    }
    
    public boolean isEqualsOrder(SQLCommand cmd){
        return isEqualsOrder(cmd.getOrder());
    }
    
    public boolean isEqualsOrder(String anotherOrder){
        if(order == null && anotherOrder == null)
            return true;
        
        return order.equals(anotherOrder);
    }
    
    public boolean isEquals(SQLCommand anotherCmd){
        if (!isEqualsOrder(anotherCmd))
            return false;

        if(getOptionsCount() != anotherCmd.getOptionsCount())
            return false;

        else if (hasOptions() && anotherCmd.hasOptions()) {
            for (int i = 0; i < options.length; i++)
                if (!options[i].equals(anotherCmd.options[i]))
                    return false;
            return true;
        }
        else
            return true;
    }

    public int getOptionsCount(){
        return options == null ? 0 : options.length;
    }
    
    public String getOrder() {
        return order;
    }

    public PossibleOption[] getOptions() {
        return options;
    }
    
}
