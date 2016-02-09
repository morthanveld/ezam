package com.maze.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Andreas on 2016-01-31.
 */
public class MazeCreator {

    private int[][] mazeData;

    public int width = 20;
    public int height = 20;

    public int playerX = 7;
    public int playerY = 6;

    private Random randomGenerator;

    public class Edge
    {
        public int ax;
        public int ay;
        public int bx;
        public int by;
        public float w;
    }

    private ArrayList<Edge> edges;
    public ArrayList<Edge> visitedEdges;
    public ArrayList<Edge> copyEdges;


    public class CustomComparator implements Comparator<Edge> {
        public int compare(Edge e1, Edge e2) {
            return (e1.w < e2.w) ? -1 : 1;
        }
    }

    public MazeCreator()
    {
        edges = new ArrayList<Edge>();
        visitedEdges = new ArrayList<Edge>();

        randomGenerator = new Random();
        randomGenerator.setSeed(1748291);

        mazeData = new int[width][];
        for (int i = 0; i < width; i++)
        {
            mazeData[i] = new int[height];

            for (int j = 0; j < height; j++)
            {
                mazeData[i][j] = 0;

                if (i < width - 1 && j < height -1)
                {
                    Edge e = new Edge();
                    e.ax = i;
                    e.ay = j;
                    e.bx = i;
                    e.by = j + 1;
                    e.w = randomGenerator.nextInt(1000);
                    edges.add(e);

                    Edge ed = new Edge();
                    ed.ax = i;
                    ed.ay = j;
                    ed.bx = i + 1;
                    ed.by = j;
                    ed.w = randomGenerator.nextInt(1000);
                    edges.add(ed);
                    //System.out.println("vertical edge");
                }
                else if (j == height - 1 && i != width - 1)
                {
                    Edge e = new Edge();
                    e.ax = i;
                    e.ay = j;
                    e.bx = i + 1;
                    e.by = j;
                    e.w = randomGenerator.nextInt(1000);
                    edges.add(e);
                   // System.out.println("horizontal edge");
                }
                else if (i == width - 1 && j != height - 1)
                {
                    Edge e = new Edge();
                    e.ax = i;
                    e.ay = j;
                    e.bx = i;
                    e.by = j + 1;
                    e.w = randomGenerator.nextInt(1000);
                    edges.add(e);
                    //System.out.println("special edge edge");
                }
            }
        }

        System.out.println("Total number of edges: " + edges.size());

        // Sort edges by weight.
        Collections.sort(edges, new CustomComparator());

        System.out.println(((Edge)edges.get(0)).w + "\t" +((Edge)edges.get(10)).w);

        copyEdges = edges;

        //if(true)
//            return;

        // Traverse maze.
        int x = playerX;
        int y = playerY;

        mazeData[x][y] = 1;

        int counter = 1;
        int idx = 0;
        int wh = width * height;

        while(edges.size() > 0)
        {
            Edge e = (Edge) edges.get(idx);

            if ((mazeData[e.ax][e.ay] == 0 || mazeData[e.bx][e.by] == 0) && ((e.ax == x && e.ay == y) || (e.bx == x && e.by == y)))
            {
                System.out.println("Matching Edge Found " + e.ax + " " + e.ay + " " + e.bx + " " + e.by);

                if (mazeData[e.ax][e.ay] == 0)
                mazeData[e.ax][e.ay] = counter++;

                if (mazeData[e.bx][e.by] == 0)
                mazeData[e.bx][e.by] = counter++;

                visitedEdges.add(e);
                edges.remove(e);

                idx = 0;
                continue;
            }

            idx += 1;

            if (idx >= edges.size())
            {
                for (int i = 0; i < edges.size(); i++)
                {
                    Edge edge = (Edge) edges.get(i);
                    if (mazeData[edge.ax][edge.ay] > 0 && mazeData[edge.bx][edge.by] == 0)
                    {
                        System.out.println("Backtrack visited node " + edge.ax + " " + edge.ay + " Unvisited Node " + edge.bx + " " + edge.by);
                        x = edge.ax;
                        y = edge.ay;
                        idx = 0;
                        break;
                    }
                    else if (mazeData[edge.ax][edge.ay] == 0 && mazeData[edge.bx][edge.by] > 0)
                    {
                        System.out.println("Backtrack visited node " + edge.bx + " " + edge.by + " Unvisited Node " + edge.ax + " " + edge.ay);
                        x = edge.bx;
                        y = edge.by;
                        idx = 0;
                        break;
                    }
                }
            }

            if (counter == wh)
            {
                System.out.println("All Nodes Has Been Visited");
                break;
            }
        }


        //
    }

    public boolean moveNorth()
    {
        for (int i = 0; i < visitedEdges.size(); i++)
        {
            Edge e = visitedEdges.get(i);
            if (e.ax == playerX && e.ay == playerY)
            {
                if (e.bx == e.ax && e.by == e.ay + 1)
                {
                    playerY++;
                    return true;
                }
            }
            else if (e.bx == playerX && e.by == playerY)
            {
                if (e.ax == e.bx && e.ay == e.by + 1)
                {
                    playerY++;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveSouth()
    {
        for (int i = 0; i < visitedEdges.size(); i++)
        {
            Edge e = visitedEdges.get(i);
            if (e.ax == playerX && e.ay == playerY)
            {
                if (e.bx == e.ax && e.by == e.ay - 1)
                {
                    playerY--;
                    return true;
                }
            }
            else if (e.bx == playerX && e.by == playerY)
            {
                if (e.ax == e.bx && e.ay == e.by - 1)
                {
                    playerY--;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveEast()
    {
        for (int i = 0; i < visitedEdges.size(); i++)
        {
            Edge e = visitedEdges.get(i);
            if (e.ax == playerX && e.ay == playerY)
            {
                if (e.by == e.ay && e.bx == e.ax + 1)
                {
                    playerX++;
                    return true;
                }
            }
            if (e.bx == playerX && e.by == playerY)
            {
                if (e.ay == e.by && e.ax == e.bx + 1)
                {
                    playerX++;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean moveWest()
    {
        for (int i = 0; i < visitedEdges.size(); i++)
        {
            Edge e = visitedEdges.get(i);
            if (e.ax == playerX && e.ay == playerY)
            {
                if (e.by == e.ay && e.bx == e.ax - 1)
                {
                    playerX--;
                    return true;
                }
            }
            if (e.bx == playerX && e.by == playerY)
            {
                if (e.ay == e.by && e.ax == e.bx - 1)
                {
                    playerX--;
                    return true;
                }
            }
        }
        return false;
    }



}
