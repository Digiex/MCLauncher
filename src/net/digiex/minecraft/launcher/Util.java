package net.digiex.minecraft.launcher;

import java.io.File;

public class Util {
	public enum osType {
		windows, linux, osx, solaris, unknown
	}

	public static File getWorkingDirectory() {
		osType os = osType.linux;
		switch (os) {
		case linux:
			return new File(System.getenv("home"), ".minecraft");
		default:
			throw (new UnsupportedOperationException(
					"Current operating system is not supported."));

		}
	}
}
