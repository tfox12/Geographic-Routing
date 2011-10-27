package georouting;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Edge
{
  /** _n1
   *  One of the endpoints of the edge
   */
  private Node _n1;

  /**
   * @return Endpoint 1 of the edge
   */
  public Node n1() { return _n1; }

  /** _n2
   *  Node of the endpoints of the edge
   */
  private Node _n2;
  /**
   * @return Endpoint 2 of the edge
   */
  public Node n2() { return _n2; }

  /** _segment
   *  This is used to aid computation of intersections
   */
  private Line2D.Float _segment

  /**
   * @return The geometric segment in euclidean space representing the edge
   */
  public Line2D.Float segment() { return _segment; }

  /**
   * Constructs an Edge ot of two nodes.
   * NOTE: neither n1 nor n2 may be instances of ImaginaryNode.
   * @param n1 Endpoint 1
   * @param n2 Endpoint 2
   */
  public Edge(Node n1, Node n2) throws Exception
  {
    if(n1 instanceof node.ImaginaryNode || n2 instanceof node.ImaginaryNode)
      throw new Exception("Wrong use of Imaginary Node");
    _n1 = n1;
    _n2 = n2;
    _segment = new Line2D.Float( n1.x(), n1.y(), n2.x(), n2.y() );
  }

  public   

}
