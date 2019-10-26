import java.sql.SQLException;
import java.sql.Statement;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server implements Observer{

    private List<Contact> listOfContacts;
    private final Lock lock;
    private String mailID;

    private JDBC connJDBC;
    private String listDistID = null;
    private int port;


    public Server(int port) {
        this.connJDBC = new JDBC();
        this.port = port;

        this.lock = new ReentrantLock();
//        Contact ez = new Contact("ez", "ra", "ezra@yellow.com");
//        Contact bnei = new Contact("bnei", "akiva", "bneiakiva@org.com");
//        listOfContacts.add(ez);
//        listOfContacts.add(bnei);
        try {
            this.startServer();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

//        public void run(){
//            try {
//                System.out.println("IM in run");
////                this.startServer();
//                System.out.println("IM after start server");
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }
//
//        }

    public List<Contact> getListOfContacts() {
        return this.listOfContacts;
    }

    /**
     * start server
     * @throws IOException
     */
    public void startServer() throws IOException {
        // server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(port);
//        String surveyID = "SV_d0F2rRsAunGkBLL";
//        String LibraryID = "\"UR_1EXBDtXrhVkFlbL\"";
        File file = new File("config.txt");
//        InputStream in = getClass().getResourceAsStream("/config.txt");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));


        try {
            List<String> res = Load.readfile(file);
            String surveyID = res.get(0);
            String LibraryID = res.get(1);
            QualtricsRequest hr = QualtricsRequest.getInstance("https://biusocialsciences.qualtrics.com/API/v3/mailinglists", surveyID, LibraryID);
            hr.setValidDate(res.get(2));
            String listName = res.get(3);
            mailID = hr.createMailingList(null, listName);

            // running infinite loop for getting
            // client request
            while (true) {
                // creat runnable that listen to config file and raise an event when it's changed
                Runnable myRunnable =
                        new Runnable() {
                            public void run() {
                                ResourceLoadingExample rs = new ResourceLoadingExample();
                                rs.addObserver(Server.this);
                                rs.listenFile();
//
                            }
                        };
                Thread thread = new Thread(myRunnable);
                // start thread
                thread.start();


                Socket s = null;

                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                    System.out.println(timeStamp);
                    System.out.println("IM before accept");

                    // socket object to receive incoming client requests
                    s = serverSocket.accept();
//                    CrunchifyGetPropertyValues properties = new CrunchifyGetPropertyValues();
//                    properties.getPropValues();
                    System.out.println("IM after afte accept");

                    System.out.println("A new client is connected : " + s);

                    // obtaining input and out streams
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                    System.out.println("Assigning new thread for this client");

                    // create a new thread object
                    ClientHandler c = new ClientHandler(s, dis, dos, mailID, lock, connJDBC);
//                    Thread t =
//                    isFirstClient = false; // updating the boolean variable
                    // Invoking the start() method
                    ((Thread) (c)).start();
//

                } catch (Exception e) {
                    s.close();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this method change members like IDSurvey when its changed
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("File Changed rezachhh");

        File file = new File("config.txt");
//        InputStream in = getClass().getResourceAsStream("/config.txt");
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            // chnge the members QulatricsRequest
            List<String> res = Load.readfile(file);
            QualtricsRequest hr = QualtricsRequest.getInstance("https://env.qualtrics.com/API/v3/mailinglists", null,null);
            hr.setSurveyID(res.get(0));
            hr.setLibraryID(res.get(1));
            hr.setValidDate(res.get(1));
            if (connJDBC.openConnection()) {
                try {
                    Statement stmt = connJDBC.getConn().createStatement();
                    String query = "DELETE FROM contact";
                    int deletedRows = stmt.executeUpdate(query);
                    connJDBC.getConn().close();
                } catch (SQLException e) {
                    e.printStackTrace();

                }

            }
            // call to deleteMailungList
            boolean flag = hr.deleteMailungList(mailID);
            System.out.println(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}
