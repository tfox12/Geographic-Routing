package georouting.graph;

import georouting;

public class StaticGraph extends Graph
{
  @Override
  public Node spawnNode(double x, double y)
  {
    return new StaticNode(x,y,this);
  }
}
