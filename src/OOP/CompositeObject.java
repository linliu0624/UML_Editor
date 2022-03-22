package OOP;

import java.awt.Graphics;
import java.util.ArrayList;

public class CompositeObject extends AllShape {

	private int layer = 0;
	private ArrayList<BasicObject> objectGroupe = new ArrayList<BasicObject>();

	public CompositeObject(Canvas canvas, int depth, BasicObject[] objectList) {
		super(canvas, depth);
		for (BasicObject object : objectList) {
			objectGroupe.add(object);
		}
	}

	@Override
	protected void paintShape(Graphics graphics) {
		// TODO Auto-generated method stub

	}
}
