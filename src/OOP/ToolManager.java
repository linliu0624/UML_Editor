package OOP;

import java.util.ArrayList;
import java.util.Objects;

import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class ToolManager {
	private static final String[] buttonIconFiles = new String[] { "select", "association_line", "generalization_line",
			"composition_line", "class", "use_case" };

	private Canvas canvas;
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private ArrayList<ToolMode> modeList = new ArrayList<ToolMode>();
	private int buttonSize;
	JButton theButton = null;

	// constructor
	public ToolManager(Canvas canvas, int posX, int posY, int buttonSize) {
		this.buttonSize = buttonSize;
		this.canvas = canvas;

		modeList.add(new SelectMode(this.canvas));
		modeList.add(new CreateLineMode(this.canvas));
		modeList.add(new CreateLineMode(this.canvas));
		modeList.add(new CreateLineMode(this.canvas));
		modeList.add(new CreateBasicObjectMode(this.canvas));
		modeList.add(new CreateBasicObjectMode(this.canvas));

		for (int i = 0; i < buttonIconFiles.length; i++) {
			ToolButton button = new ToolButton(buttonIconFiles[i], buttonIcon(buttonIconFiles[i]), modeList.get(i),
					posX, posY + buttonSize * i, buttonSize);
			modeList.get(i).setModeType(buttonIconFiles[i]);
			buttons.add(button);
		}
	}

	protected ArrayList<JButton> getAllButton() {
		return buttons;
	}

	private ImageIcon buttonIcon(String buttonName) {
		ImageIcon imgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("png/" + buttonName + ".png")));
		Image img = imgIcon.getImage();
		img = img.getScaledInstance(buttonSize - 15, buttonSize - 15, Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(img);
		return imgIcon;
	}

	private class ToolButton extends JButton {
		ToolMode mode;

		public ToolButton(String name, ImageIcon icon, ToolMode mode, int posX, int posY, int size) {
			this.mode = mode;
			setName(name);
			setBounds(posX, posY, size, size);
			setIcon(icon);
			setBackground(Color.white);
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					buttonChangeColor(e);
					canvas.setCurrentMode(mode);
				}
			});
		}

		private void buttonChangeColor(ActionEvent e) {
			if (theButton != null) {
				theButton.setBackground(Color.white);
			}
			theButton = (JButton) e.getSource();
			theButton.setBackground(Color.gray);
			System.out.println(mode.getModeType());
		}
	}
	// protected Box getToolBox() {
	// return this.toolBox;
	// }
}
