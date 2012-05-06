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
import java.net.URL;

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
				String response = helper.getResponse();
				if (response != null && response.length() > 0) {
					if (!response.contains(":")) {
						System.out.println("Error: " + response);
						error = response;
					} else {
						String[] split = response.split(":");
						userName = split[2];
						sessionId = split[3];
					}
					System.out.println("Got " + response);
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
				statusBar.setMessage("Done!");
				MCLauncher.mainForm = new MainForm(userName, sessionId);
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 2883980066044335538L;
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
	}

	public void actionPerformed(ActionEvent evt) {
		loginButton.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// Instances of javax.swing.SwingWorker are not reusuable, so
		// we create new instances as needed.
		loginTask = new Task(this);
		loginTask.addPropertyChangeListener(this);
		loginTask.execute();
	}

	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			statusBar.progressBar.setValue(progress);
			statusBar.setMessage(String.format("Completed %d%% of task.",
					loginTask.getProgress()));
		}
	}
}
