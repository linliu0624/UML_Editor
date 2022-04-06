package OOP;

import java.awt.event.MouseEvent;

public class SelectMode extends ToolMode {

	public SelectMode(Canvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);

		if (canvas.getTopObjectIndex(e) == -1) {
			for (BasicObject obj : canvas.getObjectList()) {
				obj.setSelected(false);
			}
			canvas.setSelectedObjectIndex(-1);
			canvas.setSelectedObject(null);
		} else {
			for (BasicObject obj : canvas.getObjectList()) {
				obj.setSelected(false);
			}
//			objectList.get(getTopObjectIndex(e)).setSelected(true);
			canvas.setSelectedObjectIndex(canvas.getTopObjectIndex(e));
			canvas.setSelectedObject(canvas.getObjectList().get(canvas.getTopObjectIndex(e)));
			canvas.getSelectedObject().setSelected(true);
			System.out.println("Depth: " + canvas.getObjectList().get(canvas.getTopObjectIndex(e)).getDepth());

		}

		if (canvas.getMouseOnSelectedObjectIndex(e) != -1) {
			canvas.setDragable(true);
		} else {
			canvas.setDragable(false);
		}
	}
	public void mouseDragged(MouseEvent e) {
		if (canvas.isDragable()) {
			canvas.getObjectList().get(canvas.getSelectedObjectIndex()).updatePosition(e.getX(), e.getY());
			canvas.mainFrameRepaint();
		}
	}
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		canvas.SelectManyObjects(e);
		canvas.mainFrameRepaint();
	}

}
