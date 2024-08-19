package app.controller;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public abstract class Utils {

	//Método para mostrar un mensaje de información
	public static void showMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Información", JOptionPane.INFORMATION_MESSAGE);
	}

	// Método para mostrar un mensaje de error
	public static void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	// Método para capturar una entrada de texto
	public static String promptInput(String message) {
		return JOptionPane.showInputDialog(null, message, "Entrada", JOptionPane.QUESTION_MESSAGE);
	}

	// Método para capturar Sí/No
	public static int showYesNoDialog(String message) {
		return JOptionPane.showConfirmDialog(null, message, "Confirmar", JOptionPane.YES_NO_OPTION);
	}

	// Método para capturar una confirmación con panel
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

	// Método para mostrar un menú con opciones
	public static int showMenu(String title, String message, String[] options) {
		return JOptionPane.showOptionDialog(
			null,
			message,
			title,
			JOptionPane.DEFAULT_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			options,
			options[0] // Opción predeterminada
		);
	}


	public static Map<String, Object> addFieldsToPanel(String[] labels){
		Map<String, JTextField> fieldsMap = new HashMap<>();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		for (String label : labels) {
			JTextField textField = new JTextField(30);
			fieldsMap.put(label, textField);
			panel.add(new JLabel(label + ":"));
			panel.add(textField);
			panel.add(Box.createVerticalStrut(15));
		}

		Map<String, Object> result = new HashMap<>();
		result.put("panel", panel);
		result.put("fields", fieldsMap);

		return result;
	}

}
