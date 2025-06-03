package Parkir1;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;

public class ParkirKeluar extends JFrame {
    private JTextField tfNomorTiket, tfNomorPolisi, tfJenisKendaraan, tfTanggalMasuk, tfJamMasuk;
    private JTextField tfTanggalKeluar, tfDurasi, tfTotal, tfPembayaran;
    private JTable table;
    private DefaultTableModel tableModel;
    private Timer timer;
    private LocalDateTime waktuMasuk;
    private long totalBiaya = 0;
    private JButton btnBayar;


    public ParkirKeluar() {
        setTitle("Parkir Keluar");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(850, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelUtama = new JPanel(new BorderLayout(20, 20));
        panelUtama.setBackground(Color.decode("#f9f9f9"));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setPreferredSize(new Dimension(400, 600));
        panelForm.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 150), 1, true),
                "  DATA KENDARAAN KELUAR  ",
                TitledBorder.CENTER, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 15), new Color(40, 40, 120)
        ));
        panelForm.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Nomor Tiket:"), gbc);
        tfNomorTiket = new JTextField(10);
        gbc.gridx = 1;
        panelForm.add(tfNomorTiket, gbc);
        JButton btnCek = new JButton("Cek");
        gbc.gridx = 2;
        panelForm.add(btnCek, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Nomor Polisi:"), gbc);
        tfNomorPolisi = new JTextField(10); tfNomorPolisi.setEditable(false);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(tfNomorPolisi, gbc); gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Jenis Kendaraan:"), gbc);
        tfJenisKendaraan = new JTextField(10); tfJenisKendaraan.setEditable(false);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(tfJenisKendaraan, gbc); gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Tanggal Masuk:"), gbc);
        tfTanggalMasuk = new JTextField(10); tfTanggalMasuk.setEditable(false);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(tfTanggalMasuk, gbc); gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Jam Masuk:"), gbc);
        tfJamMasuk = new JTextField(10); tfJamMasuk.setEditable(false);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(tfJamMasuk, gbc); gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Tanggal Keluar:"), gbc);
        tfTanggalKeluar = new JTextField(10); tfTanggalKeluar.setEditable(false);
        tfTanggalKeluar.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(tfTanggalKeluar, gbc); gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Durasi:"), gbc);
        tfDurasi = new JTextField(10); tfDurasi.setEditable(false);
        gbc.gridx = 1;
        panelForm.add(tfDurasi, gbc);
        JButton btnOk = new JButton("OK");
        gbc.gridx = 2;
        panelForm.add(btnOk, gbc);

       // Baris Total Biaya
gbc.gridx = 0; 
gbc.gridy++;
panelForm.add(new JLabel("Total Biaya:"), gbc);

// TextField untuk Total Biaya
tfTotal = new JTextField(10); 
tfTotal.setEditable(false);
gbc.gridx = 1;
panelForm.add(tfTotal, gbc);

// Tombol Bayar di sebelah tfTotal (gridx = 2)
btnBayar = new JButton("Bayar");
gbc.gridx = 2;
panelForm.add(btnBayar, gbc);

// Action untuk tombol Bayar
btnBayar.addActionListener(e -> {
    String input = JOptionPane.showInputDialog(this, "Silahkan masukkan pembayaran:");
    if (input != null && !input.trim().isEmpty()) {
        tfPembayaran.setText(input.trim());
    }
});



        gbc.gridx = 0; gbc.gridy++;
        tfPembayaran = new JTextField(10);
        tfPembayaran.setVisible(false);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panelForm.add(tfPembayaran, gbc); gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 3;
        JButton btnCetakStruk = new JButton("Cetak Struk");
        panelForm.add(btnCetakStruk, gbc);

        gbc.gridy++;
        JButton btnKembali = new JButton("Kembali Ke Menu Utama");
        btnKembali.setBackground(new Color(220, 220, 220));
        btnKembali.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panelForm.add(btnKembali, gbc);
        gbc.gridwidth = 1;

        String[] kolom = {"Nomor Tiket", "Nomor Polisi", "Jenis Kendaraan", "Tanggal Masuk", "Jam Masuk", "Tanggal Keluar", "Durasi"};
        tableModel = new DefaultTableModel(kolom, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(28);
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setSelectionBackground(new Color(210, 210, 255));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(600, 400));

        loadTableData();
     
        
        btnCek.addActionListener(e -> {
            String tiket = tfNomorTiket.getText().trim();
            try (Connection conn = DatabaseConnection.getConnection()) {
                String query = "SELECT * FROM parkir WHERE nomor_tiket = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, tiket);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    tfNomorPolisi.setText(rs.getString("nomor_polisi"));
                    tfJenisKendaraan.setText(rs.getString("jenis_kendaraan"));
                    tfTanggalMasuk.setText(rs.getDate("tanggal_masuk").toString());
                    tfJamMasuk.setText(rs.getTime("jam_masuk").toString());
                    waktuMasuk = LocalDateTime.of(rs.getDate("tanggal_masuk").toLocalDate(), rs.getTime("jam_masuk").toLocalTime());

                    if (timer != null) timer.stop();
                    timer = new Timer(1000, evt -> {
                        Duration durasi = Duration.between(waktuMasuk, LocalDateTime.now());
                        long jam = durasi.toMinutes() / 60;
                        long menit = durasi.toMinutes() % 60;
                        tfDurasi.setText(jam + " jam " + menit + " menit");
                    });
                    timer.start();
                } else {
                    JOptionPane.showMessageDialog(this, "Nomor Tiket tidak ditemukan.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal mengambil data dari database.");
            }
        });
   
        

        btnOk.addActionListener(e -> {
    if (timer != null) timer.stop();
    try {
        LocalDateTime keluar = LocalDateTime.now();
        Duration durasi = Duration.between(waktuMasuk, keluar);
        long totalMenit = durasi.toMinutes();

        // Pembulatan ke atas dan minimal 1 jam
        long jamDibulatkan = (long) Math.ceil(totalMenit / 60.0);
        if (jamDibulatkan == 0) jamDibulatkan = 1;

        // Hitung tarif berdasarkan jenis kendaraan
        long tarif = tfJenisKendaraan.getText().equalsIgnoreCase("Mobil") ? 5000 : 3000;
        totalBiaya = jamDibulatkan * tarif;
        tfDurasi.setText(jamDibulatkan + " jam");
        tfTotal.setText("Rp " + totalBiaya);

        try (Connection conn = DatabaseConnection.getConnection()) {
            String update = "UPDATE parkir SET tanggal_keluar=?, durasi=?, total=? WHERE nomor_tiket=?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setDate(1, java.sql.Date.valueOf(keluar.toLocalDate()));
            stmt.setString(2, tfDurasi.getText());
            stmt.setLong(3, totalBiaya);
            stmt.setString(4, tfNomorTiket.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data parkir keluar berhasil disimpan.");
            loadTableData();
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Gagal menghitung durasi atau menyimpan data.");
    }
});

        btnCetakStruk.addActionListener(e -> {
            if (tfPembayaran.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Masukkan jumlah pembayaran!");
                return;
            }

            try {
                long pembayaran = Long.parseLong(tfPembayaran.getText());
                if (pembayaran < totalBiaya) {
                    JOptionPane.showMessageDialog(this, "Pembayaran kurang!");
                    return;
                }

                long kembalian = pembayaran - totalBiaya;
                CetakStruk.cetakStruk(
                    tfNomorTiket.getText(),
                    tfNomorPolisi.getText(),
                    tfJenisKendaraan.getText(),
                    tfTanggalMasuk.getText(),
                    tfJamMasuk.getText(),
                    tfTanggalKeluar.getText(),
                    tfDurasi.getText(),
                    tfTotal.getText(),
                    String.format("Rp %d", pembayaran),
                    String.format("Rp %d", kembalian)
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Masukkan pembayaran dengan angka yang valid!");
            }
        });

        btnKembali.addActionListener(e -> {
            dispose();
            new MenuUtamaParkir().setVisible(true);
        });

        panelUtama.add(panelForm, BorderLayout.WEST);
        panelUtama.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panelUtama);
    }

    private void loadTableData() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            tableModel.setRowCount(0);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM parkir");
            while (rs.next()) {
                Object[] data = {
                        rs.getString("nomor_tiket"),
                        rs.getString("nomor_polisi"),
                        rs.getString("jenis_kendaraan"),
                        rs.getString("tanggal_masuk"),
                        rs.getString("jam_masuk"),
                        rs.getString("tanggal_keluar") != null ? rs.getString("tanggal_keluar") : "",
                        rs.getString("durasi") != null ? rs.getString("durasi") : ""
                };
                tableModel.addRow(data);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data parkir keluar dari database.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ParkirKeluar().setVisible(true));
    }
}
