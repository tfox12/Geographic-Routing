package georouting.connectivity;

import georouting.Edge;
import georouting.MobileConnectivityContract;
import georouting.Node;
import java.util.ArrayList;

class MobileUnitDiskConnectivity
      extends StaticUnitDiskConnectivity
      implements MobileConnectivityContract
{

  public MobileUnitDiskConnectivity(double radius)
  {
    super(radius);
  }

  public ArrayList<Edge> updateConnectivity(ArrayList<Node> nodes)
  {
    return null;
  }

}
