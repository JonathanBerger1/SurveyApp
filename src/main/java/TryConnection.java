////
////import com.sun.deploy.net.QualtricsRequest;
////import sun.net.www.http.HttpClient;
////
////import java.io.*;
////import java.net.HttpURLConnection;
////import java.net.URL;
////import java.net.URLEncoder;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import org.apache.http.QualtricsRequest;
//import org.apache.http.ParseException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//
//public class TryConnection {
//
////
////
////        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
////
////        try {
////
////            HttpPost request = new HttpPost("http://yoururl");
////            StringEntity params = new StringEntity("details={\"name\":\"myname\",\"age\":\"20\"} ");
////            request.addHeader("content-type", "application/x-www-form-urlencoded");
////            request.setEntity(params);
////            QualtricsRequest response = httpClient.execute(request);
////
////            //handle response here...
////
////        } catch (Exception ex) {
////
////            //handle exception here
////
////        } finally {
////            //Deprecated
////            //httpClient.getConnectionManager().shutdown();
////        }
////    }
////}
//
////    public static String executePost(String targetURL, String urlParameters) {
////        HttpURLConnection connection = null;
////        try {
//////        Create connection
////            URL url = new URL(targetURL);
////            connection = (HttpURLConnection) url.openConnection();
////            connection.setRequestMethod("POST");
////            connection.setRequestProperty("X-API-TOKEN", "19YxnZMVFbIhIRFUXoh0MLaMMnUexACjFfwNXzrY");
////            connection.setRequestProperty("content-type", "application/json");
////
////
////            connection.setUseCaches(false);
////            connection.setDoOutput(true);
////
////            //Send request
////            DataOutputStream wr = new DataOutputStream(
////                    connection.getOutputStream());
////            wr.writeBytes(urlParameters);
////            wr.close();
////
////            //Get Response
////            InputStream is = connection.getInputStream();
////            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
////            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
////            String line;
////            while ((line = rd.readLine()) != null) {
////                response.append(line);
////                response.append('\r');
////            }
////            rd.close();
////            return response.toString();
////        } catch (Exception e) {
////            e.printStackTrace();
////            return null;
////        } finally {
////            if (connection != null) {
////                connection.disconnect();
////            }
////        }
////    }
//
////    public static void main(String[] args) {
////        String urlParameters =
////                "{\"action\":\"CreateDistribution\",\"surveyId\":\"SV_d0F2rRsAunGkBLL\"," +
////                        "\"mailingListId\":\"ML_8Hq3K3UD0LDIN1j\",\"description\":\"This is a test distribution 4\"," +
////                        "\"expirationDate\":\"2020-12-05 11:22:33\", \"linkType\":\"Individual\"} ";
////        String res = executePost("https://env.qualtrics.com/API/v3/distributions", urlParameters);
////        System.out.println(res);
////        String json = EntityUtils.toString(res.getEntity(), "UTF-8");
////        try {
////            JSONParser parser = new JSONParser();
////            Object resultObject = parser.parse(res);
//
////            if (resultObject instanceof JSONArray) {
////                JSONArray array = (JSONArray) resultObject;
////                for (Object object : array) {
////                    JSONObject obj = (JSONObject) object;
////                    System.out.println(obj.get("example"));
////                    System.out.println(obj.get("fr"));
////                }
////
////            } else if (resultObject instanceof JSONObject) {
////                JSONObject jsonObject = new JSONObject(res);
////                JSONObject getSth = jsonObject.getJSONObject("result");
//////                JSONObject obj = (JSONObject) resultObject;
////                System.out.println(getSth.toString());
////            Object level = getSth.get("id");
////            System.out.println(level.toString());
////
////        } catch (JSONException e1) {
////            e1.printStackTrace();
////        }
////
////    }
////}
//
//
//
//
///**
// *  WORKS!!!
// */
//
////
////public class TryConnection {
////
////    public static void main(String[] args) {
////        String body = "{\"action\":\"CreateDistribution\",\"surveyId\":\"SV_d0F2rRsAunGkBLL\"," +
////                "\"mailingListId\":\"ML_8Hq3K3UD0LDIN1j\",\"description\":\"This is a test distribution 4\"," +
////                "\"expirationDate\":\"2020-12-05 11:22:33\", \"linkType\":\"Individual\"} ";
////        QualtricsRequest h = http("https://eu.qualtrics.com/API/v3/distributions", body);
////    }
////
////    public static QualtricsRequest http(String url,String body) {
////
////        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
////            HttpPost request = new HttpPost(url);
//
////            StringEntity params = new StringEntity(body);
////            request.addHeader("X-API-TOKEN","19YxnZMVFbIhIRFUXoh0MLaMMnUexACjFfwNXzrY");
////            request.addHeader("content-type", "application/json");
////            request.setEntity(params);
////            QualtricsRequest result = httpClient.execute(request);
////
////            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
////            try {
////                JSONParser parser = new JSONParser();
////                Object resultObject = parser.parse(json);
////
////                if (resultObject instanceof JSONArray) {
////                    JSONArray array=(JSONArray)resultObject;
////                    for (Object object : array) {
////                        JSONObject obj =(JSONObject)object;
////                        System.out.println(obj.get("example"));
////                        System.out.println(obj.get("fr"));
////                    }
////
////                }else if (resultObject instanceof JSONObject) {
////                    JSONObject obj =(JSONObject)resultObject;
////                    System.out.println(obj.get("result"));
////                }
////
////            } catch (Exception e) {
////                // TODO: handle exception
////            }
////
////        } catch (IOException ex) {
////        }
////        return null;
////    }
////}









// ***************************** ClientHandler *************************************
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//import java.sql.*;
//import java.util.concurrent.locks.Lock;
//
//public class ClientHandler extends Thread {
//
//    private QualtricsRequest httpResponse;
//    private Connection conn;
//    private final DataInputStream dis;
//    private final DataOutputStream dos;
//    private final Socket s;
//    private String mailListID;
//    private String DistListID = null;
//    private final Lock lock;
//    private JDBC jdbc;
////    private boolean isFirst = false;
//
//
//    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, String mailListID, Lock lock
//            , JDBC jdbc) {
//        this.jdbc = jdbc;
//        this.mailListID = mailListID;
//        this.lock = lock;
//        this.s = s;
//        this.dis = dis;
//        this.dos = dos;
////        this.isFirst = isFirst;
////        this.httpResponse = new QualtricsRequest("https://env.qualtrics.com/API/v3/distributions");
//    }
//
//    public void run() {
//        String received;
////        while (true) {
//        try{
////
//
//            /*
//                register treatment
//             */
//
//            // TODO read from user
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//                /*
//                TODO read from client connection info(login)
//                String info = dis.readUTF();
//                String[] infoArray = info.split(";");
//                String firstName = infoArray[0];
//                String lastName = infoArray[1];
//                String email = infoArray[2];
//                 */
//
//
//            String firstName = "joni";
//            String lastName = "Pass";
//            String email = "orpaz00007@gmail.com";
//            synchronized (lock) {
////                    this.lock.lock();
//                if (jdbc.openConnection()) {
//                    String queryCheck = "SELECT * from contact WHERE email = '" + email + "'";
////                        try (Statement stmt = JDBCconn.createStatement();
////                             ResultSet rs = stmt.executeQuery(queryCheck);
////                        ) {
//                    final PreparedStatement ps = jdbc.getConn().prepareStatement(queryCheck);
////                        ps.setString(1, email);
//                    final ResultSet resultSet = ps.executeQuery();
//                    //it will print true if a row exists for the given id and false if not.
//                    if (!resultSet.next()) {
//                        String query = " insert into contact (firstname, lastname, email, password, link)"
//                                + " values (?, ?, ?, ?, ?)";
//
//
//
//                        // todo make genarate links and insert too
//                        QualtricsRequest hr = QualtricsRequest.getInstance("https://env.qualtrics.com/API/v3/distributions");
//                        String idContact = hr.createContact(new Contact(firstName, lastName, email), mailListID);
//                        hr.setUrl("https://env.qualtrics.com/API/v3/distributions");
//                        this.DistListID = hr.generateDistributionLinks(mailListID);
//                        String link = hr.ListDisLinks(DistListID,idContact);
//                        // create the mysql insert preparedstatement
//                        PreparedStatement preparedStmt = jdbc.getConn().prepareStatement(query);
//                        preparedStmt.setString(1, firstName);
//                        preparedStmt.setString(2, lastName);
//                        preparedStmt.setString(3, email);
//                        preparedStmt.setString(4, "1111");
//                        preparedStmt.setString(5, link);
//
//
////                            preparedStmt.setString(5, "https://biusocialsciences.eu.qualtrics.com/jfe/form/" +
////                                    "SV_d0F2rRsAunGkBLL?Q_DL=jJXAn5QFvqnmr0w_d0F2rRsAunGkBLL_MLRP_bpGQmnk190P37jT&Q_CHL=gl");
//                        // execute the preparedstatement
//                        preparedStmt.execute();
//                        conn.close();
//
//                    } else {
//                        while (resultSet.next()) {
//                            System.out.println("user found in DB!");
//                            String link = resultSet.getString("Link");
//                            //System.out.println(link);
//                            // todo send to client...
//                            dos.writeUTF(link);
//
//
//                        }
//
//
//                    }
////                        try (Statement stmt = conn.createStatement();
////                             ResultSet rs = stmt.executeQuery("SELECT s.song_id,s.album_id,s.artist_id,s.danceability,s.tempo,s.song_hotness,s.loudness,s.year,s.duration, s.title, a.name as 'name' FROM song s join artist a on a.id = s.artist_id ORDER BY RAND() LIMIT 10");) {
////
////                        } catch (SQLException e) {
////                            e.printStackTrace();
////                        }
////                        try (Statement stmt = conn.createStatement();
////                             ResultSet rs = stmt.executeQuery("SELECT s.song_id,s.album_id,s.artist_id,s.danceability,s.tempo,s.song_hotness,s.loudness,s.year,s.duration, s.title, a.name as 'name' FROM song s join artist a on a.id = s.artist_id ORDER BY RAND() LIMIT 10");) {
////
////                        } catch (SQLException e) {
////                            e.printStackTrace();
////                        }
////                        this.listOfContacts.add(new Contact(firstName, lastName, email));
////                    lock.unlock();
//                    System.out.println(firstName + " " + lastName + " " + email);
//
////                dos.writeUTF("blabla");
//
//                }
//
//
//            }
//        } catch (SQLException e) {
//            System.out.println("ERROR executeQuery - " + e.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////        }
//    }
//    public String getListID () {
//        while(true) {
//            if (this.DistListID != null) {
//                return this.DistListID;
//            }
//            try {
//                sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
////    public boolean isFirst() {
////        return this.isFirst;
////    }
//}

