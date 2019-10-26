//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Date;
//import java.util.Properties;
//public class QualtricsGetPropertyValues {
//    private String result = "";
//    private InputStream inputStream;
//    public String getPropValues() throws IOException {
//
//    try {
////        surveyID=SV_d0F2rRsAunGkBLL
////        LibraryID=\"UR_1EXBDtXrhVkFlbL\"
////        expirationDate:\"2020-12-05 11:22:33\"
//        Properties prop = new Properties();
//        String propFileName = "config.properties";
//
//        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//
//        if (inputStream != null) {
//            prop.load(inputStream);
//        } else {
//            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//        }
//
//        Date time = new Date(System.currentTimeMillis());
//
//        // get the property value and print it out
//        String surveyID = prop.getProperty("surveyID");
//        String LibraryID = prop.getProperty("LibraryID");
//        String expirationDate = prop.getProperty("expirationDate");
//
//        result = surveyID + ", " + LibraryID + ", " + expirationDate;
////        System.out.println(result + "\nProgram Ran on " + time + " by user=" + user);
//    } catch (Exception e) {
//        System.out.println("Exception: " + e);
//    } finally {
//        inputStream.close();
//    }
//    return result;
//}
//}