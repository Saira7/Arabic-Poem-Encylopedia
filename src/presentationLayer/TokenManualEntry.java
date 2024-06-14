package presentationLayer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Main.Main;
import businessLayer.BLLFacade;
import businessLayer.IBLLFacade;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class TokenManualEntry extends JFrame {

    private DefaultTableModel tableModel_tokenize;
    private JTable tokenTable;
    private JTextField misraField1_tokenize;
    private JTextField misraField2_tokenize;
    private JButton split_tokenize;
    private JButton save_tokenize;

    private IBLLFacade poemFile_FacadeBLL;

    public TokenManualEntry() throws SQLException {

        poemFile_FacadeBLL = new BLLFacade();
        tableModel_tokenize = new DefaultTableModel(new String[]{"Tokenize words"}, 0);
        tokenTable = new JTable(tableModel_tokenize);

        misraField1_tokenize = new JTextField(20);
        misraField2_tokenize = new JTextField(20);

        save_tokenize = new JButton("Save");
        split_tokenize = new JButton("Split");

        save_tokenize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayToken();
                try {
                    poemFile_FacadeBLL.saveWordsToDatabase_tokenize_(tableModel_tokenize);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                tableModel_tokenize.setRowCount(0);
            }
        });

        split_tokenize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splitTextFields();
            }
        });

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

        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.add(new JLabel("Misra #1: "));
        inputPanel.add(misraField1_tokenize);
        inputPanel.add(new JLabel("Misra #2: "));
        inputPanel.add(misraField2_tokenize);
        inputPanel.add(split_tokenize);
        inputPanel.add(save_tokenize);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnBack);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding
        mainPanel.add(new JScrollPane(tokenTable), BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setTitle("Tokenization Tool");
    }

    private void displayToken() {
        String misra1 = misraField1_tokenize.getText();
        String misra2 = misraField2_tokenize.getText();
        if (!misra1.isEmpty()) {
            misraField1_tokenize.setText("");
        }
        if (!misra2.isEmpty()) {
            misraField2_tokenize.setText("");
        }
    }

    private void splitTextFields() {
        String text1 = misraField1_tokenize.getText();
        String text2 = misraField2_tokenize.getText();
        poemFile_FacadeBLL.splitTextFields_tokenize_(tableModel_tokenize, text1, text2);
        save_tokenize.setEnabled(true);
    }
}
