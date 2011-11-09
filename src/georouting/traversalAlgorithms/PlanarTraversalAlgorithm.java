package georouting.traversalAlgorithms;

import georouting.Graph;
import georouting.Node;
import georouting.PlanarizationAlgorithm;
import georouting.TraversalAlgorithm;

public abstract class PlanarTraversalAlgorithm extends TraversalAlgorithm
{

  private PlanarizationAlgorithm _pAlg;

  public PlanarTraversalAlgorithm(Graph g, Node s, Node f, PlanarizationAlgorithm pa)
  {
    super(g,s,f);
    _pAlg = pa;
  }

  @Override
  public void advance()
  {
    if(!_container.wasPlanarized())
    { // this approach will planarize after each step of movement if a mobile graph
      planarize();
    }
    advanceAlgorithm();
  }

  protected abstract void advanceAlgorithm();

  private void planarize() { _container.planarize(_pAlg); }
}
