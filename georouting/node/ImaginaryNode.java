package georouting.node;

import georouting.Node;

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


  /**
   * Should not be used, could throw an exception... we'll see where this goes
   */
  @Override
  public ArrayList<Node> neighborhood() { return null; }
}
