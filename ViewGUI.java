import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
        String newNote = noteText.getText();

        if (event.getSource() == saveBtn) {
            if (newNote.isBlank()) {
                JOptionPane.showMessageDialog(new JFrame(), "You can't save an empty note!",
                        "Warning", JOptionPane.ERROR_MESSAGE);
                noteText.setText("");
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
            new AllNotesGUI();
        }
    }
}
