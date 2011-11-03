package georouting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

public abstract class Graph implements Visualizable
{
  // 
  private TraversalAlgorithm _algorithm;

  /**
   * This is not required to run an algorithm. By "attatching" an algorithm, you allow the algorithm
   * to be attached to the visualization!
   */
  public void attachAlgorithm(TraversalAlgorithm a) { _algorithm = a; }

  /** _width
   *  width of the graph universe
   */
  private double _width;

  /**
   * returns the width of the current graph universe
   */
  public float width() { return (float) _width; }

  /** _height
   *  the height of the graph universe
   */
  private double _height;

  /**
   * returns the height of the current graph universe
   */
  public float height() { return (float) _height; }

  /** _nodes
   *  The set of nodes in the graph
   */
  private ArrayList<Node> _nodes;

  /** _edges
   *  The set of edges in the graph
   */
  private ArrayList<Edge> _edges;

  /**
   * This method has poymorphic behavior based on if this is a mobile / static graph
   */
  public abstract Node spawnNode(double x, double y);
  
  /**
   * Graph nodes are constructed as to not be coincident. They are at least 1 away from eachother.
   * @param nodeDensity Average nodes per unit
   * @param width Width of the graph universe
   * @param height Height of the graph universe
   * @param cc Contract that specifies how the nodes are to be connected to eachother
   */
  public Graph(double nodeDensity, double width, double height, ConnectivityContract cc)
  {
    _width = width;
    _height = height;
    _canvas = new JPanel(true)
          {
            public void paintComponent(Graphics g)
            {
              visPaint(g);
            }
          };
    _canvas.setPreferredSize(new Dimension((int)width,(int)height));
    int numNodes = cc.densityToNumberOfNodes(nodeDensity,width,height);
    Node.resetIdCounter();
    Random r = new Random();
    for(int i = 0; i < numNodes; ++i)
    {
      Node temp = spawnNode( r.nextDouble() * width , r.nextDouble() * height );
      for(int node_itor = 0; temp!=null && node_itor < _nodes.size(); ++node_itor)
      {
        if(_nodes.get(node_itor).distance(temp) < 1)
        {
          temp = null;
        }
      }
      if(temp == null)
      {
        --i; // redo this iteration
      }
      else
      {
        _nodes.add(temp);
      }
    }
    _edges = cc.constructionConnectivity(_nodes);
  }

  /**
   * gets the 1 hop neighbors of node n
   * @param n the node we want to use as the neighborhood reference
   */
  public ArrayList<Node> getNeighborhood(Node n)
  {
    ArrayList<Node> rtn = new ArrayList<Node>();
    for(Edge e : _edges)
    {
      if(e.containsNode(n))
      {
        rtn.add( n.equals(e.n2()) ? e.n1() : e.n2() );
      }
    }
    return rtn;
  }

  /**
   * returns all of the edges in the set of edges that intersect the provided edge
   * @param e the edge we are checking for intersection
   */
  public ArrayList<Edge> getIntersections(Edge e)
  {
    ArrayList<Edge> rtn = new ArrayList<Edge>();
    for(Edge other : _edges)
    {
      if(e.intersects(other))
      {
        rtn.add(other);
      }
    }
    return rtn;
  }

  ////////////////////////////////////////////////////////////////////
  // begin Visualizable interface

  private JPanel _canvas;
  public JPanel canvas() { return _canvas; }

  public void processKeyReleased(KeyEvent e)
  {
    if(_algorithm != null)
    {
      _algorithm.advance();
    }
  }

  public Dimension size() { return new Dimension( (int) _width, (int) _height );  }

  public void visPaint(Graphics g)
  {
    for(Edge e : _edges)
    {
      e.paint(g,Color.black);
    }
    for(Node n : _nodes)
    {
      n.paint(g,Color.black);
    }
    if(_algorithm != null)
    {
      _algorithm.paint(g);
    }
  }

  // end Visualizable inteface
  ////////////////////////////////////////////////////////////////////

}

