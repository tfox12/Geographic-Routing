package georouting.connectivity;

import georouting.Edge;
import georouting.MobileConnectivityContract;
import georouting.Node;
import java.util.ArrayList;

class MobileQuasiUnitDiskConnectivity 
      extends StaticQuasiUnitDiskConnectivity 
      implements MobileConnectivityContract
{

  public MobileQuasiUnitDiskConnectivity(double radius,double innerRadius, double probability) throws Exception
  {
    super(radius,innerRadius,probability);
  }

  public ArrayList<Edge> updateConnectivity(ArrayList<Node> nodes)
  {
    return null;
  }
}
