package net.digiex.minecraft.launcher.forms;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import net.digiex.minecraft.launcher.MCLauncher;

public class MainForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -629514425150530096L;

	public MainForm(String username, String sessionId) {
		super("Digiex.net Minecraft Launcher");

		this.setPreferredSize(new Dimension(854, 480));
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
	}
}
