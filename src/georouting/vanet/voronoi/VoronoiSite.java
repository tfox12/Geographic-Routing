package georouting.vanet.voronoi;

public class VoronoiSite
{
    private static long IDC = 0;

    private double _x, _y;
    private long _id;
    public long id() { return _id; }

    public VoronoiSite(double x, double y)
    {
        _x = x;
        _y = y;
        _id = IDC++;
    }

    public double x() { return _x; }
    public double y() { return _y; }
}
