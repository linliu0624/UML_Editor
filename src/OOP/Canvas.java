package OOP;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;

import java.awt.Graphics;

public class Canvas extends JLayeredPane implements MouseListener, MouseMotionListener {
	// private ToolManager toolManager;
	private ArrayList<BasicObject> objectList = new ArrayList<BasicObject>();
	private ArrayList<BasicLine> basicLineList = new ArrayList<BasicLine>();

	private JFrame mainFrame;
	private ToolManager toolManager;
	private int depth = 0;
	private BasicObject startObject = null;
	private BasicObject endObject = null;
	private String startPart;
	private String endPart;
	private int selectedObjectIndex = -1;
	private boolean draggAble = false;
	private int[] mouseEnterPos;
	private int[] mouseExitPos;

	public Canvas(JFrame mainFrame, ToolManager toolManager, int[] bgRGBColor, int posX, int posY, int width,
			int height) {
		super();
		this.mainFrame = mainFrame;
		this.toolManager = toolManager;

		this.setLayout(null);
		this.setOpaque(true);
		this.setBackground(new Color(bgRGBColor[0], bgRGBColor[1], bgRGBColor[2])); // 206, 232, 255
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(e.getX());
		System.out.println(e.getY());
		BasicObject basicObject = null;
		if (toolManager.getCurrentMode() == "select") {
			if (getTopObjectIndex(e) == -1) {
				for (BasicObject obj : objectList) {
					obj.setSelected(false);
				}
				selectedObjectIndex = -1;
			} else {
				for (BasicObject obj : objectList) {
					obj.setSelected(false);
				}
				objectList.get(getTopObjectIndex(e)).setSelected(true);
				System.out.println("Depth: " + objectList.get(getTopObjectIndex(e)).getDepth());
				selectedObjectIndex = getTopObjectIndex(e);
			}

		} else if (toolManager.getCurrentMode() == "class" || toolManager.getCurrentMode() == "use_case") {
			basicObject = new BasicObject(toolManager.getCurrentMode(), this, depth, e.getX(), e.getY(), 100, 100);
			objectList.add(basicObject);
			depth++;
		}
		mainFrame.revalidate();
		mainFrame.repaint();
//		basicObject.drawOnCanvas();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseEnterPos = new int[] { e.getX(), e.getY() };
		if (toolManager.getCurrentMode().equals("select")) {
			if (getMouseOnSelectedObjectIndex(e) != -1) {
				draggAble = true;
			} else {
				draggAble = false;

			}
		} else if (toolManager.getCurrentMode().equals("association_line")
				|| toolManager.getCurrentMode().equals("generalization_line")
				|| toolManager.getCurrentMode().equals("composition_line")) {
			startObject = getTopObjectIndex(e) == -1 ? null : objectList.get(getTopObjectIndex(e));
			if (startObject != null)
				startPart = startObject.getClosedPart(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseExitPos = new int[] { e.getX(), e.getY() };

		if (toolManager.getCurrentMode().equals("select")) {
			SelectManyObjects(e);
			mainFrame.repaint();
		} else if (toolManager.getCurrentMode().equals("association_line")
				|| toolManager.getCurrentMode().equals("generalization_line")
				|| toolManager.getCurrentMode().equals("composition_line")) {
			endObject = getTopObjectIndex(e) == -1 ? null : objectList.get(getTopObjectIndex(e));
			if (endObject != null && startObject != null && startObject != endObject
					&& startObject.getIsGroup() == false && endObject.getIsGroup() == false) {
				// draw line
				endPart = endObject.getClosedPart(e.getX(), e.getY());
				if (toolManager.getCurrentMode().equals("association_line")) {
					basicLineList.add(new AssociationLine(this, depth, startObject, endObject, startPart, endPart));
				} else if (toolManager.getCurrentMode().equals("generalization_line")) {
					basicLineList.add(new GeneralizationLine(this, depth, startObject, endObject, startPart, endPart));
				} else if (toolManager.getCurrentMode().equals("composition_line")) {
					basicLineList.add(new CompositionLine(this, depth, startObject, endObject, startPart, endPart));
				}
				depth++;
				mainFrame.repaint();
			} else {
				System.out.println("release start: " + startObject);
				System.out.println("release end: " + endObject);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (toolManager.getCurrentMode().equals("select")) {
			if (draggAble) {
				objectList.get(selectedObjectIndex).updatePosition(e.getX(), e.getY());
				mainFrame.repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	public void GroupObjects() {
		CompositeObject group = new CompositeObject("", this, depth, Integer.MAX_VALUE, Integer.MAX_VALUE,
				Integer.MIN_VALUE, Integer.MIN_VALUE);
		depth++;

		boolean hasSelected = false;

		for (BasicObject obj : objectList) {
			if (obj.getSelected() && !obj.getInGroup()) { //TODO 問題來源可能是因為 最上層的group會把所有東西都加進自己裡面 但我們只需要下面那層就好
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
//		((CompositeObject) objectList.get(index)).updateBound();
		objectList.remove(objectList.get(index));
	}

	public void changeObjName(String name) {
		
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
		if (!draggAble) {
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
			if (object.getSelected()) {
				if (object.getPosX() < e.getX() && object.getPosX() + object.getWidth() > e.getX()
						&& object.getPosY() < e.getY() && object.getPosY() + object.getHeight() > e.getY()) {
					return index;
				}
			}
			index++;
		}
		return -1;
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

}
