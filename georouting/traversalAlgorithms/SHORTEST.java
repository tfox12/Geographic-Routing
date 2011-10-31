package georouting.traversalAlgorithm;

import georouting.Node;
import georouting.TraversalAlgorithm;
import java.util.Set;

public class SHORTEST extends TraversalAlgorithm
{

  private Set<Node> _visited;
  private Set<Node> _heads;

  public SHORTEST(Node s, Node f)
  {
    super(s,f);
    _visited = new Set<Node>();
    _head = new Set<Node>();
    _visited.add(s);
    _start.add(s);
  }

  public void advance()
  {
    Set<Node> nextHeads = new Set<Node>();
    for(Node n : _heads)
    {
      for(Node next : n.neghborhood() )
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

  public boolean done()
  {
    return _head.contains(_destination);
  }
}
