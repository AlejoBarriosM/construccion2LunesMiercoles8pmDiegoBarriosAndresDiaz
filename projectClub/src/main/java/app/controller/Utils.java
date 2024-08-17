package app.controller;

import javax.swing.*;

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

	// Método para capturar un número entero
	public static int promptIntInput(String message) {
		while (true) {
			try {
				String input = promptInput(message);
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				showError("Entrada no válida. Por favor, ingresa un número entero.");
			}
		}
	}

	// Método para capturar un número decimal (double)
	public static double promptDoubleInput(String message) {
		while (true) {
			try {
				String input = promptInput(message);
				return Double.parseDouble(input);
			} catch (NumberFormatException e) {
				showError("Entrada no válida. Por favor, ingresa un número decimal.");
			}
		}
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
			JOptionPane.QUESTION_MESSAGE,
			null,
			options,
			options[0] // Opción predeterminada
		);
	}



}
