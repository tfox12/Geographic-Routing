package georouting.tests;

import georouting.ConnectivityContract;
import georouting.Graph;
import georouting.TraversalAlgorithm;
import georouting.Visualizer;
import georouting.graph.StaticGraph;
import georouting.connectivity.StaticUnitDiskConnectivity;
import georouting.traversalAlgorithms.VOID;

public class Test
{
  public static void main(String[] args)
  {
    // specify our connectivity model with a 40px connection radius
    ConnectivityContract staticCC = new StaticUnitDiskConnectivity(30.0);

    // construct a static graph using a node density of 2, 
    //    width of 600px, height of 600px, nand our connectivity model
    Graph graph = new StaticGraph(5,600,600,staticCC);
    
    // test with an algorithm
    TraversalAlgorithm traversal = new VOID(graph,
        graph.nodes().get(0),
        graph.nodes().get(graph.nodes().size() -1));

    // we want to visualize our graph
    Visualizer vis = new Visualizer(traversal);
    vis.begin();
  }
}
