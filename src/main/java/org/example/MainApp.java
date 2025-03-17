package org.example;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        //codigo para cambiar el icono de la pokedex
        InputStream iconStream = getClass().getResourceAsStream("/PokeBall_37565.png");
        primaryStage.getIcons().add(new Image(iconStream));
        //anchoLargo fijo
        primaryStage.setMaxWidth(880);
        primaryStage.setMaxHeight(620);
        primaryStage.setMinWidth(880);
        primaryStage.setMinHeight(620);


        //
        this.primaryStage.setTitle("Pokédex");
        initRootLayout();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/poke.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
