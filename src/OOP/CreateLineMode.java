package OOP;

import java.awt.event.MouseEvent;

public class CreateLineMode extends ToolMode {

	public CreateLineMode(Canvas canvas) {
		super(canvas);
		// TODO Auto-generated constructor stub
	}

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		
		canvas.setStartObject(canvas.getTopObjectIndex(e) == -1 ? null : canvas.getObjectList().get(canvas.getTopObjectIndex(e)));

		if (canvas.getStartObject() != null)
			canvas.setStartPart(canvas.getStartObject().getClosedPart(e.getX(), e.getY()));


	}

	public void mouseReleased(MouseEvent e) {
		canvas.setEndObject(
				canvas.getTopObjectIndex(e) == -1 ? null : canvas.getObjectList().get(canvas.getTopObjectIndex(e)));
		if (canvas.getEndObject() != null && canvas.getStartObject() != null
				&& canvas.getStartObject() != canvas.getEndObject() && canvas.getStartObject().getIsGroup() == false
				&& canvas.getEndObject().getIsGroup() == false) {
			// draw line
			canvas.setEndPart(canvas.getEndObject().getClosedPart(e.getX(), e.getY()));
			if (getModeType().equals("association_line")) {
				canvas.getBasicLineList().add(new AssociationLine(canvas, canvas.getDepthCounter(), canvas.getStartObject(),
						canvas.getEndObject(), canvas.getStartPart(), canvas.getEndPart()));
			} else if (getModeType().equals("generalization_line")) {
				canvas.getBasicLineList().add(new GeneralizationLine(canvas, canvas.getDepthCounter(), canvas.getStartObject(),
						canvas.getEndObject(), canvas.getStartPart(), canvas.getEndPart()));
			} else if (getModeType().equals("composition_line")) {
				canvas.getBasicLineList().add(new CompositionLine(canvas, canvas.getDepthCounter(),
						canvas.getStartObject(), canvas.getEndObject(), canvas.getStartPart(), canvas.getEndPart()));
			}
			canvas.setDepthCounter();
			canvas.mainFrameRepaint();
		} else {
			System.out.println("release start: " + canvas.getStartObject());
			System.out.println("release end: " + canvas.getEndObject());
		}

	}

}
