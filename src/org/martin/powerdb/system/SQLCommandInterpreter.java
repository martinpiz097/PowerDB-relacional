/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.powerdb.system;

import java.util.StringTokenizer;
import org.martin.powerdb.db.Database;
import org.martin.powerdb.db.exception.SQLSyntaxErrorException;

/**
 *
 * @author martin
 */
public class SQLCommandInterpreter {
    private final Database database;
    
    public SQLCommandInterpreter(Database database) {
        this.database = database;
    }
    
    public boolean execCommand(String cmd) throws SQLSyntaxErrorException{
        return execCommand(new SQLCommand(cmd));
    }
    
    public boolean execCommand(SQLCommand sql) throws SQLSyntaxErrorException{
        boolean executed = sql.isValidOrder();
        if (!executed)
            return executed;
            
        String sqlOrder = sql.getOrder();
        if (sqlOrder.equalsIgnoreCase(SQLCommand.CALL)) {
            
        }
        else if (sqlOrder.equalsIgnoreCase(SQLCommand.CREATE)) {
            
        }
        
        else if (sqlOrder.equalsIgnoreCase(SQLCommand.DELETE)) {
            
        }
        else if (sqlOrder.equalsIgnoreCase(SQLCommand.DESC) || 
                sqlOrder.equalsIgnoreCase(SQLCommand.DESCRIBE)) {
            
        }
        else if (sqlOrder.equalsIgnoreCase(SQLCommand.DROP)) {
            
        }
        else if (sqlOrder.equalsIgnoreCase(SQLCommand.INSERT)) {
            
        }
        else if (sqlOrder.equalsIgnoreCase(SQLCommand.SELECT)) {
            if(!sql.hasOptions()) 
                throw new SQLSyntaxErrorException("El comando select no tiene opciones");
            
            else if (sql.getOptionsCount() < 3) {
                
            }
            else{
                
            }
        }
        else if (sqlOrder.equalsIgnoreCase(SQLCommand.SHOW)) {
            
        }
        else if (sqlOrder.equalsIgnoreCase(SQLCommand.UPDATE)) {
            
        }
        else
            return false;
        
        return executed;
    }
    
}
