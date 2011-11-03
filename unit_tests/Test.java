package georouting.tests;

import georouting.Visualizer;
import georouting.graph.StaticGraph;
import georouting.connectivity.StaticUnitDiskConnectivity;

public class Test
{
  public static void main(String[] args)
  {
    // specify our connectivity model with a 40px connection radius
    StaticUnitDiskConnectivity staticCC = new StaticUnitDiskConnectivity(40.0);

    // construct a static graph using a node density of 2, 
    //    width of 600px, height of 600px, nand our connectivity model
    StaticGraph graph = new StaticGraph(2.0,600.0,600.0,staticCC);

    // we want to visualize our graph
    Visualizer vis = new Visualizer(graph);
    vis.begin();
    while(true);
  }
}
