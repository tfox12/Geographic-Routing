package georouting.graph;

import georouting.Graph;
import georouting.MobileConnectivityContract;
import georouting.Node;
import georouting.node.MobileNode;
import java.awt.event.KeyEvent;

public class MobileGraph extends Graph
{

  private double _nodeSpeed;

  private int _pauseTime;

  @Override
  protected Node spawnNode(double x, double y)
  {
    return new MobileNode(x,y,_nodeSpeed,_pauseTime,this);
  }

  private MobileConnectivityContract _connectivity;

  public MobileGraph(double nodeSpeed, int pauseTime, double density, double width, double height, MobileConnectivityContract mcc)
  {
    super(density,width,height,mcc);
    _nodeSpeed = nodeSpeed;
    _pauseTime = pauseTime;
    _connectivity = mcc;
	  for( Node n : _nodes)
	  {
		  ((MobileNode) n).setSpeed(nodeSpeed);
		  ((MobileNode) n).setPause(pauseTime);
	  }
  }

  @Override
  public void processKeyReleased(KeyEvent e)
  {
    advanceTime();
    _canvas.repaint();
  }

  public void advanceTime()
  {
    for(Node n : _nodes)
    {
      ((MobileNode) n).move();
    }
    _edges = _connectivity.updateConnectivity(_nodes);
  }
}
