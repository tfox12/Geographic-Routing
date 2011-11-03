package georouting.node;

import georouting.Graph;
import georouting.Node;

/**
 * Exists for consistency, but adding the property of being static onto a node... adds nothing!
 */

public class StaticNode extends Node
{
  public StaticNode(double x, double y, Graph g)
  {
    super(x,y,g);
  }
}
