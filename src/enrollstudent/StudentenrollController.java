package enrollstudent;

import biometric.fingerbiometric;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPFingerIndex;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.ResourceBundle;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import student.enrollstudent;
import student.getcourses;

/**
 * FXML Controller class
 *
 * @author DAVINCI
 */
public class StudentenrollController implements Initializable {

    ObservableList deptp = FXCollections.observableArrayList("Computer Science", "Information Technology", "Cyber Security", "Physics and Electronics");
    ObservableList level = FXCollections.observableArrayList("100", "200", "300", "400", "500");
    ObservableList catego = FXCollections.observableArrayList("Admin", "Lecturer");
    private ObservableList<getcourses> data;

    public Connection c;
    public String f_name;
    public String l_name;
    public String categories;
    public Image img;
    public File file;
    public PreparedStatement pt;
    public ResultSet rs;
    static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);

    @FXML
    private ImageView fingerprintimg;

    @FXML
    private Label messagelabel;

    @FXML
    private Button enrrolbtnn;

    @FXML
    private TextField fname;

    @FXML
    private TextField sname;

    @FXML
    private TextField matno;

    @FXML
    private Label fingernumber;

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
    private Button selectimg;

    @FXML
    private ComboBox<String> addusercati;

    @FXML
    private ComboBox<String> addusercati1;

    @FXML
    private VBox displayerrmess;

    @FXML
    private Label errormess;

    @FXML
    private TableView<getcourses> pretablecou;

    @FXML
    private TableColumn<getcourses, String> pretablecode;

    @FXML
    private TableColumn<getcourses, String> pretablebtn;

    @FXML
    private ComboBox<String> levelseccombo;

    @FXML
    private AnchorPane biometricscreen;

    @FXML
    private Label dispalydiv;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // set the thumbprint red image
            setfingerimg();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StudentenrollController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initVariable(Connection connect) {
        setcombo();
        this.c = connect;

    }
//// dashboard add user btn

    @FXML
    void usersbtnadd(ActionEvent event) {
        adduserscreen.toFront();

    }

    /// ssave user added data button
    @FXML
    void adduserbtn(ActionEvent event) {
        if (!fname.getText().isEmpty() && !sname.getText().isEmpty() && !matno.getText().isEmpty() && !selectdepart.getValue().isEmpty() && !selectlevel.getValue().isEmpty()) {

            System.out.println("field");

        } else {

            System.out.println("not - field");

        }
    }

    @FXML
    void useradderbtn(ActionEvent event) throws FileNotFoundException, SQLException {
        enrollstudent erro = new enrollstudent(this.c);
        if (!adduserp.getText().isEmpty() && !adduserphone.getText().isEmpty() && !adduser.getText().isEmpty() && !adduserf.getText().isEmpty() && !adduserl.getText().isEmpty() && !addemail.getText().isEmpty() && !addusercati.getValue().isEmpty() && !addusercati1.getValue().isEmpty()) {

            if (this.file.exists()) {

                if (erro.checkusername(adduser.getText()) == false) {
                    FileInputStream img = new FileInputStream(file);
                    Boolean aa = erro.adduser(adduserf.getText(), adduserl.getText(), adduser.getText(), adduserphone.getText(), addemail.getText(), adduserp.getText(), addusercati1.getValue().toString(), addusercati.getValue().toString(), img, file);
                    if (aa = true) {
                        errormessage("Added Successfully");
                    } else {
                        errormessage("Unable to add user");
                    }
                } else {
                    errormessage("Username exit already");
                }
            } else {
                errormessage("Choose an Image");
            }

        } else {

        }

    }

    @FXML
    void startfingerbtn(ActionEvent event) throws FileNotFoundException {
        fingernumber.setText("");
        Image imae = new Image(new FileInputStream("thumb.png"));
        fingerprintimg.setImage(imae);
        //StudentenrollController std = new StudentenrollController();
        //DPFPTemplate temp = std.getTemplate(null, 1);
        //byte[] b = temp.serialize();
        //temp.insert(1, b);
    }

    @FXML
    void takeattendancebtn(ActionEvent event) {

    }

//        addusers susy = new addusers(this.c);
//if (!adduserp.getText().isEmpty() && !adduserphone.getText().isEmpty() && !adduser.getText().isEmpty() && !adduserf.getText().isEmpty() && !adduserl.getText().isEmpty() && !addemail.getText().isEmpty() && !addusercati.getValue().isEmpty() && !addusercati1.getValue().isEmpty()) {
//   System.out.println("reach here");
//if (!adduser.getText().isEmpty()) {
//} else {
//   System.out.println("not here");
//    }
//} else {
//   System.out.println("ll - field");
// }
    @FXML
    void selectphotobtn(ActionEvent event) {

        setgroup();

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

    public void setfingerimg() throws FileNotFoundException {
        // /Users/JERRY/NetBeansProjects/biometricapp/src/img/thumb.png
        try {
            Image image = new Image(new FileInputStream("thumb.png"));
                           fingerprintimg.setImage(image);
            //fingerbiometric ffif = new fingerbiometric();
            //Boolean present = ffif.listReaders();

//            if (present) {
//                fingerprintimg.setImage(image);
//                fingernumber.setText("");
//                dispalydiv.setText("DigitalPersona device is Connected");
//            } else {
//                fingernumber.setText("");
//                Image imae = new Image(new FileInputStream("device.png"));
//                fingerprintimg.setImage(imae);
//                dispalydiv.setText("Device disconnected");
//
//            }
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
        levelseccombo.setItems(level);

    }

    public void setgroup() {
        FileChooser dirchooser = new FileChooser();
        dirchooser.setTitle("Select Your Profile Image");

        dirchooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image file", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        Stage stage = (Stage) adduserscreen.getScene().getWindow();
        this.file = dirchooser.showOpenDialog(stage);
        if (file != null) {

            //selectimg.
            //
            Image ima = new Image(file.toURI().toString());
            adduserimg.setImage(ima);

        } else {
            ////set default path
            System.out.println("No image was selected");
            adduserimg.setImage(null);
            errormessage("Select an Image");
            // img_warning.setText("*No Image Selected*");
        }
    }

    ////Error display function
    public void errormessage(String err_message) {
        //error_display
        //error_text
        String l = "Update Successfull";
        String p = "Save Successfull";
        String s = "Added Successfully";
        if (err_message == l || err_message == p || err_message == s) {
            errormess.setText(err_message);
            errormess.setStyle("-fx-text-fill: white;");
            displayerrmess.setStyle("-fx-background-color: #00875E; -fx-background-radius: 5 5 5 5;");
            //displayerrmess.toFront();
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    errormess.setText("");
                    errormess.setStyle("-fx-text-fill: black;");
                    displayerrmess.setStyle("-fx-background-color: black; -fx-background-radius: 5 5 5 5;");
                }

            }));

            timeline.play();
        } else {
            errormess.setText(err_message);
            errormess.setStyle("-fx-text-fill: white;");
            displayerrmess.setStyle("-fx-background-color: #E3311C; -fx-background-radius: 5 5 5 5;");

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    errormess.setText("");
                    errormess.setStyle("-fx-text-fill: black;");
                    displayerrmess.setStyle("-fx-background-color: black; -fx-background-radius: 5 5 5 5;");
                }

            }));

            timeline.play();
        }
    }

    /// table for adding courses in dashboard preview
    public void selectcourse(String level) {
        try {
            String sql = "SELECT " + level + " FROM Courses";

            this.data = FXCollections.observableArrayList();
            Statement st = this.c.createStatement();
            rs = st.executeQuery(sql);
            // ResultSet rx = this.c.createStatement().executeQuery(sql);

            while (rs.next()) {
                this.data.add(new getcourses(rs.getString(level)));

            }
        } catch (SQLException ex) {
            Logger.getLogger(getcourses.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.pretablecode.setCellValueFactory(new PropertyValueFactory<getcourses, String>("level"));
        this.pretablecou.setItems(null);
        this.pretablecou.setItems(data);
    }

    public static final EnumMap<DPFPFingerIndex, String> fingerNames;

    static {
        fingerNames = new EnumMap<DPFPFingerIndex, String>(DPFPFingerIndex.class);
        fingerNames.put(DPFPFingerIndex.LEFT_THUMB, "left thumb");
        fingerNames.put(DPFPFingerIndex.RIGHT_THUMB, "right thumb");
    }

    public DPFPTemplate getTemplate(String activeReader, int nFinger) throws FileNotFoundException {
        System.out.printf("Performing fingerprint enrollment...\n");
        messagelabel.setText("Place your Left thumb");

        DPFPTemplate template = null;
        try {
            DPFPFingerIndex finger = DPFPFingerIndex.values()[nFinger];
            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
            DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();
            while (enrollment.getFeaturesNeeded() > 0) {
                DPFPSample sample = getSample(activeReader,
                        String.format("Scan your %s finger (%d remaining)\n", fingerName(finger), enrollment.getFeaturesNeeded()));
                messagelabel.setText("Scanning thumb 4 times");
                fingernumber.setText(fingerName(finger));
                enrrolbtnn.setDisable(true);
                if (sample == null) {
                    continue;
                }
                DPFPFeatureSet featureSet;

                try {

                    featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

                } catch (DPFPImageQualityException e) {

                    System.out.printf("Bad image quality: \"%s\". Try again. \n", e.getCaptureFeedback().toString());

                    continue;

                }

                enrollment.addFeatures(featureSet);

            }

            template = enrollment.getTemplate();

            System.out.printf("The %s was enrolled.\n", fingerprintName(finger));
            enrrolbtnn.setDisable(false);
            messagelabel.setText("Enrollment Completed");
            fingernumber.setText("");

        } catch (DPFPImageQualityException e) {
            enrrolbtnn.setDisable(false);
            messagelabel.setText("Check if device connected");
            Image image = new Image(new FileInputStream("device.png"));
            fingerprintimg.setImage(image);
            System.out.printf("Failed to enroll the finger.\n");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return template;
    }

    public DPFPSample getSample(String activeReader, String prompt)
            throws InterruptedException {
        final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>();
        DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
        capture.setReaderSerialNumber(activeReader);
        capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
        capture.addDataListener(new DPFPDataListener() {
            public void dataAcquired(DPFPDataEvent e) {
                if (e != null && e.getSample() != null) {
                    try {
                        samples.put(e.getSample());
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        capture.addReaderStatusListener(new DPFPReaderStatusAdapter() {
            int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;

            public void readerConnected(DPFPReaderStatusEvent e) {
                if (lastStatus != e.getReaderStatus()) {
                    System.out.println("Reader is connected");
                }
                lastStatus = e.getReaderStatus();
            }

            public void readerDisconnected(DPFPReaderStatusEvent e) {
                if (lastStatus != e.getReaderStatus()) {
                    System.out.println("Reader is disconnected");
                }
                lastStatus = e.getReaderStatus();
            }
        });

        try {
            capture.startCapture();
            System.out.print(prompt);
            return samples.take();
        } catch (RuntimeException e) {
            System.out.printf("Failed to start capture. Check that reader is not used by another application.\n");
            throw e;
        } finally {
            capture.stopCapture();
        }
    }

    public String fingerName(DPFPFingerIndex finger) {
        return fingerNames.get(finger);
    }

    public String fingerprintName(DPFPFingerIndex finger) {
        return fingerNames.get(finger) + " fingerprint";
    }
}
