package georouting;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

public interface Visualizable
{
  public JPanel canvas();
  public void processKeyReleased(KeyEvent e);
  public Dimension size();
}
