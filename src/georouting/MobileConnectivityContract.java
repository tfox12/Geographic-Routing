package georouting;

import java.util.List;

public interface MobileConnectivityContract extends ConnectivityContract
{
  public List<Edge> updateConnectivity(List<Node> nodes, List<Edge> current);
}
