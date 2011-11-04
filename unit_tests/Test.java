package georouting.tests;

import georouting.MobileConnectivityContract;
import georouting.Graph;
import georouting.TraversalAlgorithm;
import georouting.Visualizer;
import georouting.graph.MobileGraph;
import georouting.connectivity.MobileUnitDiskConnectivity;
import georouting.traversalAlgorithms.VOID;

public class Test
{
  public static void main(String[] args)
  {
    // specify our connectivity model with a 40px connection radius
    MobileConnectivityContract cc = new MobileUnitDiskConnectivity(30.0);

    // construct a static graph using a node density of 2, 
    //    width of 600px, height of 600px, nand our connectivity model
    Graph graph = new MobileGraph(5,10,5,600,600,cc);
    
    // we want to visualize our graph
    Visualizer vis = new Visualizer(graph);
    vis.begin();
  }
}
