import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class QualtricsRequest {
    private Map<String,String[]> headers;
    private String message;



    public void setHeaders(Map<String, String[]> headers) {
        this.headers = headers;
    }

    private String url;
    private static QualtricsRequest instance =null;
    private final String token;
    private String surveyID = "SV_d0F2rRsAunGkBLL";
    private String LibraryID= "\"UR_1EXBDtXrhVkFlbL\"";
    private String validDate = "\"2020-12-05 11:22:33\"";



    private QualtricsRequest(String url, String surveyID, String LibraryID){
        final int size = 10;
        token = "X-API-TOKEN: 19YxnZMVFbIhIRFUXoh0MLaMMnUexACjFfwNXzrY";
        this.url = url;
        this.surveyID = surveyID;
        this.LibraryID= LibraryID;
        headers = new HashMap<String,String[]>();
        setMap();

    }

    public static QualtricsRequest getInstance(String url,String surveyID,String LibraryID)
    {
        // To ensure only one instance is created
        if (instance == null)
        {
            instance = new QualtricsRequest(url,surveyID,LibraryID);
        }
        return instance;
    }


    /**
     * this method implemnt DELETE CALL. called when surveyID changed
     * @param url
     * @return
     */
    private boolean deleteRequest(String url) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpDelete httpDelete = new HttpDelete(url);

            System.out.println("Executing request " + httpDelete.getRequestLine());
            httpDelete.addHeader("X-API-TOKEN", "19YxnZMVFbIhIRFUXoh0MLaMMnUexACjFfwNXzrY");
            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);

                }
            };
            String responseBody = httpclient.execute(httpDelete, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }
    }

    /**
     * this method execute POST call.
     * @param urlParameters urlParameters
     * @param method method
     * @return response
     */
        private String execute(String urlParameters, String method) {

        String[] parts;
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);

            StringEntity params = new StringEntity(urlParameters);
            for (String s: this.headers.get(method)) {
                parts = s.split(": ");
                request.addHeader(parts[0], parts[1]);
            }
            request.setEntity(params);
            org.apache.http.HttpResponse result = httpClient.execute(request);

            return EntityUtils.toString(result.getEntity(), "UTF-8");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setMap(){
        headers.put("POST",new String[]{token, "Content-Type: application/json"});
        headers.put("GET",new String[]{token});
        headers.put("PUT",new String[]{token, "Content-Type: application/json"});
        headers.put("DELETE",new String[]{token});
    }

    public Map<String, String[]> getHeaders() {
        return headers;
    }


    /**
     * this method recive string and genarate GET call
     * @param url String
     * @return response
     * @throws Exception
     */
    private String sendGet(String url) throws Exception {
        String[] parts;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        String he[] = this.headers.get("GET");
        for (String s: he) {

            parts = s.split(": ");
            con.setRequestProperty(parts[0], parts[1]);
        }


        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        return response.toString();
    }



    public String getSurveyID() {
        return surveyID;
    }

    public void setSurveyID(String surveyID) {
        this.surveyID = surveyID;
    }

    public String getLibraryID() {
        return LibraryID;
    }

    public void setLibraryID(String libraryID) {
        LibraryID = libraryID;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }





//        HttpURLConnection connection = null;
//        String[] parts;
//        try {
////        Create connection
//            URL url = new URL(this.url);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod(method);
//            for (String s: this.headers) {
//                parts = s.split(": ");
//                connection.setRequestProperty(parts[0], parts[1]);
//            }
//            connection.setUseCaches(false);
//            connection.setDoOutput(true);
//
//            //Send request
//            DataOutputStream wr = new DataOutputStream(
//                    connection.getOutputStream());
//            wr.writeBytes(urlParameters);
//            wr.close();
//
//            //Get Response
//            InputStream is = connection.getInputStream();
//            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
//            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
//            String line;
//            while ((line = rd.readLine()) != null) {
//                response.append(line);
//                response.append('\r');
//            }
//            rd.close();
////            ParseJson(response.toString());
//            return response.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//    }

    /**
     * 2Create a distribution without sending any emails.
     * The created distribution will be of type "GeneratedInvite".
     * The survey must be active before you can generate a distribution invite.
     * Refer to the documentation below for further information regarding usage of this API's result.
     * @return Json string
     */
    public String generateDistributionLinks(String mailList){

        String body = "{\"action\":\"CreateDistribution\",\"surveyId\":" +surveyID +", \"mailingListId\":\""+mailList+"\", " +
                "\"description\":\"This is a test distribution 4\"," +
                "\"expirationDate\":"+validDate+", \"linkType\":\"Individual\"} ";

        String json =  this.execute(body,"POST");
        return ParseJson(json,"result","id");
    }

    /**
     * Retrieve a list of individual distribution links for an existing distribution,
     * for unsubscribed contacts in specified mailing list, the link will be null.
     * Refer to the documentation below for further information regarding usage of this API's result.
     * @param listID from generateDistributionLinks
     * @return
     */
    public String ListDisLinks(String listID, String contactID)
    {

//        String servuyID ="SV_d0F2rRsAunGkBLL";
        String res = null;
        String id = surveyID.replace("\"", "");
        String body = "https://eu.qualtrics.com/API/v3/distributions/"+listID+"/links?surveyId="+id;
        try {
            res = this.sendGet(body);
            JSONObject obj = new JSONObject(res);
            JSONArray a= obj.getJSONObject("result").getJSONArray("elements");
            System.out.println(a.toString());
            for (int i=0; i < a.length(); i++) {
                JSONObject element = a.getJSONObject(i);
                if (element.get("contactId").equals(contactID)) {
                    res = element.get("link").toString();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Creates a new mailing list
     * @param details
     * @param name
     * @return
     */
    public String createMailingList (String [] details, String name) {
        String body = "{\"category\": \"ProjectCategory\",\"libraryId\": "+LibraryID+ ",\"name\": " + name + "} ";

        String body1 = "{\"category\":\"ProjectCategory\",\"libraryId\":\"UR_1EXBDtXrhVkFlbL\"," +
                "\"name\":" + name + "} ";
        try {
            String json =  this.execute(body, "POST");

            return ParseJson(json,"result","id");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a contact in the specified mailing list
     * @param subject
     * @return
     */
    public String createContact(String subject,String mailListId) {
        this.setUrl("https://env.qualtrics.com/API/v3/mailinglists/"+mailListId+"/contacts");
        String body = "{\"email\": " + subject + ",\"unsubscribed\": true} ";
        try {
            String json =  this.execute(body, "POST");
            return ParseJson(json,"result","id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * this method call API delete Mailing lisr
     * @param mailListID mailListID
     * @return true if seccess
     */
    public boolean deleteMailungList(String mailListID){
        String url = "https://env.qualtrics.com/API/v3/mailinglists/"+mailListID+"/";
        return this.deleteRequest(url);

    }

    /**
     * this method parse json object
     * @param json string
     * @param s1 string
     * @param s2 string
     * @return return string
     */
    public String ParseJson(String json,String s1,String s2){
        JSONObject jsonObject = null;
        JSONObject getSth = null;
        try {
            jsonObject = new JSONObject(json);

        getSth = jsonObject.getJSONObject(s1);
        System.out.println(getSth.toString());
        Object level = null;
        level = getSth.get(s2);
        return level.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
