package presentationLayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLayer.BLLFacade;
import businessLayer.IBLLFacade;
import transferObject.PoemTO;

public class PoemUI extends JFrame {
	
    private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JTextField textField_title;
	
	ArrayList<String> poemID = new ArrayList<>(); //array of ID's
	
    private IBLLFacade poemFacadeBLL; //Facade Object
    
    public PoemUI(String bookID, IBLLFacade bookbll) throws SQLException {
        poemFacadeBLL = bookbll;
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] label = { "Poem Title", };
		model = new DefaultTableModel(label, 0);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(160, 196, 222));
		panel.setBounds(10, 10, 501, 343);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Poem");
		lblNewLabel.setBounds(199, 10, 88, 26);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		panel.add(lblNewLabel);

		table = new JTable(model); 
		table.setBounds(94, 117, 310, 188);
		panel.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(78, 56, 314, 142);
		panel.add(scrollPane);

		textField_title = new JTextField();
		textField_title.setColumns(10);
		textField_title.setBounds(140, 215, 200, 26);
		panel.add(textField_title);

		JButton btnAdd = new JButton("Add");

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PoemTO poem = new PoemTO();
				if(textField_title.getText().isEmpty())
				{
				    JOptionPane.showMessageDialog(contentPane, "Field is Empty", "Error", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					Object[] obj = new Object[1];
					obj[0] = textField_title.getText().toString();
		
					try {
						
						poem.setPoemTitle(textField_title.getText().toString());
						poemFacadeBLL.insertPoem(poem,bookID);
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					 model.addRow(obj);
	                 textField_title.setText("");
	                 
	               //re-initialize the class (so changes can be made)
	                 
	                 try {
	                	 
						PoemUI poemUI = new PoemUI( bookID, bookbll);
						dispose();
						poemUI.show();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                 
				}      
			}
		});

		btnAdd.setBounds(105, 258, 85, 21);
		panel.add(btnAdd);

		JButton btnNewButton = new JButton("Update");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				PoemTO poems = new PoemTO();

				poems.setPoemTitle(textField_title.getText().toString());

				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1) {
					int confirmation = JOptionPane.showConfirmDialog(contentPane,
							"Are you sure you want to Update the selected Poem?", "Confirm Updation",
							JOptionPane.YES_NO_OPTION);
					if (confirmation == JOptionPane.YES_OPTION) {
						
						model.setValueAt(textField_title.getText().toString(), selectedRow, 0);

						try {
							
							poemFacadeBLL.updatePoem_(poems, poemID.get(selectedRow));
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						textField_title.setText("");

					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Select a Poem to Update.", "No Poem Selected",
							JOptionPane.ERROR_MESSAGE);
				}

		

			}
		});

		btnNewButton.setBounds(202, 258, 85, 21);
		panel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1) {
					int confirmation = JOptionPane.showConfirmDialog(contentPane,
							"Are you sure you want to delete the selected Poem?", "Confirm Deletion",
							JOptionPane.YES_NO_OPTION);
					if (confirmation == JOptionPane.YES_OPTION) {

						String tableIndex = String.valueOf(table.getValueAt(selectedRow, 0));

						try {

							poemFacadeBLL.deletePoem((poemID.get(selectedRow)));

						} catch (SQLException e1) {

							e1.printStackTrace();
						}

						model.removeRow(selectedRow);

						textField_title.setText("");

					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Select a Poem to delete.", "No Poem Selected",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnNewButton_1.setBounds(300, 258, 85, 21);
		panel.add(btnNewButton_1);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
				try {
					ViewSingleBook viewSingleBook = new ViewSingleBook(String.valueOf(bookID), bookbll);
					dispose();
					viewSingleBook.show();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		    }
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBackground(UIManager.getColor("activeCaption"));
		btnBack.setBounds(10, 16, 70, 21);
		panel.add(btnBack);

		JButton btnNewButton_3 = new JButton("View Single Poem");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1) {
					int confirmation = JOptionPane.showConfirmDialog(contentPane,
							"Are you sure you want to Select this Title?", "Confirm Updation",
							JOptionPane.YES_NO_OPTION);
					if (confirmation == JOptionPane.YES_OPTION) {
						
						model.setValueAt(textField_title.getText().toString(), selectedRow, 0);
						
						try {
							textField_title.setText("");
							dispose();
							
							//Sending the selected ID from Title into "ViewSinglePoem" to get it's Verse Detail
							ViewSinglePoem viewSinglePoem = new ViewSinglePoem(poemID.get(selectedRow), bookID, poemFacadeBLL);
                            System.out.println(poemID.get(selectedRow));
							System.out.println(bookID);
							viewSinglePoem.show();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						

					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Select a Poem to to View", "No Poem Selected",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnNewButton_3.setBounds(170, 300, 152, 21);
		panel.add(btnNewButton_3);

		ArrayList<PoemTO> poems = poemFacadeBLL.getPoems(Integer.parseInt(bookID));
		
		for (int i = 0; i < poems.size(); i++) {

			Object[] obj = new Object[1];

			poemID.add(String.valueOf(poems.get(i).getPoemId()));
			
			obj[0] = poems.get(i).getPoemTitle();
			model.addRow(obj);
		}

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				textField_title.setText((String) table.getValueAt(selectedRow, 0));
			}
		});
    	
    }
 
    
}
