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

import businessLayer.IBLLFacade;
import transferObject.MisraTO;

public class ViewSinglePoem extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JTextField textField_misra1;
	private JTextField textField_misra2;
	ArrayList<String> poemID = new ArrayList<>();
	private AssignRootUI rootUI;
	private IBLLFacade poemFacadeBLL; // Facade Object

	public ViewSinglePoem(String ID, String bookID, IBLLFacade poemFacadeBLL_) throws SQLException {

		poemFacadeBLL = poemFacadeBLL_;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] label = { "Misra 1", "Misra 2", };
		model = new DefaultTableModel(label, 0);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(160, 196, 222));
		panel.setBounds(10, 10, 501, 343);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Individual Poem");
		lblNewLabel.setBounds(160, 10, 250, 35);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		panel.add(lblNewLabel);

		table = new JTable(model);
		table.setBounds(94, 117, 310, 188);
		panel.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(78, 56, 314, 142);
		panel.add(scrollPane);

		textField_misra1 = new JTextField();
		textField_misra1.setColumns(10);
		textField_misra1.setBounds(80, 215, 150, 26);
		panel.add(textField_misra1);

		textField_misra2 = new JTextField();
		textField_misra2.setColumns(10);
		textField_misra2.setBounds(240, 215, 150, 26);
		panel.add(textField_misra2);

		JButton btnAdd = new JButton("Add");

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MisraTO misra = new MisraTO();

				if (textField_misra1.getText().isEmpty() || textField_misra2.getText().isEmpty()) {
					JOptionPane.showMessageDialog(contentPane, "Field is Empty", "Error", JOptionPane.ERROR_MESSAGE);

				} else {
					misra.setMisra1(textField_misra1.getText().toString()); // stores misra 1
					misra.setMisra2(textField_misra2.getText().toString()); // stores misra 2

					misra.setPoemId((Integer.parseInt(ID))); // stores Poem ID

					Object[] obj = new Object[2];

					obj[0] = textField_misra1.getText().toString();
					obj[1] = textField_misra2.getText().toString();

					try {

						poemFacadeBLL_.addMisra_(misra);

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					model.addRow(obj);

					textField_misra1.setText("");
					textField_misra2.setText("");

				}
			}
		});

		btnAdd.setBounds(80, 258, 85, 21);
		panel.add(btnAdd);

		JButton btnUpdate = new JButton("Update");

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MisraTO misraTO = new MisraTO();

				int selectedRow = table.getSelectedRow();

				if (selectedRow != -1) {
					int confirmation = JOptionPane.showConfirmDialog(contentPane,
							"Are you sure you want to Update the selected Misra?", "Confirm Updation",
							JOptionPane.YES_NO_OPTION);
					if (confirmation == JOptionPane.YES_OPTION) {

						misraTO.setPoemId(Integer.parseInt(ID));
						misraTO.setMisra1(textField_misra1.getText().toString());
						misraTO.setMisra2(textField_misra2.getText().toString());

						model.setValueAt(textField_misra1.getText().toString(), selectedRow, 0);
						model.setValueAt(textField_misra2.getText().toString(), selectedRow, 1);

						poemFacadeBLL_.updateMisra_(misraTO, poemID.get(selectedRow));

						textField_misra1.setText("");
						textField_misra2.setText("");

					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Select a Misra to Update.", "No Poem Selected",
							JOptionPane.ERROR_MESSAGE);

				}
			}
		});

		btnUpdate.setBounds(195, 258, 85, 21);
		panel.add(btnUpdate);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();

				if (selectedRow != -1) {
					int confirmation = JOptionPane.showConfirmDialog(contentPane,
							"Are you sure you want to delete the selected Misra?", "Confirm Deletion",
							JOptionPane.YES_NO_OPTION);
					if (confirmation == JOptionPane.YES_OPTION) {
						try {

							poemFacadeBLL_.deleteMisra(poemID.get(selectedRow));
							model.removeRow(selectedRow);
							textField_misra1.setText("");
							textField_misra2.setText("");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Select a Misra to delete.", "No Poem Selected",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		btnDelete.setBounds(305, 258, 85, 21);
		panel.add(btnDelete);

		JButton btnTokenize = new JButton("Tokenize");

		btnTokenize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MisraTO misraTO = new MisraTO();

				int selectedRow = table.getSelectedRow();

				if (selectedRow != -1) {

					int confirmation = JOptionPane.showConfirmDialog(contentPane,
							"Are you sure you want to Automatically Tokenize the selected Misra?", "Confirm Action",
							JOptionPane.YES_NO_OPTION);

					if (confirmation == JOptionPane.YES_OPTION) {

						misraTO.setPoemId(Integer.parseInt(ID));
						misraTO.setMisra1(textField_misra1.getText().toString());
						misraTO.setMisra2(textField_misra2.getText().toString());

						poemFacadeBLL_.submitToken(misraTO);

						JOptionPane.showMessageDialog(contentPane, "Tokens Added successfully in Database", "Success",
								JOptionPane.INFORMATION_MESSAGE);

						textField_misra1.setText("");
						textField_misra2.setText("");

					} else {

						JOptionPane.showMessageDialog(contentPane, "Select a Misra to Update.", "No Poem Selected",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});

		btnTokenize.setBounds(250, 300, 120, 21);
		panel.add(btnTokenize);
		JButton AssignRootBtn = new JButton("Assign Roots");
		AssignRootBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedRow = table.getSelectedRow();
					String selectedVerse = (String) table.getValueAt(selectedRow, 0) + table.getValueAt(selectedRow, 1);
					rootUI = new AssignRootUI(ID,poemID.get(selectedRow), selectedVerse, bookID, poemFacadeBLL_);
					System.out.println(poemID.get(selectedRow));
					dispose();
					(rootUI).show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		AssignRootBtn.setBounds(100, 300, 120, 21);
		panel.add(AssignRootBtn);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					PoemUI poemUI = new PoemUI(bookID, poemFacadeBLL_);
					System.out.println(bookID);
					dispose();
					poemUI.show();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBackground(UIManager.getColor("activeCaption"));
		btnBack.setBounds(10, 16, 70, 21);
		panel.add(btnBack);

		MisraTO misraObj = new MisraTO();
		misraObj.setPoemId(Integer.parseInt(ID));

		ArrayList<MisraTO> misra = poemFacadeBLL_.getMisras(misraObj);

		for (int i = 0; i < misra.size(); i++) {

			Object[] obj = new Object[2];

			poemID.add(String.valueOf(misra.get(i).getMisraId()));

			obj[0] = misra.get(i).getMisra1();
			obj[1] = misra.get(i).getMisra2();
			model.addRow(obj);
		}

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				textField_misra1.setText((String) table.getValueAt(selectedRow, 0));
				textField_misra2.setText((String) table.getValueAt(selectedRow, 1));
			}
		});

	}

	public ViewSinglePoem(String id, IBLLFacade poemFacadeBLLPassed, String selectedRoot) throws SQLException {

		poemFacadeBLL = poemFacadeBLLPassed;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		String[] label = { "Misra 1", "Misra 2", };
		model = new DefaultTableModel(label, 0);
		JPanel panel = new JPanel();
		panel.setBackground(new Color(160, 196, 222));
		panel.setBounds(10, 10, 501, 343);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Individual Poem");
		lblNewLabel.setBounds(160, 10, 250, 35);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		panel.add(lblNewLabel);

		table = new JTable(model);
		table.setBounds(94, 117, 310, 188);
		panel.add(table);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(78, 56, 314, 142);
		panel.add(scrollPane);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				VersesForRootUI poemUI = new VersesForRootUI(poemFacadeBLLPassed, selectedRoot);
				dispose();
				poemUI.show();

			}
		});
		btnBack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnBack.setBackground(UIManager.getColor("activeCaption"));
		btnBack.setBounds(10, 16, 70, 21);
		panel.add(btnBack);

		MisraTO misraObj = new MisraTO();
		misraObj.setPoemId(Integer.parseInt(id));

		ArrayList<MisraTO> misra = poemFacadeBLLPassed.getMisras(misraObj);

		for (int i = 0; i < misra.size(); i++) {

			Object[] obj = new Object[2];

			poemID.add(String.valueOf(misra.get(i).getMisraId()));

			obj[0] = misra.get(i).getMisra1();
			obj[1] = misra.get(i).getMisra2();
			model.addRow(obj);
		}

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				textField_misra1.setText((String) table.getValueAt(selectedRow, 0));
				textField_misra2.setText((String) table.getValueAt(selectedRow, 1));
			}
		});

	}
}
