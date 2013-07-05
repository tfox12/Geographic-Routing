package georouting;

import java.util.List;

public abstract class PlanarizationAlgorithm
{
  public abstract List<Edge> planarize(List<Edge> edges);
}
