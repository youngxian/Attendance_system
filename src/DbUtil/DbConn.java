
package DbUtil;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;


public class DbConn {
    public Connection db;
    Statement statement;
    public String ip;
    public String database;
    public String login;
    public String pass;
    
    public DbConn(String ipaddress, String databasename, String login, String pass){
    
        this.database = databasename;
        this.ip = ipaddress;
        this.login = login;
        this.pass = pass;
}
    
    public void initDB(String ipaddress, String databasename, String login, String pass) {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      db = (Connection) DriverManager.getConnection("jdbc:mysql://" + ipaddress + "/" + databasename, login, pass);
      statement = (Statement) db.createStatement();
      
    } catch (Exception e) {
      System.out.println("Could not initialize the database.");
      e.printStackTrace();
    }
    
  }
}
