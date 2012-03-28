package georouting.vanet.voronoi;

public class VoronoiSite
{
    private double _x, _y;

    public VoronoiSite(double x, double y)
    {
        _x = x;
        _y = y;
    }

    public double x() { return _x; }
    public double y() { return _y; }
}
