package georouting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
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

  private Point2D.Float _point;

  public Point2D.Float point() { return _float; }

  /** _parent
   *  Graph that contains this node
   */
  potected Graph _parent;

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
    _point = new Point.Float((float)x,(float)y);
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
        Math.pow(other.y() - y() , 2 ) +
        Math.pow(other.x() - x() , 2 ) );
  }
  
  /**
   * Get the neighborhood of this node
   * @return All of the neighbors of this node according to the parent graph
   */
  public ArrayList<Node> neighborhood()
  {
    return _parent.getNeighborhood(this);
  }

  /**
   * define equallity for 2 nodes
   * @param o object we are checking aginst this for equality
   */
  @Override
  public boolean equals(Object o)
  {
    if(o instanceof Node)
    {
      Node n = (Node)o;
      boolean rtn = n.id() == id();
      if(!rtn)
      {
        rtn = n.x() == x() && n.y() == y();
      }
      return rtn;
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    int hash = 7;
    hash = 37 * hash + (int) (Double.doubleToLongBits(_x) ^ (Double.doubleToLongBits(_x) >>> 32));
    hash = 37 * hash + (int) (Double.doubleToLongBits(_y) ^ (Double.doubleToLongBits(_y) >>> 32));
    return hash;
  }

  @Override
  public String toString()
  {
    return "( " + x() + " , " + y() + " )";
  }

  public void paint(Graphics g, Color c)
  {
    g.setColor(c);
    g.fillOval((int)_x-3,(int)_y-3,6,6);
  }
}
