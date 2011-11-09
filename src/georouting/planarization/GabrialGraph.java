package georouting.planarization;

import georouting.Edge;
import georouting.Node;
import georouting.PlanarizationAlgorithm;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * <p>
 * The gabrial graph performs planarization by removing edges in the following way:<br/>
 * <ol>
 *  <li>Let e be an element of the set of edges</li>
 *  <li>Construct 2 circles (c1 and c2). Both are have a radius equal to the distance between e.n1 and e.n2</li>
 *  <li>Position these circles so one is centered at e.n1 and the other at e.n2</li>
 *  <li>Let a be the intersection of c1 and c2.</li>
 *  <li>if the unique set constructed of N(e.n1) U N(e.n2) contains a vetex that exists in the space of a, then remove e form the set of edges</li>
 * </ol>
 * </p>
 */
public class GabrialGraph extends PlanarizationAlgorithm
{

  public ArrayList<Edge> planarize(ArrayList<Edge> edges)
  { // the return is redundent, the array is going to be modified during the algorithm
    // which will affect the call to neighborhood, thus this approch will actually work
    // (recall that neighborhood is resolved based on edge connections)
    Iterator<Edge> edge_itor = edges.iterator();
    do
    {
      Edge e = edge_itor.next();
      float dist = e.n1().distance(e.n2());
      Ellipse2D c1 = new Ellipse2D.Float(e.n1().x() - dist,
                                         e.n1().y() - dist,
                                         2 * dist, 2 * dist);
      Ellipse2D c2 = new Ellipse2D.Float(e.n2().x() - dist,
                                         e.n2().y() - dist,
                                         2 * dist, 2 * dist);
      Area a = new Area(c1);
      a.intersect(new Area(c2));
      boolean searching = true;
      Iterator<Node> node_itor = e.n1().neighborhood().iterator();
      while(searching && node_itor.hasNext())
      {
        if(a.contains(node_itor.next().point()))
        {
          edge_itor.remove();
          searching = false;
        }
      }
      node_itor = e.n2().neighborhood().iterator();
      while(searching && node_itor.hasNext())
      {
        if(a.contains(node_itor.next().point()))
        {
          edge_itor.remove();
          searching = false;
        }
      }
    }
    while(edge_itor.hasNext());
    return edges;
  }

}
