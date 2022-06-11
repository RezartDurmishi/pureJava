import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ViewGUI extends JFrame implements ActionListener {

    private static final String post = "post";
    private static final String get = "get";
    private static String newNote = "";
    private static Driver driver = new Driver();
    private static JTextField noteTextField = new JTextField();
    private static JButton saveBtn;
    private static JButton viewAllBtn;

    public ViewGUI() {
        JFrame frame;

        frame = new JFrame();
        frame.setBounds(100, 100, 570, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setBounds(83, 97, 51, 16);
        frame.getContentPane().add(noteLabel);

        noteTextField.setBounds(141, 94, 376, 22);
        noteTextField.setColumns(10);
        frame.getContentPane().add(noteTextField);

        saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("Helvetica", Font.BOLD, 13));
        saveBtn.setBounds(150, 208, 120, 25);
        frame.getContentPane().add(saveBtn);
        //if noteTextField = "" , return Dialog "You can't save an empty note."
        saveBtn.addActionListener(this);

        viewAllBtn = new JButton("View All");
        viewAllBtn.setFont(new Font("Helvetica", Font.BOLD, 13));
        viewAllBtn.setBounds(300, 208, 120, 25);
        frame.getContentPane().add(viewAllBtn);
        viewAllBtn.addActionListener(this);

        frame.setResizable(false);
        frame.setTitle("Add new note");
//        frame.getContentPane().setBackground(new Color(243, 161, 56));
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ViewGUI();
    }

    /*
     * On button click
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == saveBtn) {
            newNote = noteTextField.getText();
            String createQuery = "insert into notes (note) values('" + newNote + "')";
            try {
                driver.executeQuery(createQuery, post);
                noteTextField.setText("");
                // add dialog box  "Note saved";
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (event.getSource() == viewAllBtn) {
            String getAllQuery = "select * from notes";
            try {
                List<String> allNotes = driver.executeQuery(getAllQuery, get);
                final Object[][] rowData = {};
                final Object[] columnNames = {"Notes"};

                DefaultTableModel listTableModel = new DefaultTableModel(rowData, columnNames);
                allNotes.forEach(note->{
                    listTableModel.addRow(new Object[]{note});
                });

                JFrame frame;
                // Table
                JTable table;

                // Frame initialization
                frame = new JFrame();
//                frame.getContentPane().setBackground(new Color(243, 161, 56));

                // Frame Title
                frame.setTitle("Notes");

                // Initializing the JTable
                table = new JTable(listTableModel);
                table.setBounds(30, 40, 200, 300);

                // adding it to JScrollPane
                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);
                // Frame Size
                frame.setSize(500, 200);
                // Frame Visible = true
                frame.setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
