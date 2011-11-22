package georouting.tests;

import georouting.MobileConnectivityContract;
import georouting.Graph;
import georouting.TraversalAlgorithm;
import georouting.Visualizer;
import georouting.graph.MobileGraph;
import georouting.connectivity.MobileQuasiUnitDiskConnectivity;
import georouting.traversalAlgorithms.SHORTEST;
import georouting.traversalAlgorithms.VOID;
import georouting.node.MobileNode;

public class Test
{
  public static void main(String[] args)
  {
    final int NUMRUNS = 1000;
    for(int i = 0; i <= 25; ++i)
    {
        System.out.println("" + i + " " + NUMRUNS);
        for(int runs = 0; runs < NUMRUNS; ++runs)
        {
            MobileConnectivityContract cc = null; 
            try { cc = new MobileQuasiUnitDiskConnectivity(25,16.7,.5); }
            catch(Exception e){ System.err.println("I fucked up my parameters D:"); System.exit(1); }
            MobileGraph graph = null;
            do
            {
                graph = new MobileGraph(i,10,4.71,500,500,cc);
            }while(!graph.isConnected(graph.nodes().get(0),graph.nodes().get(graph.nodes().size()-1)));
            VOID algorithm = new VOID(graph,graph.nodes().get(0),graph.nodes().get(graph.nodes().size()-1));
            while(!algorithm.done())
            {
                algorithm.advance();
                graph.advanceTime();
            }
            System.out.println(algorithm.hops() + " " + (algorithm.successful() ? "1" : "0"));
        }
    }
    System.out.println("0 0");
  }
}
