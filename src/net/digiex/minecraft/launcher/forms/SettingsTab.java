package net.digiex.minecraft.launcher.forms;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsTab extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2662875068995581794L;

	public SettingsTab() {
		this.setLayout(new GridLayout(2, 2));
		this.add(new JLabel("Memory for Minecraft: "));
		this.add(new JTextField("256M"));
		this.add(new JLabel("Java installation path: "));
		this.add(new JTextField("/usr/bin/java"));
	}
}
