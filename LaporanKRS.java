import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class LaporanKRS extends JFrame {

    JTable table;

    public LaporanKRS() {
        setTitle("Laporan KRS");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new java.awt.BorderLayout());

        JLabel header = new JLabel("LAPORAN KRS MAHASISWA", JLabel.CENTER);
        header.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        add(header, java.awt.BorderLayout.NORTH);

        table = new JTable();
        add(new JScrollPane(table), java.awt.BorderLayout.CENTER);

        JButton btnKembali = new JButton("Kembali");
        btnKembali.addActionListener(e -> dispose());
        add(btnKembali, java.awt.BorderLayout.SOUTH);

        tampilData();
    }

    void tampilData() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"NIM", "Nama", "Mata Kuliah", "Semester"}, 0);

        try {
            String sql =
                    "SELECT m.nim, m.nama, mk.nama_mk, k.semester " +
                    "FROM krs k " +
                    "JOIN mahasiswa m ON k.nim = m.nim " +
                    "JOIN matakuliah mk ON k.kode_mk = mk.kode_mk";

            Statement st = Koneksi.getConnection().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                });
            }
            table.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
}
