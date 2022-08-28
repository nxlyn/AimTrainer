package projects.mora.j0927.aimtrainer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("aim-menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setTitle("Jose's Aim Trainer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream( "windowIcon.png" )));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}