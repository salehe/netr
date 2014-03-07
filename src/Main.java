import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class Main
{
    public static void main(String[] args)
    {
        SimpleGraph g = new SimpleGraph(7);
        g.addEdge(0,2,1);
        g.addEdge(0,3,1);
//        g.addEdge(1,3,1);
        g.addEdge(1,5,1);
        g.addEdge(2,3,1);
        g.addEdge(2,4,1);
        g.addEdge(2,5,1);
        g.addEdge(5,6,1);

        ArrayList<Integer> terminals = new ArrayList<>();
        terminals.add(0);
        terminals.add(1);
        terminals.add(6);

        Set<Edge> t = Steiner.kmb(g, terminals);
        System.out.println(t);
    }

    public static SimpleGraph randomGraph1(int nv)
    {
        SimpleGraph g = new SimpleGraph(nv);
        Random rand = new Random();

        for (int i = 0; i < nv; i++)
            for (int j = 0; j < nv; j++)
            {
                if (i != j)
                {
                    double r = rand.nextDouble();
                    if (r <= 0.3)
                        g.addEdge(i, j, 1);
                    else
                        g.addEdge(i, j, 0);
                }
        }
        return g;
    }
}
