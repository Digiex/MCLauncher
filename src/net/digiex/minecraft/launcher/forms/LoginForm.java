package net.digiex.minecraft.launcher.forms;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.digiex.minecraft.launcher.MCLauncher;
import net.digiex.minecraft.launcher.gui.StatusBar;

public class LoginForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2883980066044335538L;
	private JTextField userNameBox = new JTextField();
	private JPasswordField passwordBox = new JPasswordField();
	private JCheckBox rememberBox = new JCheckBox("Remember me");
	private JButton loginButton = new JButton("Login");
	private JLabel label1 = new JLabel("Username or email");
	private JLabel label2 = new JLabel("Password");
	private StatusBar statusBar = new StatusBar();

	public LoginForm() {
		super("Login");

		this.setPreferredSize(new Dimension(259, 143));
		pack();
		setLocationRelativeTo(null);
		try {
			setIconImage(ImageIO.read(MCLauncher.class
					.getResource("favicon.png")));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		this.setLayout(new GridLayout(6, 1));
		this.add(label1);
		this.add(userNameBox);
		this.add(label2);
		this.add(passwordBox);
		JPanel loginandremember = new JPanel(new GridLayout(1, 2));
		loginandremember.add(rememberBox);
		loginandremember.add(loginButton);
		this.add(loginandremember);
		this.add(statusBar);
	}
}
