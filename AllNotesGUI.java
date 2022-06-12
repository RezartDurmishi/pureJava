import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class AllNotesGUI extends JFrame {
    private static Driver driver = new Driver();

    AllNotesGUI() {
        String getAllQuery = "select * from notes";
        List<String> allNotes = null;
        try {
            allNotes = driver.executeQuery(getAllQuery, "select");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final Object[][] rowData = {};
        final Object[] columnNames = {"Notes"};

        DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);
        allNotes.forEach(note -> {
            tableModel.addRow(new Object[]{note});
        });

        // Frame initialization
        JFrame frame = new JFrame();

        // Initializing the JTable
        JTable table = new JTable(tableModel);

//        table.getColumn("Modify").setMaxWidth(70);
        table.setBounds(30, 40, 200, 300);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        // adding it to JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        // Frame Size
        frame.setSize(500, 300);

        // Frame Visible = true
        frame.setVisible(true);
    }
}