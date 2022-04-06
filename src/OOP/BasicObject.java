package OOP;

import java.util.HashMap;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

public class BasicObject extends AllShape {
	// 0-99
	// private ImageIcon imageIcon;

	protected Canvas canvas;
	private String objectName = "name";
	private boolean seleced = false;
	private String type = "";
	private int depth;
	private int posX, posY;
	private int width, height;
	private HashMap<String, Integer[]> fourPart = new HashMap<String, Integer[]>();
	private int[] disWithGroup = { 0, 0 };
	private boolean isGroup = false;
	private boolean inGroup = false;

	public BasicObject(String type, Canvas canvas, int depth, int posX, int posY, int width, int height) {
		super(canvas, depth);
		this.canvas = canvas;
		this.depth = depth;
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		if (type.equals("use_case")) {
			this.setHeight(this.height - 15);
		}
		this.type = type;
		this.fourPart.put("top", new Integer[] { this.posX + this.width / 2, this.posY });
		this.fourPart.put("left", new Integer[] { this.posX, this.posY + this.height / 2 });
		this.fourPart.put("bottom", new Integer[] { this.posX + this.width / 2, this.posY + this.height });
		this.fourPart.put("right", new Integer[] { this.posX + this.width, this.posY + this.height / 2 });
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
//			int[] xPoints = { posX+1, posX + width, posX + width, posX+1 };
//			int[] yPoints = { posY+1, posY+1, posY + height, posY + height };
//			graphics2d.setColor(Color.yellow);
//			graphics2d.fillPolygon(xPoints, yPoints, 4);
//			graphics2d.setColor(Color.black);
			graphics2d.drawLine(posX, posY + height / 3, posX + width, posY + height / 3);
			graphics2d.drawLine(posX, posY + height * 2 / 3, posX + width, posY + height * 2 / 3);
			drawFont(graphics2d, 10, 8);

		} else if (type.equals("use_case")) {
			graphics2d.setStroke(new BasicStroke(3));
			graphics2d.drawOval(posX, posY, width, height);
//			graphics2d.setColor(Color.yellow);
//			graphics2d.fillOval(posX, posY, width, height);
//			graphics2d.setColor(Color.black);
			drawFont(graphics2d, 8, 2);
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
	private void drawFont(Graphics2D graphics2d, int perWidth, int perHeight) {
		Font font = new Font(Font.DIALOG, Font.BOLD, 14);
		graphics2d.setFont(font);
		graphics2d.drawString(objectName, getPosX() + width / perWidth, getPosY() + height / perHeight);
	}

	public void changeName(String name) {
		this.objectName = name;
	}

	public String getClosedPart(int mouseX, int mouseY) {
		String closedPart = null;
		double distance = Double.MAX_VALUE;

		for (String i : fourPart.keySet()) {
			double tmpDis = Math.sqrt((mouseX - fourPart.get(i)[0]) * (mouseX - fourPart.get(i)[0])
					+ (mouseY - fourPart.get(i)[1]) * (mouseY - fourPart.get(i)[1]));
			// 新距離比較近的話
			if (distance > tmpDis) {
				distance = tmpDis;
				closedPart = i;
			}
		}
		return closedPart;
	}

	public boolean clickInObject(int x, int y) {
		if (getPosX() < x && getPosX() + getWidth() > x && getPosY() < y && getPosY() + getHeight() > y) {
			return true;
		}
		return false;

	}

	protected void updatePosition(int mouseX, int mouseY) {
		this.posX = mouseX;
		this.posY = mouseY;
		this.fourPart.put("top", new Integer[] { posX + width / 2, posY });
		this.fourPart.put("left", new Integer[] { posX, posY + height / 2 });
		this.fourPart.put("bottom", new Integer[] { posX + width / 2, posY + height });
		this.fourPart.put("right", new Integer[] { posX + width, posY + height / 2 });
	}

	protected boolean getInGroup() {
		return inGroup;
	}

	protected void setInGroup(boolean flag) {
		inGroup = flag;
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

	public int getDepth() {
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

	public boolean getIsGroup() {
		return isGroup;
	}

	public void setIsGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	public HashMap<String, Integer[]> getFourPart() {
		return fourPart;
	}

	public void setDisWithGroup(int groupX, int groupY) {
		disWithGroup[0] = getPosX() - groupX;
		disWithGroup[1] = getPosY() - groupY;
	}

	public int[] getDisWithGroup() {
		return disWithGroup;
	}
}