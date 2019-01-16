package DDP;

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

    public Flight[] flightRead() throws FileNotFoundException
    {
        Scanner sc = new Scanner(new BufferedReader(new FileReader("DDP/flights.txt")));
        int rows = 7;
        int columns = 8;
        Flight [] myArray = new Flight[rows];
        for (int i=0; i<myArray.length; i++)
        {
            int[] line = Arrays.stream(sc.nextLine().trim().split(" ")).mapToInt(Integer::parseInt).toArray();
            Flight new_flight = new Flight();
            new_flight.setFlight_no(line[0]);
            new_flight.setStart_node(line[1]);
            new_flight.setEnd_node(line[2]);
            new_flight.setStart_time(line[3]);
            new_flight.setSpeed(line[4]);
            new_flight.setSep(line[5]);
            new_flight.setRun_len(line[6]);
            myArray[i] = new_flight;
            //System.out.println();
        }
        return myArray;
    }
}

