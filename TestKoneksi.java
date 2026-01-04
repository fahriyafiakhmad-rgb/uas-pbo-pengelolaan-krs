import java.sql.Connection;

public class TestKoneksi {
    public static void main(String[] args) {
        Connection conn = Koneksi.getConnection();
        if (conn != null) {
            System.out.println("KONEKSI MYSQL BERHASIL");
        } else {
            System.out.println("KONEKSI MYSQL GAGAL");
        }
    }
}
