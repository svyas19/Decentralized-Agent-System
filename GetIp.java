/*
 
 */
package pingpong;

/**
 *
 * @author Shiv
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Vector;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.*;
import java.util.*;

public class GetIp {

    //Declarations
    public static String[] IPList = new String[10000];

    //Creating methods whih returns available ip address in the same network;
    public String[] SendIP() throws UnknownHostException, IOException {
        String localhostIp = InetAddress.getLocalHost().getHostAddress();//Taking your IP address
        String intialPart = null;
        int ipno = 0;
        System.out.println(localhostIp);
        for (int i = 0; i < 255; i++) {
            //The loop can run maximum of 255 times ad IPV4 Address
            if (localhostIp.charAt(i) == '.') {
                //If "." found take the substring i.e. if Ip = 172.122.130.11 take 172.122.130. and add i at the end and check 

                intialPart = localhostIp.substring(0, i + 1);
                InetAddress ipaddr = InetAddress.getByName(intialPart + i);
                //Search each ip address and check whether it is reachbale or not and if reachable add it to IP List
                System.out.println(ipaddr);
                if (ipaddr.isReachable(10000)) {
                    // System.out.println("Reached");
                    IPList[ipno] = ipaddr.toString();
                    ipno++;

                } else {
                    //System.out.println("NO");
                }

            }

        }

        return IPList;

    }
    /* //Testing Code 
public static void main(String[] args) throws IOException {
        GetIp gp = new GetIp();
        String[] a = new String[100000];
        gp.SendIP();
        int i = 0;
        System.out.println("2");
        while(a[i]!=null)
        {   
            System.out.println("1");
            System.out.println("a" + a[i]);
            i++;
        }
        
    }
    }
     */
}
