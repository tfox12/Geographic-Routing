package georouting.vanet.voronoi;

import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.ArrayList;

public class VoronoiSweepLine
{
    private VoronoiBSTree _beachLine;

    private PriorityQueue<VoronoiEvent> _eventQueue;

    private ArrayList<VoronoiEdge> _edges;
    private ArrayList<VoronoiEdge> _incompleteEdges;
    private ArrayList<VoronoiIntersection> _intersections;
    public void addEdge(VoronoiEdge edge)
    {
        _incompleteEdges.add(edge);
    }
 
    public ArrayList<VoronoiEdge> edges(){ return _edges; }
    public ArrayList<VoronoiIntersection> intersections(){ return _intersections; }

    private double _y;
    public double height() { return _y; }

    public VoronoiSweepLine(ArrayList<VoronoiSite> sites)
    {
        _y = 0;
        _beachLine = new VoronoiBSTree();
        _eventQueue = new PriorityQueue<VoronoiEvent>();
        _edges = new ArrayList<VoronoiEdge>();
        _incompleteEdges = new ArrayList<VoronoiEdge>();
        _intersections = new ArrayList<VoronoiEdge>();

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
                
                // add new point
                VoronoiSite[] search = siteEvent.addSite(siteEvent.site());
                
                // remove stale vetex events
                Iterator<VoronoiEvent> itor = _eventQueue.iterator();
                while(itor.hasNext())
                {
                    VoronoiEvent event = itor.next();
                    if(event instanceof VoronoiVertexEvent && 
                      ((VoronoiVertexEvent)event).containsSequence(search[0],search[1]))
                    {
                        itor.remove();
                    }
                }

                // add new vertex events
                ArrayList<VoronoiVertexEvent> events = _beachLine.eventsForSite(siteEvent.site());
                _eventQueue.addAll(events);
            }
            else
            {
                VoronoiVertexEvent vertexEvent = (VoronoiVertexEvent) nextEvent;
                VoronoiVertexEvent[] newEvents = _beachLine.removeNode(vertexEvent.left(),
                                                                        vertexEvent.mid(),
                                                                        vertexEvent.right());
                // remove all of the events where left or right is vertexEvent.mid
                Iterator<VoronoiEvent> queueItor = _eventQueue.iterator();
                while(queueItor.hasNext())
                {
                    VoronoiEvent event = queueItor.next();
                    if(event instanceof VoronoiVertexEvent && 
                      ( ((VoronoiVertexEvent)event).left() == vertexEvent.mid() ||
                        ((VoronoiVertexEvent)event).right() == vertexEvent.mid() )
                    {
                        queueItor.remove();
                    }
                }
                _incompleteEdges.add(new VoronoiEdge(vertex.left(),vertex.right()));
                
                VoronoiCircle circle = new VoronoiCircle(vertexEvent.left(),
                                                         vertexEvent.mid(),
                                                         vertexEvent.right());
                VoronoiIntersection endpoint = new VoronoiIntersection(circle.x(),circle.y());
                _intersections.add(endpoint):

                Iterator<VoronoiEdge> ieItor = _incompleteEdges.iterator();
                int count = 0;
                while(count < 2 && ieItor.hasNext())
                {
                    VoronoiEdge edge = ieItor.next();
                    if((edge.endpoints()[0] == vertex.left() && edge.endpoints()[1] == vertex.mid()) ||
                       (edge.endpoints()[0] == vertex.mid() && edge.endpoints()[1] == vertex.left()))||
                       (edge.endpoints()[0] == vertex.mid() && edge.endpoints()[1] == vertex.right())||
                       (edge.endpoints()[0] == vertex.right() && edge.endpoints()[1] == vertex.mid()))
                    {
                        edge.addEndpoint(endpoint);
                        ++count;
                        if(edge.isComplete())
                        {
                            ieItor.remove();
                            _edges.add(edge);
                        }
                    }
                }

                if(newEvents[0] != null) _eventQueue.add(newEvents[0]);
                if(newEvents[1] != null) _eventQueue.add(newEvents[1]);
            }
        }    
    }
}
