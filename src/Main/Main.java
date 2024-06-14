package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import presentationLayer.BookUI;
import presentationLayer.PoemDataUI;
import presentationLayer.PoemFileUI;
import presentationLayer.RootUI;
import presentationLayer.TokenManualEntry;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Main frame = new Main();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
 
    public Main() {
    	setTitle("Encyclopedia of Arabic poetry");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 525, 400);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(41, 128, 185)); // Flat Blue
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(5, 1, 0, 10));

        JLabel lblNewLabel = new JLabel("Encyclopedia of Arabic poetry");
       
        lblNewLabel.setFont(new Font("Arial", Font.BOLD, 22));
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNewLabel);

        JButton btnView = createButton("Books");
        btnView.addActionListener(e -> {
			try {
				openWindow(new BookUI());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        panel.add(btnView);

        JButton btnRoot = createButton("Root");
        btnRoot.addActionListener(e -> {
			try {
				openWindow(new RootUI());
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        panel.add(btnRoot);
        
        JButton btnTokenize = createButton("Tokenize");
        btnTokenize.addActionListener(e -> {
			try {
				openWindow(new TokenManualEntry());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        panel.add(btnTokenize);
          
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                dispose();
            }
        });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(52, 73, 94)); // Dark Blue
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void openWindow(JFrame window) {
        dispose();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
