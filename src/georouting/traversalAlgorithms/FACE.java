package georouting.traversalAlgorithms;

import georouting.Edge;
import georouting.Graph;
import georouting.Node;
import georouting.PlanarizationAlgorithm;
import georouting.node.ImaginaryNode;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

public class FACE extends PlanarTraversalAlgorithm
{
  private Direction _dir;
  private Node _previous;
  private ImaginaryNode _X;

  private Edge justTraversed()
  {
    Edge rtn = null;
    try{ rtn = new Edge(_previous,_current); }
    catch(Exception e) { System.err.println("Critical Error"); System.exit(1); }
    return rtn;
  }

  private Edge xd()
  {
    return _X.imaginaryEdgeWith(_destination);
  }

  public FACE(Graph g, Node s, Node f, PlanarizationAlgorithm pa)
  {
    super(g,s,f,pa);
    _dir = Direction.CLOCKWISE;
    _X = new ImaginaryNode(s);
    _previous = null;
  }

  @Override
  public void visPaint(Graphics g)
  {
    super.visPaint(g);
    xd().paint(g,Color.cyan);
    canvas().repaint();
  }

  @Override
  protected void advanceAlgorithm()
  {
    if(done()) return;
    if(_current.neighborhood().isEmpty()) return;
    if(_current.neighborhood().contains(_destination))
    {
      moveTo(_destination);
      return;
    }
    if(_previous == null)
    {
      firstRun();
      return;
    }
    if(justTraversed().intersects(xd()))
    {
      _X = justTraversed().pointOfIntersection(xd());
      flipDirection();
    }
    moveTo(bestNeighbor());
  }

  private void firstRun()
  {
    _dir = Direction.CLOCKWISE;
    Node cwWinner = bestNeighbor();
    double cwAngle = getNodeAngle(_destination,_current,cwWinner);
    _dir = Direction.COUNTERCLOCKWISE;
    Node ccwWinner = bestNeighbor();
    double ccwAngle = getNodeAngle(_destination,_current,ccwWinner);
    if(cwAngle < ccwAngle)
    {
      _dir = Direction.CLOCKWISE;
      moveTo(cwWinner);
    }
    else
    {
      moveTo(ccwWinner);
    }
  }

  private void moveTo(Node n)
  {
    _hops += movesToGetThere(_current,n);
    _previous = _current;
    _current = n;
  }

  private void flipDirection()
  {
    float x1 = -( _current.y() - _previous.y());
    float y1 = _current.x() - _previous.x();
    float x2 = _destination.x() - _previous.x();
    float y2 = _destination.y() - _previous.y();
    double dot_product = x1*x2 + y1*y2;
    if( (float) dot_product > 0) _dir = Direction.CLOCKWISE;
    else                         _dir = Direction.COUNTERCLOCKWISE;
  }

  private Node bestNeighbor()
  {
    List<Node> neighborhood = _current.neighborhood();
    Node rtn = _current;
    double angle = Double.MAX_VALUE;
    for(Node n : neighborhood)
    {
      if(getNodeAngle(n) > 0 && getNodeAngle(n) < angle)
      {
        angle = getNodeAngle(n);
        rtn = n;
      }
    }
    return rtn;
  }

  private float getNodeAngle(Node a)
  {
    return getNodeAngle( 
        ( ( _previous == null ) ? _destination : _previous ) , 
        _current , 
        a);
  }

  private float getNodeAngle(Node a, Node b, Node c)
  {
    double angle = Math.atan2( c.y() - b.y() , c.x() - b.x() )
                 - Math.atan2( a.y() - b.y() , a.x() - b.x() );
    angle = (_dir == Direction.CLOCKWISE) ? 2 * Math.PI - angle : angle;
    while( (float) angle > 2 * Math.PI ) angle -= 2 * Math.PI;
    while( (float) angle <= 0 ) angle += 2 * Math.PI;
    return (float) angle;
  }

  private int movesToGetThere(Node start,Node dest)
  {
    SHORTEST shortestsPath = new SHORTEST(_container,start,dest);
    while(!shortestsPath.done())
    {
      shortestsPath.advance();
    }
    return shortestsPath.hops();
  }

  @Override
  public boolean done()
  {
    return _current.equals(_destination);
  }
}
