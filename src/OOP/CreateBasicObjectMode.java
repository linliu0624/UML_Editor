package OOP;

import java.awt.event.MouseEvent;

public class CreateBasicObjectMode extends ToolMode {

	public CreateBasicObjectMode(Canvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		
		BasicObject basicObject = new BasicObject(getModeType(), canvas, canvas.getDepthCounter(), e.getX(),
				e.getY(), 100, 100);
		canvas.getObjectList().add(basicObject);
		System.out.println("SIZE:"+ canvas.getObjectList().size());
		canvas.setDepthCounter();
		canvas.mainFrameRepaint();
	}
}
