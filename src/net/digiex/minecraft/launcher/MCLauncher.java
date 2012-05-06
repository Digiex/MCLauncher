package net.digiex.minecraft.launcher;

import javax.swing.UIManager;

import net.digiex.minecraft.launcher.forms.LoginForm;
import net.digiex.minecraft.launcher.forms.MainForm;

public class MCLauncher {
	public static LoginForm loginForm;

	public static MainForm mainForm;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		loginForm = new LoginForm();
		loginForm.setVisible(true);
	}

}
