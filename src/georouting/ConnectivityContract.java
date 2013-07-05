package georouting;

import java.util.List;

public interface ConnectivityContract
{
  public float connectivityRange();
  public List<Edge> constructionConnectivity( List<Node> nodes);
  public int densityToNumberOfNodes(double density, double width, double height);
}
