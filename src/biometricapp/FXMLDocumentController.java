/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biometricapp;

import Authentication.auth;
import DbUtil.DbConn;
import enrollstudent.StudentenrollController;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//String ipaddress, String databasename, String login, String pass
public class FXMLDocumentController implements Initializable {

    public Connection c;
    DbConn db;
    public String ipaddres;
    public String dbase;
    public String dbusername;
    public String pass;
    
        @FXML
    private VBox settingsscreen;

    @FXML
    private TextField ipaddress;

    @FXML
    private TextField databasename;

    @FXML
    private TextField dbuser;

    @FXML
    private TextField dbpass;

    @FXML
    private VBox loginscreen;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label messlabel;
    
    @FXML
    private Label logmess;
    
    @FXML
    private Label dbmessage;
    
    
    //// Athetication class added here
    
    HashMap<String, String> user_details = new HashMap<String, String>();
    
    /// login button

    @FXML
    void loginbtn(ActionEvent event) throws SQLException {
        auth auth = new auth(dbconn());
          user_details = auth.validate(username.getText().toLowerCase(), password.getText().toLowerCase());
        if(!user_details.isEmpty()){
               
            loadscene(c, user_details.get("f_name"), user_details.get("l_name"), user_details.get("categories"), auth.img);
            
            
        }else{
            logmess.setText("* Invalid username/password *");
        }

            
    }

    @FXML
    void submitbtn(ActionEvent event) {
        
        try {
            if(dbconn().isValid(0)){
                
                loginscreen.toFront();
                messlabel.setText("Database Connected");
                System.out.println("Database Connected");
            }else{
                messlabel.setText("* Unable to connect to Database *");
                System.out.println("not-connected");
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
//System.out.println("clicked");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    //dbconn();
    }

    public Connection dbconn() {
        
        try {
            db = new DbConn(ipaddres, dbase, dbusername, pass);
            
            ipaddres = ipaddress.getText().toString();
            dbase = databasename.getText().toString();
            dbusername = dbuser.getText().toString();
            pass = dbpass.getText().toString();
            
            db.initDB (ipaddres, dbase, dbusername, pass);
            this.c = db.db;
            if(c.isValid(0)){
                return c;
            }else{
                return c;
            }   } catch (SQLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            return c;
        }
        
}
    public void loadscene(Connection connect, String fname, String lname, String categories, InputStream imgfilepath){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/enrollstudent/studentenroll.fxml"));
            Parent parent = loader.load();
            StudentenrollController dashboardcontroller = loader.<StudentenrollController>getController();
            dashboardcontroller.initVariable(connect);
            //adjusted here , imgfilepath
            dashboardcontroller.loginname(fname, lname, imgfilepath, categories);
            Scene scene = new Scene(parent);
            //scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            //stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(true);
            stage.setTitle("");
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}

