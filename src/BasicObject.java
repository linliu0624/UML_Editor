import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class BasicObject extends AllShape {
    // 0-99
    // private ImageIcon imageIcon;

    protected JPanel jPanel;
    protected Canvas canvas;
    private String type = "";
    private int depth;
    private int posX, posY;
    private int width, height;
    private int origin = 5;

    public BasicObject(String type, Canvas canvas, int depth, int posX, int posY, int width, int height) {
        super(canvas, depth);
        this.canvas = canvas;
        this.depth = depth;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    @Override
    protected void paintShape() {
        System.out.println("print basic object -> " + type);
        panel = new JPanel() {
            public void paint(Graphics graphics) {
                super.paint(graphics);
                System.out.println("HI");
                Graphics2D graphics2d = (Graphics2D) graphics;
                graphics2d.setStroke(new BasicStroke(3));
                if (type.equals("class")) {
                    graphics2d.drawRect(posX, posY, width, height);
                    graphics2d.drawLine(posX, posY + height / 3, posX + width, posY + height / 3);
                    graphics2d.drawLine(posX, posY + height * 2 / 3, posX + width, posY + height * 2 / 3);
                } else if (type.equals("use_case")) {
                    graphics2d.drawOval(posX, posY, width, height);
                }

                if (seleced) {
                    graphics2d.setStroke(new BasicStroke(10));
                    // top
                    graphics2d.drawLine((posX + width) / 2 - origin, posY, (posX + width) / 2 + origin, posY);
                    // left
                    graphics2d.drawLine(posX, (posY + height) / 2 - origin, posX,
                            (posY + height) / 2 + origin);
                    // bottom
                    graphics2d.drawLine((posX + width) / 2 - origin, posY + height, (posX + width) / 2 + origin,
                            posY + height);
                    // right
                    graphics2d.drawLine(posX + width, (posY + height) / 2 - origin, posX + width + origin,
                            (posY + height) / 2 + origin);

                }
            }
        };
        System.out.println(jPanel.getClass());
        // jPanel.setBounds(posX, posY, width, height);
        // jPanel.setVisible(true);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int GetDepth() {
        return this.depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

}