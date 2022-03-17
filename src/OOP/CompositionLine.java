package OOP;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class CompositionLine extends BasicLine {
	
	private int arrowSize = 10 ;
	private double arrowAngle = Math.PI / 6;

	public CompositionLine(Canvas canvas, int depth, BasicObject startObject, BasicObject endObject, String startPart,
			String endPart) {
		super(canvas, depth, startObject, endObject, startPart, endPart);
	}

	@Override
	protected void drawArrow(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setStroke(new BasicStroke(2));
		double dx = super.getEndX() - super.getStartX(), dy = super.getEndY() - super.getStartY();
		double lineAngle = Math.atan2(dy, dx);

		double harbX1 = super.getEndX() - arrowSize * Math.cos(lineAngle + arrowAngle);
		double harbY1 = super.getEndY() - arrowSize * Math.sin(lineAngle + arrowAngle);
//		graphics2d.drawLine(super.getEndX(), super.getEndY(), (int) harbX1, (int) harbY1);

		double harbX2 = super.getEndX() - arrowSize * Math.cos(lineAngle - arrowAngle);
		double harbY2 = super.getEndY() - arrowSize * Math.sin(lineAngle - arrowAngle);
//		graphics2d.drawLine(super.getEndX(), super.getEndY(), (int) harbX2, (int) harbY2);
		double harbX3 = harbX1 - arrowSize * Math.cos(lineAngle - arrowAngle);
		double harbY3 = harbY1 - arrowSize * Math.sin(lineAngle - arrowAngle);
		
		int[] xPoints = {super.getEndX(),(int)harbX1,(int)harbX3, (int)harbX2};
		int[] yPoints = {super.getEndY(),(int)harbY1,(int)harbY3, (int)harbY2};
		
		graphics2d.setColor(Color.black);
		graphics2d.drawPolygon(xPoints, yPoints, 4);
		graphics2d.setColor(new Color(206, 232, 255));
		graphics2d.fillPolygon(xPoints, yPoints, 4);
		graphics2d.setColor(Color.black);
		
		
	}

	@Override
	protected void paintShape(Graphics graphics) {
		super.paintShape(graphics);
		drawArrow(graphics);

	}
}
