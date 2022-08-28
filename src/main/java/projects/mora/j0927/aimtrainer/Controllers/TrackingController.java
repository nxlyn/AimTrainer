package projects.mora.j0927.aimtrainer.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import projects.mora.j0927.aimtrainer.Main;
import projects.mora.j0927.aimtrainer.Targets.TrackingTarget;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.Timer;
import java.util.TimerTask;


public class TrackingController {
    private int paneClicked, totalMisses, targetsHit, combo, highestCombo, score;
    @FXML
    private Label comboCounter, accuracyCounter, pointsCounter, gridCountdown;
    @FXML
    private Button menuButton, restartButton;
    @FXML
    private Circle circle;
    @FXML
    private Pane aimPane;
    //menuButton will update PlayerData.txt and then load aim-menu.fxml
    @FXML
    public void loadMenu() throws IOException{
        addData();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("aim-menu.fxml"));
        Stage window = (Stage) menuButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 800,800));
    }
    //restartButton will update PlayerData.txt and then a new aim scene will be loaded
    @FXML
    public void restartScene() throws IOException {
        addData();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("tracking-scene.fxml"));
        Stage window = (Stage) restartButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 800, 800));
    }
    //onClick will begin the 1-minute countdown and set all values to 0.
    @FXML
    public void startTraining(MouseEvent mouseEvent){
        if(gridCountdown.getText().equals("Click to Begin")){
            TrackingTarget target = new TrackingTarget();
            circle.setRadius(target.getTargetRadius());
            target.specifiedMovement(circle);

            paneClicked = totalMisses = targetsHit = combo = highestCombo = score = 0;
            gridCountdown.setFont(new Font("Agency FB", 244));
            Timer time = new Timer();
            time.scheduleAtFixedRate(new TimerTask(){
                int i = 60;
                public void run() {
                    Platform.runLater(() -> {
                        gridCountdown.setText(Integer.toString(i));
                        i--;

                        if (i < 0) {
                            time.cancel();
                            endGameSceneUpdate();
                        }
                    });
                }
            }, 0, 1000);
        }
    }
    //when mouse moved inside target circle, score will be added.
    @FXML
    public void targetTracking(MouseEvent mouseEvent){
        circle.setFill(Color.LIGHTGREEN);
        score += 7;
        pointsCounter.setText(score+"");
        targetsHit = 1;
        totalMisses = 1;
        accuracyCounter.setText("1");
        highestCombo = 1;
    }
    //when mouse exits target circle, color of target will be set back to red.
    @FXML
    public void trackingMissed(MouseEvent mouseEvent) {
        circle.setFill(Color.RED);
    }
    //Method is called once the scene gridCountdown label has fully countdown.
    //This method adjusts nodes values and placement, it will also double-check the combo counter.
    private void endGameSceneUpdate(){
        //Updating timer label to output "Game Over"
        gridCountdown.setFont(new Font("Agency FB", 119));
        gridCountdown.setText("Game Over");
        //Moving buttons into frame, and moving circle out of frame
        menuButton.setLayoutX(182);
        menuButton.setLayoutY(583);
        restartButton.setLayoutX(475);
        restartButton.setLayoutY(583);
        //Last check if highestCombo is less than combo, if so, set new combo
        if(highestCombo<combo){
            highestCombo = combo;
        }
    }
    //Method will update data by adding new line of data to PlayerData.txt file
    private void addData() {
        String line = targetsHit +"," + totalMisses +"," +accuracyCounter.getText() +"," + pointsCounter.getText() +"," + highestCombo + "\n";

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("src/main/resources/projects/mora/j0927/aimtrainer/PlayerData.txt",true);
            BufferedWriter buffered_Writer = new BufferedWriter(fileWriter);
            buffered_Writer.write(line);
            buffered_Writer.flush();
            buffered_Writer.close();

        } catch (IOException e) {
            System.out.println("Add line failed!!" +e);
        }
    }
}
