package georouting.vanet.voronoi;

public class VoronoiVertexEvent extends VoronoiEvent
{
    private VoronoiSite _left, _mid, _right;

    public VoronoiSite left() { return _left; }
    public VoronoiSite mid() { return _mid; }
    public VoronoiSite right() { return _right; }

    public boolean containsSequence(VoronoiSite first, VoronoiSite second)
    {
        return (first == _left && second == _mid) || (first == _mid && second == _right);
    }    

    public VoronoiVertexEvent(VoronoiSite left, VoronoiSite mid, VoronoiSite right)
    {
        super(new VoronoiCircle(left,mid,right).greatestY());

        _left = left;
        _mid = mid;
        _right = right;
    }    
}
