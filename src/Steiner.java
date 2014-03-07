import java.util.Iterator;
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
                for (Integer t: target)
                {
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

        }
        if (minSource == -1 || minTarget == -1)
            return null;
        return new Pair<>(minSource, minTarget);
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

            int minSource = next.getFirst();
            int minTarget = next.getSecond();

            tVertices.add(minTarget);
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

        return tEdges;
    }

}
