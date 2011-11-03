package georouting.traversalAlgorithms;

import georouting.Graph;
import georouting.Node;
import georouting.TraversalAlgorithm;

public class GREEDY extends TraversalAlgorithm
{

  public GREEDY(Graph g,Node s,Node f)
  {
    super(g,s,f);
  }

  public void advance()
  {
    _current = nextNode(); 
  }

  public boolean done()
  {
    return _current.equals(_destination) || nextNode() == null;
  }

  private Node nextNode()
  {
    Node winner = null;
    float tempDist = _current.distance(_destination);
    for(Node n : _current.neighborhood() )
    {
      if(n.distance(_destination) < tempDist)
      {
        tempDist = n.distance(_destination);
        winner = n;
      }
    }
    return winner;
  }
}
