package presentationLayer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import businessLayer.IBLLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class VersesForRootUI extends JFrame {
    private JPanel contentPane;
    private JTable versesTable;
    private DefaultTableModel tableModel;

    private IBLLFacade rootBLL;
    private String selectedRoot;

    public VersesForRootUI(IBLLFacade rootBLL, String selectedRoot) {
        this.rootBLL = rootBLL;
        this.selectedRoot = selectedRoot;

        setTitle("Verses for Root");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RootUI rootUI;
                try {
                    rootUI = new RootUI();
                    dispose();
                    rootUI.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnBack.setBackground(UIManager.getColor("activeCaption"));
        btnBack.setBounds(10, 10, 70, 30);
        contentPane.add(btnBack);

        JLabel lblVersesForRoot = new JLabel(selectedRoot);
        lblVersesForRoot.setFont(new Font("Times New Roman", Font.BOLD, 16));
        lblVersesForRoot.setBounds(300, 10, 400, 50);
        contentPane.add(lblVersesForRoot);

        // Define the columns for the table
        String[] columns = { "SR. NO.", "Misra1", "Misra2","Status" };

        // Create a DefaultTableModel
        tableModel = new DefaultTableModel(columns, 0);

        // Create a JTable with the DefaultTableModel
        versesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(versesTable);
        scrollPane.setBounds(50, 70, 500, 200);
        contentPane.add(scrollPane);

        // Add an action listener to the table to get the selected verse and display the poem
        JButton  viewPoemButton=new JButton();
        viewPoemButton = new JButton("View Poem");
		viewPoemButton.setBounds(150, 300, 100, 30);
		//viewVersesButton.setBackground(SystemColor.inactiveCaption);
		viewPoemButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(viewPoemButton);
		
		JButton  verifyRootsButton=new JButton();
        verifyRootsButton = new JButton("Verify Root");
		verifyRootsButton.setBounds(300, 300, 100, 30);
		//viewVersesButton.setBackground(SystemColor.inactiveCaption);
		verifyRootsButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(verifyRootsButton);
		viewPoemButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = versesTable.getSelectedRow();
		        if (selectedRow != -1) {
		            String selectedVerse = rootBLL.getPoemIdFromVerse((String) versesTable.getValueAt(selectedRow, 1));
                     try {
		                ViewSinglePoem poemScreen = new ViewSinglePoem(selectedVerse, rootBLL,selectedRoot);
		                dispose();
		                poemScreen.setVisible(true);
		            } catch (SQLException e1) {
		                e1.printStackTrace();
		            }
		        } else {
		            JOptionPane.showMessageDialog(contentPane, "Please select a root.", "No Root Selected",
		                    JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		verifyRootsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = versesTable.getSelectedRow();
				if (selectedRow != -1) {
					String selectedVerse = (String) tableModel.getValueAt(selectedRow, 1);

					// Verify the roots associated with the selected root
					rootBLL.verifyRoots(selectedVerse);

					// Update the table with the verified status
					updateVerificationStatus(selectedVerse);
				} else {
					JOptionPane.showMessageDialog(contentPane, "Please select a root to verify.", "No Root Selected",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			private void updateVerificationStatus(String selectedRoot) {
				try {
					// Get the updated verification status from the BLL
					String updatedStatus = rootBLL.getVerificationStatus(selectedRoot);

					// Update the table with the verified status
					int selectedRow = (versesTable).getSelectedRow();

					tableModel.setValueAt(updatedStatus, selectedRow, 3); // verification status column index is 2
				} catch (Exception e) {
					e.printStackTrace();
					// Handle the exception as needed
				}
			}});

        // Populate the table with verses for the selected root
        List<String> verses = rootBLL.getVersesForRoot(selectedRoot);
       
        int srNo = 1;
        for(String verse:verses) {
        String[] misras = verse.split("//", 2);
        String misra1 = misras[0];
        String misra2 = (misras.length > 1) ? misras[1] : ""; // Default to empty string if no Misra2
        String verification_status=rootBLL.getVerificationStatus(misra1);
        tableModel.addRow(new Object[] { srNo++, misra1, misra2,verification_status});}
    }
}
