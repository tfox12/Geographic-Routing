package georouting.vanet.vanetmobisim.graph;

import georouting.connectivity.MobileUnitDiskConnectivity;
import georouting.vanet.vanetmobisim.node.Node;

import de.uni_stuttgart.informatik.canu.mobisim.core.Universe;

import java.util.ArrayList;
import java.util.List;

/**
 * Acts as a wrapper graph around the VanetMobiSim Graph environment.
 * This permits us to apply our algorithms and connectivity models over the
 * mobility engine provided.
 */
public class Graph extends georouting.graph.MobileGraph
{
    private Universe universe;

    public Graph(Universe universe)
    {
        super(universe.getDimensionX(), universe.getDimensionY(), new MobileUnitDiskConnectivity(50.0), new ArrayList());
        this.universe = universe;

        for(Object o  : universe.getNodes())
        {

            _nodes.add(new Node((de.uni_stuttgart.informatik.canu.mobisim.core.Node) o, this));
        }
    }

    /**
     * {@inheritDoc}
     * <br/><br/>
     * We also need to make sure that we advance all of the nodes in VanetMobiSim
     */
    @Override
    public void advanceTime()
    {
        universe.advanceTime();

        super.advanceTime();
    }

}
