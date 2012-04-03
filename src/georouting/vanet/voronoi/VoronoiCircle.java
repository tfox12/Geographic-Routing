package georouting.vanet.voronoi;

/**
 * <p>The voronoi Circle is used when trying to create a Vertex Event.<br/>
 *      When 3 sites in the beach line create a VoronoiCircle that has the highest
 *      y coordinate that is lower than the sweep line, then the parabola's have
 *      creased! We need to make a vertex at this (x,y). We attach the bisector of
 *      left-center and center-right to this vertex.
 */
public class VoronoiCircle
{
    private VoronoiSite _left, _center, _right;
    private double _x, _y, _radius;

    public VoronoiCircle(VoronoiSite left,
                         VoronoiSite center,
                         VoronoiSite right)
    {
        _left = left;
        _center = center;
        _right = right;

        double Xcl = (center.x() - left.x())  / 2,
               Ycl = (center.y() - left.y())  / 2,
               Xrc = (right.x() - center.x()) / 2,
               Yrc = (right.y() - center.y()) / 2,
               m1  = (left.x() - center.x())  / (center.y() - left.y()),
               m2  = (center.x() - right.x()) / (right.y() - center.y());
        
        if(
        (float)(center.y() - left.y()) == 0 )
        {
            _x = Xcl;
            _y = left.y();
        }
        else if(
        (float)(right.y() - center.y()) == 0)
        {
            _x = Xrc;
            _y = right.y();
        }
        else
        {
            _x = (Yrc + m1*Xcl - m2*Xrc - Ycl) / (m1 - m2);
            _y = (m1*m2*(Xrc - Xcl) + m2*Ycl - m1*Yrc) / (m2 - m1);
        }

        _radius = Math.sqrt( Math.pow(_y - left.y(),2) +
                             Math.pow(_x - left.x(),2));
    }

    public double x()    { return _x; }
    public double y()    { return _y; }
    public double radius() { return _radius; }
    public double greatestY() { return _y + _radius; }
}
