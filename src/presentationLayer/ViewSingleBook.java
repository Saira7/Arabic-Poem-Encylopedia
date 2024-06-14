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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLayer.BLLFacade;
import businessLayer.IBLLFacade;
import transferObject.BookTO;

public class ViewSingleBook extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	ArrayList<String> BookID = new ArrayList<>();

	private IBLLFacade bookFacade; // Facade Object

	public ViewSingleBook(String bookId, IBLLFacade bookbll) throws SQLException {
    	
    	bookFacade = bookbll;
    	    	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] label = { "Title", "Author","Date", };
		model = new DefaultTableModel(label, 0);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(160, 196, 222));
		panel.setBounds(10, 10, 501, 343);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Single Book");
		lblNewLabel.setBounds(185, 10, 250, 35);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		panel.add(lblNewLabel);

		table = new JTable(model);
		table.setBounds(94, 117, 310, 188);
		panel.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(78, 56,320, 100);
		panel.add(scrollPane);

		JButton btnAddManual = new JButton("Add Data (Manual)");

		btnAddManual.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();

		        if (selectedRow != -1) {
		            int confirmation = JOptionPane.showConfirmDialog(contentPane,
		                    "Are you sure you want to Add Data Manually?", "Confirm Action",
		                    JOptionPane.YES_NO_OPTION);
		            if (confirmation == JOptionPane.YES_OPTION) {
		            	
		               try {
						PoemDataUI poemDataUI = new PoemDataUI(bookId);
						dispose();
						poemDataUI.show();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		               
		            }
		        } else {
		            JOptionPane.showMessageDialog(contentPane, "Select Book first", "Error",
		                    JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

		btnAddManual.setBounds(259, 170, 140, 30);
		panel.add(btnAddManual);
		
		JButton btnAddFile = new JButton("Add Data (File)");
		
		btnAddFile.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();

		        if (selectedRow != -1) {
		            int confirmation = JOptionPane.showConfirmDialog(contentPane,
		                    "Are you sure you want to Add Data using File?", "Confirm Action",
		                    JOptionPane.YES_NO_OPTION);
		            if (confirmation == JOptionPane.YES_OPTION) {
		            	
						try {
							PoemFileUI poemFileUI = new PoemFileUI(Integer.parseInt(bookId));
							dispose();
			            	poemFileUI.show();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		            }
		        } else {
		            JOptionPane.showMessageDialog(contentPane, "Select Book first", "Error",
		                    JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});

		btnAddFile.setBounds(259, 220, 140, 30);

		panel.add(btnAddFile);

		JButton btnViewTitle = new JButton("View Poems");

		btnViewTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selectedRow = table.getSelectedRow();
				
				if (selectedRow != -1) {
					int confirmation = JOptionPane.showConfirmDialog(contentPane,
							"Are you sure you want to open this Book?", "Confirm Action",
							JOptionPane.YES_NO_OPTION);
					if (confirmation == JOptionPane.YES_OPTION) {
												
						try {
							PoemUI poemUI = new PoemUI(bookId,bookbll);
							dispose();
							poemUI.show();
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
						
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Select a Book First.", "Error",
							JOptionPane.ERROR_MESSAGE);
					
				}
			}
		});

		btnViewTitle.setBounds(259, 270, 140, 30);
		panel.add(btnViewTitle);



			JButton btnBack = new JButton("Back");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BookUI book;
					try {
						book = new BookUI();
						dispose();
						book.show();
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
			
			
		BookTO bookObj = new BookTO();
		
		bookObj.setBookID(bookId); //gives the obj the ID
		
		ArrayList<BookTO> book = bookFacade.getBookByID(bookId);
		
		for (int i = 0; i < book.size(); i++) {

			Object[] obj = new Object[3];

			BookID.add(String.valueOf(book.get(i).getAuthor()));		
			
			obj[0] = book.get(i).getTitle();
			obj[1] = book.get(i).getAuthor();
			obj[2] = book.get(i).getDate();
			model.addRow(obj);
		}			
		

	}

}
