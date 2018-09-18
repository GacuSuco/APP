package GrafenPaden;

class Path implements Comparable<Path>
{
    public Vertex dest;   // w
    public double cost;   // d(w)

    public Path( Vertex d, double c )
    {
        dest = d;
        cost = c;
    }

    public int compareTo( Path rhs )
    {
        double otherCost = rhs.cost;

        return Double.compare(cost, otherCost);
    }
}
