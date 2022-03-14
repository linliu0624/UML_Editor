import javax.swing.JPanel;

public abstract class AllShape {
    protected int depth;
    protected JPanel panel;
    protected Canvas canvas;
    protected boolean seleced = false;

    public AllShape(Canvas canvas, int depth) {
        this.canvas = canvas;
        this.depth = depth;
    }

    protected abstract void paintShape();

    protected void drawOnCanvas() {
        paintShape();
        canvas.add(panel, Integer.valueOf(depth));
        canvas.revalidate();
        canvas.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }
}
