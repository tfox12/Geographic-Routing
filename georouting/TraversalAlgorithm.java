package georouting;

/**
 * The traversal algorithm is the meat of the framework. It specifies how we want to
 * forward packets in the network. We represent this by having the traversal algorithm
 * specifying how to traverse the graph
 */
public abstract class TraversalAlgorithm
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

  /**
   * @param s Starting point
   * @param d End point
   */
  public TraversalAlgorithm(Node s, Node d)
  {
    _source = _current = s;
    _destination = d;
    _hops = 0;
  }

}
