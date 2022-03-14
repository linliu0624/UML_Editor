import java.util.ArrayList;
import java.util.Objects;

import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;

public class ToolManager {
    // private Box toolBox;
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private String currentMode = "";
    private int buttonSize;

    // constructor
    public ToolManager(String[] buttonsName, int posX, int posY, int buttonSize) {
        // this.toolBox = Box.createVerticalBox();
        // toolBox.setBounds(posX, posY, width, height);
        this.buttonSize = buttonSize;
        for (int i = 0; i < buttonsName.length; i++) {

            JButton button = new JButton(buttonIcon(buttonsName[i]));
            button.setName(buttonsName[i]);
            button.setBounds(posX, posY + buttonSize * i, buttonSize, buttonSize);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    onClick(e);
                }

            });
            buttons.add(button);
            // toolBox.add(button);
            // toolBox.add(Box.createVerticalStrut(5));
        }
    }

    public String getCurrentMode() {
        return currentMode;
    }

    // public void setCurrentMode(String currentMode) {
    // this.currentMode = currentMode;
    // }

    private ImageIcon buttonIcon(String buttonName) {
        ImageIcon imgIcon = new ImageIcon(
                Objects.requireNonNull(getClass().getResource("icon/png/" + buttonName + ".png")));
        Image img = imgIcon.getImage();
        img = img.getScaledInstance(buttonSize - 15, buttonSize - 15, Image.SCALE_SMOOTH);
        imgIcon = new ImageIcon(img);
        return imgIcon;
    }

    private void onClick(ActionEvent e) {
        JButton theButton = (JButton) e.getSource();
        String lastMode = "";
        if (getCurrentMode().equals("")) {
            // setCurrentMode(theButton.getName());
            currentMode = theButton.getName();
            theButton.setBackground(Color.gray);
            lastMode = theButton.getName();
        } else if (getCurrentMode().equals(theButton.getName())) {
            // setCurrentMode("");
            currentMode = "";
            theButton.setBackground(null);
        } else {
            for (JButton button : buttons) {
                if (!button.getName().equals(lastMode)) {
                    button.setBackground(null);
                }
            }
            // setCurrentMode(theButton.getName());
            currentMode = theButton.getName();
            theButton.setBackground(Color.gray);
        }
        System.out.println(getCurrentMode());
    }

    protected ArrayList<JButton> getAllButton() {
        return buttons;
    }
    // protected Box getToolBox() {
    // return this.toolBox;
    // }
}
