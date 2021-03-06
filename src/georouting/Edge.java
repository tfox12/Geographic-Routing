package georouting;

import georouting.node.ImaginaryNode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class Edge
{
  /** _n1
   *  One of the endpoints of the edge
   */
  protected Node _n1;

  /**
   * @return Endpoint 1 of the edge
   */
  public Node n1() { return _n1; }

  /** _n2
   *  Node of the endpoints of the edge
   */
  protected Node _n2;
  /**
   * @return Endpoint 2 of the edge
   */
  public Node n2() { return _n2; }

  /** _segment
   *  This is used to aid computation of intersections
   */
  protected Line2D.Float _segment;

  /**
   * @return The geometric segment in euclidean space representing the edge
   */
  public Line2D.Float segment() { return _segment; }

  /**
   * Checks to see if this edge intersects edge e
   * NOTE: edges DO NOT intersect at nodes
   * @param n the edge we are checking against this for intersection
   */
  public boolean intersects(Edge e)
  {
    if(e.containsNode(n1()) || e.containsNode(n2()))
    {
      return false;
    }
    return _segment.intersectsLine(e.segment());
  }

  public float distance(Node n)
  {
    return (float) _segment.ptSegDist(n.point());
  }

  /**
   * Creates an imaginary node at the point of intersection of 2 edges
   * The formula used in this method comes from representing each edge in
   * point slope form ( y - y1 = m ( x - x1) ) and solvinig for x and y
   * @param e edge to use for intersection
   *          PRECONDITION: e intersects this
   */
  public ImaginaryNode pointOfIntersection(Edge e)
  {
    double y1 = n1().y(),
           x1 = n1().x(),
           y3 = e.n1().y(),
           x3 = e.n1().x(),
           m1 = (n2().y() - n1().y())/(n2().x() - n1().x()),
           m2 = (e.n2().y() - e.n1().y())/(e.n2().x() - e.n1().x());
    double x, y;
    if(m1 == Double.POSITIVE_INFINITY || m1 == Double.NEGATIVE_INFINITY)
    {
      x = x1;
      y = m2 * (x1 - x3) + y3;
    }
    else if(m2 == Double.POSITIVE_INFINITY || m2 == Double.NEGATIVE_INFINITY)
    {
      x = x3;
      y = m1 * (x3 - x1) + y1;
    }
    else
    {
      x = (y1 - m1 * x1 - y3 + m2 * x3) / 
          (m2 - m1) ;
      y = (m1 * m2 * (x3 - x1) - m1 * y3 + m2 * y1) / 
          (m2 - m1) ;
    }
    return new ImaginaryNode(x,y);
  }

  /**
   * Constructs an Edge ot of two nodes.
   * NOTE: neither n1 nor n2 may be instances of ImaginaryNode.
   * @param n1 Endpoint 1
   * @param n2 Endpoint 2
   */
  public Edge(Node n1, Node n2) throws Exception
  {
    if(n1 instanceof ImaginaryNode || n2 instanceof ImaginaryNode)
      throw new Exception("Wrong use of Imaginary Node");
    _n1 = n1;
    _n2 = n2;
    _segment = new Line2D.Float( n1.x(), n1.y(), n2.x(), n2.y() );
  }

  protected Edge()
  {
      _n1 = null;
      _n2 = null;
      _segment = null;
  }

  /**
   * checks to see if either n1 or n2 is n
   * @param n node we are checking
   */
  public boolean containsNode(Node n)
  {
    return n.equals(_n1) || n.equals(_n2);
  }

  @Override
  public boolean equals(Object o)
  {
    if(o instanceof Edge)
    {
      Edge e = (Edge) o;
      return e.containsNode(n1()) && e.containsNode(n2()) ;
    }
    return false;
  }

  @Override
  public String toString()
  {
    return "( " + n1() + " , " + n2() + " )";
  }

  public List<Edge> intersections()
  {
    return _n1.parent().getIntersections(this);
  }

  public void paint(Graphics g, Color c)
  {
    g.setColor(c);
    g.drawLine(
        (int) _n1.x(), (int) _n1.y() ,
        (int) _n2.x(), (int) _n2.y() );
  }

}
