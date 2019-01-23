package DDP;

import java.io.FileNotFoundException;
import java.util.Arrays;


public class main
{
    public static int[][] generateRouteMatrix(Flight[] flightInfo, int[][][] pathMap)
    {
        int[][] route_matrix = new int[7][15];
        for(int i=0;i<7;i++)
        {
            int[] source = pathMap[flightInfo[i].getStart_node()][flightInfo[i].getEnd_node()];
            int[] flightRoute = Arrays.copyOfRange(source,0,source.length);
            System.out.println(Arrays.toString(flightRoute));
            for(int j=0;j<flightRoute.length;j++)
            {
                route_matrix[i][flightRoute[j]]=1;
            }
        }
        return route_matrix;
    }

    public static int[][] generateTimeMatrix(Flight[] flightInfo, int[][][] pathMap, int[][] map)
    {
        int[][] time_matrix = new int[7][15];
        for(int i=0;i<7;i++)
        {
            int start_time = flightInfo[i].getStart_time();
            int current_time=start_time;
            int[] source = pathMap[flightInfo[i].getStart_node()][flightInfo[i].getEnd_node()];
            int[] flightRoute = Arrays.copyOfRange(source,0,source.length);
            time_matrix[i][flightRoute[0]]=current_time;
            for(int j=1;j<flightRoute.length;j++)
            {
                current_time= current_time + map[flightRoute[j-1]][flightRoute[j]];
                time_matrix[i][flightRoute[j]]=current_time;
            }
        }
        return time_matrix;
    }

    public static int[][] generateConflictMatrix(Flight[] flightInfo, int[][] route_matrix, int[][] time_matrix)
    {
        int[][] conflict_matrix = new int[7][15];
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<15;j++)
            {
                for(int k=0;k<7;k++)
                {
                    if(i==k||(route_matrix[i][j]==0 || route_matrix[k][j]==0))
                        continue;
                    //System.out.println(flightInfo[i].getSep());

                    if (Math.abs((time_matrix[i][j] - time_matrix[k][j])) < flightInfo[i].getSep())
                       conflict_matrix[i][j] = 1;
                 }
            }
        }
        return conflict_matrix;
    }

    public static int[][] updateTimeMatrix(int[][] time_matrix, int flight, int node, int wait)
    {
        for(int j=0;j<15;j++)
        {
            if(time_matrix[flight][j]>=time_matrix[flight][node])
                time_matrix[flight][j]+=wait;
        }
        return time_matrix;
    }

//    public static int[][] updateConflictMatrix(Flight[] flightInfo, int[][] route_matrix, int[][] time_matrix)
//    {
//    }


    public static int[][] resolveConflicts(Flight[] flightInfo, int[][][] pathMap, int[][] map, int[][] route_matrix, int[][] time_matrix, int[][] conflict_matrix)
    {
        //int [][] final_time_matrix = new int[7][15];
        int wait=0, flight, node;
        int total_wait=0;
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<15;j++)
            {
                while(conflict_matrix[i][j]==1)
                {
                    for(int k=0;k<7;k++)
                    {
                        if(i==k)
                            continue;
                        if (Math.abs((time_matrix[i][j] - time_matrix[k][j])) < flightInfo[i].getSep())
                        {
                            flight = (time_matrix[i][j] > time_matrix[k][j])?i:k;
                            node = j;
                            wait = flightInfo[i].getSep() - Math.abs((time_matrix[i][j] - time_matrix[k][j]));
                            time_matrix = updateTimeMatrix(time_matrix,flight,node,wait);
                            conflict_matrix = generateConflictMatrix(flightInfo,route_matrix,time_matrix);
                        }
                    }
                }
            }
            total_wait+=wait;
        }
        System.out.println();
        System.out.println("Total wait = "+total_wait);
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<15;j++)
            {
                //System.out.println("blah");
                System.out.print(time_matrix[i][j]+"\t");

            }
            System.out.println();
        }
        System.out.println();
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<15;j++)
            {
                //System.out.println("blah");
                System.out.print(conflict_matrix[i][j]+"\t");

            }
            System.out.println();
        }
        return time_matrix;

    }


    public static void main(String[] args) throws FileNotFoundException
    {

        int[][] map = new int[15][15];
        /*int[][][] test = new int[2][3][5];
        int[] tester = {1, 2, 3, 4};
        test[1][2] = tester;
        System.out.println(Arrays.deepToString(test));
        System.out.println(Arrays.toString(test[1][2]));*/
        FileRead fr = new FileRead();
        Dijkstra d = new Dijkstra();
        map = fr.mapRead();
        Flight[] flightInfo = fr.flightRead();
        int[][][] pathMap = new int[15][15][15];
        for (int i = 0; i < map.length; i++)
        {
            pathMap = d.dijkstra(map, i, pathMap);
        }
        System.out.println("\n\n\n\n\n\n\n\n");
        //System.out.println(Arrays.toString(pathMap[flightInfo[0].getStart_node()][flightInfo[0].getEnd_node()]));
        //System.out.println(flightInfo[1].getStart_node());

        int[][] route_matrix = new int[7][15];
        int[][] time_matrix = new int[7][15];
        int[][] conflict_matrix = new int [7][15];
        route_matrix = generateRouteMatrix(flightInfo,pathMap);
//        for(int i=0;i<7;i++)
//        {
//            for(int j=0;j<15;j++)
//            {
//                    System.out.print(route_matrix[i][j]+"\t");
//
//            }
//            System.out.println();
//            System.out.println();
//        }
        time_matrix = generateTimeMatrix(flightInfo,pathMap,map);
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<15;j++)
            {
                System.out.print(time_matrix[i][j]+"\t");

            }
            System.out.println();
        }
        System.out.println();
        conflict_matrix = generateConflictMatrix(flightInfo,route_matrix,time_matrix);
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<15;j++)
            {
                //System.out.println("blah");
                System.out.print(conflict_matrix[i][j]+"\t");

            }
            System.out.println();
        }
        int [][] final_time_matrix = new int[7][15];
        final_time_matrix = resolveConflicts(flightInfo,pathMap,map,route_matrix,time_matrix,conflict_matrix);

//        System.out.println();
//        for(int i=0;i<7;i++)
//        {
//            for(int j=0;j<15;j++)
//            {
//                //System.out.println("blah");
//                System.out.print(final_time_matrix[i][j]+"\t");
//
//            }
//            System.out.println();
//        }
//

    }
}
