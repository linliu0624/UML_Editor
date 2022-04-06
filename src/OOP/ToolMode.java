package OOP;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class ToolMode implements MouseListener, MouseMotionListener {
	protected Canvas canvas;
	protected String modeType;

	public ToolMode(Canvas canvas) {
		this.canvas = canvas;
	}

	public void setModeType(String modeType) {
		this.modeType = modeType;
	}

	public String getModeType() {
		return modeType;
	}

	public void mousePressed(MouseEvent e) {
		canvas.setMouseEnterPos(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {
		canvas.setmouseExitPos(e.getX(), e.getY());
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
