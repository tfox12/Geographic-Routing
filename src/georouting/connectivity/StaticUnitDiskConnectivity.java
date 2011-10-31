package georouting.connectivity;

import georouting.ConnectivityContract;
import georouting.Node;
import georouting.Edge;
import java.util.ArrayList;
import java.util.Set;

class StaticUnitDiskConnectivity implements ConnectivityContract
{

  private double _radius;

  public float radius() { return (float) _radius; }

  public StaticUnitDiskConnectivity(double radius)
  {
    _radius = radius;
  }

  public ArrayList<Edge> constructionConnectivity(ArrayList<Nodes> nodes)
  {
    Set<Edge> rtn = new Set<Edge>();
    for(Node a : nodes)
    {
      for(Node b : nodes)
      {
        if( !a.equals(b) && a.distance(b) <= radius() )
        {
          rtn.add(new Edge(a,b));
        }
      }
    }
    return new ArrayList<Edge>(rtn);
  }

  public int densityToNumberOfNodes(double density, double width, double height)
  {
    return (int) (
        (density*width*height)/
        (Math.PI*_radius*_radius));
  }

}
