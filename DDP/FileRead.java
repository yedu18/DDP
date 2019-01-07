package DDP;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.Arrays;
import java.util.Scanner;


public class FileRead
{
    public int[][] Read() throws FileNotFoundException
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
}

