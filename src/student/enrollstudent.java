package student;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class enrollstudent {

    Connection c;
    Statement stmt;
    PreparedStatement pt;
    ResultSet rs;

    public enrollstudent(Connection c) {
        this.c = c;
    }
/// enroll student
    public void enrollstudents(String fname, String lname, String matno, String department, String level, byte[] digital, FileInputStream img) {

        if(check_matno(matno) == true){
            try {
            String sql = "INSERT INTO `" + "Students" + "` (`id`, `First Name`, `Last Name`, `Matric Number`, `Department`, `Level`, `Image filepath`, `thumb`) VALUES (NULL, '" + fname + "', '" + lname + "', '" + matno + "', '" + department + "', '" + level + "', '" + img + "', '" + digital + "')";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            //loadstudentsdata();
            //displayerror.setText("** You have been add **");

        } catch (SQLException es) {
            es.printStackTrace();
        }
        }else{
            
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
    public Boolean checkusername(String uname){
        Boolean check = false;

        try {
            String command = "SELECT * FROM User_login WHERE `Username` = ?";
            pt = c.prepareStatement(command);
            pt.setString(1, uname);

            rs = pt.executeQuery();

            if (rs.next()) {
                check = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return check;
    }
    //// add user here;
    public void adduser(String fname, String lname, String uname, String phoneno, String email, String password, String department, String categories, FileInputStream img) throws SQLException{
        
        try {
            String sql = "INSERT INTO `" + "User_login" + "` (`id`, `First Name`, `Last Name`, `Username`, `Password`, `Status`, `Image filepath`) VALUES (NULL, '" + fname + "', '" + lname + "', '" + uname + "', '" + password + "', '" + categories + "', '" + img +"')";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            //loadstudentsdata();
            //displayerror.setText("** You have been add **");

        } catch (SQLException es) {
            es.printStackTrace();
        }
        stmt.close();
        
        try {
            String sql = "INSERT INTO `" + "Lecturers" + "` (`id`, `First Name`, `Last Name`, `Email`, `Department`, `Phone Number`) VALUES (NULL, '" + fname + "', '" + lname + "', '" + email + "', '" + department + "', '" + phoneno +"')";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            //loadstudentsdata();
            //displayerror.setText("** You have been add **");

        } catch (SQLException es) {
            es.printStackTrace();
        
        
        }
        
    }

}
