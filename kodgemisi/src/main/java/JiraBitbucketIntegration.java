import java.io.*;
/*
public class JiraBitbucketIntegration {

    public static void main(String[] args) {


        String s = null;
        Process p = null, p1 = null, p2 = null, p3 = null;
        getData(p, p1, s);
        readJson();
        downloadModule();
        uploadProject();

    }

    public static void getData(Process p,Process p1,String s){

        try {
            p = Runtime.getRuntime().exec("curl -u aydinnyunus:112358yunus -X GET -H 'Content-Type: application/json' http://10.0.2.4:8080/rest/api/2/project");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                try {
                    FileWriter myWriter = new FileWriter("/home/aydinnyunus/Desktop/filename.json");
                    myWriter.write(s);
                    System.out.println("Successfully wrote to the file.");
                    myWriter.close();
                }
                catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }finally {

                }
            p.waitFor();
            p.destroy();

        } catch (Exception e) {}

    }

    public static void readJson(){

        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("/home/aydinnyunus/Desktop/filename.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray projectList = (JSONArray) obj;
            System.out.println(projectList);

            projectList.forEach( emp-> {
                try {
                    parseProjectData((JSONObject)emp);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    public static void parseProjectData(JSONObject projects) throws IOException {
        JSONObject project = (JSONObject) projects.get("projects");

        String projectName = (String) projects.get("name");
        System.out.println("Project Name : " + projectName);

        FileWriter myWriter = new FileWriter("projectNames.txt");
        myWriter.write(projectName);
        myWriter.close();
    }

    public static void downloadModule(){

        Process p2;

        try{
            p2 = Runtime.getRuntime().exec("pip install stashy");
            p2.waitFor();
            p2.destroy();
            System.out.println("Stashy downloaded succesfully.");

        }
        catch (Exception e){
            System.out.println("ERROR");
        }

    }

    public static void uploadProject(){

        Process p3;

        try{
            p3 = Runtime.getRuntime().exec("python3 /home/aydinnyunus/Desktop/test.py");
            p3.waitFor();
            p3.destroy();
            System.out.println("Project upload to BitBucket succesfully.");
        }
        catch (Exception e){
            System.out.println("ERROR");
        }
    }
}
*/