package OOP;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class AllShape {
    protected int depth;
    protected JPanel panel;
    protected Canvas canvas;
    protected boolean seleced = false;

    public AllShape(Canvas canvas, int depth) {
        this.canvas = canvas;
        this.depth = depth;
    }

    protected abstract void paintShape(Graphics graphics);

    protected void drawOnCanvas(Graphics graphics) {
        paintShape(graphics);
//        canvas.add(panel, Integer.valueOf(depth));
//        canvas.revalidate();
//        canvas.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }
}
