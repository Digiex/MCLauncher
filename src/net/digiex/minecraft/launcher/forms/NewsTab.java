package net.digiex.minecraft.launcher.forms;

import java.awt.Color;
import java.net.URL;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.MatteBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import net.digiex.minecraft.launcher.MCLauncher;

public class NewsTab extends JScrollPane {

	private static final long serialVersionUID = -452439171584850598L;

	public NewsTab() {
		try {
			final JTextPane editorPane = new JTextPane() {
				private static final long serialVersionUID = 1L;
			};
			editorPane.setContentType("text/html");
			editorPane
					.setText("<html><body><font color=\"#808080\"><br><br><br><br><br><br><br><center>Loading digiex.net info page..</center></font></body></html>");
			editorPane.addHyperlinkListener(new HyperlinkListener() {
				@Override
				public void hyperlinkUpdate(HyperlinkEvent he) {
					if (he.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
						// Util.openLink(u.toURI());
					}
				}
			});
			new Thread() {
				@Override
				public void run() {
					try {
						editorPane.setPage(new URL(
								"http://minecraft.digiex.org/intool.php?java=true&version="
										+ MCLauncher.version));
					} catch (Exception e) {
						e.printStackTrace();
						editorPane
								.setText("<html><body><font color=\"#808080\"><br><br><br><br><br><br><br><center>Failed to get digiex.net info page<br>"
										+ e.toString()
										+ "</center></font></body></html>");
					}
				}
			}.start();
			editorPane.setBackground(Color.DARK_GRAY);
			editorPane.setEditable(false);
			this.setViewportView(editorPane);
			this.setBorder(null);
			editorPane.setMargin(null);

			this.setBorder(new MatteBorder(0, 0, 2, 0, Color.BLACK));
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
