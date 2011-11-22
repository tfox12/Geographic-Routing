package georouting.node;

import georouting.Edge;
import georouting.Node;

import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * An imaginary is more of a point than a node, but can be used in conjunction
 *  with "real" nodes in order to compute distance / angles.
 *  An Imaginary Node may not be placed into a graph. As a side effect, Imaginary
 *  Nodes may not be used when constructing Edges (Unless I introduce an ImaginaryEdge :D)
 */
public class ImaginaryNode extends Node
{
  /**
   * since Imaginary Nodes can not be placed into a graph, the graph
   *  instance of parent Node should always be null
   * @param x X coordinate of the node
   * @param y Y coordinate of the node
   */
  public ImaginaryNode(double x,double y)
  {
    super(x,y,null);
  }

  public ImaginaryNode(Node n)
  {
    super(n.x(),n.y(),n.parent());
  }

  public Edge imaginaryEdgeWith(Node target)
  {
    Edge e = null;
    try { e = new ImaginaryEdge(this,target); }
    catch(Exception exc) { }
    return e;
  }

  /**
   * Should not be used, could throw an exception... we'll see where this goes
   */
  @Override
  public ArrayList<Node> neighborhood() { return null; }

  class ImaginaryEdge extends Edge
  {
    public ImaginaryEdge(ImaginaryNode n1, Node n2) throws Exception
    {
      super();
      _n1 = n1;
      _n2 = n2;
      _segment = new Line2D.Float( n1.x(), n1.y(), n2.x(), n2.y() );
    }
  }
}
