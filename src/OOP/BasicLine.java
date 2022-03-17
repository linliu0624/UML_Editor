package OOP;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class BasicLine extends AllShape {
	protected Canvas canvas;
	private int depth;

	private BasicObject startObject;
	private String startPart;
	private BasicObject endObject;
	private String endPart;
	private int startX;
	private int startY;
	private int endX;
	private int endY;

	public BasicLine(Canvas canvas, int depth, BasicObject startObject, BasicObject endObject,
			String startPart, String endPart) {
		super(canvas, depth);
		this.canvas = canvas;
		this.depth = depth;
		this.startObject = startObject;
		this.startPart = startPart;
		this.endObject = endObject;
		this.endPart = endPart;
	}

	protected abstract void drawArrow(Graphics graphics);

	@Override
	protected void paintShape(Graphics graphics) {
		startX = startObject.getFourPart().get(startPart)[0];
		startY = startObject.getFourPart().get(startPart)[1];
		endX = endObject.getFourPart().get(endPart)[0];
		endY = endObject.getFourPart().get(endPart)[1];

		Graphics2D g2d = (Graphics2D) graphics;
		g2d.setStroke(new BasicStroke(1));
		g2d.drawLine(startX, startY, endX, endY);

		g2d.setStroke(new BasicStroke(10));
		g2d.drawLine(startX, startY, startX, startY);
		g2d.drawLine(endX, endY, endX, endY);
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	public int getEndX() {
		return endX;
	}

	public int getEndY() {
		return endY;
	}
}
