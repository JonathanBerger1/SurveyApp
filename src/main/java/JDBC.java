import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class JDBC {
    private Connection conn; // DB connection
    //    private String password = null;
//    private String address = null;
//    private String schema = null;
//    private String userName = null;
    private String password = null;
    private String address = null;
    private String schema = null;
    private String userName = null;

    public JDBC() {
        this.conn = null;
        readConfig();
        //openConnection();
    }

    /**
     * open connection to databsae
     * @return true if the connection seccess
     */
    public boolean openConnection() {

        System.out.print("Trying to connect... ");

        // creating the connection. Parametrs should be taken from config file.
        String all = "jdbc:mysql://" + this.address + "/" + this.schema + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        try {
//            String DRIVER = "com.mysql.cj.jdbc.Driver";
//            Class.forName(DRIVER).newInstance();
//            System.out.println("New Driver Instantiated");
            conn = DriverManager.getConnection(all, this.userName, this.password);
        } catch (SQLException e) {
            System.out.println("Unable to connect - " + e.getMessage());
            conn = null;
            return false;
        } catch (Exception e) {

        }
        System.out.println("Connected!");
        return true;
    }

    public Connection getConn() {
        return this.conn;
    }

    /**
     * read from config file information of database
     */
    private void readConfig() {
        try {
            File file = new File("DatabaseConfig.txt");
            int count = 0;
            BufferedReader br = new BufferedReader(new FileReader(file));
            for (String line; (line = br.readLine()) != null; ) {
//                System.out.println(line);
                if (line.startsWith("#"))
                    continue;
                count++;
                switch (count) {
                    case 1:
                        this.schema = line;
                        break;
                    case 2:
                        this.userName = line;
                        break;
                    case 3:
                        this.password = line;
                        break;
                    case 4:
                        this.address = line;
                        break;
                    default:
                        break;
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
