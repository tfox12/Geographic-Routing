package georouting.connectivity;

import georouting.ConnectivityContract;
import georouting.Node;
import georouting.Edge;
import java.util.ArrayList;
import java.util.Set;

class StaticQuasiUnitDiskConnectivity implements ConnectivityContract
{

  private double _radius;

  public float radius() { return (float) _radius; }

  private double _innerRadius;

  public float innerRadius() { return (float) _innerRadius; }

  private double _probability;

  public float probability() { return (float) _probability; }

  public StaticQuasiUnitDiskConnectivity(double radius double innerRadius, double probability) throws Exception
  {
    if((float) probability > 1) 
    {
      throw new Exception("Probability must be represented as a decimal less than or equal to 1");
    }
    _radius = radius;
    _innerRadius = innerRadius;
    _probability = probability;
  }

  public ArrayList<Edge> constructionConnectivity(ArrayList<Nodes> nodes)
  {
    Set<Edge> rtn = new Set<Edge>();
    for(Node a : nodes)
    {
      for(Node b : nodes)
      {
        if( !a.equals(b) && 
            (a.distance(b) <= innerRadius() ||
            (a.distance(b) <= radius() && new Random().nextDouble() <= probability() )))
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