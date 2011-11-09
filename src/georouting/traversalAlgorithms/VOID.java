package georouting.traversalAlgorithms;

import georouting.Edge;
import georouting.Graph;
import georouting.Node;
import georouting.TraversalAlgorithm;
import georouting.node.ImaginaryNode;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class VOID extends TraversalAlgorithm
{

  @Override
  public boolean done()
  {
    return _current == _destination;
  }

  @Override
  public void advance()
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
    Edge justTraversed = null;
    try { justTraversed = new Edge(_previous,_current); }
    catch(Exception e) { System.err.println("Critical Eror"); System.exit(1); }
    ArrayList<Edge> traversedIntersections = justTraversed.intersections();
    if(_butIDidThatLastTime != null)
    {
      float distance = justTraversed.pointOfIntersection(_butIDidThatLastTime)
                                    .distance(_current);
      for(int i = traversedIntersections.size() -1; i >= 0; --i)
      {
        if(traversedIntersections.get(i).pointOfIntersection(justTraversed).distance(_current) > distance ||
           traversedIntersections.get(i).equals(_butIDidThatLastTime))
        {
          traversedIntersections.remove(i);
        }
      }
      _butIDidThatLastTime = null;
    }
    if(!traversedIntersections.isEmpty() && 
       handledIntersection(traversedIntersections, justTraversed))
    {
      return;
    }
    if(justTraversed.intersects(xd()))
    {
      _X = justTraversed.pointOfIntersection(xd());
      flipDirection();
    }
    moveTo(bestNeighbor());
    return;
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

  @Override
  public void visPaint(Graphics g)
  {
    super.visPaint(g);
    xd().paint(g,Color.cyan);
    canvas().repaint();
  }

  private boolean handledIntersection(ArrayList<Edge> traversedIntersections, Edge justTraversed)
  {
    ArrayList<Edge> winners = new ArrayList<Edge>();
    ImaginaryNode winningPoint = null;
    double dist = Double.MAX_VALUE;
    for(Edge edge : traversedIntersections)
    {
      Node temp = edge.pointOfIntersection(justTraversed);
      if(temp.distance(_previous) < dist && temp.distance(_previous) > 0)
      {
        dist = temp.distance(_previous);
        winners.clear();
        winners.add(edge);
        winningPoint = new ImaginaryNode(temp);
      }
    }
    if(winners.isEmpty())
    {
      return false;
    }
    _butIDidThatLastTime = justTraversed;
    justTraversed = winningPoint.imaginaryEdgeWith(_previous);
    if(justTraversed.intersects(xd()))
    {
      _X = justTraversed.pointOfIntersection(xd());
      flipDirection();
    }
    ArrayList<Node> nextRoundWinners = new ArrayList<Node>();
    double winningAngle = Double.MAX_VALUE;
    for(Edge edge : winners)
    {
      float a1 = getNodeAngle(_previous,winningPoint,edge.n1());
      float a2 = getNodeAngle(_previous,winningPoint,edge.n2());
      if(a1 < winningAngle)
      {
        nextRoundWinners.clear();
        nextRoundWinners.add(edge.n1());
        winningAngle = a1;
      }
      else if(a1 == (float) winningAngle)
      {
        nextRoundWinners.add(edge.n1());
      }
      if(a2 < winningAngle)
      {
        nextRoundWinners.clear();
        nextRoundWinners.add(edge.n2());
        winningAngle = a2;
      }
      else if(a2 == (float) winningAngle)
      {
        nextRoundWinners.add(edge.n2());
      }
    }
    Node finalWinner = null;
    double distance = Double.MAX_VALUE;
    for(Node node : nextRoundWinners)
    {
      if(node.distance(winningPoint) < distance)
      {
        finalWinner = node;
        distance = node.distance(winningPoint);
      }
    }
    _hops += movesToGetThere(_current,finalWinner);
	  if(_hops == -1)
	  {
		  System.exit(1);
	  }
    for(Edge edge : winners)
    {
      if(edge.containsNode(finalWinner))
      {
        _current = finalWinner;
        _previous = ( edge.n1().equals(finalWinner) ) ? edge.n2() : edge.n1();
        break;
      }
    }
    return true;
  }

  public VOID(Graph g, Node s, Node f)
  {
    super(g,s,f);
    _previous = null;
    _dir = Direction.CLOCKWISE;
    _X = new ImaginaryNode(s);
    _butIDidThatLastTime = null;
  }

  private void moveTo(Node n)
  {
    _previous = _current;
    _current = n;
    ++_hops;
  }

  private Node _previous;
  
  private ImaginaryNode _X;

  private Edge xd()
  {
    return _X.imaginaryEdgeWith(_destination);
  }

  private Edge _butIDidThatLastTime;

  private Direction _dir;

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
    ArrayList<Node> neighborhood = _current.neighborhood();
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
}
