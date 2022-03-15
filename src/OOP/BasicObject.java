package OOP;

import java.util.HashMap;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;

public class BasicObject extends AllShape {
	// 0-99
	// private ImageIcon imageIcon;

	protected Canvas canvas;
	private boolean seleced = false;
	private String type = "";
	private int depth;
	private int posX, posY;
	private int width, height;
	private int origin = 5;
	private HashMap<String, Integer[]> fourPart = new HashMap<String, Integer[]>();

	public BasicObject(String type, Canvas canvas, int depth, int posX, int posY, int width, int height) {
		super(canvas, depth);
		this.canvas = canvas;
		this.depth = depth;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.type = type;
		this.fourPart.put("top", new Integer[] { posX + width / 2, posY });
		this.fourPart.put("left", new Integer[] { posX, posY + height / 2 });
		this.fourPart.put("bottom", new Integer[] { posX + width / 2, posY + height });
		this.fourPart.put("right", new Integer[] { posX + width, posY + height / 2 });
	}

	@Override
	protected void paintShape(Graphics graphics) {
		System.out.println("print basic object -> " + type);

//		panel = new JPanel() {
//			@Override
//			public void paint(Graphics graphics) {
//				super.paint(graphics);

		Graphics2D graphics2d = (Graphics2D) graphics;
		if (type.equals("class")) {
			graphics2d.setStroke(new BasicStroke(3));
			graphics2d.setColor(Color.black);
			graphics2d.drawRect(posX, posY, width, height);
			graphics2d.drawLine(posX, posY + height / 3, posX + width, posY + height / 3);
			graphics2d.drawLine(posX, posY + height * 2 / 3, posX + width, posY + height * 2 / 3);
		} else if (type.equals("use_case")) {
			graphics2d.setStroke(new BasicStroke(3));
			graphics2d.drawOval(posX, posY, width, height);
		}

		if (seleced) {
			graphics2d.setStroke(new BasicStroke(10));
			// top
			graphics2d.drawLine(fourPart.get("top")[0], fourPart.get("top")[1], fourPart.get("top")[0],
					fourPart.get("top")[1]);
			// left
			graphics2d.drawLine(fourPart.get("left")[0], fourPart.get("left")[1], fourPart.get("left")[0],
					fourPart.get("left")[1]);
			// bottom
			graphics2d.drawLine(fourPart.get("bottom")[0], fourPart.get("bottom")[1], fourPart.get("bottom")[0],
					fourPart.get("bottom")[1]);
			// right
			graphics2d.drawLine(fourPart.get("right")[0], fourPart.get("right")[1], fourPart.get("right")[0],
					fourPart.get("right")[1]);
		}
	}
//		};
//		panel.setBounds(posX, posY, width, height);
//		panel.setBackground(new Color(255, 255, 255));
//		panel.setLayout(null);
//		panel.setVisible(true);
//	}

	public Integer[] getClosedPart(int mouseX, int mouseY) {
		Integer[] closedPart = null;
		double distance = 10000.0;

		for (String i : fourPart.keySet()) {
			double tmpDis = Math.sqrt((mouseX - fourPart.get(i)[0]) * (mouseX - fourPart.get(i)[0])
					+ (mouseY - fourPart.get(i)[1]) * (mouseY - fourPart.get(i)[1]));
			// 新距離比較近的話
			if (distance > tmpDis) {
				distance = tmpDis;
				closedPart = fourPart.get(i);
			}
		}
		return closedPart;
	}

	protected void updatePositionAndFourPart(int mouseX, int mouseY) {
		this.posX = mouseX;
		this.posY = mouseY;
		this.fourPart.put("top", new Integer[] { posX + width / 2, posY });
		this.fourPart.put("left", new Integer[] { posX, posY + height / 2 });
		this.fourPart.put("bottom", new Integer[] { posX + width / 2, posY + height });
		this.fourPart.put("right", new Integer[] { posX + width, posY + height / 2 });
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

	public boolean getSelected() {
		return this.seleced;
	}

	public void setSelected(boolean flag) {
		this.seleced = flag;
	}

	public HashMap<String, Integer[]> getFourPart() {
		return fourPart;
	}

}