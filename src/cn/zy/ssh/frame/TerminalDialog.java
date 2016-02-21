package cn.zy.ssh.frame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


import ch.ethz.ssh2.Session;


public class TerminalDialog extends JDialog{

	private static final long serialVersionUID = 1L;

//	JPanel botPanel;
//	JButton logoffButton;
	JTextArea terminalArea;

	Session sess;
	InputStream in;
	OutputStream out;

	int x, y;

	/**
	 * This thread consumes output from the remote server and displays it in
	 * the terminal window.
	 *
	 */
	class RemoteConsumer extends Thread
	{
//		char[][] lines = new char[y][];
		byte[][] lines = new byte[y][];
		int posy = 0;
		int posx = 0;

		private void addText(byte[] data, int len)
		{
			for (int i = 0; i < len; i++)
			{
				char c = (char) (data[i] & 0xff);


				if (c == 8) // Backspace, VERASE
				{
					if (posx < 0)
						continue;
					posx--;
					continue;
				}

				if (c == '\r')
				{
					posx = 0;
					continue;
				}

				if (c == '\n')
				{
					posy++;
					if (posy >= y)
					{
						for (int k = 1; k < y; k++)
							lines[k - 1] = lines[k];
						posy--;
						lines[y - 1] = new byte[x];
						for (int k = 0; k < x; k++)
							lines[y - 1][k] = ' ';
					}
					continue;
				}

				if (c < 32)
				{
					continue;
				}

				if (posx >= x)
				{
					posx = 0;
					posy++;
					if (posy >= y)
					{
						posy--;
						for (int k = 1; k < y; k++)
							lines[k - 1] = lines[k];
						lines[y - 1] = new byte[x];
						for (int k = 0; k < x; k++)
							lines[y - 1][k] = ' ';
					}
				}

				if (lines[posy] == null)
				{
					lines[posy] = new byte[x];
					for (int k = 0; k < x; k++)
						lines[posy][k] = ' ';
				}

				lines[posy][posx] = (byte)c;
				posx++;
			}

			StringBuffer sb = new StringBuffer(x * y);

			for (int i = 0; i < lines.length; i++)
			{
				if (i != 0)
					sb.append('\n');

				if (lines[i] != null)
				{
					//
					String temp = new String(lines[i]);
					
					//
					sb.append(temp);
				}

			}
			setContent(sb.toString());
			
			
			
			
		}

		public void run()
		{
			byte[] buff = new byte[8192];

			try
			{
				while (true)
				{
					int len = in.read(buff);
					// change the unicode
//					String temp = new String(buff, "UTF-8");
//					buff = temp.getBytes();
					//
					if (len == -1)
						return;
					addText(buff, len);
				}
			}
			catch (Exception e)
			{
			}
		}
	}

	public TerminalDialog(JFrame parent, String title, Session sess, int x, int y) throws IOException
	{
		super(parent, title, true);

		this.sess = sess;

		in = sess.getStdout();
		out = sess.getStdin();

		this.x = x;
		this.y = y;

//		botPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

//		logoffButton = new JButton("Logout");
//		botPanel.add(logoffButton);

//		logoffButton.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				/* Dispose the dialog, "setVisible(true)" method will return */
//				dispose();
//			}
//		});

		Font f = new Font("Monospaced", Font.PLAIN, 16);
		
		terminalArea = new JTextArea(y, x);
		terminalArea.setFont(f);
		terminalArea.setBackground(Color.BLACK);
		terminalArea.setForeground(Color.WHITE);
		/* This is a hack. We cannot disable the caret,
		 * since setting editable to false also changes
		 * the meaning of the TAB key - and I want to use it in bash.
		 * Again - this is a simple DEMO terminal =)
		 */
		terminalArea.setCaretColor(Color.BLACK);

		KeyAdapter kl = new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				int c = e.getKeyChar();

				try
				{
					out.write(c);
				}
				catch (IOException e1)
				{
				}
				e.consume();
			}
		};

		terminalArea.addKeyListener(kl);

		getContentPane().add(terminalArea, BorderLayout.CENTER);
//		getContentPane().add(botPanel, BorderLayout.PAGE_END);

		setResizable(false);
		pack();
		setLocationRelativeTo(parent);

		
		new RemoteConsumer().start();
		
	
		
	}

	public void setContent(String lines)
	{
		// setText is thread safe, it does not have to be called from
		// the Swing GUI thread.
		terminalArea.setText(lines);
	}


}
