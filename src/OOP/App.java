package OOP;

import javax.swing.*;

public class App {
	public static JFrame mainFrame = null;
	private static int[] canvasColor = { 206, 232, 255 };
	private static final int SCREEN_WIDTH = 1280;
	private static final int SCREEN_HEIGHT = 720;

	private static void initMainFrame() {
		mainFrame = new JFrame("UML Editor");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setLocationByPlatform(true);
		mainFrame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		mainFrame.setLayout(null);

		Canvas mainCanvas = new Canvas(mainFrame, canvasColor, 80, 20, 1180, 650);
		ToolManager toolManager = new ToolManager(mainCanvas, 0, 20, 75);
		MenuManager menuManager = new MenuManager(mainCanvas);

		for (JButton button : toolManager.getAllButton()) {
			mainFrame.add(button);
		}

		mainFrame.add(mainCanvas);
		mainFrame.add(menuManager);
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		initMainFrame();
	}
}