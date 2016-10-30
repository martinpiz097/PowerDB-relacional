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

    public static final String SELECT = "SELECT";
    public static final String INSERT = "INSERT";
    public static final String UPDATE = "UPDATE";
    public static final String DELETE = "DELETE";
    public static final String SHOW = "SHOW";
    public static final String DROP = "DROP";
    public static final String CALL = "CALL";
    public static final String CREATE = "CREATE";
    public static final String DESCRIBE = "DESCRIBE";
    public static final String DESC = "DESC";
    
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

    public boolean hasOrder(){
        return order != null && !order.isEmpty();
    } 
    
    public boolean isValidOrder(){
        if (!hasOrder()) return false;

        return order.equalsIgnoreCase(INSERT) || order.equalsIgnoreCase(SELECT) || 
                order.equalsIgnoreCase(UPDATE) || order.equalsIgnoreCase(DELETE) ||
                order.equalsIgnoreCase(CALL) || order.equalsIgnoreCase(CREATE) || 
                order.equalsIgnoreCase(DESC) || order.equalsIgnoreCase(DESCRIBE) ||
                order.equalsIgnoreCase(DROP) || order.equalsIgnoreCase(SHOW); 
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
