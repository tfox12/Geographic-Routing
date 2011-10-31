package georouting;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.JPanel;
import java.awt.event.KeyEvent;

public interface Visualizable
{
  public JPanel canvas();
  public void processKeyReleased(KeyEvent e);
  public Dimension size();
}
