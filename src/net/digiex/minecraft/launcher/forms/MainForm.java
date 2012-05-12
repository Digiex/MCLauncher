package net.digiex.minecraft.launcher.forms;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import net.digiex.minecraft.launcher.MCLauncher;

public class MainForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -629514425150530096L;
	// private String userName;
	// private String sessionId;
	private String reply;
	private JTabbedPane tabbedPane;
	private JComboBox jarBox;
	private JButton launchButton;
	private Thread minecraftThread;

	private TrayIcon trayIcon;

	public MainForm(String userName, String sessionId, String reply) {
		super("Digiex.net Minecraft Launcher");
		// this.userName = userName;
		// this.sessionId = sessionId;
		this.reply = reply;
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
		JPanel bottomPanel = new JPanel(new GridLayout(1, 4));
		jarBox = new JComboBox();
		launchButton = new JButton("Launch");
		bottomPanel.add(new JLabel("Logged in as " + userName));
		bottomPanel.add(new JLabel("Select Jar:"), "East");
		jarBox.setEditable(true);
		for (String s : MCLauncher.jarList) {
			jarBox.addItem(s);
		}
		jarBox.setSelectedItem("minecraft.jar");
		bottomPanel.add(jarBox);
		bottomPanel.add(launchButton);
		this.add(bottomPanel, "South");

		launchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Button clicked");
				PopupMenu popMenu = new PopupMenu();
				MenuItem item1 = new MenuItem("Exit");
				MenuItem item2 = new MenuItem("Show");
				MenuItem item3 = new MenuItem("Force Close MC");
				popMenu.add(item3);
				popMenu.add(item2);
				popMenu.add(item1);
				ActionListener showActionListener = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Icon doubleclicked");
						MainForm.this.setVisible(true);
						SystemTray.getSystemTray().remove(trayIcon);
					}
				};
				item1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						System.out.println("Clicked exit");
						System.exit(0);
					}
				});
				item2.addActionListener(showActionListener);
				item3.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (minecraftThread != null) {
							if (minecraftThread.isAlive()
									&& !minecraftThread.isInterrupted()) {
								minecraftThread.interrupt();
							}
						}
					}
				});
				try {
					trayIcon = new TrayIcon(ImageIO.read(MCLauncher.class
							.getResource("favicon.png")),
							"Digiex.net Minecraft Launcher", popMenu);
					trayIcon.setImageAutoSize(true);
					trayIcon.addActionListener(showActionListener);
					SystemTray.getSystemTray().add(trayIcon);
					MainForm.this.setVisible(false);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				minecraftThread = new Thread() {
					@Override
					public void run() {
						System.out.println("Thread starting");
						String pathToJar = System.getProperties().getProperty(
								"java.class.path", null);
						if (pathToJar == null) {
							try {
								pathToJar = MainForm.class
										.getProtectionDomain().getCodeSource()
										.getLocation().toURI().getPath();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						System.out.println(pathToJar);
						int result = 0;
						ArrayList<String> pb = new ArrayList<String>();
						pb.add("java");
						pb.add("-Xmx256m");
						pb.add("-Xms64m");
						pb.add("-cp");
						pb.add(pathToJar);
						pb.add("-Dsun.java2d.noddraw=true");
						pb.add("-Dsun.java2d.d3d=false");
						pb.add("-Dsun.java2d.opengl=false");
						pb.add("-Dsun.java2d.pmoffscreen=false");
						pb.add("net.digiex.minecraft.launcher.loader.Launcher");
						pb.add(MainForm.this.reply);
						pb.add((String) MainForm.this.jarBox.getSelectedItem());
						ProcessBuilder localProcessBuilder = new ProcessBuilder(
								pb);
						// localProcessBuilder.redirectErrorStream(true);
						result = -1;
						Process localProcess = null;
						try {
							localProcess = localProcessBuilder.start();
							localProcess.waitFor();
							// OutputStream localOutputStream = localProcess
							// .getOutputStream();
							// pipe(localProcess.getInputStream(), System.out);
						} catch (Exception localException) {
							if (localProcess != null) {
								localProcess.destroy();
							}
							result = 2;
							localException.printStackTrace();
						} finally {
							if ((result != 0) && (localProcess != null)) {
								// localProcess.destroy();
							}
						}

						if (result == 1) {
							System.out
									.println("Failed to launch: end of stream");
						}

						SystemTray.getSystemTray().remove(trayIcon);
						MainForm.this.setVisible(true);
						System.out.println("Thread stopping");
					}
				};
				minecraftThread.start();
			}
		});
	}
}
