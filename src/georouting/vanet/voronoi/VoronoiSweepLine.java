package georouting.vanet.voronoi;

import java.util.ArrayList;

public class VoronoiSweepLine
{
    private ArrayList<VoronoiSite> _beachLine;

    private PriorityQueue<VoronoiEvent> _eventQueue;

    private double _y;

    public VoronoiSweepLine(ArrayList<VoronoiSite> sites)
    {
        _y = 0;
        _beachLine = new ArrayList<VoronoiSite>();
        _eventQueue = new PriorityQueue<VoronoiEvent>();

        for(VoronoiSite site : sites)
        {
            _eventQueue.add(new VoronoiSiteEvent(site));
        }
    }

    public void sweep()
    {
        while(!_eventQueue.isEmpty())
        {
            VoronoiEvent nextEvent = _eventQueue.poll();
            if(nextEvent instanceof VoronoiSiteEvent)
            {
                VoronoiSiteEvent siteEvent = (VoronoiSiteEvent) nextEvent;
                // update the sweep line location
                _y = siteEvent.setSweepHeight();

                // which arc does this split?
                // the closest node in the beach line owns that arc!
                if(_beachLine.isEmpty()) _beachLine.add(siteEvent.site());
                else
                {
                    double distnace = Double.MAX_VALUE;
                    VoronoiSite winner = null;
                    for(
                }
            }
        }
    }
}
