package net.digiex.minecraft.launcher;

import java.util.ArrayList;

import javax.swing.UIManager;

import net.digiex.minecraft.launcher.forms.LoginForm;
import net.digiex.minecraft.launcher.forms.MainForm;

public class MCLauncher {
	public static LoginForm loginForm;

	public static MainForm mainForm;
	public static String version = "2.0.0";
	public static ArrayList<String> jarList = new ArrayList<String>();

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
