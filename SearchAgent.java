/*
This Class allows Ping or Pong Agent the ability to find if there are any other Ping or Pong Agents running on the same system.
 */
package pingpong;

/**
 *
 * @author Shiv
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SearchAgent {

    //Declarations
    public String[] arr;

    public SearchAgent() throws IOException {
        //Declaring an array with big length to store agent data
        //this.arr beacuse declaring threads for each and every agents
        this.arr = new String[5000];

        //Calling Methods to get data
        sendList();
        sendAgent();
    }

    public void sendList() throws IOException {
        //Building Process to execute commands in command prompt
        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c ", "jps");
        //"JPS" Command finds all the java process running on the system
        //Handling Error
        builder.redirectErrorStream(true);
        //Execution of command , .start() starts the execution
        Process p = builder.start();
        //Creating a buffere reader to read output from command execution
        //Bufferereader beacuse we dont know the exact length of the data that will come
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        //Declaration  of string which will be used to read from bufferreader
        String line;
        int i = 0;
        while (true) {
            //Reading Lines
            line = r.readLine();
            if (line == null) {
                break;
                //If no more data stop the loop
            }

            //JPS will provide all the java process running on the system
            //This output might contain java process which are not part of agent system
            //Separating the process which are the part of AgentSystem
            if (line.contains(("AgentSystem"))) {
                StringTokenizer st = new StringTokenizer(line, " ");
                arr[i] = st.nextToken();
                //Storing and Printing Process ID's of Java Agent System
                System.out.println(arr[i]);
                i++;
            }
        }
    }

    //Find and Send Agents from process ID
    public void sendAgent() throws IOException {
        int i = 0;
        while (arr[i] != null) {
            //Taking one by one all the process ID from array
            List<String> list = new ArrayList<String>();
            //Creating list when you have "-" and "|" both in cmd command it java gets confused whether it is a string or ccommand symbol
            list.add("cmd.exe");
            list.add("/c");
            //Repeating the same command for all the elements in array
            list.add("netstat -ano | findstr " + arr[i]);
            //Building Process for execution , Command is taken from list
            ProcessBuilder builder = new ProcessBuilder(list);
            //error handling
            builder.redirectErrorStream(true);
            //Start execution of coomand
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            line = r.readLine();
            if (line != null) {
                //Read untill null=No more data left
                //  System.out.println("the line is "+line);
                //Separating the Port number from the String read
                StringTokenizer asd = new StringTokenizer(line, " ");
                System.out.println(asd.nextToken());
                StringTokenizer asd1 = new StringTokenizer(asd.nextToken(), ":");
                asd1.nextToken();
                //Printin Port
                System.out.println("port is " + asd1.nextToken());
                //Printing extra details of agent
                System.out.println(line);
            }
            i = i + 1;
            //Increamenting the loop
        }
    }

    public void searchAgents() throws IOException {
        //cmdTest ct = new cmdTest();
        SearchAgent sa = new SearchAgent();
        //sa.sendList();
        //sa.sendAgent();
    }

}
