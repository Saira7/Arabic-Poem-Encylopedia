package presentationLayer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import businessLayer.BLLFacade;
import businessLayer.IBLLFacade;

public class PoemFileUI extends JFrame {
	
    private DefaultTableModel tableModel;
    private JTable poemTable;
    private JButton openButton;
    private JButton submitButton;

    private IBLLFacade poemFile_FacadeBLL; // Facade Business Layer instance

    public PoemFileUI(int index) throws SQLException {
    	
    	JButton btnBack = new JButton("Back");
    	btnBack.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        try {
    	        	int parsedBookID = Integer.valueOf(index);
    	            ViewSingleBook viewSingleBook = new ViewSingleBook(String.valueOf(index), poemFile_FacadeBLL);
    	            dispose();
    	            viewSingleBook.show();
    	        } catch (SQLException e1) {
    	            e1.printStackTrace();
    	        }
    	    }
    	});
    	btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
    	btnBack.setBackground(UIManager.getColor("activeCaption"));
    	
    	    
        tableModel = new DefaultTableModel(new String[]{"Verse", "Title", "Misra #1", "Misra #2"}, 0);
        poemTable = new JTable(tableModel);
        openButton = new JButton("Open File");
        submitButton = new JButton("Submit");

        poemFile_FacadeBLL = new BLLFacade(); // Create Business Layer instance

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    tableModel = poemFile_FacadeBLL.parseCSV_(filePath); // Use Business Layer
                    poemTable.setModel(tableModel);
                    submitButton.setEnabled(true);
                    poemFile_FacadeBLL.setTableModel_(tableModel);
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableModel != null) {
                	try {
						poemFile_FacadeBLL.submitPoemDataToDatabase_(index);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} // Use Business Layer
                    tableModel.setRowCount(0);
                    submitButton.setEnabled(false);
                }
            }
        });

        submitButton.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(poemTable);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnBack);
        buttonPanel.add(openButton);
        buttonPanel.add(submitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    
    }
}
