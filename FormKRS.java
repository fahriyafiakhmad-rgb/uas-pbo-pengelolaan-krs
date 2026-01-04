import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormKRS extends javax.swing.JFrame {

    public FormKRS() {
        initComponents();
        loadCombo();
        tampilData();
    }

    // ================== LOAD COMBO ==================
    private void loadCombo() {
        try {
            Statement st = Koneksi.getConnection().createStatement();

            ResultSet rs1 = st.executeQuery("SELECT nim FROM mahasiswa");
            while (rs1.next()) {
                cbNim.addItem(rs1.getString("nim"));
            }

            ResultSet rs2 = st.executeQuery("SELECT kode_mk FROM matakuliah");
            while (rs2.next()) {
                cbMK.addItem(rs2.getString("kode_mk"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ================== TAMPIL DATA ==================
    private void tampilData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("NIM");
        model.addColumn("Kode MK");
        model.addColumn("Semester");

        try {
            Statement st = Koneksi.getConnection().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM krs");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("nim"),
                    rs.getString("kode_mk"),
                    rs.getString("semester")
                });
            }
            tblKRS.setModel(model);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        cbNim = new javax.swing.JComboBox<>();
        cbMK = new javax.swing.JComboBox<>();
        txtSemester = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKRS = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Form KRS");

        btnSimpan.setText("Simpan KRS");
        btnKembali.setText("Kembali");

        btnSimpan.addActionListener(evt -> simpanKRS());
        btnKembali.addActionListener(evt -> kembaliKeMenu());

        jScrollPane1.setViewportView(tblKRS);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup()
                    .addComponent(cbNim, 200, 200, 200)
                    .addComponent(cbMK)
                    .addComponent(txtSemester)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSimpan)
                        .addComponent(btnKembali)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, 400, 400, 400)
                .addContainerGap()
        );

        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addComponent(cbNim)
                    .addComponent(cbMK)
                    .addComponent(txtSemester)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(btnSimpan)
                        .addComponent(btnKembali)))
                .addComponent(jScrollPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }

    // ================== SIMPAN KRS ==================
    private void simpanKRS() {
        try {
            String sql = "INSERT INTO krs (nim, kode_mk, semester) VALUES (?,?,?)";
            PreparedStatement ps = Koneksi.getConnection().prepareStatement(sql);
            ps.setString(1, cbNim.getSelectedItem().toString());
            ps.setString(2, cbMK.getSelectedItem().toString());
            ps.setString(3, txtSemester.getText());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "KRS berhasil disimpan");
            tampilData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ================== KEMBALI ==================
    private void kembaliKeMenu() {
        new MenuUtama().setVisible(true);
        this.dispose();
    }

    // ================== MAIN ==================
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormKRS().setVisible(true);
        });
    }

    // ================== VARIABEL ==================
    private javax.swing.JButton btnKembali;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cbMK;
    private javax.swing.JComboBox<String> cbNim;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblKRS;
    private javax.swing.JTextField txtSemester;
}
