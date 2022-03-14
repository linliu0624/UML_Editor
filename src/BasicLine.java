import java.awt.Graphics;

public class BasicLine extends AllShape {
    private int startX, endX, startY, endY;

    public BasicLine(Canvas canvas, int depth, int startX, int endX, int startY, int endY) {
        super(canvas, depth);
        this.startX = startX;
        this.endX = endX;
        this.startY = startY;
        this.endY = endY;
    }

    @Override
    protected void paintShape() {
        // TODO Auto-generated method stub
        
    }

}
