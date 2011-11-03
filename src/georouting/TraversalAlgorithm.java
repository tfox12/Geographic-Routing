package georouting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

/**
 * <p>The traversal algorithm is the meat of the framework. It specifies how we want to
 * forward packets in the network. We represent this by having the traversal algorithm
 * specifying how to traverse the graph.</p>
 */
public abstract class TraversalAlgorithm implements Visualizable
{
  /** _source
   *  The starting node in the traversal algorithm
   */
  protected Node _source;

  /** _destination
   *  The node that the algorithm is trying to reach
   */
  protected Node _destination;

  /** _current
   *  specifies where the algorithm is in the network at the moment
   */
  protected Node _current;

  /** _hops
   *  specifies how my moves the algorithm took.
   */
  protected int _hops;

  /**
   * accessor for hops. mainly used for querying number of steps after the algorithm finishes
   */
  public int hops() { return _hops; }

  /**
   * advances the algorithms logic during 1 unit of time
   */
  public abstract void advance();

  /**
   * Is the algorithm done?
   */
  public abstract boolean done();

  protected Graph _container;

  /**
   * @param s Starting point
   * @param d End point
   */
  public TraversalAlgorithm(Graph g, Node s, Node d)
  {
    _canvas = new JPanel(true)
    {
      @Override
      public void paintComponent(Graphics g)
      {
        visPaint(g);
      }
    };
    _container = g;
    _source = _current = s;
    _destination = d;
    _hops = 0;
  }

  public void visPaint(Graphics g)
  {
    _container.visPaint(g);
    _current.paint(g,Color.green);
    _source.paint(g,Color.blue);
    _destination.paint(g,Color.red);
  }

  private JPanel _canvas;
  public JPanel canvas() { return _canvas; }
  public void processKeyReleased(KeyEvent e)
  {
    if(!done()) advance();
    _canvas.repaint();
  }
  public Dimension size() { return _container.size(); }

}
