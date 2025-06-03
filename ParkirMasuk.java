package Parkir1;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ParkirMasuk extends JFrame {
    private JTextField tfNomorTiket, tfNomorPolisi, tfTanggal, tfJam;
    private JComboBox<String> cbJenisKendaraan;
    private JTable table;
    private DefaultTableModel tableModel;
    private Timer timer;

    public ParkirMasuk() {
        setTitle("Parkir Masuk");
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
                "  DATA KENDARAAN MASUK  ",
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
        tfNomorTiket.setEditable(false);
        gbc.gridx = 1;
        panelForm.add(tfNomorTiket, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Nomor Polisi:"), gbc);
        tfNomorPolisi = new JTextField(10);
        gbc.gridx = 1;
        panelForm.add(tfNomorPolisi, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Jenis Kendaraan:"), gbc);
        cbJenisKendaraan = new JComboBox<>(new String[]{"Motor", "Mobil"});
        gbc.gridx = 1;
        panelForm.add(cbJenisKendaraan, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Tanggal:"), gbc);
        tfTanggal = new JTextField(10);
        tfTanggal.setEditable(false);
        tfTanggal.setText(LocalDate.now().toString());
        gbc.gridx = 1;
        panelForm.add(tfTanggal, gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelForm.add(new JLabel("Jam:"), gbc);
        tfJam = new JTextField(10);
        tfJam.setEditable(false);
        gbc.gridx = 1;
        panelForm.add(tfJam, gbc);

        // Jalankan timer untuk update jam realtime
        timer = new Timer(1000, e -> {
            tfJam.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        });
        timer.start();

        gbc.gridx = 0; gbc.gridy++;
        JPanel panelButtonRow = new JPanel(new GridLayout(1, 3, 10, 0));
        panelButtonRow.setBackground(Color.WHITE);
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");

        Dimension uniformButtonSize = new Dimension(180, 35);
        btnTambah.setPreferredSize(uniformButtonSize);
        btnEdit.setPreferredSize(uniformButtonSize);
        btnHapus.setPreferredSize(uniformButtonSize);

        panelButtonRow.add(btnTambah);
        panelButtonRow.add(btnEdit);
        panelButtonRow.add(btnHapus);

        gbc.gridwidth = 2;
        panelForm.add(panelButtonRow, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy++;
        JButton btnCetak = new JButton("Cetak Tiket");
        btnCetak.setPreferredSize(new Dimension(560, 35));
        gbc.gridwidth = 2;
        panelForm.add(btnCetak, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy++;
        JButton btnKembali = new JButton("Kembali Ke Menu Utama");
        btnKembali.setPreferredSize(new Dimension(560, 35));
        btnKembali.setBackground(new Color(220, 220, 220));
        btnKembali.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridwidth = 2;
        panelForm.add(btnKembali, gbc);

        String[] kolom = {"Nomor Tiket", "Nomor Polisi", "Jenis Kendaraan", "Tanggal Masuk", "Jam Masuk"};
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

        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        btnKembali.addActionListener(e -> {
            dispose();
            new MenuUtamaParkir().setVisible(true);
        });
        btnCetak.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dicetak tiketnya.");
                return;
            }
            String tiket = tableModel.getValueAt(selected, 0).toString();
            String polisi = tableModel.getValueAt(selected, 1).toString();
            String jenis = tableModel.getValueAt(selected, 2).toString();
            String tanggal = tableModel.getValueAt(selected, 3).toString();
            String jam = tableModel.getValueAt(selected, 4).toString();
            CetakTiket.cetakTiket(tiket, polisi, jenis, tanggal, jam);
        });

        muatDataDariDatabase();
        buatNomorTiketOtomatis();

        panelUtama.add(panelForm, BorderLayout.WEST);
        panelUtama.add(scrollPane, BorderLayout.CENTER);
        setContentPane(panelUtama);
    }

   private void tambahData() {
    String tiket = tfNomorTiket.getText();
    String polisi = tfNomorPolisi.getText();
    String jenis = cbJenisKendaraan.getSelectedItem().toString();
    String tanggal = LocalDate.now().toString();
    String jam = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "INSERT INTO parkir (nomor_tiket, nomor_polisi, jenis_kendaraan, tanggal_masuk, jam_masuk) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, tiket);
        pst.setString(2, polisi);
        pst.setString(3, jenis);
        pst.setString(4, tanggal);
        pst.setString(5, jam);
        pst.executeUpdate();

        tableModel.addRow(new Object[]{tiket, polisi, jenis, tanggal, jam});
        tfNomorPolisi.setText("");
        buatNomorTiketOtomatis();
        tfTanggal.setText(LocalDate.now().toString());

        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal menambahkan data: " + e.getMessage());
    }
}


   private void editData() {
    int selected = table.getSelectedRow();
    if (selected == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu.");
        return;
    }

    int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah yakin ingin mengubah data parkiran?", "Konfirmasi Edit", JOptionPane.YES_NO_OPTION);
    if (konfirmasi != JOptionPane.YES_OPTION) {
        return;
    }

    String tiket = tableModel.getValueAt(selected, 0).toString();
    String polisi = tfNomorPolisi.getText();
    String jenis = cbJenisKendaraan.getSelectedItem().toString();

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "UPDATE parkir SET nomor_polisi=?, jenis_kendaraan=? WHERE nomor_tiket=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, polisi);
        pst.setString(2, jenis);
        pst.setString(3, tiket);
        pst.executeUpdate();

        tableModel.setValueAt(polisi, selected, 1);
        tableModel.setValueAt(jenis, selected, 2);

        JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal mengedit data: " + e.getMessage());
    }
}


    private void hapusData() {
    int selected = table.getSelectedRow();
    if (selected == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data terlebih dahulu.");
        return;
    }

    int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah yakin ingin menghapus data?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
    if (konfirmasi != JOptionPane.YES_OPTION) {
        return;
    }

    String tiket = tableModel.getValueAt(selected, 0).toString();

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "DELETE FROM parkir WHERE nomor_tiket=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, tiket);
        pst.executeUpdate();

        tableModel.removeRow(selected);

        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
    }
}

    private void muatDataDariDatabase() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT nomor_tiket, nomor_polisi, jenis_kendaraan, tanggal_masuk, jam_masuk FROM parkir";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getString("nomor_tiket"),
                        rs.getString("nomor_polisi"),
                        rs.getString("jenis_kendaraan"),
                        rs.getString("tanggal_masuk"),
                        rs.getString("jam_masuk")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage());
        }
    }

    private void buatNomorTiketOtomatis() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT nomor_tiket FROM parkir ORDER BY id DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String nomorBaru = "TKT0001";
            if (rs.next()) {
                String last = rs.getString("nomor_tiket");
                int nextNum = Integer.parseInt(last.substring(3)) + 1;
                nomorBaru = String.format("TKT%04d", nextNum);
            }
            tfNomorTiket.setText(nomorBaru);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal membuat nomor tiket otomatis: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ParkirMasuk().setVisible(true));
    }
}