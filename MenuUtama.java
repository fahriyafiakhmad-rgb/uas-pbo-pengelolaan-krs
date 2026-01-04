import javax.swing.*;
import java.awt.*;

public class MenuUtama extends JFrame {

    JMenuBar menuBar;
    JMenu menuMaster, menuTransaksi, menuLaporan;
    JMenuItem itemMhs, itemMK, itemKRS;
    JMenuItem itemLapKRS, itemLapPelunasan;

    public MenuUtama() {
        setTitle("Menu Utama");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initMenu();
        initDesain();
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        menuMaster = new JMenu("Master");
        menuTransaksi = new JMenu("Transaksi");
        menuLaporan = new JMenu("Laporan");

        itemMhs = new JMenuItem("Mahasiswa");
        itemMK = new JMenuItem("Mata Kuliah");
        itemKRS = new JMenuItem("KRS");

        itemLapKRS = new JMenuItem("Laporan KRS");
        itemLapPelunasan = new JMenuItem("Laporan Pelunasan KRS");

        itemMhs.addActionListener(e -> new FormMahasiswa().setVisible(true));
        itemMK.addActionListener(e -> new FormMatakuliah().setVisible(true));
        itemKRS.addActionListener(e -> new FormKRS().setVisible(true));

        itemLapKRS.addActionListener(e -> new LaporanKRS().setVisible(true));
        itemLapPelunasan.addActionListener(e -> new LaporanPelunasanKRS().setVisible(true));

        menuMaster.add(itemMhs);
        menuMaster.add(itemMK);
        menuTransaksi.add(itemKRS);
        menuLaporan.add(itemLapKRS);
        menuLaporan.add(itemLapPelunasan);

        menuBar.add(menuMaster);
        menuBar.add(menuTransaksi);
        menuBar.add(menuLaporan);

        setJMenuBar(menuBar);
    }

    private void initDesain() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(45, 118, 232));

        JLabel lbl = new JLabel("SISTEM PENGELOLAAN KRS", SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 24));
        lbl.setForeground(Color.WHITE);

        panel.add(lbl, BorderLayout.CENTER);
        add(panel);
    }

    public static void main(String[] args) {
        new MenuUtama().setVisible(true);
    }
}
