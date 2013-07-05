package georouting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JPanel;

public abstract class Graph implements Visualizable
{

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

  private float _unit;
  public float unit() { return _unit; }

  /** _nodes
   *  The set of nodes in the graph
   */
  protected List<Node> _nodes;
  public List<Node> nodes() { return _nodes; }

  /** _edges
   *  The set of edges in the graph
   */
  protected List<Edge> _edges;

  /**
   * This method has poymorphic behavior based on if this is a mobile / static graph
   */
  protected abstract Node spawnNode(double x, double y);
  
  public Graph(double width, double height, ConnectivityContract cc, List<Node> nodes)
  {
    _wasPlanarized = false;
    _nodes = nodes;
    _width = width;
    _height = height;
    _unit = cc.connectivityRange();
    _canvas = new JPanel(true)
    {
      @Override
      public void paintComponent(Graphics g)
      {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0,0,(int)_width,(int)_height);
        visPaint(g);
      }
    };
    _canvas.setPreferredSize(new Dimension((int)width,(int)height));
    _edges = cc.constructionConnectivity(_nodes);
  }

  /**
   * Graph nodes are constructed as to not be coincident. They are at least 1 away from eachother.
   * @param nodeDensity Average nodes per unit
   * @param width Width of the graph universe
   * @param height Height of the graph universe
   * @param cc Contract that specifies how the nodes are to be connected to eachother
   */
  public Graph(double nodeDensity, double width, double height, ConnectivityContract cc)
  {
    _wasPlanarized = false;
    _nodes = new ArrayList<Node>();
    _width = width;
    _height = height;
    _unit = cc.connectivityRange();
    _canvas = new JPanel(true)
    {
      @Override
      public void paintComponent(Graphics g)
      {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0,0,(int)_width,(int)_height);
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
  public List<Node> getNeighborhood(Node n)
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
  public List<Edge> getIntersections(Edge e)
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
	
  /**
   * Planarizing the graph removes edges so that no 2 edges intersect except for at verticies
   * @param algorithm the planarization technique to apply to the graph to achieve planarization
   */
  public void planarize(PlanarizationAlgorithm algorithm)
  {
    _edges = algorithm.planarize(_edges);
    _wasPlanarized = true;
  }

  protected boolean _wasPlanarized;
  public boolean wasPlanarized() { return _wasPlanarized; }

	public boolean isConnected(Node from, Node to)
	{
		HashSet<Node> touched = new HashSet<Node>();
		HashSet<Node> toProcess = new HashSet<Node>();
		toProcess.add(from);
		touched.add(from);
		while(!toProcess.isEmpty())
		{
			Iterator<Node> itor = toProcess.iterator();
			Node next = itor.next();
			itor.remove();
			for(Node n : next.neighborhood())
			{
				if(n == to) return true;
				if(touched.add(n))
				{
					toProcess.add(n);
				}
			}
		}
		return false;
	}

  ////////////////////////////////////////////////////////////////////
  // begin Visualizable interface

  protected JPanel _canvas;
  public JPanel canvas() { return _canvas; }

  public void processKeyReleased(KeyEvent e)
  {
    _canvas.repaint();
  }

  @Override
  public String toString()
  {
    return "This graph contains " + _nodes.size() + " nodes";
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
  }

  // end Visualizable inteface
  ////////////////////////////////////////////////////////////////////

}

