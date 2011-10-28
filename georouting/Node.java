package georouting;

import java.util.ArrayList;

public class Node
{
  /** _IDCOUNTER
   *  helper class instance used to id each new node
   */
  private static int _IDCOUNTER = 0;

  /**
   * resets the id counter for the new graph
   * may end up moving this into the constructor so multiple graphs can be initalized at once
   */
  public static void resetIdCounter() { _IDCOUNTER = 0; }

  /** _id
   *  used to identify nodes
   */
  private int _id;

  /**
   * @return the id of the node
   */
  public int id() { return _id; }

  /** _x
   *  X coordinate of the node
   */
  protected double _x;

  /**
   * @return the X coordinate of the node. Returned as float to discard round off error
   */
  public float x() { return (float) _x; }

  /** _y
   *  Y coordinate of the node
   */
  protected double _y;

  /**
   * @return THe Y coordinate of the node. Returned as a float to discard round off error
   */
  public float y() { return (float) _y; }

  /** _parent
   *  Graph that contains this node
   */
  private Graph _parent;

  /**
   * @return The parent graph
   */
  public Graph parent() { return _parent; }

  /**
   * Construct Node with given data
   * @param x X Coordinate
   * @param y Y Coordinate
   * @param g Containing Graph
   */
  public Node(double x, double y, Graph g)
  {
    _id = _IDCOUNTER++;
    _x = x;
    _y = y;
    _parent = g;
  }

  /**
   * Compute the euclidean distance from this node to other node
   * @param other Node we want toe get the distance to
   * @return The distance between this and other
   */
  public float distance(Node other)
  {
    return (float) Math.sqrt(
        Math.pow(other.y() - y() ) +
        Math.pow(other.x() - x() ) );
  }
  
  /**
   * Get the neighborhood of this node
   * @return All of the neighbors of this node according to the parent graph
   */
  public ArrayList<Node> neighborhood()
  {
    return _parent.getNeighborhood(this);
  }

}
