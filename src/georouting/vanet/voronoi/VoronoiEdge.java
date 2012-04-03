package georouting.vanet.voronoi;

public class VoronoiEdge
{
    /* with 10 miles behind me... and 10,000 more to go 
        there's a song that they sing when they take to the highway
        a song that they sing when they take to the sea
        a song that they sing of their home in the sky
        maybe you can believe it if it helps you to sleep
        but singing works just fine for me...
    */

    private VoronoiSite _s1;
    private VoronoiSite _s2;

    public VoronoiSite s1(){ return _s1; }
    public VoronoiSite s2(){ return _s2; }

    private VoronoiIntersection[] _endpoints = {null,null};
    public VoronoiIntersection[] endpoints() { return _endpoints; }   
 
    public VoronoiEdge(VoronoiSite s1, VoronoiSite s2)
    {
        _s1 = s1;
        _s2 = s2;
    }

    public boolean complete() { return _endpoints[0] != null && _endpoints[1] != null; }

    public void addEndpoint(VoronoiIntersection intersection)
    {
        if(_endpoints[0] == null) _endpoints[0] = intersection;
        else if(_endpoints[1] == null) _endpoints[1] = intersection;
    }
}
