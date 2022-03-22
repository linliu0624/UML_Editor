package OOP;

import java.awt.Graphics;
import java.util.ArrayList;

public class CompositeObject extends BasicObject {

	private int layer = 0;
	private int width = 0;
	private int height = 0;

	private ArrayList<BasicObject> objectGroup = new ArrayList<BasicObject>();

	public CompositeObject(String type, Canvas canvas, int depth, int layer, int posX, int posY, int width,
			int height) {
		super(type, canvas, depth, posX, posY, width, height);
		this.layer = layer;
	}

	@Override
	protected void paintShape(Graphics graphics) {
		graphics.drawRect(super.getPosX(), super.getPosY(), super.getWidth(), super.getHeight());
	}

	public void updatePosition() {
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		for (BasicObject object : objectGroup) {
			if (object.getPosX() < super.getPosX()) {
				super.setPosX(object.getPosX());				
			}
			if (object.getPosY() < super.getPosY()) {
				super.setPosY(object.getPosY());
			}

			if (object.getPosX() + object.getWidth() > maxX) {
				maxX = object.getPosX() + object.getWidth();
			}
			if (object.getPosY() + object.getHeight() > maxY) {
				maxY = object.getPosY() + object.getHeight();
			}
		}
		super.setWidth(maxX - super.getPosX());
		super.setHeight(maxY - super.getPosY());
		
//		System.out.println(super.getPosX());
//		System.out.println(super.getPosY());
//		System.out.println(super.getWidth());
//		System.out.println(super.getHeight());
		
	}

	public void setSelected(boolean flag) {
		super.setSelected(flag);
		for (BasicObject object : objectGroup) {
			object.setSelected(flag);
		}
	}

	public void addObject(BasicObject object) {
		objectGroup.add(object);
	}

	public ArrayList<BasicObject> getObjectGroup() {
		return objectGroup;
	}

}
