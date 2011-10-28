package georouting;

import java.util.ArrayList;

public abstract class Graph
{
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
    int numNodes = cc.densityToNumberOfNodes(nodeDensity);
    Node.resetIdCounter();
    Random rand = new Random();
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
      if(Node == null)
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
  }

}

