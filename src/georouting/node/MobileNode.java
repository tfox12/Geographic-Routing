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

  /**
   * @param x X coordinate
   * @param y Y coordinate
   * @param g Parent graph
   */
  public MobileNode(double x,double y,double maxSpeed,int pauseLength,Graph g)
  {
    super(x,y,g);
    _maxSpeed = maxSpeed;
    _waitLength = pauseLength;
    setNewDestination();
  }

  /**
   * If the node is not at its destination, it moves to it
   * This will also increment the wait period if already at the destination
   * This will also set a new destination if the wait expires during the call
   */
  public void move()
  {
    if(_waitCount > 0) { --_waitCount; }
    else if(distance(_dest) < (float) _moveSpeed )
    {
      _x = _dest.x();
      _y = _dest.y();
      _waitCount = _waitLength;
      setNewDestination();
    }
    else
    {
      float theta = (float) Math.atan2( _dest.y() - y() , _dest.x() - x() );
      _x += (float)(Math.cos(theta) * _moveSpeed);
      _y += (float)(Math.cos(theta) * _moveSpeed);
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
  }

}
