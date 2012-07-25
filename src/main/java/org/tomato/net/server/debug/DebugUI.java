package org.tomato.net.server.debug;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tomato.net.server.core.ByteArrayMaplePacket;
import org.tomato.net.server.login.LoginServer;
import org.tomato.net.server.world.WorldServer;
import org.tomato.tools.HexTool;
import org.tomato.tools.net.LoginPacketCreator;
import org.tomato.client.MapleAccount;

/**
 * A Swing GUI for developing and debugging servers.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class DebugUI extends JFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(DebugUI.class);
    
	private static final long serialVersionUID = -8144315523215216792L;
	private Integer loginBound, worldBound;
	private LoginServer login;
	private WorldServer world;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DebugUI frame = new DebugUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DebugUI() {
		login = new LoginServer();
		login.setPipelineFactory(new DebuggingLoginServerPipelineFactory());
		world = new WorldServer();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(textPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
						.addComponent(tabbedPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPane, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		Box horizontalBox = Box.createHorizontalBox();
		tabbedPane.addTab("Login", null, horizontalBox, null);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut);
		
		JLabel lblLoginServer = new JLabel("Login Server:");
		horizontalBox.add(lblLoginServer);

		final JButton btnBind = new JButton("Bind");
		
		textField = new JTextField();
		textField.setText("8484");
		horizontalBox.add(textField);
		textField.setColumns(10);
		
		btnBind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (loginBound != null && loginBound.equals(Integer.parseInt(textField.getText()))) {
					if (worldBound != null) {
						try {
							login.disconnect("127.0.0.1", worldBound);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						LOGGER.warn("Disconnected from World Server");
					}
					login.unbind();
					loginBound = null;
					btnBind.setText("Bind");
					textField.setEditable(true);
					LOGGER.warn("Unbound server on {}", textField.getText());
					
				} else {
					login.bind(Integer.parseInt(textField.getText()));
					loginBound = Integer.parseInt(textField.getText());
					btnBind.setText("Unbind");
					textField.setEditable(false);
					LOGGER.info("Bound on {}", textField.getText());
					if (worldBound != null) {
						try {
							login.connect("127.0.0.1", worldBound);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						LOGGER.info("Connected to World Server.");
					}
				}
			}
		});
		horizontalBox.add(btnBind);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_1);
		
		Box horizontalBox_3 = Box.createHorizontalBox();
		tabbedPane.addTab("World", null, horizontalBox_3, null);
		
		Component horizontalStrut_6 = Box.createHorizontalStrut(20);
		horizontalBox_3.add(horizontalStrut_6);
		
		JLabel lblWorldServer = new JLabel("World Server:");
		horizontalBox_3.add(lblWorldServer);

		final JButton btnBind_1 = new JButton("Bind");
		
		textField_2 = new JTextField();
		textField_2.setText("8383");
		horizontalBox_3.add(textField_2);
		textField_2.setColumns(10);
		
		btnBind_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (worldBound != null && worldBound.equals(Integer.parseInt(textField_2.getText()))) {
					world.unbind();
					worldBound = null;
					btnBind_1.setText("Bind");
					textField_2.setEditable(true);
					LOGGER.warn("[World] Unbound server on " + textField_2.getText() + ".");
				} else {
					textField_2.setEditable(false);
					world.bind(Integer.parseInt(textField_2.getText()));
					worldBound = Integer.parseInt(textField_2.getText());
					btnBind_1.setText("Unbind");
					LOGGER.info("[World] Bound server on " + textField_2.getText() + ".");
				}
			}
		});
		horizontalBox_3.add(btnBind_1);
		
		Component horizontalStrut_7 = Box.createHorizontalStrut(20);
		horizontalBox_3.add(horizontalStrut_7);
		
		Box horizontalBox_1 = Box.createHorizontalBox();
		tabbedPane.addTab("Packets", null, horizontalBox_1, null);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_2);
		
		textField_1 = new JTextField();
		horizontalBox_1.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((DebuggingLoginServerPipelineFactory) login.getPipelineFactory()).debugWriteAll(new ByteArrayMaplePacket(HexTool.getByteArrayFromHexString(textField_1.getText())));
				LOGGER.info("[Send] " + textField_1.getText());
			}
		});
		horizontalBox_1.add(btnSend);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_3);
		
		Box horizontalBox_2 = Box.createHorizontalBox();
		tabbedPane.addTab("Premade", null, horizontalBox_2, null);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_4);
		
		JButton btnRice = new JButton("Rice");
		btnRice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChannelBuffer buffer = LoginPacketCreator.getLoginSuccess(MapleAccount.rice());
				((DebuggingLoginServerPipelineFactory) login.getPipelineFactory()).debugWriteAll(buffer);
				LOGGER.info("[Sent] " + ChannelBuffers.hexDump(buffer));
			}
		});
		horizontalBox_2.add(btnRice);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(20);
		horizontalBox_2.add(horizontalStrut_5);
		contentPane.setLayout(gl_contentPane);
		
		MessageConsole mc = new MessageConsole(textPane);
		mc.setMessageLines(11);
		mc.redirectOut(null, System.out);
		mc.redirectErr(Color.RED, System.err);
		
		LOGGER.info("[Debug] Debugger attached.");
	}
}
