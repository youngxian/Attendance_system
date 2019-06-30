package student;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class enrollstudent {

    public Connection c;
    public Statement stmt;
    public PreparedStatement pt;
    public ResultSet rs;

    public enrollstudent(Connection c) {
        this.c = c;
    }
/// enroll student

    public void enrollstudes(String fname, String lname, String matno, String department, String level, byte[] digital, FileInputStream img) {

        if (check_matno(matno) == true) {
            try {
                String sql = "INSERT INTO `" + "Students" + "` (`id`, `First Name`, `Last Name`, `Matric Number`, `Department`, `Level`, `Image filepath`, `thumb`) VALUES (NULL, '" + fname + "', '" + lname + "', '" + matno + "', '" + department + "', '" + level + "', '" + img + "', '" + digital + "')";
                stmt = c.createStatement();
                stmt.executeUpdate(sql);
                //loadstudentsdata();
                //displayerror.setText("** You have been add **");

            } catch (SQLException es) {
                es.printStackTrace();
            }
        } else {

        }

    }

    // check if matric no exist
    public boolean check_matno(String mat_no) {
        Boolean check = false;

        try {
            String command = "SELECT * FROM Students WHERE `Matric Number` = ?";
            pt = c.prepareStatement(command);
            pt.setString(1, mat_no);

            rs = pt.executeQuery();

            if (rs.next()) {
                check = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }

    /// check if login username already exist before saving
    public Boolean checkusername(String uname) throws SQLException {
        Boolean check = false;

        String command = "SELECT * FROM User_login WHERE `Username` = ?";

        if (c.isValid(0)) {
            pt = c.prepareStatement(command);
            pt.setString(1, uname);
            rs = pt.executeQuery();

            while (rs.next()) {
                check = true;
            }

        } else {
            return check;
        }
        return check;
    }

    //// add user here;
    public Boolean adduser(String fname, String lname, String uname, String phoneno, String email, String password, String department, String categories, FileInputStream img, File image) throws SQLException {
        Boolean added = false;
        try {
            String sql = "INSERT INTO `" + "User_login" + "` (`First Name`, `Last Name`, `Username`, `Password`, `Status`, `Image filepath`) VALUES (?,?,?,?,?,?)";

            pt = c.prepareStatement(sql);
            pt.setString(1, fname);
            pt.setString(2, lname);
            pt.setString(3, uname);
            pt.setString(4, password);
            pt.setString(5, categories);
            pt.setBinaryStream(6, (InputStream) img, (int) (image.length()));
            pt.executeUpdate();
            added = true;

        } catch (SQLException es) {
            es.printStackTrace();
        }
        pt.close();

        try {
            String sql = "INSERT INTO `" + "Lecturers" + "` (`id`, `First Name`, `Last Name`, `Email`, `Department`, `Phone Number`, `100`, `200`, `300`, `400`) VALUES (NULL, '" + fname + "', '" + lname + "', '" + email + "', '" + department + "', '" + phoneno + "', '" + "nil" + "', '" + "nil" + "', '" + "nil" + "', '" + "nil" + "')";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);

            //loadstudentsdata();
            //displayerror.setText("** You have been add **");
        } catch (SQLException es) {
            es.printStackTrace();

        }

        return added;
    }

}
