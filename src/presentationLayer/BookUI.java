package presentationLayer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

import com.toedter.calendar.JDateChooser;

import Main.Main;
import businessLayer.BLLFacade;
import businessLayer.IBLLFacade;
import transferObject.BookTO;

public class BookUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model; 
	private JTextField textField;
	private JTextField textField_1;
	 IBLLFacade bookbll = new BLLFacade();
	ArrayList<String> bookID = new ArrayList<>(); //array of ID's
	
	public BookUI() throws SQLException {
    	setTitle("Book CRUD Operation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		String[] label = {"Title", "Author", "Passing Year"};
		model = new DefaultTableModel(label,0);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(160, 196, 222));
		panel.setBounds(10, 10, 501, 343);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Books ");
		lblNewLabel.setBounds(199, 10, 88, 26);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		panel.add(lblNewLabel);
		
		table = new JTable(model);
		table.setBounds(94, 117, 310, 188);
		panel.add(table);
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(78, 56, 314, 142);
		panel.add(scrollPane);
		
		textField = new JTextField();
		textField.setBounds(78, 222, 112, 26);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(194, 222, 102, 26);
		panel.add(textField_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(300, 222, 92, 26);
		panel.add(dateChooser);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookTO book = new BookTO();
		        book.setTitle(textField.getText().toString());
		        book.setAuthor(textField_1.getText().toString());
		        Object[] obj = new Object[3];
				obj[0]=textField.getText().toString();
				obj[1]= textField_1.getText().toString();
		        Date selectedDate = dateChooser.getDate();

		        if (selectedDate != null) {
		            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		            String selectedDateStr = dateFormat.format(selectedDate);
		            book.setDate(selectedDateStr);
		            obj[2]= selectedDateStr;
		            Date currentDate=new Date(); 
		            if (selectedDate.before(currentDate)) {
		            	
						try {
							
							bookbll.addBook(book);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							
						}
		            	  model.addRow(obj);
		                  System.out.println("Selected date is before the specific date.");
		                  textField.setText("");
		                  textField_1.setText("");
		                  dateChooser.setDate(null);
		                  
		                  //re-initialize the class (so changes can be made)
		                  try {
		                	dispose();
							BookUI main = new BookUI();
							main.show();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		             } 
		            System.out.println("No date selected.");
		        }
			}
		});
		btnAdd.setBounds(105, 258, 85, 21);
		panel.add(btnAdd);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BookTO books = new BookTO();
		        books.setTitle(textField.getText().toString());
		        books.setAuthor(textField_1.getText().toString());
		        Date selectedDate = dateChooser.getDate();
		        int selectedRow = table.getSelectedRow();
		        String book = (String) table.getValueAt(selectedRow, 0);
		        model.setValueAt(textField.getText().toString(), selectedRow, 0);
		        model.setValueAt(textField_1.getText().toString(), selectedRow, 1);
		        
		        if (selectedDate != null) {
		            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		            String selectedDateStr = dateFormat.format(selectedDate);
		            books.setDate(selectedDateStr);
		            model.setValueAt(selectedDateStr, selectedRow, 2);
		            Date currentDate=new Date(); 
		          
		            if (selectedDate.before(currentDate)) {
		            	
		            	
						try {
							
							bookbll.updateBook(books,book);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
							
						}
		            	
		                  System.out.println("Selected date is before the specific date.");
		                  textField.setText("");
		                  textField_1.setText("");
		                  dateChooser.setDate(null);
		             } 
		        } else {
		            System.out.println("No date selected.");
		        }
			}
		});
		btnUpdate.setBounds(202, 258, 85, 21);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectedRow = table.getSelectedRow();
	                if (selectedRow != -1) {
	                    int confirmation = JOptionPane.showConfirmDialog(contentPane, "Are you sure you want to delete the selected book?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
	                    if (confirmation == JOptionPane.YES_OPTION) {
	                        String selectedBook = (String) table.getValueAt(selectedRow, 0);
	                        
	                        try {
	                        	bookbll.deleteBook(selectedBook);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	                        model.removeRow(selectedRow); 
	                        textField.setText("");
	                        textField_1.setText("");
	                        dateChooser.setDate(null);
	                        
	                        //re-initialize the class (so changes can be made)
			                  try {
			                	dispose();
								BookUI main = new BookUI();
								main.show();
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(contentPane, "Select a book to delete.", "No Book Selected", JOptionPane.ERROR_MESSAGE);
	                }
			}
		});
		btnDelete.setBounds(300, 258, 85, 21);
		panel.add(btnDelete);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main main = new Main();
				dispose();
				main.show();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBackground(UIManager.getColor("activeCaption"));
		btnBack.setBounds(10, 16, 70, 21);
		panel.add(btnBack);
		
		JButton btnViewSingleBook = new JButton("View Single Book");
		btnViewSingleBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			
			int selectedRow = table.getSelectedRow();
			
			if (selectedRow != -1) {
				int confirmation = JOptionPane.showConfirmDialog(contentPane,
						"Are you sure you want to Select this Book?", "Confirm Selecion",
						JOptionPane.YES_NO_OPTION);
				if (confirmation == JOptionPane.YES_OPTION) {
										
					textField.setText("");
					   textField_1.setText("");
					dispose();
					
					ViewSingleBook singleBook = null;
					try {
						singleBook = new ViewSingleBook(bookID.get(selectedRow),bookbll);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dispose();
					singleBook.show();
	
				}
			} else {
				JOptionPane.showMessageDialog(contentPane, "Select a Book to to View", "No Book Selected",
						JOptionPane.ERROR_MESSAGE);
			}
			}
		});
		btnViewSingleBook.setBounds(170, 300, 152, 21);
		panel.add(btnViewSingleBook);
		
		
		
		ArrayList<BookTO> books = bookbll.getBooks();
		
		for (int i=0; i<books.size();i++) {
		
			Object[] obj = new Object[3];
			
			bookID.add(String.valueOf(books.get(i).getBookID()));

			obj[0]= books.get(i).getTitle();
			obj[1]= books.get(i).getAuthor();
			obj[2]= books.get(i).getDate();
			model.addRow(obj);
		}
		
		table.getSelectionModel().addListSelectionListener(e -> {
		    int selectedRow = table.getSelectedRow();
		    if (selectedRow != -1) {
		        textField.setText((String) table.getValueAt(selectedRow, 0));
		        textField_1.setText((String) table.getValueAt(selectedRow, 1));

		        
		        String dateString = (String) table.getValueAt(selectedRow, 2);
		        try {
		        	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		            Date date = dateFormat.parse(dateString);
		            dateChooser.setDate(date);
		        } catch (ParseException ex) {
		            ex.printStackTrace(); 
		        }
		    }
		});

	}
}
