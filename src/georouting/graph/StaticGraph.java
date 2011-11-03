package georouting.graph;

import georouting.ConnectivityContract;
import georouting.Graph;
import georouting.Node;
import georouting.node.StaticNode;

public class StaticGraph extends Graph
{
  public StaticGraph(double nodeDensity, double width, double height, ConnectivityContract cc)
  {
    super(nodeDensity,width,height,cc);
  }

  @Override
  public Node spawnNode(double x, double y)
  {
    return new StaticNode(x,y,this);
  }
}
