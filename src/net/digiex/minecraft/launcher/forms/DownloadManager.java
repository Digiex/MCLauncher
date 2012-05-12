package net.digiex.minecraft.launcher.forms;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.JDialog;

import net.digiex.minecraft.launcher.MCLauncher;

public class DownloadManager extends JDialog {
	private static final long serialVersionUID = -7531939160421140121L;

	public DownloadManager() {
		this.setTitle("Jar Downloader");
		this.setSize(477, 409);
		try {
			setIconImage(ImageIO.read(MCLauncher.class
					.getResource("favicon.png")));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
			}
		});
	}
}
