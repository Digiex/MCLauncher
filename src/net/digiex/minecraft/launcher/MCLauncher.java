package net.digiex.minecraft.launcher;

import javax.swing.UIManager;

import net.digiex.minecraft.launcher.forms.LoginForm;

public class MCLauncher {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1571182324796731546L;

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
