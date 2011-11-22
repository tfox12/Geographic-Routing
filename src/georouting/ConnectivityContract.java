package georouting;

import java.util.ArrayList;

public interface ConnectivityContract
{
  public float connectivityRange();
  public ArrayList<Edge> constructionConnectivity( ArrayList<Node> nodes);
  public int densityToNumberOfNodes(double density, double width, double height);
}
