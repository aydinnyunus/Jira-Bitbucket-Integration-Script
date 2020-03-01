package com.example.helloworld;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class test {


    /**
     * This is the main method.  It creates the variables used through the
     * calls to the five functions and calls them in the proper order.  It
     * also handles any errors and writes a final SUCCESS or FAILURE message.
     *
     * @param args Unused.
     */
    public static void main(String[] args){


        String s;
        Process p,p1;
        try {
            p = Runtime.getRuntime().exec("curl -u aydinnyunus:112358yunus -X GET -H 'Content-Type: application/json' http://10.0.2.4:8080/rest/api/2/project");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                try {
                    FileWriter myWriter = new FileWriter("filename.txt");
                    myWriter.write(s);
                    System.out.println("Successfully wrote to the file.");
                    myWriter.close();
                }
                catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            p.waitFor();
            p.destroy();

        } catch (Exception e) {}

        try{
            p1 = Runtime.getRuntime().exec("mv filename.txt filename.json");
            p1.waitFor();
            p1.destroy();}
        catch (Exception e){}
        
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("/home/aydinnyunus/Desktop/filename.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray projectList = (JSONArray) obj;
            System.out.println(projectList);


            //Iterate over employee array
            projectList.forEach( emp->parseEmployeeObject((JSONObject)emp));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static void parseEmployeeObject(JSONObject projects)
    {
        //Get employee object within list
        JSONObject project = (JSONObject) projects.get("projects");

        //Get employee first name
        String projectName = (String) projects.get("name");
        System.out.println("Name" + projectName);

        FileWriter myWriter = new FileWriter("projectNames.txt");
        myWriter.write(projectName);
        myWriter.close();
    }

    }
    
}
