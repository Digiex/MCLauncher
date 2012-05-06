package net.minecraft;

/* This will prevent breaking user's launch command line when moving from the official launcher to this launcher */
public class LauncherFrame {
	public static void main(String[] args) {
		net.digiex.minecraft.launcher.MCLauncher.main(args);
	}
}
