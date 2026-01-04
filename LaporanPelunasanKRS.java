import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class LaporanPelunasanKRS extends JFrame {

    JTable table;
    JComboBox<String> cmbStatus;

    public LaporanPelunasanKRS() {
        setTitle("Laporan Pelunasan KRS");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setLayout(new java.awt.BorderLayout());

        JPanel atas = new JPanel();
        atas.add(new JLabel("Status:"));
        cmbStatus = new JComboBox<>(new String[]{"LUNAS", "BELUM"});
        atas.add(cmbStatus);

        JButton btnFilter = new JButton("Filter");
        atas.add(btnFilter);

        add(atas, java.awt.BorderLayout.NORTH);

        table = new JTable();
        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> dispose());
        add(btnKembali, java.awt.BorderLayout.SOUTH);

        btnFilter.addActionListener(e -> tampilData());
        tampilData();
    }

    void tampilData() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"NIM", "Nama", "Mata Kuliah", "Semester", "Status"}, 0);

        try {
            String status = cmbStatus.getSelectedItem().toString();

            String sql =
                    "SELECT m.nim, m.nama, mk.nama_mk, k.semester, k.status_pelunasan " +
                    "FROM krs k " +
                    "JOIN mahasiswa m ON k.nim = m.nim " +
                    "JOIN matakuliah mk ON k.kode_mk = mk.kode_mk " +
                    "WHERE k.status_pelunasan = ?";

            PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
