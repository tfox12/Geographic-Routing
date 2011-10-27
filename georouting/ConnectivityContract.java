package georouting;

import java.util.ArrayList

public interface ConnectivityContract
{
  public ArrayList<Edge> constructionConnectivity( ArrayList<Nodes> nodes);
  public int densityToNumberOfNodes(double density);
}
