
package Authentication;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


public class auth {
    
    String username;
    String password;
    public final Connection c;
    PreparedStatement pt;
    ResultSet rs;
    public InputStream  img;
    
    HashMap<String, String> user_info;
    public auth(Connection c){
        this.c = c;
        
    }
    
    public HashMap validate(String uname, String p) throws SQLException{
        try {
            user_info = new HashMap<String, String>();
            if(this.c.isValid(0)){
                    System.out.println("dadada " + c.isValid(0));
                    String sql = "SELECT * FROM User_login WHERE Username = ? AND Password = ?";
                    pt = this.c.prepareStatement(sql);
                    pt.setString(1, uname);
                    pt.setString(2, p);
                    rs = pt.executeQuery();
                    
                    while (rs.next()) {
                        user_info.put("f_name", rs.getString("First Name").substring(0,1).toUpperCase());
                        user_info.put("l_name", rs.getString("Last Name").substring(0,1).toUpperCase());
                        user_info.put("categories", rs.getString("Status").substring(0,1).toUpperCase());
                        //user_info.put("img", rs.get("Image filepath"));
                        img= rs.getBinaryStream("Image filepath");
                    }
                }else{
                        return user_info;     
            }   
        } catch (SQLException ex) {
            Logger.getLogger(auth.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return user_info; 
    }
}
