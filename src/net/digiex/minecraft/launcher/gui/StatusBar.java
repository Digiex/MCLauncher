package net.digiex.minecraft.launcher.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class StatusBar extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1496474368041059401L;
	public JProgressBar progressBar = new JProgressBar();
	private JLabel label = new JLabel();

	/** Creates a new instance of StatusBar */
	public StatusBar() {
		super();
		super.setPreferredSize(new Dimension(100, 16));
		this.setLayout(new GridLayout(1, 2));
		this.add(label);
		this.add(progressBar);
		setMessage("Ready");
	}

	public void setMessage(String message) {
		label.setText(" " + message);
	}
}