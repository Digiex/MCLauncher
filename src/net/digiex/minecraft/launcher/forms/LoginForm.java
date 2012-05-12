package net.digiex.minecraft.launcher.forms;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import net.digiex.minecraft.launcher.HTTPHelper;
import net.digiex.minecraft.launcher.MCLauncher;
import net.digiex.minecraft.launcher.gui.StatusBar;
import net.digiex.minecraft.launcher.loader.Launcher;

public class LoginForm extends JFrame implements ActionListener,
		PropertyChangeListener {
	class Task extends SwingWorker<Void, Void> {
		private LoginForm loginForm;

		public Task(LoginForm loginForm) {
			this.loginForm = loginForm;
		}

		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			error = null;
			// Initialize progress property.
			setProgress(0);
			try {
				HTTPHelper helper = new HTTPHelper();
				helper.setUrl(new URL("https://login.minecraft.net/"));
				helper.addPostString("user", userNameBox.getText());
				helper.addPostString("password",
						new String(passwordBox.getPassword()));
				helper.addPostString("version", "53");
				setProgress(20);
				String response = helper.getResponse();
				setProgress(60);
				if (response != null && response.length() > 0) {
					if (!response.contains(":")) {
						System.out.println("Error: " + response);
						error = response;
					} else {
						String[] split = response.split(":");
						userName = split[2];
						sessionId = split[3];
					}
					setProgress(80);
					LoginForm.this.response = response;
					File workDir = Launcher.getWorkingDirectory();
					// create a file that is really a directory
					File aDirectory = new File(workDir + File.separator + "bin");

					// get a listing of all files in the directory
					String[] filesInDir = aDirectory.list();
					String[] ignore = { "lwjgl.jar", "jinput.jar",
							"lwjgl_util.jar", "lwjgl.jar.lzma",
							"jinput.jar.lzma", "lwjgl_util.jar.lzma", };
					// sort the list of files (optional)
					if (filesInDir != null && filesInDir.length > 2) {
						Arrays.sort(filesInDir);
						// have everything i need, just print it now
						for (int i = 0; i < filesInDir.length; i++) {
							if (filesInDir[i].contains(".jar")
									&& !arraySearch(ignore, filesInDir[i])) {
								MCLauncher.jarList.add(filesInDir[i]);
								System.out.println("Found JAR: "
										+ filesInDir[i]);
							}

						}
					}

					setProgress(100);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			loginButton.setEnabled(true);
			setCursor(null); // turn off the wait cursor
			if (error == null) {
				writeUsername();
				statusBar.setMessage("Done!");
				MCLauncher.mainForm = new MainForm(userName, sessionId,
						response);
				MCLauncher.mainForm.setVisible(true);
				setVisible(false);
				dispose();
			} else {
				statusBar.setMessage("Error: " + error);
				JOptionPane.showMessageDialog(loginForm, error, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private String error = null;
	private String userName;
	private String sessionId;
	private String response;

	/**
	 * 
	 */
	private static final long serialVersionUID = 2883980066044335538L;

	public static boolean arraySearch(String[] haystack, String needle) {
		for (String element : haystack) {
			if (element.equals(needle)) {
				return true;
			}
		}

		return false;
	}

	private JTextField userNameBox = new JTextField();
	private JPasswordField passwordBox = new JPasswordField();
	private JCheckBox rememberBox = new JCheckBox("Remember me");
	private JButton loginButton = new JButton("Login");
	private JLabel label1 = new JLabel("Username or email");
	private JLabel label2 = new JLabel("Password");
	private StatusBar statusBar = new StatusBar();

	private Task loginTask;

	public LoginForm() {
		super("Login");

		this.setPreferredSize(new Dimension(259, 143));
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
		this.setLayout(new GridLayout(6, 1));
		this.add(label1);
		this.add(userNameBox);
		this.add(label2);
		this.add(passwordBox);
		JPanel loginandremember = new JPanel(new GridLayout(1, 2));
		loginandremember.add(rememberBox);
		loginandremember.add(loginButton);
		this.add(loginandremember);
		this.add(statusBar);
		loginButton.addActionListener(this);
		readUsername();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		loginButton.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// Instances of javax.swing.SwingWorker are not reusuable, so
		// we create new instances as needed.
		loginTask = new Task(this);
		loginTask.addPropertyChangeListener(this);
		loginTask.execute();
	}

	private Cipher getCipher(int mode, String password) throws Exception {
		Random random = new Random(43287234L);
		byte[] salt = new byte[8];
		random.nextBytes(salt);
		PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, 5);

		SecretKey pbeKey = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
				.generateSecret(new PBEKeySpec(password.toCharArray()));
		Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
		cipher.init(mode, pbeKey, pbeParamSpec);
		return cipher;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			statusBar.progressBar.setValue(progress);
			statusBar.setMessage(String.format("Completed %d%% of task.",
					loginTask.getProgress()));
		}
	}

	private void readUsername() {
		try {
			File lastLogin = new File(
					net.digiex.minecraft.launcher.loader.Launcher
							.getWorkingDirectory(),
					"lastlogin");

			Cipher cipher = getCipher(2, "passwordfile");
			DataInputStream dis;
			if (cipher != null) {
				dis = new DataInputStream(new CipherInputStream(
						new FileInputStream(lastLogin), cipher));
			} else {
				dis = new DataInputStream(new FileInputStream(lastLogin));
			}
			userNameBox.setText(dis.readUTF());
			passwordBox.setText(dis.readUTF());
			rememberBox.setSelected(passwordBox.getPassword().length > 0);
			dis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeUsername() {
		try {
			File lastLogin = new File(
					net.digiex.minecraft.launcher.loader.Launcher
							.getWorkingDirectory(),
					"lastlogin");

			Cipher cipher = getCipher(1, "passwordfile");
			DataOutputStream dos;
			if (cipher != null) {
				dos = new DataOutputStream(new CipherOutputStream(
						new FileOutputStream(lastLogin), cipher));
			} else {
				dos = new DataOutputStream(new FileOutputStream(lastLogin));
			}
			dos.writeUTF(userNameBox.getText());
			dos.writeUTF(rememberBox.isSelected() ? new String(passwordBox
					.getPassword()) : "");
			dos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
