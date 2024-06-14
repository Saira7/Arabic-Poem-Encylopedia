package presentationLayer;


import businessLayer.IBLLFacade;
import businessLayer.BLLFacade;
import javax.swing.*;

import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AssignRootUI extends JFrame {
    private JPanel contentPane;
    private JTextArea textAreaRoots;
    private JComboBox<String> comboBoxStemmedRoots;
    private JButton btnSave;
    private String Id;
    private IBLLFacade rootBLL;
    private String VerseID;
    public AssignRootUI(String Id, String VerseID, String Verse, String bookID, IBLLFacade poemFacadeBLL) throws SQLException {
    	setTitle("Manual Assigning of Roots");
        rootBLL = poemFacadeBLL;
        this.Id=Id;
        this.VerseID=VerseID;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 525, 400);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        

        textAreaRoots = new JTextArea();  
        textAreaRoots.setBounds(228, 108, 200, 150);  
        contentPane.add(textAreaRoots);

       
        
        JLabel lblVersesForRoot = new JLabel(Verse);
        lblVersesForRoot.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblVersesForRoot.setBounds(166, 47, 400, 50);
        contentPane.add(lblVersesForRoot);

        comboBoxStemmedRoots = new JComboBox<>();
        comboBoxStemmedRoots.setBounds(54, 164, 150, 20);

        populateStemmedRoots();
        contentPane.add(comboBoxStemmedRoots);

        btnSave = new JButton("Save");
        btnSave.setBounds(294, 296, 80, 20);
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveRoot();
            }
        });
        comboBoxStemmedRoots.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSelectedRootText();
            }

			private void updateSelectedRootText() {
				 String selectedRoot = (String) comboBoxStemmedRoots.getSelectedItem();
			        textAreaRoots.append(selectedRoot+"\n");
				
			}
        });
        contentPane.add(btnSave);
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewSinglePoem poemUI;
                try {System.out.println("BackButton: "+Id);
                    poemUI = new  ViewSinglePoem(Id, bookID, rootBLL);
                    
                    dispose();
                    poemUI.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.setBackground(UIManager.getColor("activeCaption"));
        btnBack.setBounds(21, 39, 70, 30);
        contentPane.add(btnBack);
    }

    private void populateStemmedRoots() {
        // Populate the combo box with stemmed roots based on the selected verse
            List<String> stemmedRoots = rootBLL.getStemmedRootsForVerse(VerseID);
           
            System.out.println("Stemmed Roots size: " + stemmedRoots.size());
           
            for (String stemmedRoot : stemmedRoots) {
                comboBoxStemmedRoots.addItem(stemmedRoot);
            }
        
    }
    

    private void saveRoot() {
    	  String rootsText = textAreaRoots.getText();
          List<String> rootsList = Arrays.asList(rootsText.split("\n"));

          for (String root : rootsList) {
        	  if(root!= null) {
              rootBLL.saveRoot(root, VerseID);}
          }

          JOptionPane.showMessageDialog(contentPane, "Roots saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
          clearFields();
      
    }

    private void clearFields() {
    	textAreaRoots.setText("");
        comboBoxStemmedRoots.setSelectedIndex(0);
    }

   

  
}

