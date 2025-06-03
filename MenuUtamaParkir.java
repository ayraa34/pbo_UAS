package Parkir1;

import java.awt.*;
import javax.swing.*;

public class MenuUtamaParkir extends JFrame {

    public MenuUtamaParkir() {
        setTitle("Menu Utama Parkir");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400); // Ukuran awal
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null); // Pusat saat pertama dibuka
        setResizable(true);

        // Panel utama dengan layout GridBag untuk fleksibilitas
        JPanel panelUtama = new JPanel(new GridBagLayout());
        panelUtama.setBackground(Color.WHITE); // Latar belakang putih untuk tampilan bersih
        panelUtama.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0); // Spasi antar komponen

        // Label Judul
        JLabel labelJudul = new JLabel("Parkir UNSIKA", SwingConstants.CENTER);
        labelJudul.setFont(new Font("Segoe UI", Font.BOLD, 30));
        labelJudul.setForeground(new Color(40, 40, 100)); // Biru tua
        gbc.gridy = 0;
        panelUtama.add(labelJudul, gbc);

        // Tombol-tombol
        JButton btnMasuk = new JButton("Parkir Masuk");
        JButton btnKeluar = new JButton("Parkir Keluar");
        JButton btnLogout = new JButton("Logout");

        Dimension tombolUkuran = new Dimension(250, 45);
        for (JButton btn : new JButton[]{btnMasuk, btnKeluar, btnLogout}) {
            btn.setPreferredSize(tombolUkuran);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            gbc.gridy++;
            panelUtama.add(btn, gbc);
        }

        // Tambahkan ke frame
        setContentPane(panelUtama);

        // Aksi tombol
        btnMasuk.addActionListener(e -> {
            new ParkirMasuk().setVisible(true);
            dispose();
        });

        btnKeluar.addActionListener(e -> {
            new ParkirKeluar().setVisible(true);
            dispose();
        });

        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new Login().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuUtamaParkir().setVisible(true));
    }
}
