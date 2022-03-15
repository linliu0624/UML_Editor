package OOP;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Canvas extends JLayeredPane implements MouseListener, MouseMotionListener {
	// private ToolManager toolManager;
	private ArrayList<BasicObject> objectList = new ArrayList<BasicObject>();
	private ArrayList<BasicLine> basicLineList = new ArrayList<BasicLine>();

	private JFrame mainFrame;
	private ToolManager toolManager;
	private int depth = 0;
	private BasicObject startObject = null;
	private BasicObject endObject = null;
	private Integer[] startPart;
	private Integer[] endPart;
	private int selectedObjectIndex = -1;
	private boolean draggAble = false;

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
				System.out.println("Depth: " + objectList.get(getTopObjectIndex(e)).GetDepth());
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
		if (toolManager.getCurrentMode().equals("select")) {

			if (getMouseOnSelectedObjectIndex(e) != -1) {
				draggAble = true;
			} else {
				draggAble = false;
			}
		} else if (toolManager.getCurrentMode().equals("association_line")) {
			startObject = getTopObjectIndex(e) == -1 ? null : objectList.get(getTopObjectIndex(e));
			if (startObject != null)
				startPart = startObject.getClosedPart(e.getX(), e.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (toolManager.getCurrentMode().equals("association_line")) {
			endObject = getTopObjectIndex(e) == -1 ? null : objectList.get(getTopObjectIndex(e));
			if (endObject != null && startObject != null) {
				// draw line
				endPart = endObject.getClosedPart(e.getX(), e.getY());
				basicLineList.add(new BasicLine(toolManager.getCurrentMode(), this, depth, startPart[0], startPart[1],
						endPart[0], endPart[1]));
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
				objectList.get(selectedObjectIndex).updatePositionAndFourPart(e.getX(), e.getY());
				mainFrame.repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	public int getTopObjectIndex(MouseEvent e) {
		int minDepth = 100;
		int index = 0;
		int topObjectIndex = -1;
//		BasicObject topObejct = null;
		for (BasicObject object : objectList) {
			if (object.getPosX() < e.getX() && object.getPosX() + object.getWidth() > e.getX()
					&& object.getPosY() < e.getY() && object.getPosY() + object.getHeight() > e.getY()) {
				if (object.GetDepth() < minDepth) {
					topObjectIndex = index;
//					topObejct = object;
					minDepth = object.GetDepth();
				}
			}
			index++;
		}
		return topObjectIndex;
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

}
