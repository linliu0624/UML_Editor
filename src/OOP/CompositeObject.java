package OOP;

import java.awt.Graphics;
import java.util.ArrayList;

public class CompositeObject extends BasicObject {

	private ArrayList<BasicObject> objectGroup = new ArrayList<BasicObject>();

	public CompositeObject(String type, Canvas canvas, int depth, int posX, int posY, int width,
			int height) {
		super(type, canvas, depth, posX, posY, width, height);
		super.setIsGroup(true);
	}

	@Override
	protected void paintShape(Graphics graphics) {
		graphics.drawRect(super.getPosX(), super.getPosY(), super.getWidth(), super.getHeight());
	}

	protected void updateBound() {
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
		setMembersDis();
	}

	private void setMembersDis() {
		for (BasicObject object : objectGroup) {
			if (!object.getBindingWithGroup()) {
				object.setDisWithGroup(super.getPosX(), super.getPosY());
				object.setBindingWithGroup(true);
			}
		}

	}

	public void disBindingAllMember() {
		for (BasicObject object : getObjectGroup()) {
			object.setBindingWithGroup(false);
		}
	}

	@Override
	protected void updatePosition(int mouseX, int mouseY) {
		for (BasicObject object : objectGroup) {
			super.updatePosition(mouseX, mouseY);
			object.updatePosition(super.getPosX() + object.getDisWithGroup()[0],
					super.getPosY() + object.getDisWithGroup()[1]);
		}
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
