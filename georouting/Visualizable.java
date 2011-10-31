package georouting;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public interface Visualizable
{
  public JPanel canvas();
  public void processKeyReleased(KeyEvent e);
  public Dimension size();
  public void isBeingWatched(boolean amI);
  public void visPaint(Graphics g);
}
