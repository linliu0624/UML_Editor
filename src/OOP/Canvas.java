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
	private ArrayList<AllShape> objectList = new ArrayList<AllShape>();
//	private ArrayList<BasicObject> basicObjectList = new ArrayList<BasicObject>();
//	private ArrayList<BasicLine> basicLineList = new ArrayList<BasicLine>();
	
	private JFrame mainFrame;
	private ToolManager toolManager;
	private int depth = 0;
	private BasicObject startObject = null;
	private BasicObject endObject = null;

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
		for (AllShape object : objectList) {
			object.drawOnCanvas(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		AllShape basicObject = null;

		if (toolManager.getCurrentMode() == "class" || toolManager.getCurrentMode() == "use_case") {
			basicObject = new BasicObject(toolManager.getCurrentMode(), this, depth, e.getX(), e.getY(), 100, 100);
			depth++;
		}
//		basicObject.drawOnCanvas();
		objectList.add(basicObject);
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		int minDepth = 100;
		if (toolManager.getCurrentMode() == "association_line") {
			for (AllShape object : objectList) {
				// BUG if draw line, the line well be basicObject
				if(!object.getClass().equals("BasicObject")) { continue; }
				else if (((BasicObject) object).getPosX() < e.getX()
						&& ((BasicObject) object).getPosX() + ((BasicObject) object).getWidth() > e.getX()
						&& ((BasicObject) object).getPosY() < e.getY()
						&& ((BasicObject) object).getPosY() + ((BasicObject) object).getHeight() > e.getY()) {
					if (((BasicObject) object).GetDepth() < minDepth) {
						startObject = ((BasicObject) object);
						minDepth = ((BasicObject) object).GetDepth();
					}
				}

			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int minDepth = 100;
		if (toolManager.getCurrentMode() == "association_line") {
			for (AllShape object : objectList) {
				if(!object.getClass().equals("BasicObject")) { continue; }
				else if (((BasicObject) object).getPosX() < e.getX()
						&& ((BasicObject) object).getPosX() + ((BasicObject) object).getWidth() > e.getX()
						&& ((BasicObject) object).getPosY() < e.getY()
						&& ((BasicObject) object).getPosY() + ((BasicObject) object).getHeight() > e.getY()) {
					if (((BasicObject) object).GetDepth() < minDepth) {
						endObject = ((BasicObject) object);
						minDepth = ((BasicObject) object).GetDepth();
					}
				}
			}
		}

		if (endObject != null && startObject != null) {
			// draw line
			objectList.add(new BasicLine(toolManager.getCurrentMode(), this, depth, startObject.getPosX(),
					endObject.getPosX(), endObject.getPosX(), endObject.getPosY()));
			depth++;
			mainFrame.repaint();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
