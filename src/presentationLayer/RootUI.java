package presentationLayer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultListModel;
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

import Main.Main;
import businessLayer.BLLFacade;
import businessLayer.IBLLFacade;
import transferObject.RootTO;

public class RootUI extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel model;
	private JButton removeButton;
	private DefaultListModel<String> rootListModel;
	private JButton viewVersesButton;
	private JButton verifyRootsButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				RootUI frame = new RootUI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public RootUI() throws SQLException {
		setTitle("Root Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		IBLLFacade rootBLL = new BLLFacade();
		String[] labels = { "SR. NO.", "Roots", "Verses Count" };
		model = new DefaultTableModel(labels, 0);

		JLabel lblRootManagement = new JLabel("Roots");
		lblRootManagement.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblRootManagement.setBounds(230, 10, 150, 50);
		contentPane.add(lblRootManagement);

		table = new JTable(model);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(80, 50, 350, 150);
		contentPane.add(scrollPane);
           rootBLL.generateroot();

		removeButton = new JButton("Remove");
		removeButton.setBounds(100, 250, 100, 30);
		//removeButton.setBackground(SystemColor.inactiveCaption);
		removeButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(removeButton);

		viewVersesButton = new JButton("View Verses");
		viewVersesButton.setBounds(250, 250, 100, 30);
		//viewVersesButton.setBackground(SystemColor.inactiveCaption);
		viewVersesButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(viewVersesButton);
//
//		verifyRootsButton = new JButton("Verify Roots");
//		verifyRootsButton.setBounds(320, 250, 100, 30);
//		//verifyRootsButton.setBackground(SystemColor.inactiveCaption);
//		verifyRootsButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		contentPane.add(verifyRootsButton);

		rootListModel = new DefaultListModel<>();

		List<RootTO> roots = rootBLL.getAllRootsWithVersesCount();
		int i = 1;
		for (RootTO root : roots) {

			Object[] obj = new Object[4];
			obj[0] = i;
			i++;
			obj[1] = root.getRootName();
			obj[2] = root.getVersesCount();
			model.addRow(obj);

			rootListModel.addElement(root.getRootName());

		}
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Main main = new Main();
				dispose();
				main.show();
		    }
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBack.setBounds(10, 10, 70, 30);
		contentPane.add(btnBack);


		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					int confirmation = JOptionPane.showConfirmDialog(contentPane,
							"Are you sure you want to delete the selected root?", "Confirm Deletion",
							JOptionPane.YES_NO_OPTION);
					if (confirmation == JOptionPane.YES_OPTION) {
						String selectedRoot = (String) table.getValueAt(selectedRow, 1);
						rootBLL.removeRoot(selectedRoot);
						model.removeRow(selectedRow);
						//rootInputField.setText("");
						  updateSrNumbers();
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Please select a root to delete.", "No Root Selected",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			private void updateSrNumbers() {
		        for (int i = 0; i < model.getRowCount(); i++) {
		            model.setValueAt(String.valueOf(i + 1), i, 0); // Assuming 0 is the index of the Sr. No. column
		        }
		    }
		});

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				table.getValueAt(selectedRow, 1);
			}
		});

		viewVersesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int selectedRow = table.getSelectedRow();
	                if (selectedRow != -1) {
	                    String selectedRoot = (String) table.getValueAt(selectedRow, 1);

	                    // Open the VersesForRootUI screen
	                    VersesForRootUI versesForRootUI = new VersesForRootUI(rootBLL, selectedRoot);
	                    dispose();
	                    versesForRootUI.setVisible(true);
	                } else {
	                    JOptionPane.showMessageDialog(contentPane, "Please select a root.", "No Root Selected",
	                            JOptionPane.ERROR_MESSAGE);
	                }
	            }
		});
		

		
		
	}
}
