import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class FormMahasiswa extends JFrame {

    JTextField txtNim, txtNama, txtProdi, txtAngkatan;
    JButton btnSimpan, btnUpdate, btnHapus, btnReset;
    JTable tblMahasiswa;

    public FormMahasiswa() {
        setTitle("Form Mahasiswa");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        tampilData();
    }

    private void initUI() {

        // ===== HEADER =====
        JPanel header = new JPanel();
        header.setBackground(new Color(45, 118, 232));
        header.setPreferredSize(new Dimension(100, 60));

        JLabel title = new JLabel("FORM DATA MAHASISWA", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 22));

        header.setLayout(new BorderLayout());
        header.add(title, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        // ===== FORM INPUT =====
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(new EmptyBorder(20, 20, 20, 20));

        txtNim = new JTextField();
        txtNama = new JTextField();
        txtProdi = new JTextField();
        txtAngkatan = new JTextField();

        form.add(new JLabel("NIM"));
        form.add(txtNim);
        form.add(new JLabel("Nama"));
        form.add(txtNama);
        form.add(new JLabel("Prodi"));
        form.add(txtProdi);
        form.add(new JLabel("Angkatan"));
        form.add(txtAngkatan);

        // ===== TOMBOL =====
        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");

        tombol.add(btnSimpan);
        tombol.add(btnUpdate);
        tombol.add(btnHapus);
        tombol.add(btnReset);

        JPanel kiri = new JPanel(new BorderLayout());
        kiri.add(form, BorderLayout.CENTER);
        kiri.add(tombol, BorderLayout.SOUTH);

        add(kiri, BorderLayout.WEST);

        // ===== TABLE =====
        tblMahasiswa = new JTable();
        JScrollPane scroll = new JScrollPane(tblMahasiswa);
        add(scroll, BorderLayout.CENTER);

        // ===== EVENT =====
        btnSimpan.addActionListener(e -> simpan());
        btnUpdate.addActionListener(e -> update());
        btnHapus.addActionListener(e -> hapus());
        btnReset.addActionListener(e -> reset());

        tblMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblMahasiswa.getSelectedRow();
                txtNim.setText(tblMahasiswa.getValueAt(row, 0).toString());
                txtNama.setText(tblMahasiswa.getValueAt(row, 1).toString());
                txtProdi.setText(tblMahasiswa.getValueAt(row, 2).toString());
                txtAngkatan.setText(tblMahasiswa.getValueAt(row, 3).toString());
            }
        });
    }

    // ===== CRUD =====
    private void tampilData() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"NIM", "Nama", "Prodi", "Angkatan"}, 0);

        try {
            Statement st = Koneksi.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM mahasiswa");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("prodi"),
                    rs.getString("angkatan")
                });
            }
            tblMahasiswa.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void simpan() {
        try {
            PreparedStatement ps = Koneksi.getConnection()
                    .prepareStatement("INSERT INTO mahasiswa VALUES (?,?,?,?)");
            ps.setString(1, txtNim.getText());
            ps.setString(2, txtNama.getText());
            ps.setString(3, txtProdi.getText());
            ps.setString(4, txtAngkatan.getText());
            ps.executeUpdate();
            tampilData();
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void update() {
        try {
            PreparedStatement ps = Koneksi.getConnection()
                    .prepareStatement(
                            "UPDATE mahasiswa SET nama=?, prodi=?, angkatan=? WHERE nim=?");
            ps.setString(1, txtNama.getText());
            ps.setString(2, txtProdi.getText());
            ps.setString(3, txtAngkatan.getText());
            ps.setString(4, txtNim.getText());
            ps.executeUpdate();
            tampilData();
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void hapus() {
        try {
            PreparedStatement ps = Koneksi.getConnection()
                    .prepareStatement("DELETE FROM mahasiswa WHERE nim=?");
            ps.setString(1, txtNim.getText());
            ps.executeUpdate();
            tampilData();
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void reset() {
        txtNim.setText("");
        txtNama.setText("");
        txtProdi.setText("");
        txtAngkatan.setText("");
    }
}
