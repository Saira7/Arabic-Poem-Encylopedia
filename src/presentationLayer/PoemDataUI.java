package presentationLayer;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import businessLayer.BLLFacade;
import businessLayer.IBLLFacade;
import transferObject.MisraTO;
import transferObject.PoemTO;
import transferObject.VerseTO;

public class PoemDataUI extends JFrame {
	
    //Iteration 1 _________________________________________________

    private DefaultTableModel tableModel;
    private JTable poemTable;
    private JTextField poemTitleField;
    private JTextField misraField1;
    private JTextField misraField2;
    private int verseCount;
    private JButton saveButton;
	private JPanel contentPane;
    private String index;
    private IBLLFacade poemFacadeBLL; //Facade Object
    private JButton backButton;

    public PoemDataUI(String index) throws SQLException {
    	this.index = index;
        tableModel = new DefaultTableModel(new String[]{"Verse", "Title", "Misra #1", "Misra #2"}, 0);
        poemTable = new JTable(tableModel);
        poemTitleField = new JTextField(20);
        misraField1 = new JTextField(20);
        misraField2 = new JTextField(20);
        verseCount = 1;
        saveButton = new JButton("Save");
        poemFacadeBLL = new BLLFacade();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPoem();
            }
        });
       
    	
    	
       
        JScrollPane scrollPane = new JScrollPane(poemTable);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Tahoma", Font.BOLD, 14));
    	backButton.setBackground(UIManager.getColor("activeCaption"));
        getContentPane().add(backButton, BorderLayout.NORTH);
        backButton.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        try {
    	        	//int parsedBookID = Integer.valueOf(index);
    	            ViewSingleBook viewSingleBook = new ViewSingleBook(String.valueOf(index), poemFacadeBLL);
    	            dispose();
    	            viewSingleBook.show();
    	        } catch (SQLException e1) {
    	            e1.printStackTrace();
    	        }
    	    }
    	});
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Poem Title: "));
        inputPanel.add(poemTitleField);
        inputPanel.add(new JLabel("Misra #1: "));
        inputPanel.add(misraField1);
        inputPanel.add(new JLabel("Misra #2: "));
        inputPanel.add(misraField2);
        inputPanel.add(saveButton);

        getContentPane().add(inputPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void displayPoem() {
        String poemTitle = poemTitleField.getText();
        String misra1 = misraField1.getText();
        String misra2 = misraField2.getText();

        if (poemTitle.isEmpty() && misra1.isEmpty() && misra2.isEmpty()) {
        	JOptionPane.showMessageDialog(contentPane, "Operation Unseccessful", "Error",
					JOptionPane.ERROR_MESSAGE);
        }
        
        PoemTO poem = new PoemTO();
        poem.setPoemTitle(poemTitle);

        VerseTO verse = new VerseTO();
        verse.setVerseId(verseCount);
       
        MisraTO misra = new MisraTO();
        misra.setPoemId(verseCount);
        misra.setMisra1(misra1);
        misra.setMisra2(misra2);
        
        try {
            if (!poemTitle.isEmpty()) {
            	
            	 verse.setNoOfVerses(1);
                poemFacadeBLL.savePoem_(poem, verse, misra,index);
                
                // Reset verseCount when a new poem title is entered
                verseCount = 1;
                
            } else {
                // Increment verseCount if no new poem title is entered
            	 verse.setNoOfVerses(0);
                verseCount++;
                poemFacadeBLL.savePoem_(poem, verse, misra,index);
            }

            Object[] rowData = {verseCount, poemTitle, misra1, misra2};
            tableModel.addRow(rowData);

            poemTitleField.setText("");
            misraField1.setText("");
            misraField2.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                try {
//					new PoemDataUI();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            }
//        });
//    }
}
