package georouting.traversalAlgorithms;

import georouting.Graph;
import georouting.Node;
import georouting.TraversalAlgorithm;
import java.util.HashSet;

public class SHORTEST extends TraversalAlgorithm
{

  private HashSet<Node> _visited;
  private HashSet<Node> _heads;

  public SHORTEST(Graph g, Node s, Node f)
  {
    super(g,s,f);
    _visited = new HashSet<Node>();
    _heads = new HashSet<Node>();
    _visited.add(s);
    _heads.add(s);
  }

  public void advance()
  {
    HashSet<Node> nextHeads = new HashSet<Node>();
    for(Node n : _heads)
    {
      for(Node next : n.neighborhood() )
      {
        if(_visited.add(next))
        { // this neighbor was not visited yet! its next to be in head
          nextHeads.add(next);
        }
      }
    }
    _heads = nextHeads;
    ++_hops;
  }

  @Override
  public int hops()
  {
     if(_heads.isEmpty())
         return -1;
     else
         return super.hops();
  }

  public boolean done()
  {
    return _heads.contains(_destination) || _heads.isEmpty();
  }
}
