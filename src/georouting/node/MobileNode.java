package georouting.node;

import java.util.Random;
import georouting.Node;
import georouting.Graph;

public class MobileNode extends Node
{
  /** _moveSpeed
   *  defines how many units the node can move in one frame
   */
  private double _moveSpeed;
  
  /** _MAXSPEED
   *  defines the upper limit on the speed
   */
  private double _maxSpeed;

  /** _WAITLENGTH
   *  defines how many frames a node should wait once it has reached its destination
   */
  private int _waitLength;

  /** _waitCount
   *  If in wait mode, then this is incremented until it equals _WAITLENGTH
   */
  private int _waitCount;

  /**
   * represents where the node is trying to move to
   */
  private ImaginaryNode _dest;

  private final double DEFAULT_SPEED = 1;

	public void setSpeed(double s) 
	{ 
		_maxSpeed = s;
		_moveSpeed = new Random().nextDouble() * _maxSpeed;
	}
	
  private final int DEFAULT_PAUSE = 5;
	
	public void setPause(int p) { _waitLength = p; }
	
  /**
   * @param x X coordinate
   * @param y Y coordinate
   * @param g Parent graph
   */
  public MobileNode(double x,double y,double maxSpeed,int pauseLength,Graph g)
  {
    super(x,y,g);
    _maxSpeed = DEFAULT_SPEED;
    _waitLength = DEFAULT_PAUSE;
    _waitCount = 0;
    setNewDestination();
  }

	private boolean leftOf;
	
  /**
   * If the node is not at its destination, it moves to it
   * This will also increment the wait period if already at the destination
   * This will also set a new destination if the wait expires during the call
   */
  public void move()
  {
    if(_waitCount > 0) { --_waitCount; }
    else if(distance(_dest) < _moveSpeed || x() < _dest.x() != leftOf)
    {
      _x = _dest.x();
      _y = _dest.y();
      _waitCount = _waitLength;
      setNewDestination();
    }
    else
    {
	  double theta = Math.atan2( _dest.y() - y() , _dest.x() - x() );
      _x += (Math.cos(theta) * _moveSpeed);
      _y += (Math.sin(theta) * _moveSpeed);
    }
  }

  /**
   * Sets where the node is supposed to move to next
   */
  private void setNewDestination()
  {
    Random r = new Random();
    double x = r.nextDouble() * _parent.width();
    double y = r.nextDouble() * _parent.height();
    _dest = new ImaginaryNode(x,y);
    _moveSpeed = r.nextDouble() * _maxSpeed;
	  leftOf = x() < _dest.x();
  }

}
