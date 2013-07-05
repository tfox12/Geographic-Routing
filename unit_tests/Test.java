package georouting.tests;

import de.uni_stuttgart.informatik.canu.mobisim.core.*;
import georouting.vanet.vanetmobisim.graph.Graph;
import georouting.Visualizer;


import javax.xml.parsers.*; 

public class Test
{ 
  
     public static void main(String[] args)
     {
        // Create the universe for VanetMobiSim
        Universe u = Universe.getReference();
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
  
            org.w3c.dom.Document document = builder.parse(new java.io.FileInputStream(new java.io.File("universe.xml")));
  
            org.w3c.dom.Element root=document.getDocumentElement();
  
            String rootTag=root.getNodeName();
            if (!rootTag.equals("universe"))
                throw new Exception("Invalid parent tag: "+rootTag);
  
            u.load(root);
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
            System.exit(1);
        }
        u.initialize();

        // The universe has been init'd.
        Graph graph = new Graph(u);
        Visualizer vis = new Visualizer(graph);
        vis.begin();
    }

}
