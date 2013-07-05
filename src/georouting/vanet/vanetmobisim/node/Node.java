package georouting.vanet.vanetmobisim.node;

import georouting.vanet.vanetmobisim.graph.Graph;


public class Node extends georouting.node.MobileNode
{
    de.uni_stuttgart.informatik.canu.mobisim.core.Node vertex;

    @Override
    public void move()
    {
        _x = vertex.getPosition().getX();
        _y = vertex.getPosition().getY();
    }

    public Node(de.uni_stuttgart.informatik.canu.mobisim.core.Node vertex, Graph parent)
    {
        super(vertex.getPosition().getX(), vertex.getPosition().getY(), parent);
        this.vertex = vertex;
    }


}
