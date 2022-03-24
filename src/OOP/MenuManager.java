package OOP;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class MenuManager extends JMenuBar {
	private Canvas canvas;
	private String[] buttonsName = new String[] { "File", "Edit", "Group", "Ungroup", "Change object name" };
	
	public MenuManager(Canvas canvas) {
		this.canvas = canvas;

		JMenu menu;
		JMenuItem menuItem;


		menu = new JMenu(buttonsName[0]);
		add(menu);


		menu = new JMenu(buttonsName[1]);
		add(menu);
		
		menuItem = new JMenuItem(buttonsName[4]);
		menu.add(menuItem);
		menuItem.addActionListener(new ChangeNameListener());

		menuItem = new JMenuItem(buttonsName[2]);
		menu.add(menuItem);
		menuItem.addActionListener(new GroupObjectListener());

		menuItem = new JMenuItem(buttonsName[3]);
		menu.add(menuItem);
		menuItem.addActionListener(new UngroupObjectListener());

		this.setBounds(0, 0, 1280, 20);
		this.setBackground(Color.gray);
	}

	private void changeNameForm() {
		JFrame inputTextFrame = new JFrame("Object Name");
		inputTextFrame.setSize(400, 100);
		inputTextFrame.getContentPane().setLayout(new GridLayout(0, 1));

		JPanel panel = null;
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JTextField Text = new JTextField("Object Name");
		panel.add(Text);
		inputTextFrame.getContentPane().add(panel);

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JButton confirm = new JButton("OK");
		panel.add(confirm);

		JButton cancel = new JButton("Cancel");
		panel.add(cancel);

		inputTextFrame.getContentPane().add(panel);

		inputTextFrame.setLocationRelativeTo(null);
		inputTextFrame.setVisible(true);

		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				canvas.changeObjName(Text.getText());
				inputTextFrame.dispose();

			}
		});

		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputTextFrame.dispose();
			}
		});

	}

	class UngroupObjectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			canvas.UnGroupObjects();
		}
	}

	class GroupObjectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			canvas.GroupObjects();
		}
	}

	class ChangeNameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			changeNameForm();
		}
	}

}
