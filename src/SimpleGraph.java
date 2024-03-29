import java.util.*;


class Edge implements Comparable<Edge>
{
    int v1;
    int v2;
    double w;

    public Edge(int v1, int v2, double w)
    {
        this.v1 = v1;
        this.v2 = v2;
        this.w = w;
    }


    @Override
    public int compareTo(Edge o)
    {
        if (v1 < o.v1) return -1;
        if (v1 > o.v1) return 1;
        if (v2 < o.v2) return -1;
        if (v2 > o.v2) return 1;
        if (o.w < o.w) return -1;
        if (w > o.w) return 1;
        return 0;
    }

    @Override
    public String toString()
    {
        return "Edge{" +
                "(" + v1 +
                ", " + v2 +
                '}';
    }
}

class APSPInfo
{

    double[][] dist;
    int[][] path;

    public APSPInfo(double[][] dist, int[][] path)
    {
        this.dist = dist;
        this.path = path;
    }
}

public class SimpleGraph
{
    int nv;
    ArrayList<Edge> edges;
    Edge[][] adjencacyMatrix;

    public SimpleGraph(int nv)
    {
        this.nv = nv;
        edges = new ArrayList<>();
        adjencacyMatrix = new Edge[nv][];
        for (int i = 0; i < nv; i++)
            adjencacyMatrix[i] = new Edge[nv];
    }

    public SimpleGraph addEdge(int v1, int v2, double w)
    {
        Edge edge = new Edge(v1, v2, w);
        edges.add(edge);
        adjencacyMatrix[v1][v2] = edge;
        adjencacyMatrix[v2][v1] = edge;

        return this;
    }

    public double[][] adjMatrixWeights()
    {
        double[][] amw = new double[nv][];
        for (int i = 0; i < nv; i++)
            amw[i] = new double[nv];

        for (int i = 0; i < nv; i++)
            for (int j = 0; j < nv; j++)
            {
                if (i == j)
                    amw[i][j] = 0.0;
                else if (adjencacyMatrix[i][j] != null)
                    amw[i][j] = adjencacyMatrix[i][j].w;
                else
                    amw[i][j] = Double.MAX_VALUE;
            }

        return amw;
    }

    public APSPInfo apsp()
    {
        double[][] dist = adjMatrixWeights();

        int[][] path = new int[nv][nv];
        for (int i = 0; i < nv; i++)
            for (int j = 0; j < nv; j++)
            {
                path[i][j] = i;
            }

        for (int k = 0; k < nv; k++)
            for (int i = 0; i < nv; i++)
                for (int j = 0; j < nv; j++)
                {
                    if (dist[i][j] > dist[i][k] + dist[k][j])
                    {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = path[k][j];
                    }
                }
        return new APSPInfo(dist, path);
    }
}


