package net.server.debug;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * A document listener that places limitations on the number of lines.
 * @author Public Domain
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class LimitLinesDocumentListener implements DocumentListener {
	private int maximumLines;
	private boolean isRemoveFromStart;

	/**
	 * Creates a document listener that removes excess lines from the start of the document.
	 * @param maximumLines the maximum number of lines for the document
	 */
	public LimitLinesDocumentListener(int maximumLines) {
		this(maximumLines, true);
	}

	/**
	 * Creates a document listener that removes excess lines from a document.
	 * @param maximumLines the maximum number of lines for the document
	 * @param isRemoveFromStart whether or not to remove the lines from the start of the document
	 */
	public LimitLinesDocumentListener(int maximumLines, boolean isRemoveFromStart) {
		setLimitLines(maximumLines);
		this.isRemoveFromStart = isRemoveFromStart;
	}

	/**
	 * Gets the maximum number of lines for the document.
	 * @return  the maximum number of lines for the document
	 */
	public int getLimitLines() {
		return maximumLines;
	}

	/**
	 * Sets the maximum number of lines for the document
	 * @param maximumLines the maximum number of lines for the document
	 */
	public void setLimitLines(int maximumLines) {
		if (maximumLines < 1) {
			String message = "Maximum lines must be greater than 0";
			throw new IllegalArgumentException(message);
		}

		this.maximumLines = maximumLines;
	}
	
	@Override
	public void insertUpdate(final DocumentEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				removeLines(e);
			}
		});
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	/**
	 * Removes lines from the document.
	 * @param e an event from the document to work on
	 */
	private void removeLines(DocumentEvent e) {
		Document document = e.getDocument();
		Element root = document.getDefaultRootElement();
		while (root.getElementCount() > maximumLines) {
			if (isRemoveFromStart) {
				removeFromStart(document, root);
			} else {
				removeFromEnd(document, root);
			}
		}
	}

	/**
	 * Removes lines from the start of the document
	 * @param document the document to work on
	 * @param root the root element of the document
	 */
	private void removeFromStart(Document document, Element root) {
		Element line = root.getElement(0);
		int end = line.getEndOffset();

		try {
			document.remove(0, end);
		} catch (BadLocationException ble) {
			System.out.println(ble);
		}
	}

	/**
	 * Removes lines from the end of the document
	 * @param document the document to work on
	 * @param root the root element of the document
	 */
	private void removeFromEnd(Document document, Element root) {
		Element line = root.getElement(root.getElementCount() - 1);
		int start = line.getStartOffset();
		int end = line.getEndOffset();
		try {
			document.remove(start - 1, end - start);
		} catch (BadLocationException ble) {
			System.out.println(ble);
		}
	}
}
