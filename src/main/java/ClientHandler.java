import com.google.common.hash.Hashing;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.util.concurrent.locks.Lock;

//import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

public class ClientHandler extends Thread {

    private QualtricsRequest httpResponse;

    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private String mailListID;
    private String DistListID = null;
    private final Lock lock;
    private JDBC jdbc;
//    private boolean isFirst = false;


    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, String mailListID, Lock lock
            , JDBC jdbc) {
        this.jdbc = jdbc;
        this.mailListID = mailListID;
        this.lock = lock;
        this.s = s;
        this.dis = dis;
        this.dos = dos;
//        this.isFirst = isFirst;
//        this.httpResponse = new QualtricsRequest("https://env.qualtrics.com/API/v3/distributions");
    }

    /**
     * this method responsible of communication with clients
     */
    public void run() {
        boolean stop = false;
        while (!stop) {
            String received;
            PreparedStatement ps = null;
            ResultSet resultSet = null;
            try {
//
            /*
                register handle
             */
                lock.lock();
                String isRegister = dis.readUTF();
                lock.unlock();
                if (isRegister.equals("signup")) {
//              if(false) {
                    System.out.println("in sign up");
                    // read from user
                    lock.lock();
                    String info = dis.readUTF();
                    lock.unlock();
                    String[] infoArray = info.split(";");
                    String userName = infoArray[0];
                    String Password = infoArray[1];
                    System.out.println("in sign up after read username password");

//

//                    synchronized (lock) {
//

                        if (jdbc.openConnection()) {
                            lock.lock();
                            String queryCheck = "SELECT * from contact WHERE user_name = '" + userName + "'";
//
                            ps = jdbc.getConn().prepareStatement(queryCheck);
//                        ps.setString(1, email);
                            resultSet = ps.executeQuery();
                            //it will print true if a row exists for the given id and false if not.
                            if (!resultSet.next()) {
                                String query = " insert into contact (user_name, password, link)"
                                        + " values (?, ?, ?)";
                                QualtricsRequest hr = QualtricsRequest.getInstance("https://env.qualtrics.com/API/v3/distributions", null, null);
                                String idContact = hr.createContact("\"" + userName + "@gmail.com" + "\"", mailListID);
                                hr.setUrl("https://env.qualtrics.com/API/v3/distributions");
                                this.DistListID = hr.generateDistributionLinks(mailListID);
//
                                String link = hr.ListDisLinks(DistListID, idContact);
                                // create the mysql insert preparedstatement
                                // insert to DB
                                PreparedStatement preparedStmt = jdbc.getConn().prepareStatement(query);
                                preparedStmt.setString(1, userName);
                                String sha256hex = Hashing.sha256()
                                        .hashString(Password, StandardCharsets.UTF_8)
                                        .toString();
                                preparedStmt.setString(2, sha256hex);
                                preparedStmt.setString(3, link);


//
                                // execute the preparedstatement
                                preparedStmt.execute();
                                dos.writeUTF("success");
                                System.out.println("in sign up succes");
                                jdbc.getConn().close();
                                lock.unlock();

                            } else {
                                // the user exist in DataBase
//                                while (resultSet.next()) {
                                    System.out.println("user found in DB!");
//                                String link = resultSet.getString("Link");
                                    //System.out.println(link);
                                    // todo send to client...

                                    dos.writeUTF("used");
                                    lock.unlock();
                                    System.out.println("in sign up used");

//
                                    jdbc.getConn().close();


//                                }


                            }
                        }
//                        lock.unlock();
//
//                    }

//                } else if (true) {
                } else if (isRegister.equals("signin")) {
                    // login handle
                    // isRegister equal 'signIn'
                    System.out.println("login handle");

                    if (jdbc.openConnection()) {
                        lock.lock();
                        String[] data = dis.readUTF().split(";");
                        String userName = data[0];
                        String password = data[1];
//                    String email = "sqa@gmail.com";
//                    String password = "12345678";
                        String passwordSha2 = Hashing.sha256()
                                .hashString(password, StandardCharsets.UTF_8)
                                .toString();
                        String queryCheck = "SELECT user_name,password,Link from contact WHERE user_name = '" + userName + "' and password = '" + passwordSha2 + "'";
                        Statement stmt = null;
                        ResultSet rs = null;
                        stmt = jdbc.getConn().createStatement();
                        // execute query
                        rs = stmt.executeQuery(queryCheck);
//
                        if (rs.next()) {
                            // send link to client
                            System.out.println("found member");
                            String link = rs.getString("Link");
                            dos.writeUTF(link);
                            System.out.println("link sent");
                            lock.unlock();
//                            stop = true;
                        } else {
                            // not found in db
                            System.out.println("not found member");
                            dos.writeUTF("notfound");
                            lock.unlock();
                        }

                    }
                    jdbc.getConn().close();



                }
            } catch (SQLException e) {
                System.out.println("ERROR executeQuery - " + e.getMessage());
            } catch (IOException e) {
                stop = true;
                e.printStackTrace();
            }
//        }
        }
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getListID () {
        while(true) {
            if (this.DistListID != null) {
                return this.DistListID;
            }
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * this method convert password to md5 hash bytes
     * @param source source
     * @return md5 hash bytes
     */
    public static String md5( String source ) {
        try {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            byte[] bytes = md.digest( source.getBytes("UTF-8") );
            return getString( bytes );
        } catch( Exception e )  {
            e.printStackTrace();
            return null;
        }
    }
    private static String getString( byte[] bytes ) {
        StringBuffer sb = new StringBuffer();
        for( int i=0; i<bytes.length; i++ )
        {
            byte b = bytes[ i ];
            String hex = Integer.toHexString((int) 0x00FF & b);
            if (hex.length() == 1)
            {
                sb.append("0");
            }
            sb.append( hex );
        }
        return sb.toString();
    }

//    public boolean isFirst() {
//        return this.isFirst;
//    }
}

