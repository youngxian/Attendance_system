
package student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class addusers {
    public Connection c;
    public PreparedStatement pt;
    public ResultSet rs;
    
    public addusers(Connection c){
        this.c = c;
    }
    public void add(String uname){
        try {
            String sql = "SELECT * FROM User_login WHERE `First Name` = ? AND `Last Name` = ?";
            pt = c.prepareStatement(sql);
            pt.setString(1, uname);
            rs = pt.executeQuery();
            while (rs.next()) {
                
                System.out.println(rs.getString("Password"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(addusers.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
