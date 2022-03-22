package OOP;
import javax.swing.*;

public class App {
    public static JFrame mainFrame = null;
    private static String[] buttonIconFiles = new String[] { "select", "association_line", "generalization_line",
            "composition_line", "class", "use_case" };
    private static int[] canvasColor = { 206, 232, 255 };

    private static void initMainFrame() {
        mainFrame = new JFrame("UML Editor");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationByPlatform(true);
        mainFrame.setSize(1280, 720);
        mainFrame.setLayout(null);

        ToolManager toolManager = new ToolManager(buttonIconFiles, 0, 20, 75);
        Canvas mainCanvas = new Canvas(mainFrame, toolManager, canvasColor, 80, 20, 1180, 650);
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