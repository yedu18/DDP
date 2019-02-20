package DDP;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class main
{
    public static int[][] generateRouteMatrix(Flight[] flightInfo, int[][][] pathMap, int[][] map, int[][] path_type)
    {
        int[][] route_matrix = new int[7][40];
        for(int i=0;i<7;i++)
        {
            List<Integer> route = new ArrayList<Integer>();
            int current=0;
            int status = flightInfo[i].getPriority();
            int runlen = 0;
            if(status==1 || status==2)
            {
                current = flightInfo[i].getStart_node();
                route.add(current);
            }
            if(status==3||status==4)
            {
                current = flightInfo[i].getEnd_node();
                route.add(current);
            }
            //System.out.println(current);
            boolean flag = false;
            int j=0;
            while(flag==false)
            {
                //System.out.println(status);
                if (map[current][j] == 0)
                {
                    j=j+1;
                }
                else
                {
                    switch (status)
                    {
                        case 1:
                            if(path_type[current][j]==1 && !(route.contains(j)))
                            {
                                //System.out.println("blah");
                                route.add(j);
                                runlen=runlen+map[current][j];
                                current=j;
                                //j=0;
                                //System.out.println(route);
                                //System.out.println(current);
                            }
                            else
                                j=j+1;
                            break;
                        case 2:
                            if(path_type[current][j]==2 &&!(route.contains(j)))
                            {
                                route.add(j);
                                runlen=runlen+map[current][j];
                                current=j;
                                j=0;
                            }
                            else
                                j=j+1;
                            break;
                        case 3:
                            if(path_type[current][j]==1 &&!(route.contains(j)))
                            {
                                route.add(j);
                                runlen=runlen+map[current][j];
                                current=j;
                                j=0;
                            }
                            else
                                j=j+1;
                            break;
                        case 4:
                            if(path_type[current][j]==2 &&!(route.contains(j)))
                            {
                                route.add(j);
                                runlen=runlen+map[current][j];
                                current=j;
                                j=0;
                            }
                            else
                                j=j+1;
                            break;
                    }
                }
                if(runlen>=flightInfo[i].getRun_len())
                    flag=true;
            }
            //System.out.println(route);
            if(status==1||status==2)
            {
                int[] source = pathMap[current][flightInfo[i].getEnd_node()];
                int[] flightRoute = new int[source.length+route.size()-1];
                int n=1;
                for(int m=0;m<flightRoute.length;m++)
                {
                    if(m<route.size())
                        flightRoute[m]=route.get(m);
                    else
                        flightRoute[m] = source[n++];
                }
                //System.out.println(flightRoute.length);

                //int[] flightRoute = Arrays.copyOfRange(source,0,source.length);
                System.out.println(Arrays.toString(flightRoute));
                for(int k=0;k<flightRoute.length;k++)
                {
                    route_matrix[i][flightRoute[k]]=1;
                }
            }
            if(status==3||status==4)
            {
                //System.out.println(route);
                int[] source = pathMap[current][flightInfo[i].getStart_node()];
                //System.out.println(Arrays.toString(source));
                int[] flightRoute = new int[source.length+route.size()-1];

//                for(int m=flightRoute.length-1;m>=0;m--)
//                {
//                    if(m>=route.size())
//                        flightRoute[m]= route.get(n++);
//                    else
//                        flightRoute[m] = route.get(m);
//                    //System.out.println(Arrays.toString(flightRoute));
//                }
                int n=source.length-1;
                int o = route.size()-2;
                for(int m=0;m<flightRoute.length;m++)
                {
                    if(m<source.length)
                        flightRoute[m] = source[n--];
                    else
                        flightRoute[m] = route.get(o--);
                }

                //System.out.println(flightRoute.length);

                //int[] flightRoute = Arrays.copyOfRange(source,0,source.length);
                System.out.println(Arrays.toString(flightRoute));
                for(int k=0;k<flightRoute.length;k++)
                {
                    route_matrix[i][flightRoute[k]]=1;
                }
            }

        }
        //time_matrix = generateTimeMatrix(flightInfo,pathMap,map);

        return route_matrix;
    }

    public static int[][] generateTimeMatrix(Flight[] flightInfo, int[][][] pathMap, int[][] map)
    {
        int[][] time_matrix = new int[7][40];
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
        int[][] conflict_matrix = new int[7][40];
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<40;j++)
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
        for(int j=0;j<40;j++)
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
        //int [][] final_time_matrix = new int[7][40];
        int wait=0, flight, node;
        int total_wait=0;
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<40;j++)
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
            for(int j=0;j<40;j++)
            {
                //System.out.println("blah");
                System.out.print(time_matrix[i][j]+"\t");

            }
            System.out.println();
        }
        System.out.println();
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<40;j++)
            {
                //System.out.println("blah");
                System.out.print(conflict_matrix[i][j]+"\t");

            }
            System.out.println();
        }
        return time_matrix;

    }

    public static void fileWrite(Flight[] flightInfo, int[][][] pathMap, int[][] map, int[][] route_matrix, int[][] time_matrix, int[][] conflict_matrix) throws IOException
    {

        BufferedWriter writer = new BufferedWriter(new FileWriter("DDP/solution.txt"));
        writer.write("Time : 0 mins 0 secs");
        writer.newLine();
        for(int i=0;i<7;i++)
        {
            writer.write(i+" : ");
            int[] source = pathMap[flightInfo[i].getStart_node()][flightInfo[i].getEnd_node()];
            int[] flightRoute = Arrays.copyOfRange(source,0,source.length);
            for(int j=0;j<flightRoute.length;j++)
            {
                if(j==flightRoute.length-1)
                    writer.write((flightRoute[j]+1)+" "+time_matrix[i][flightRoute[j]]);
                else
                    writer.write((flightRoute[j]+1)+" "+time_matrix[i][flightRoute[j]]+" - ");
            }
            writer.newLine();
        }
        writer.close();
    }

    public static void main(String[] args) throws FileNotFoundException
    {

        int[][] map = new int[40][40];
        int[][] path_type;
        /*int[][][] test = new int[2][3][5];
        int[] tester = {1, 2, 3, 4};
        test[1][2] = tester;
        System.out.println(Arrays.deepToString(test));
        System.out.println(Arrays.toString(test[1][2]));*/
        FileRead fr = new FileRead();
        Dijkstra d = new Dijkstra();
        map = fr.mapRead();
        path_type = fr.pathType();
        Flight[] flightInfo = fr.flightRead();
        int[][][] pathMap = new int[40][40][40];
        for (int i = 0; i < map.length; i++)
        {
            pathMap = d.dijkstra(map, i, pathMap);
        }
        System.out.println("\n\n\n\n");
        //System.out.println(Arrays.toString(pathMap[flightInfo[0].getStart_node()][flightInfo[0].getEnd_node()]));
        //System.out.println(flightInfo[1].getStart_node());

        int[][] route_matrix = new int[7][40];
        int[][] time_matrix = new int[7][40];
        int[][] conflict_matrix = new int [7][40];

        route_matrix = generateRouteMatrix(flightInfo,pathMap,map,path_type);
//        for(int i=0;i<7;i++)
//        {
//            for(int j=0;j<40;j++)
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
            for(int j=0;j<40;j++)
            {
                System.out.print(time_matrix[i][j]+"\t");

            }
            System.out.println();
        }
        System.out.println();
        conflict_matrix = generateConflictMatrix(flightInfo,route_matrix,time_matrix);
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<40;j++)
            {
                //System.out.println("blah");
                System.out.print(conflict_matrix[i][j]+"\t");

            }
            System.out.println();
        }
        int [][] final_time_matrix = new int[7][40];
        final_time_matrix = resolveConflicts(flightInfo,pathMap,map,route_matrix,time_matrix,conflict_matrix);

//        System.out.println();
//        for(int i=0;i<7;i++)
//        {
//            for(int j=0;j<40;j++)
//            {
//                //System.out.println("blah");
//                System.out.print(final_time_matrix[i][j]+"\t");
//
//            }
//            System.out.println();
//        }
//
        try {
            fileWrite(flightInfo,pathMap,map,route_matrix,time_matrix,conflict_matrix);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }
}
