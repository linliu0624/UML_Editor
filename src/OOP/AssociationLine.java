package OOP;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;

public class AssociationLine extends BasicLine {

	private int arrowSize = 20;
	private double arrowAngle = Math.PI / 6;

	public AssociationLine(Canvas canvas, int depth, BasicObject startObject, BasicObject endObject, String startPart,
			String endPart) {
		super(canvas, depth, startObject, endObject, startPart, endPart);
	}

	@Override
	protected void drawArrow(Graphics graphics) {
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setStroke(new BasicStroke(1));
		double dx = super.getEndX() - super.getStartX(), dy = super.getEndY() - super.getStartY();
		double lineAngle = Math.atan2(dy, dx);
		
		double harbX = super.getEndX() - arrowSize * Math.cos(lineAngle + arrowAngle);
		double harbY = super.getEndY() - arrowSize * Math.sin(lineAngle + arrowAngle);
		graphics2d.drawLine(super.getEndX(), super.getEndY(), (int) harbX, (int) harbY);
		
		harbX = super.getEndX() - arrowSize * Math.cos(lineAngle - arrowAngle);
		harbY = super.getEndY() - arrowSize * Math.sin(lineAngle - arrowAngle);
		graphics2d.drawLine(super.getEndX(), super.getEndY(), (int) harbX, (int) harbY);
	}

	@Override
	protected void paintShape(Graphics graphics) {
		super.paintShape(graphics);
		drawArrow(graphics);

	}

}
