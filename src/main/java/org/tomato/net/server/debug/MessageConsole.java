package org.tomato.net.server.debug;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * A handler for consoles displayed in a GUI.
 * @author Public Domain
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class MessageConsole {
	private JTextComponent textComponent;
	private Document document;
	private boolean isAppend;
	private DocumentListener limitLinesListener;

	/**
	 * Creates a message console with a text component, that is appending.
	 * @param textComponent the text component to use
	 */
	public MessageConsole(JTextComponent textComponent) {
		this(textComponent, true);
	}

	/**
	 * Creates a message console with a text component.
	 * @param textComponent the text component to use
	 * @param isAppend whether or not the console should append
	 */
	public MessageConsole(JTextComponent textComponent, boolean isAppend) {
		this.textComponent = textComponent;
		this.document = textComponent.getDocument();
		this.isAppend = isAppend;
		textComponent.setEditable(false);
	}

	/**
	 * Redirects <code>System.out</code> to the console.
	 */
	public void redirectOut() {
		redirectOut(null, null);
	}

	/**
	 * Redirects <code>System.out</code> to both the console and a print stream.
	 * @param textColor the color to redirect the text as
	 * @param printStream the print stream to redirect it to
	 */
	public void redirectOut(Color textColor, PrintStream printStream) {
		ConsoleOutputStream cos = new ConsoleOutputStream(textColor, printStream);
		System.setOut(new PrintStream(cos, true));
	}

	/**
	 * Redirects <code>System.err</code> to the console.
	 */
	public void redirectErr() {
		redirectErr(null, null);
	}
	
	/**
	 * Redirects <code>System.err</code> to both the console and a print stream.
	 * @param textColor the color to redirect the text as
	 * @param printStream the print stream to redirect it to
	 */
	public void redirectErr(Color textColor, PrintStream printStream) {
		ConsoleOutputStream cos = new ConsoleOutputStream(textColor, printStream);
		System.setErr(new PrintStream(cos, true));
	}

	/**
	 * Sets the number of lines for the console
	 * @param lines the number of lines for the console
	 */
	public void setMessageLines(int lines) {
		if (limitLinesListener != null)
			document.removeDocumentListener(limitLinesListener);
		limitLinesListener = new LimitLinesDocumentListener(lines, isAppend);
		document.addDocumentListener(limitLinesListener);
	}

	/**
	 *  An output stream for the console.
	 * @author tomato
	 * @version 1.0
	 * @since alpha
	 */
	private class ConsoleOutputStream extends ByteArrayOutputStream {
		private SimpleAttributeSet attributes;
		private PrintStream printStream;
		private StringBuffer buffer = new StringBuffer(80);
		private boolean isFirstLine;

		/**
		 * Creates an output stream for consoles.
		 * @param textColor the text color to use
		 * @param printStream the print stream to redirect to
		 */
		public ConsoleOutputStream(Color textColor, PrintStream printStream) {
			if (textColor != null) {
				attributes = new SimpleAttributeSet();
				StyleConstants.setForeground(attributes, textColor);
			}
			this.printStream = printStream;
			if (isAppend)
				isFirstLine = true;
		}

		/**
		 * Flushes the stream.
		 */
		public void flush() {
			String message = toString();

			if (message.length() == 0)
				return;

			if (isAppend)
				handleAppend(message);
			else
				handleInsert(message);

			reset();
		}

		/**
		 * Appends a message to the stream.
		 * @param message the message to append
		 */
		private void handleAppend(String message) {
			if (message.endsWith("\r") || message.endsWith("\n")) {
				buffer.append(message);
			} else {
				buffer.append(message);
				clearBuffer();
			}
		}

		/**
		 * Inserts a message into the stream.
		 * @param message the message to insert
		 */
		private void handleInsert(String message) {
			buffer.append(message);

			if (message.endsWith("\r") || message.endsWith("\n")) {
				clearBuffer();
			}
		}

		/**
		 * Clears the stream completely.
		 */
		private void clearBuffer() {
			if (isFirstLine && document.getLength() != 0) {
				buffer.insert(0, "\n");
			}
			isFirstLine = false;
			String line = buffer.toString();
			try {
				if (isAppend) {
					int offset = document.getLength();
					document.insertString(offset, line, attributes);
					textComponent.setCaretPosition(document.getLength());
				} else {
					document.insertString(0, line, attributes);
					textComponent.setCaretPosition(0);
				}
			} catch (BadLocationException ble) {
			}
			if (printStream != null) {
				printStream.print(line);
			}
			buffer.setLength(0);
		}
	}
}
