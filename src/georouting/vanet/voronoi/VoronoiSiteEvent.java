package georouting.vanet.voronoi;

public class VoronoiSiteEvent extends VoronoiEvent
{
    private VoronoiSite _site;
    
    public VoronoiSite site() { return _site; }

    public double setSweepHeight() { return _site.y(); }

    public VoronoiSiteEvent(VoronoiSite site)
    {
        super(site.y());
        _site = site;
    }
}
