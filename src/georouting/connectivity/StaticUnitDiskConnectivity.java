package georouting.connectivity;

import georouting.ConnectivityContract;
import georouting.Node;
import georouting.Edge;
import java.util.ArrayList;
import java.util.HashSet;

public class StaticUnitDiskConnectivity implements ConnectivityContract
{

  private double _radius;

  public float radius() { return (float) _radius; }

  public StaticUnitDiskConnectivity(double radius)
  {
    _radius = radius;
  }

  public ArrayList<Edge> constructionConnectivity(ArrayList<Node> nodes)
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

  public int densityToNumberOfNodes(double density, double width, double height)
  {
    return (int) (
        (density*width*height)/
        (Math.PI*_radius*_radius));
  }

}
