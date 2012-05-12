package net.digiex.minecraft.launcher.forms;

import java.awt.Dialog.ModalityType;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import net.digiex.minecraft.launcher.MCLauncher;

public class JarsTab extends JPanel {
	private static final long serialVersionUID = -630929428997145989L;
	private JButton downloadsButton = new JButton("Download more Jars");

	public JarsTab() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Jars");
		createNodes(top);
		JTree tree = new JTree(top);

		JScrollPane treeView = new JScrollPane(tree);
		JPanel leftPanel = new JPanel(new GridLayout(2, 1));
		leftPanel.add(treeView);
		leftPanel.add(downloadsButton);
		this.add(leftPanel, "West");
		JPanel rightPanel = new JPanel();
		rightPanel
				.add(new JLabel("More features available in the .NET version"));
		this.add(rightPanel, "Center");
		downloadsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DownloadManager dlman = new DownloadManager();
				dlman.setModalityType(ModalityType.APPLICATION_MODAL);
				dlman.setModal(true);
				dlman.setVisible(true);
			}
		});
	}

	private void createNodes(DefaultMutableTreeNode top) {
		for (String jar : MCLauncher.jarList) {
			top.add(new DefaultMutableTreeNode(jar));
		}
	}
}
