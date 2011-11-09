package georouting.tests;

import georouting.MobileConnectivityContract;
import georouting.Graph;
import georouting.TraversalAlgorithm;
import georouting.Visualizer;
import georouting.graph.MobileGraph;
import georouting.connectivity.MobileUnitDiskConnectivity;
import georouting.traversalAlgorithms.SHORTEST;
import georouting.traversalAlgorithms.VOID;
import georouting.node.MobileNode;

public class Test
{
  public static void main(String[] args)
  {
    MobileConnectivityContract cc = new MobileUnitDiskConnectivity(25);
    MobileGraph graph = null;
    do
    {
        graph = new MobileGraph(25,10,4.71,500,500,cc);
    }while(!graph.isConnected(graph.nodes().get(0),graph.nodes().get(graph.nodes().size()-1)));
    VOID algorithm = new VOID(graph,graph.nodes().get(0),graph.nodes().get(graph.nodes().size()-1));
    ((MobileNode)graph.nodes().get(graph.nodes().size()-1)).setSpeed(0);
    Visualizer vis = new Visualizer(algorithm);
    vis.begin();
  }
}
