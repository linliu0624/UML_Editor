package OOP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class MenuManager {
	private String[] buttonsName = new String[] { "File", "Groupe", "Ungroupe" };
	private ArrayList<JButton> buttons = new ArrayList<JButton>();
	private int buttonWidth = 100;
	private int buttonheight = 20;

	public MenuManager() {
		for (int i = 0; i < buttonsName.length; i++) {

			JButton button = new JButton(buttonsName[i]);
			button.setName(buttonsName[i]);
			button.setBounds(i * buttonWidth, 0, buttonWidth, buttonheight);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
//					onClick(e);
				}

			});
			buttons.add(button);
			// toolBox.add(button);
			// toolBox.add(Box.createVerticalStrut(5));
		}
	}
	
    protected ArrayList<JButton> getAllButton() {
        return buttons;
    }
}
