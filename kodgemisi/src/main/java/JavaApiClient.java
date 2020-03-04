import com.atlassian.bitbucket.project.Project;
import com.cdancy.bitbucket.rest.BitbucketClient;
import com.cdancy.bitbucket.rest.options.CreateProject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class JavaApiClient {


    /*
    Yorumlar..
     */
    public static List<String> getProjectNames(String baseURL, String subpageURL, String loginUserName, String loginPassWord){

        // Create http get request client.
        HttpGet httpGet = new HttpGet(baseURL + subpageURL);

        // Create get request authentication header.
        String auth = loginUserName + ":" + loginPassWord;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);

        // Set http header.
        httpGet.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        httpGet.setHeader("Content-type", "application/json");

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            List<String> returnValue = new ArrayList<>();
            String entities = EntityUtils.toString(response.getEntity());
            System.out.println(entities);
            JSONArray responseArray = new JSONArray(entities);

            for (int i=0; i< responseArray.length();i++){
                JSONObject obj = (JSONObject) responseArray.get(i);
                String id = obj.getString("key");
                System.out.println("====KEY====: "+id);
                returnValue.add(id);
            }
            return returnValue;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean createBitbucketProject(String bitbucketURL, String bitbucketProjectURL, String bitbucketUserName, String bitbucketPassword, String projectName) throws AuthenticationException, IOException {

        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(bitbucketURL + bitbucketProjectURL);

        String json = "{\"key\":" + "\"" + projectName + "\"}";
        HttpEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(bitbucketUserName, bitbucketPassword);
        httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
        CloseableHttpResponse response = client.execute(httpPost);
        if (response.getStatusLine().getStatusCode() == 201) {
            System.out.println("Project created successfully");
        }
        if (response.getStatusLine().getStatusCode() == 409){
            System.out.println("Project name exists");
        }
        client.close();

        return true;
    }

    public static void main(String[] args) throws IOException, AuthenticationException {

        String jiraUserName = "aydinnyunus";
        String bitbucketUserName = "aydinnyunus";
        String jiraPassword = "112358yunus";
        String bitbucketPassword = "112358";

        String jiraURL = "http://localhost:8080";
        String bitbucketURL = "http://localhost:7990";
        String jiraProjectURL = "/rest/api/2/project";
        String bitbucketProjectURL = "/rest/api/1.0/projects";



        List<String> jiraProjectNames = getProjectNames(jiraURL, jiraProjectURL, jiraUserName, jiraPassword);
        if (jiraProjectNames != null) {
            for (String projectName : jiraProjectNames) {
                createBitbucketProject(bitbucketURL, bitbucketProjectURL, bitbucketUserName, bitbucketPassword, projectName);
            }
        }else {
            System.out.println("bulunamadı.");
        }

    }


    public static void uploadProjects(String username, String bitbucketPassword, String bitbucketURL, String bitbucketProjectURL, List<String> id){
/*
        BitbucketClient.Builder client = BitbucketClient.builder();
        client.credentials(username + ":" + bitbucketPassword);
        client.endPoint(bitbucketURL + bitbucketProjectURL);
        System.out.println("Size : " + id.size());
        client.build();
*//*
        BitbucketClient.Builder builder = BitbucketClient.builder();
        builder.endPoint("http://localhost:7990/rest/api/1.0/projects");
        builder.credentials("aydinnyunus:112358");
        BitbucketClient client = builder // will base64 for you if not already done. Can optionally use token auth as well.
                .build();
*//*
        BitbucketClient.Builder builder = BitbucketClient.builder();
        builder.endPoint(bitbucketURL + bitbucketProjectURL);
        builder.credentials(username + ":" + bitbucketPassword);
        BitbucketClient client = builder.build();
*/
        BitbucketClient client = BitbucketClient.builder()
                .endPoint("http://localhost:7990/")
                .token("MjE1MTEzMjI0MTQwOs7e95/kiKQE85FWVka/yF7lTI0w") // will base64 for you if not already done. Can optionally use token auth as well.
                .build();

        for(int i=0;i<id.size();i++){


            CreateProject project = CreateProject.create(id.get(i),id.get(i),"Project" + i,"Avatar" + i);
            Project pro = (Project) client.api().projectApi().create(project);

        }

    }

}
