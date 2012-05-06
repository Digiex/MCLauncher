package net.digiex.minecraft.launcher;

import javax.swing.UIManager;

import net.digiex.minecraft.launcher.forms.LoginForm;

public class MCLauncher {

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
		(new LoginForm()).setVisible(true);
	}

}
