package DDP;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class main
{
    public static int[][] generateRouteMatrix(Flight[] flightInfo, int[][][] pathMap)
    {
        int[][] route_matrix = new int[7][40];
        for(int i=0;i<7;i++)
        {
            int[] source = pathMap[flightInfo[i].getStart_node()][flightInfo[i].getEnd_node()];
            int[] flightRoute = Arrays.copyOfRange(source,0,source.length);
            System.out.println(Arrays.toString(flightRoute));
            for(int j=0;j<40;j++)
            {
                if(Arrays.binarySearch(flightRoute,j)==1)
                    route_matrix[i][j]=1;
                else
                    route_matrix[i][j]=0;

            }
        }
        return route_matrix;
    }

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
        Flight[] flightInfo = fr.flightRead();
        int[][][] pathMap = new int[40][40][40];
        for (int i = 0; i < adjacencyMatrix.length; i++)
        {
            pathMap = d.dijkstra(adjacencyMatrix, i, pathMap);
        }
        System.out.println("\n\n\n\n\n\n\n\n");
        System.out.println(Arrays.toString(pathMap[flightInfo[0].getStart_node()][flightInfo[0].getEnd_node()]));
        //System.out.println(flightInfo[1].getStart_node());

        int[][] route_matrix = new int[7][40];
        int[][] time_matrix = new int[7][40];
        int[][] conflict_matrix = new int [7][40];
        route_matrix = generateRouteMatrix(flightInfo,pathMap);
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<40;j++)
            {
                    System.out.print(route_matrix[i][j]);

            }
            System.out.println();
        }
        //time_matrix = generateTimeMatrix(flightInfo,pathMap);
        //conflict_matrix = generateConflictMatrix(flightInfo,pathMap);

    }
}
