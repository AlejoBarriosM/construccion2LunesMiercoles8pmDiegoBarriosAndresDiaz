package app.controller;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Utils {

	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Información", JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static String promptInput(String message) {
		return JOptionPane.showInputDialog(null, message, "Entrada", JOptionPane.QUESTION_MESSAGE);
	}

	public static boolean showYesNoDialog(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION;
	}

	public static boolean showConfirmDialog(JPanel panel, String title) {

		int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			return true;
		} else if (result == JOptionPane.CANCEL_OPTION) {
			return false;
		} else if (result == JOptionPane.CLOSED_OPTION) {
			return false;
		}
        return false;
    }

	public static int showMenu(String title, String message, String[] options) {
		return JOptionPane.showOptionDialog(
			null,
			message,
			title,
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			options,
			options[0]
		);
	}

	public static Map<String, JTextField> createPanelWithFields(String[] labels, JPanel panel) {
		Map<String, JTextField> fieldsMap = new HashMap<>();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		for (String label : labels) {
			JTextField textField = new JTextField(30);
			fieldsMap.put(label, textField);
			panel.add(new JLabel(label + ":"));
			panel.add(textField);
			panel.add(Box.createVerticalStrut(15));
		}

		return fieldsMap;
	}

	public static void createPanelWithScroll(Map<Long, String> fields, String title) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		for (Map.Entry<Long, String> entry : fields.entrySet()) {
			JTextArea textArea = new JTextArea(entry.getValue());
			textArea.setEditable(false);
			textArea.setLineWrap(true);
			textArea.setWrapStyleWord(true);
			panel.add(textArea);
			panel.add(Box.createVerticalStrut(15));
		}

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setPreferredSize(new Dimension(600, 300));

		JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.PLAIN_MESSAGE);
	}


}
