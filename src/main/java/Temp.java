//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.client.methods.HttpRequestBase;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.util.EntityUtils;
//
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipInputStream;
//public class Temp {
//
//        private static final String SURVEY_ID = "SV_d0F2rRsAunGkBLL";
//        private static final String STATUS_COMPLETE = "complete";
//        private static final String API_TOKEN = ("19YxnZMVFbIhIRFUXoh0MLaMMnUexACjFfwNXzrY");
//        private static final String DATA_CENTER = ("eu");
//
//        private static HttpClient httpClient;
//        private static ObjectMapper mapper;
//
//
//        public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException
//        {
//            httpClient = HttpClientBuilder.create().build();
//            mapper = new ObjectMapper();
//
//            // Step 1: Start the export
//            URI uri = new URIBuilder()
//                    .setScheme("https")
//                    .setHost(String.format("%s.qualtrics.com", DATA_CENTER))
//                    .setPath("/API/v3/surveys/" + SURVEY_ID + "/export-responses")
//                            .build();
//
//            HttpPost post = new HttpPost(uri);
//            post.setHeader("X-API-TOKEN", API_TOKEN);
//            post.setHeader("Content-Type", "application/json");
//
//            StringEntity body = new StringEntity(String.format("{ \"format\": \"csv\" }"));
//            post.setEntity(body);
//            JsonNode responseJson = sendRequest(post);
//            String progressId = responseJson.get("result").get("progressId").asText();
//            URI exportStatusUrl = new URIBuilder()
//                    .setScheme("https")
//                    .setHost(String.format("%s.qualtrics.com", DATA_CENTER))
//                    .setPath("/API/v3/surveys/" + SURVEY_ID + "/export-responses/" + progressId)
//                    .build();
//
//            // Step 2: Wait for the export to complete
//            String fileId;
//            while (true)
//            {
//                HttpGet statusGet = new HttpGet(exportStatusUrl);
//                statusGet.setHeader("X-API-TOKEN", API_TOKEN);
//                JsonNode statusJson = sendRequest(statusGet);
//                if (statusJson.get("result").get("status").asText().equals(STATUS_COMPLETE))
//                {
//                    fileId = statusJson.get("result").get("fileId").asText();
//                    break;
//                }
//                Thread.sleep(2000);
//            }
//
//            // Step 3: Download the exported file
//            URI fileUrl = new URIBuilder()
//                    .setScheme("https")
//                    .setHost(String.format("%s.qualtrics.com", DATA_CENTER))
//                    .setPath("/API/v3/surveys/" + SURVEY_ID + "/export-responses/" + fileId + "/file")
//                    .build();
//            HttpGet statusGet = new HttpGet(fileUrl);
//            statusGet.setHeader("X-API-TOKEN", API_TOKEN);
//            HttpResponse response = httpClient.execute(statusGet);
//
//            // Step 4: Extract exported file
//            ZipInputStream zs = new ZipInputStream(response.getEntity().getContent());
//            ZipEntry entry;
//            while ((entry = zs.getNextEntry()) != null)
//            {
//                FileOutputStream out = new FileOutputStream("./" + entry.getName());
//                IOUtils.copy(zs, out);
//                out.close();
//            }
//        }
//
//        private static JsonNode sendRequest(HttpRequestBase httpRequest) throws IOException
//        {
//            HttpResponse response = httpClient.execute(httpRequest);
//            String body = EntityUtils.toString(response.getEntity());
//            return mapper.readTree(body);
//        }
//    }
