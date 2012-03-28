package georouting.vanet.voronoi;

public abstract class VoronoiEvent implements Comparable<VoronoiEvent>
{
    private double _y;

    public VoronoiEvent(double y) { _y = y; } 

    public int compareTo(VoronoiEvent e)
    {
        return _y - e._y;
    }
}
