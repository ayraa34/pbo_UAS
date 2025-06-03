package Parkir1;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {
    private JTextField userField;
    private JPasswordField passField;

    public Login() {
        setTitle("Login Parkir UNSIKA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null); // Pusat saat pertama dibuka

        // Root panel dengan BorderLayout
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 245, 245));
        root.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Panel judul
        JLabel title = new JLabel("Parkir UNSIKA", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(new Color(40, 40, 120));
        root.add(title, BorderLayout.NORTH);

        // Panel tengah dengan GridBagLayout
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(Color.WHITE);
        center.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100,100,150), 2, true),
            new EmptyBorder(30, 30, 30, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label "Login"
        JLabel loginLabel = new JLabel("LOGIN", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        loginLabel.setForeground(new Color(60, 60, 100));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        center.add(loginLabel, gbc);

        // Username
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        center.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        center.add(userField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        center.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        center.add(passField, gbc);

        // Panel tombol
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        btnPanel.setBackground(Color.WHITE);
        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        loginBtn.setBackground(new Color(40, 120, 200));
        loginBtn.setForeground(Color.WHITE);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        cancelBtn.setBackground(new Color(200, 40, 40));
        cancelBtn.setForeground(Color.WHITE);
        btnPanel.add(loginBtn);
        btnPanel.add(cancelBtn);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        center.add(btnPanel, gbc);

        root.add(center, BorderLayout.CENTER);
        setContentPane(root);

        // Aksi tombol
        loginBtn.addActionListener(e -> handleLogin());
        cancelBtn.addActionListener(e -> { userField.setText(""); passField.setText(""); });
    }

    private void handleLogin() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword());
        if (user.equals("admin") && pass.equals("1234")) {
            JOptionPane.showMessageDialog(this, "Login berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new MenuUtamaParkir().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Username atau Password salah.", "Error", JOptionPane.ERROR_MESSAGE);
            userField.setText("");
            passField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Login().setVisible(true));
    }
}

