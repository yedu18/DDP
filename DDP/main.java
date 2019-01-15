package DDP;

import java.io.FileNotFoundException;
import DDP.Dijkstra;
import DDP.Flight;

public class main
{
    public static void main(String[] args) throws FileNotFoundException
    {

        int[][] adjacencyMatrix = new int[40][40];
        /*int[][][] test = new int[2][3][5];
        int[] tester = {1, 2, 3, 4};
        test[1][2] = tester;
        System.out.println(Arrays.deepToString(test));
        System.out.println(Arrays.toString(test[1][2]));*/
        FileRead fr = new FileRead();
        Dijkstra d = new Dijkstra();
        adjacencyMatrix = fr.mapRead();
        DDP.Flight flightInfo = fr.flightRead();
        for (int i = 0; i < adjacencyMatrix.length; i++)
        {
            d.dijkstra(adjacencyMatrix, i);
        }
    }
}
