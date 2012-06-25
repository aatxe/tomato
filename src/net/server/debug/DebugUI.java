package net.server.debug;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
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
import net.server.core.ByteArrayMaplePacket;
import net.server.exec.ServerBootstrapper;
import tools.ConsoleOutput;
import tools.HexTool;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class DebugUI extends JFrame {

	private static final long serialVersionUID = -8144315523215216792L;
	private ArrayList<Integer> bound = new ArrayList<Integer>();
	private ServerBootstrapper exec;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

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
		exec = new ServerBootstrapper();
		exec.setPipelineFactory(new DebuggingServerPipelineFactory());
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
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bound.contains(Integer.parseInt(textField.getText()))) {
					btnBind.setText("Unbind");
				} else {
					btnBind.setText("Bind");
				}
			}
		});
		textField.setText("8484");
		horizontalBox.add(textField);
		textField.setColumns(10);
		
		btnBind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bound.contains(Integer.parseInt(textField.getText()))) {
					exec.unbind(Integer.parseInt(textField.getText()));
					bound.remove(Integer.parseInt(textField.getText()));
					btnBind.setText("Bind");
					ConsoleOutput.print("[Login] Unbound server on " + textField.getText());
				} else {
					exec.bind(Integer.parseInt(textField.getText()));
					bound.add(Integer.parseInt(textField.getText()));
					btnBind.setText("Unbind");
					ConsoleOutput.print("[Login] Bound on " + textField.getText());
				}
			}
		});
		horizontalBox.add(btnBind);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		horizontalBox.add(horizontalStrut_1);
		
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
				((DebuggingServerPipelineFactory) exec.getPipelineFactory()).debugWriteAll(new ByteArrayMaplePacket(HexTool.getByteArrayFromHexString(textField_1.getText())));
				ConsoleOutput.print("[Send] " + textField_1.getText());
			}
		});
		horizontalBox_1.add(btnSend);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(20);
		horizontalBox_1.add(horizontalStrut_3);
		contentPane.setLayout(gl_contentPane);
		
		MessageConsole mc = new MessageConsole(textPane);
		mc.setMessageLines(11);
		mc.redirectOut(null, System.out);
		mc.redirectErr(Color.RED, System.err);
		
		ConsoleOutput.print("[Debug] Debugger attached.");
	}
}
