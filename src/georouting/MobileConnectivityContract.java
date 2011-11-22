package georouting;

import java.util.ArrayList;

public interface MobileConnectivityContract extends ConnectivityContract
{
  public ArrayList<Edge> updateConnectivity(ArrayList<Node> nodes, ArrayList<Edge> current);
}
