package georouting.connectivity;

import georouting.Edge;
import georouting.MobileConnectivityContract;
import georouting.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class MobileUnitDiskConnectivity
      extends StaticUnitDiskConnectivity
      implements MobileConnectivityContract
{

  public MobileUnitDiskConnectivity(double radius)
  {
    super(radius);
  }

  public List<Edge> updateConnectivity(List<Node> nodes, List<Edge> current)
  {
    HashSet<Edge> rtn = new HashSet<Edge>();
    for(Node a : nodes)
    {
      for(Node b : nodes)
      {
        if( !a.equals(b) && a.distance(b) <= radius() )
        {
          try
          {
            rtn.add(new Edge(a,b));
          }
          catch(Exception e)
          { 
            System.err.println(
              "ERROR: you just tried to justify the existance on an Imaginary Node. STOP THAT!"); 
          }
        }
      }
    }
    return new ArrayList<Edge>(rtn);
  }

}
