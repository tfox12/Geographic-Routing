package georouting;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Visualizer extends JFrame
{
  private Visualizable _content;

  public Visulizer(Visualizable v)
  {
    _content = v;
    setPreferredSize(v.size());
    JScrollPane jsp = new JScrollPane(g.canvas());
    getContentPane().add(jsp);
    addKeyListener(new KeyAdapeter()
          {
            @Override
            public void keyRelease(KeyEvent e)
            {
              _content.processKeyReleased(e);
            }
          });
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void begin()
  {
    isBeingWatched(true);
    pack();
    setVisible(true);
  }

  public void end()
  {
    isBeingWatched(false);
    setVisible(false);
  }

}
