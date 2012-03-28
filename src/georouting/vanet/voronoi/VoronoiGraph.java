package georouting.vanet.voronoi;

import java.util.Random;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class VoronoiGraph
{
    private ArrayList<VoronoiSite>   _sites;
    private ArrayList<VoronoiEdge>   _edges;
    private ArrayList<VoronoiVertex> _verticeis;

    private double _width, _height;

    private VoronoiSweepLine _sweepLine;

    public VoronoiGraph(int numSites, double width, double height)
    {
        _sites = new ArrayList<VoronoiSite>();
        _edges = new ArrayList<VoronoiEdge>();
        _verticeis = new ArrayList<VoronoiVertex>();

        _width = width;
        _height = height;

        Random rand = new Random();
        for(int i = 0; i < numSites; ++i)
        {
            VoronoiSite newSite = new VoronoiSite(rand.nextDouble() * width,
                                                  rand.nextDouble() * height);
            _sites.add(newSite);
        }

        _sweepLine = new VoronoiSweepLine();
    }
}
