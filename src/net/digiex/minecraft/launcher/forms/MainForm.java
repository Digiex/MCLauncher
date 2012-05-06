package net.digiex.minecraft.launcher.forms;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import net.digiex.minecraft.launcher.MCLauncher;

public class MainForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -629514425150530096L;
	private String userName;
	private String sessionId;
	private JTabbedPane tabbedPane;

	public MainForm(String userName, String sessionId) {
		super("Digiex.net Minecraft Launcher");
		this.userName = userName;
		this.sessionId = sessionId;
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
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("News", new NewsTab());
		tabbedPane.addTab("Settings", new SettingsTab());
		tabbedPane.addTab("Jars", new JarsTab());
		tabbedPane.addTab("Screenshots", new ScreenshotsTab());
		this.add(tabbedPane);
	}
}
