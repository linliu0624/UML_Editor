package OOP;

import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;

import java.awt.Graphics;

public class Canvas extends JLayeredPane{
	// private ToolManager toolManager;
	private ArrayList<BasicObject> objectList = new ArrayList<BasicObject>();
	private ArrayList<BasicLine> basicLineList = new ArrayList<BasicLine>();

	private JFrame mainFrame;
	private int depthCounter = 0;
	private BasicObject startObject = null;
	private BasicObject endObject = null;
	private String startPart;
	private String endPart;
	private int selectedObjectIndex = -1;
	private BasicObject selectedObject = null;
	private boolean dragable = false;
	private int[] mouseEnterPos;
	private int[] mouseExitPos;
	private ToolMode toolMode = null;

	public Canvas(JFrame mainFrame, int[] bgRGBColor, int posX, int posY, int width, int height) {
		super();
		this.mainFrame = mainFrame;
//		this.toolManager = toolManager;

		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(new Color(bgRGBColor[0], bgRGBColor[1], bgRGBColor[2])); // 206, 232, 255
		this.setBounds(posX, posY, width, height);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println(objectList.size());
		for (BasicObject object : objectList) {
			object.drawOnCanvas(g);
		}
		for (BasicLine line : basicLineList) {
			line.drawOnCanvas(g);
		}
	}

	public void GroupObjects() {
		CompositeObject group = new CompositeObject("", this, depthCounter, Integer.MAX_VALUE, Integer.MAX_VALUE,
				Integer.MIN_VALUE, Integer.MIN_VALUE);
		depthCounter++;

		boolean hasSelected = false;

		for (BasicObject obj : objectList) {
			if (obj.getSelected() && !obj.getInGroup()) { // TODO 問題來源可能是因為 最上層的group會把所有東西都加進自己裡面 但我們只需要下面那層就好
				group.addObject(obj);
				hasSelected = true;
			}
		}
		if (hasSelected) {
			group.updateBound();
			objectList.add(group);
		}
	}

	public void UnGroupObjects() {
		int index = getIndexOfTheBiggestDepth(objectList);
		((CompositeObject) objectList.get(index)).setAllMemberNotInGroup();
		objectList.remove(objectList.get(index));

// 只有唯一選擇可以ungroup
//		int counter = 0;
//		int i = 0;
//		for (BasicObject object : objectList) {
//			if (object.getSelected() && object.getIsGroup() && !object.getInGroup()) {
//				index = i;
//				counter++;
//			}
//			i++;
//		}
//		if (counter < 2) {
//			((CompositeObject) objectList.get(index)).setAllMemberNotInGroup();
//			objectList.remove(objectList.get(index));
//		}
	}

	public void changeObjName(String name) {
		selectedObject.changeName(name);
		mainFrame.repaint();
	}

	public int getTopObjectIndex(MouseEvent e) {
		int minDepth = Integer.MIN_VALUE; // 100
		int index = 0;
		int topObjectIndex = -1;
//		BasicObject topObejct = null;
		for (BasicObject object : objectList) {
			if (object.clickInObject(e.getX(), e.getY())) {
				if (object.getDepth() > minDepth) {
					topObjectIndex = index;
//					topObejct = object;
					minDepth = object.getDepth();
				}
			}
			index++;
		}
		return topObjectIndex;
	}

	public void SelectManyObjects(MouseEvent e) {
		if (mouseEnterPos[0] > mouseExitPos[0] && mouseEnterPos[1] > mouseExitPos[1]) {
			int[] temp = mouseEnterPos;
			mouseEnterPos = mouseExitPos;
			mouseExitPos = temp;
		} else if (mouseEnterPos[0] > mouseExitPos[0] && mouseEnterPos[1] < mouseExitPos[1]) {
			int enterX = mouseEnterPos[0];
			int exitX = mouseExitPos[0];
			mouseEnterPos[0] = exitX;
			mouseExitPos[0] = enterX;
		} else if (mouseEnterPos[1] > mouseExitPos[1] && mouseEnterPos[0] < mouseExitPos[0]) {
			int enterY = mouseEnterPos[1];
			int exitY = mouseExitPos[1];
			mouseEnterPos[1] = exitY;
			mouseExitPos[1] = enterY;
		}
		int i = 0;
		if (!dragable) {
			for (BasicObject object : objectList) {
				if (object.getPosX() > mouseEnterPos[0] && object.getPosX() + object.getWidth() < mouseExitPos[0]
						&& object.getPosY() > mouseEnterPos[1]
						&& object.getPosY() + object.getHeight() < mouseExitPos[1]) {
					object.setSelected(true);
					System.out.println(i);
					i++;
				} else {
					object.setSelected(false);
				}
			}
		}
	}

	public int getMouseOnSelectedObjectIndex(MouseEvent e) {
		int index = 0;
		for (BasicObject object : objectList) {
			if (object.getSelected() && object.clickInObject(e.getX(), e.getY())) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public BasicObject getMoustOnObjectIndex(MouseEvent e) {
		int index = 0;
		int theIndex = -1;
		int maxDepth = Integer.MIN_VALUE;
		for (BasicObject object : objectList) {
//			object.setSelected(false);
			if (object.clickInObject(e.getX(), e.getY()) && object.getDepth() > maxDepth) {
				theIndex = index;
				maxDepth = object.getDepth();
			}
			index++;
		}
//		objectList.get(theIndex).setSelected(true);
		return objectList.get(theIndex);
	}

	public int getIndexOfTheBiggestDepth(ArrayList<BasicObject> objectList) {
		int index = 0;
		int theIndex = -1;
		int maxDepth = Integer.MIN_VALUE;

		for (BasicObject object : objectList) {
			if (object.getIsGroup() && object.getDepth() > maxDepth && object.getSelected()) {
				theIndex = index;
				maxDepth = object.getDepth();
			}
			index++;
		}

		return theIndex;
	}

	public void setCurrentMode(ToolMode mode) {
		removeMouseListener((MouseListener) toolMode);
		removeMouseMotionListener((MouseMotionListener) toolMode);
		this.toolMode = mode;
		addMouseListener((MouseListener) toolMode);
		addMouseMotionListener((MouseMotionListener) toolMode);
	}

	public void mainFrameRepaint() {
		mainFrame.repaint();
	}

	public ArrayList<BasicObject> getObjectList() {
		return objectList;
	}

	public ArrayList<BasicLine> getBasicLineList() {
		return basicLineList;
	}

	public int getDepthCounter() {
		return depthCounter;
	}

	public void setDepthCounter() {
		depthCounter++;
	}

	public void setSelectedObject(BasicObject object) {
		selectedObject = object;
	}

	public BasicObject getSelectedObject() {
		return selectedObject;
	}

	public int getSelectedObjectIndex() {
		return selectedObjectIndex;
	}

	public void setSelectedObjectIndex(int index) {
		selectedObjectIndex = index;
	}

	public boolean isDragable() {
		return dragable;
	}

	public void setDragable(boolean flag) {
		dragable = flag;
	}

	public void setMouseEnterPos(int x, int y) {
		mouseEnterPos = new int[] { x, y };
	}

	public int[] getMouseEnterPos() {
		return mouseEnterPos;
	}

	public void setmouseExitPos(int x, int y) {
		mouseExitPos = new int[] { x, y };
	}

	public int[] getmouseExitPos() {
		return mouseExitPos;
	}

	public BasicObject getStartObject() {
		return startObject;
	}

	public void setStartObject(BasicObject object) {
		startObject = object;
	}

	public BasicObject getEndObject() {
		return endObject;
	}

	public void setEndObject(BasicObject object) {
		endObject = object;
	}

	public void setStartPart(String port) {
		startPart = port;
	}

	public String getStartPart() {
		return startPart;
	}

	public void setEndPart(String port) {
		endPart = port;
	}

	public String getEndPart() {
		return endPart;
	}
}
