import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Steiner
{
    private static Pair<Integer, Integer> minDistanceFromSet(Set<Integer> target, List<Integer> source, double[][] dist)
    {
        double minDistance = java.lang.Double.MAX_VALUE;
        int minSource = -1;
        int minTarget = -1;

        for (Integer s : source)
        {
            if (!target.contains(s))
            {
                for (Integer t : target)
                {
                    if (dist[s][t] < minDistance)
                    {
                        minDistance = dist[s][t];
                        minSource = s;
                        minTarget = t;
                    }
                }
            }
        }

        if (minSource == -1 || minTarget == -1)
            return null;
        return new Pair<>(minSource, minTarget);
    }

    public static Set<Edge> subGraphPrim(SimpleGraph g, Set<Integer> subVertices, double[][] w)
    {
        ArrayList<Integer> o = new ArrayList<>();
        double[] curDists = new double[g.nv];
        Integer[] curDistsCor = new Integer[g.nv];
        boolean[] mark = new boolean[g.nv];

        TreeSet<Edge> treeEdges = new TreeSet<>();

        int next = subVertices.iterator().next();

        for (int i = 0; i < g.nv; i++)
        {
            mark[i] = false;
            curDists[i] = w[next][i];
            curDistsCor[i] = next;
        }
        mark[next] = true;

        while (true)
        {
            double min = Double.MAX_VALUE;
            for (Integer s : subVertices)
            {
                next = -1;
                if (!mark[s] && curDists[s] < min)
                {
                    next = s;
                    min = curDists[s];
                    break;
                }
            }
            if (next == -1)
                break;

            mark[next] = true;
            treeEdges.add(g.adjencacyMatrix[next][curDistsCor[next]]);

            for (int i = 0; i < g.nv; i++)
            {
                if (w[next][i] < curDists[i])
                {
                    curDists[i] = w[next][i];
                    curDistsCor[i] = next;
                }
            }
        }

        return treeEdges;
    }

    public static Set<Edge> kmb(SimpleGraph g, List<Integer> terminals)
    {
        double[][] amw = g.adjMatrixWeights();
        Edge[][] am = g.adjencacyMatrix;

        APSPInfo apsp = g.apsp();
        double[][] dist = apsp.dist;
        int[][] path = apsp.path;

        TreeSet<Integer> tVertices = new TreeSet<>();
        TreeSet<Edge> tEdges = new TreeSet<>();

        tVertices.add(terminals.get(0));

        while (true)
        {
            Pair<Integer, Integer> next = minDistanceFromSet(tVertices, terminals, apsp.dist);
            if (next == null)
                break;

            int minSource = next.getSecond();
            int minTarget = next.getFirst();

            int end = minTarget;

            while (true)
            {
                tVertices.add(end);
                if (minSource == end)
                    break;
                Edge nextEdge = am[path[minSource][end]][end];
                tEdges.add(nextEdge);
                end = path[minSource][end];
            }

        }

        //phase 2, remove
        Set<Edge> treeEdges = subGraphPrim(g, tVertices, amw);
        treeEdges = pruneTree(g, terminals, treeEdges);


        return tEdges;
    }

    private static Set<Edge> pruneTree(SimpleGraph g, List<Integer> terminals, Set<Edge> treeEdges)
    {
        int[] degree = new int[g.nv];

        for (Edge e: treeEdges)
        {
            degree[e.v1]++;
            degree[e.v2]++;
        }

        while (true)
        {
            int next = -1;
            for (int i = 0; i < g.nv; i++)
                if (degree[i] == 1 && !terminals.contains(i))
                {
                    next = i;
                    break;
                }

            if (next == -1)
                break;

            for (int i = 0; i < g.nv; i++)
                if (g.adjencacyMatrix[next][i] != null)
                    if (treeEdges.contains(g.adjencacyMatrix[next][i]))
                    {
                        degree[next]--;
                        degree[i]--;
                        treeEdges.remove(g.adjencacyMatrix[next][i]);
                    }
        }

        return treeEdges;
    }

}
