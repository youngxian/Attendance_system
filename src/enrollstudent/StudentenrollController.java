package enrollstudent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author DAVINCI
 */
public class StudentenrollController implements Initializable {

    ObservableList deptp = FXCollections.observableArrayList("Computer Science", "Information Technology", "Cyber Security", "Physics and Electronics");
    ObservableList level = FXCollections.observableArrayList("100", "200", "300", "400", "500");
    ObservableList catego = FXCollections.observableArrayList("Admin", "Lecturer");
    public Connection c;
    public String f_name;
    public String l_name;
    public String categories;
    public Image img;

    @FXML
    private ImageView fingerprintimg;

    @FXML
    private Label messagelabel;

    @FXML
    private TextField fname;

    @FXML
    private TextField sname;

    @FXML
    private TextField matno;

    @FXML
    private ComboBox<String> selectdepart;

    @FXML
    private ComboBox<String> selectlevel;

    @FXML
    private AnchorPane adduserscreen;

    @FXML
    private ImageView adduserimg;

    @FXML
    private TextField adduserf;

    @FXML
    private TextField adduserl;

    @FXML
    private TextField addemail;

    @FXML
    private TextField adduserphone;

    @FXML
    private TextField adduser;

    @FXML
    private TextField adduserp;

    @FXML
    private TextField adduserc;

    @FXML
    private ComboBox<String> addusercati;

    @FXML
    private ComboBox<String> addusercati1;

    //// dashboard add user btn
    
        @FXML
    void usersbtnadd(ActionEvent event) {
        adduserscreen.toFront();
        
    }
    
    /// ssave user added data button
    
    @FXML
    void adduserbtn(ActionEvent event) {
            if(!fname.getText().isEmpty() && !sname.getText().isEmpty() && !matno.getText().isEmpty() && !selectdepart.getValue().isEmpty() && !selectlevel.getValue().isEmpty()){
                System.out.println("field");
                
                
            }else{
                System.out.println("not - field");
                
            }
        
        
    }
    
    
    @FXML
    void enrollsubmitbtn(ActionEvent event) {
            if(!adduserp.getText().isEmpty() && !adduserphone.getText().isEmpty() && !adduser.getText().isEmpty() && !adduserf.getText().isEmpty() && !adduserl.getText().isEmpty() && !addemail.getText().isEmpty() && !addusercati.getValue().isEmpty() && !addusercati1.getValue().isEmpty()){
                System.out.println("present");
                
                
            }else{
                System.out.println("ll - field");
                
            }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        // set the thumbprint red image
        setfingerimg();
    }

    public void initVariable(Connection connect) {
        setcombo();
        this.c = c;

    }

    public void loginname(String fname, String lname, InputStream imgfilepath, String categories) throws FileNotFoundException, IOException {
      
       
            this.f_name = fname;
            this.l_name = lname;
            this.categories = categories;
            OutputStream fout = new FileOutputStream(new File(fname + ".jpg"));
            byte[] content = new byte[1024];
            int size = 0;

            while ((size = imgfilepath.read(content)) != -1) {

                fout.write(content, 0, size);
            }

            fout.close();
            imgfilepath.close();
            Image ima = new Image(new File(fname + ".jpg").toURI().toString());
            this.img = ima;

       
    }

    public void setfingerimg() {
       // /Users/JERRY/NetBeansProjects/biometricapp/src/img/thumb.png
        try {
            
            Image image = new Image(new FileInputStream("/Users/JERRY/NetBeansProjects/biometricapp/src/img/thumb.png"));
            fingerprintimg.setImage(image);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentenrollController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /// set the details in combo box
    public void setcombo() {
        selectdepart.setItems(deptp);
        selectlevel.setItems(level);
        addusercati.setItems(catego);
        addusercati1.setItems(deptp);

    }

}
