import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ViewGUI extends JFrame implements ActionListener {
    private static JTextArea noteText;
    private static JButton saveBtn;
    private static JButton viewAllBtn;
    private static JFrame frame;

    public static void main(String[] args) {
        new ViewGUI();
    }

    public ViewGUI() {
        frame = new JFrame();
        frame.setBounds(100, 100, 630, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setBounds(60, 97, 51, 16);
        frame.getContentPane().add(noteLabel);

        noteText = new JTextArea();
        noteText.setBounds(130, 54, 376, 122);
        noteText.setColumns(10);
        frame.getContentPane().add(noteText);

        saveBtn = new JButton("Save");
        saveBtn.setFont(new Font("Helvetica", Font.BOLD, 13));
        saveBtn.setBounds(180, 208, 120, 25);
        saveBtn.setBackground(Color.GREEN);
        frame.getContentPane().add(saveBtn);
        saveBtn.addActionListener(this);

        viewAllBtn = new JButton("View All");
        viewAllBtn.setFont(new Font("Helvetica", Font.BOLD, 13));
        viewAllBtn.setBounds(340, 208, 120, 25);
        frame.getContentPane().add(viewAllBtn);
        viewAllBtn.addActionListener(this);

        frame.setResizable(false);
        frame.setTitle("Add new note");
        frame.getContentPane().setBackground(new Color(0, 153, 204));
        frame.setVisible(true);
    }

    /*
     * On button click
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Driver driver = new Driver();
        if (event.getSource() == saveBtn) {
            String newNote = noteText.getText();
            if (newNote.equals("")) {
                JOptionPane.showMessageDialog(new JFrame(), "You can't save an empty note!",
                        "Warning", JOptionPane.ERROR_MESSAGE);
                return;
            }
            newNote = newNote.replaceAll("'", "''");  //todo: use of regex
            String createQuery = "insert into notes (note) values(' " + newNote + " ')";  //todo: string concatenation
            try {
                driver.executeQuery(createQuery, "insert");
                noteText.setText("");
                JOptionPane.showMessageDialog(frame, "Note saved.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (event.getSource() == viewAllBtn) {
            String getAllQuery = "select * from notes";
            try {
                List<String> allNotes = driver.executeQuery(getAllQuery, "select");
                final Object[][] rowData = {};
                final Object[] columnNames = {"Notes"};

                DefaultTableModel listTableModel = new DefaultTableModel(rowData, columnNames);
                allNotes.forEach(note -> {
                    listTableModel.addRow(new Object[]{note});
                });

                JFrame frame;
                // Table
                JTable table;

                // Frame initialization
                frame = new JFrame();

                // Initializing the JTable
                table = new JTable(listTableModel);
                table.setBounds(30, 40, 200, 300);

                // adding it to JScrollPane
                JScrollPane scrollPane = new JScrollPane(table);
                frame.add(scrollPane);

                // Frame Size
                frame.setSize(500, 300);

                // Frame Visible = true
                frame.setVisible(true);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
