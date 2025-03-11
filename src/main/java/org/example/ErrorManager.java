// ErrorManager.java

package org.example;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorManager {

    public static void showErrorDialog(String errorMessage, Exception e, String imagePath, boolean isUrl) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Ha ocurrido un error");

        // Añadir la imagen personalizada
        ImageView imageView = null;
        try {
            if (isUrl) {
                imageView = new ImageView(new Image(imagePath));
            } else {
                imageView = new ImageView(new Image(ErrorManager.class.getResourceAsStream(imagePath)));
            }
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
        } catch (Exception ex) {
            System.out.println("No se pudo cargar la imagen: " + ex.getMessage());
        }

        DialogPane dialogPane = alert.getDialogPane();
        if (imageView != null) {
            dialogPane.setGraphic(imageView);
        }

        // Añadir el mensaje de error
        Text errorText = new Text(errorMessage);
        VBox content = new VBox(errorText);
        dialogPane.setContent(content);

        // Añadir los detalles de la excepción
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Text exceptionTextArea = new Text(exceptionText);
        //content.getChildren().add(exceptionTextArea);

        alert.showAndWait();
    }

    // Método para manejar cualquier tipo de excepción
    public static void handleException(Exception e, String imagePath, boolean isUrl) {
        showErrorDialog("Ha ocurrido un error: " + e.getMessage(), e, imagePath, isUrl);
    }
}
