package georouting.connectivity;

import georouting.Edge;
import georouting.MobileConnectivityContract;
import georouting.Node;
import georouting.node.MobileNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class MobileQuasiUnitDiskConnectivity 
      extends StaticQuasiUnitDiskConnectivity 
      implements MobileConnectivityContract
{


  public MobileQuasiUnitDiskConnectivity(double radius,double innerRadius, double probability) throws Exception
  {
    super(radius,innerRadius,probability);
  }

  public ArrayList<Edge> updateConnectivity(ArrayList<Node> nodes, ArrayList<Edge> current)
  {
    HashSet<Edge> rtn = new HashSet<Edge>();
    
    for(Node a : nodes)
    {
        for(Node b : nodes)
        {
            if(a != b)
            {
                if(a.distance(b) <= radius())
                {
                    Edge tempConnection = null;
                    try{ tempConnection = new Edge(a,b); }
                    catch(Exception e) { System.err.println(e); System.exit(1); }
                    if(a.distance(b) <= innerRadius())
                    {
                        rtn.add(tempConnection);
                    }
                    else
                    {
                        if(current.contains(tempConnection))
                        {
                            rtn.add(tempConnection);
                        }
                        else if(a.distance( ((MobileNode)b).previousLocation()) > radius() &&
                                new Random().nextDouble() > probability())
                        {
                            rtn.add(tempConnection);
                        }
                    }
                }
            }
        }
    }    
    return new ArrayList<Edge>(rtn);
  }
}
