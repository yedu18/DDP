package DDP;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Arrays;
import java.util.Scanner;


public class FileRead
{
    public int[][] mapRead() throws FileNotFoundException
    {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("DDP/map_network.txt")));
        int rows = 40;
        int columns = 40;
        int [][] myArray = new int[rows][columns];
        for (int i=0; i<myArray.length; i++) {
            String[] line = sc.nextLine().trim().split(" ");
            for (int j=0; j<line.length; j++) {
                //System.out.print(Integer.parseInt(line[j])+" ");
                myArray[i][j] = Integer.parseInt(line[j]);
            }
            //System.out.println();
        }
        return myArray;
    }

    public int[][] flightRead() throws FileNotFoundException
    {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("DDP/flights.txt")));
        int rows = 7;
        int columns = 8;
        Flight [][] myArray = new Flight[rows][columns];
        for (int i=0; i<myArray.length; i++)
        {
            String[] line = sc.nextLine().trim().split(" ");
            Flight[i].setFlight_no(line[0]);
            Flight[i].setStart_node(line[1]);
            Flight[i].setEnd_node(line[2]);
            Flight[i].setStart_time(line[3]);
            Flight[i].setSpeed(line[4]);
            Flight[i].setSep(line[5]);
            Flight[i].setRun_len(line[6]);
            //System.out.println();
        }
        return myArray;
    }
}

