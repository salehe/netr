import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        SimpleGraph g = randomGraph1(10);
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
