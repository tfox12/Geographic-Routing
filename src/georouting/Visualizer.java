package georouting;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Visualizer extends JFrame
{
  private Visualizable _content;

  public Visualizer(Visualizable v)
  {
    _content = v;
    setPreferredSize(v.size());
    JScrollPane jsp = new JScrollPane(v.canvas());
    getContentPane().add(jsp);
    addKeyListener(new KeyAdapter()
          {
            @Override
            public void keyReleased(KeyEvent e)
            {
              _content.processKeyReleased(e);
            }
          });
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void begin()
  {
    pack();
    setVisible(true);
  }

  public void end()
  {
    setVisible(false);
  }

}
