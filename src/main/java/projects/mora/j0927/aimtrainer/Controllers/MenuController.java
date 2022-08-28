package projects.mora.j0927.aimtrainer.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import projects.mora.j0927.aimtrainer.Main;

import java.io.IOException;

public class MenuController {
    @FXML
    private Button standardButton, statisticsButton, trackingButton, precisionButton;
    //handleStandard will load aim-scene.fxml on button click
    @FXML
    public void loadStandard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("aim-scene.fxml"));
        Stage window = (Stage) standardButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 800,800));

        TrainerController controller = fxmlLoader.getController();
        controller.setScene("Standard");
    }
    @FXML
    public void loadTracking() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tracking-scene.fxml"));
        Stage window = (Stage) trackingButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 800,800));
    }
    @FXML
    public void loadPrecision() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("aim-scene.fxml"));
        Stage window = (Stage) precisionButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 800,800));

        TrainerController controller = fxmlLoader.getController();
        controller.setScene("Precision");
    }
    @FXML
    //On click, it'll update displayed stats and load stats scene.
    public void loadStats() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("stats-scene.fxml"));
        Stage window = (Stage) statisticsButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 800,800));

        StatsController controller = fxmlLoader.getController();
        controller.updateData();
    }
}
