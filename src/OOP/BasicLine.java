package OOP;

import java.awt.Graphics;

public class BasicLine extends AllShape {
	protected Canvas canvas;
	private String type = "";
	private int depth;
	private int startX, endX;
	private int startY, endY;
	private int origin = 5;

	public BasicLine(String type, Canvas canvas, int depth, int startX, int startY, int endX, int endY) {
		super(canvas, depth);
		this.canvas = canvas;
		this.type = type;
		this.depth = depth;
		this.startX = startX;
		this.endX = endX;
		this.startY = startY;
		this.endY = endY;
	}

	@Override
	protected void paintShape(Graphics graphics) {
		graphics.drawLine(startX, startY, endX, endY);
	}
	
	protected void updatePosition() {
		
	}

}
