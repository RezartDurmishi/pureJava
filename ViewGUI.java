import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ViewGUI extends JFrame {

    public static String dbms = "mysql";

    public static void main(String[] args) throws SQLException {
        JFrame frame;
        JTextField noteTF;
        JButton saveBtn;

        frame = new JFrame();
        frame.setBounds(100, 100, 570, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel addressLbl = new JLabel("Note:");
        addressLbl.setBounds(83, 97, 51, 16);
        frame.getContentPane().add(addressLbl);

        noteTF = new JTextField();
        noteTF.setBounds(141, 94, 376, 22);
        noteTF.setColumns(10);
        frame.getContentPane().add(noteTF);

        saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
        saveBtn.setBounds(200, 208, 166, 25);
        frame.getContentPane().add(saveBtn);

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Add new note");
        frame.setVisible(true);

        Connection connection = getConnection();
    }

    static Connection getConnection() throws SQLException {

        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", "root");
        connectionProps.put("password", "");
//        DriverManager.registerDriver(new com.mysql.jdbc.Driver ());


        if (dbms.equals("mysql")) {
            conn = DriverManager.getConnection(
                    "jdbc:" + dbms + "://" +
                            "localhost" +
                            ":" + "3306" + "/",
                    connectionProps);
        } else if (dbms.equals("derby")) {
            conn = DriverManager.getConnection(
                    "jdbc:" + dbms + ":" +
                            "testDatabase" +
                            ";create=true",
                    connectionProps);
        }
        System.out.println("Connected to database");
        return conn;
    }
}
