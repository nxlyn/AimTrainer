package projects.mora.j0927.aimtrainer.Controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import projects.mora.j0927.aimtrainer.ActionExceedsTimerLimitException;
import projects.mora.j0927.aimtrainer.Main;
import projects.mora.j0927.aimtrainer.Targets.StandardTarget;
import projects.mora.j0927.aimtrainer.Targets.Target;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.util.Timer;
import java.util.TimerTask;


public class TrainerController {
    private int paneClicked, totalMisses, targetsHit, combo, highestCombo, score;
    private String scene;
    @FXML
    private Label comboCounter, accuracyCounter, pointsCounter, gridCountdown;
    @FXML
    private Button menuButton, restartButton;
    @FXML
    private Circle circle1, circle2, circle3;
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
    public void restartAimScene() throws IOException {
        addData();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("aim-scene.fxml"));
        Stage window = (Stage) restartButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load(), 800, 800));
    }
    //onClick will begin the 1-minute countdown and set all values to 0.
    @FXML
    public void startTraining(MouseEvent mouseEvent){
        if(gridCountdown.getText().equals("Click to Begin")){
            adjustTargets(scene).specifiedMovement(circle1);
            adjustTargets(scene).specifiedMovement(circle2);
            adjustTargets(scene).specifiedMovement(circle3);
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
    //targetHit methods will call onTargetHit() and targetOverlap()
    @FXML
    public void targetHit1(MouseEvent mouseEvent){
        onTargetHit(circle1);
        while(targetOverlap(circle1, circle2, circle3)){
            System.out.println("c1 overlapping");
            adjustTargets(scene).specifiedMovement(circle1);
        }
    }
    @FXML
    public void targetHit2(MouseEvent mouseEvent){
        onTargetHit(circle2);
        while(targetOverlap(circle2, circle1, circle3)){
            System.out.println("c2 overlapping");
            adjustTargets(scene).specifiedMovement(circle2);
        }
    }
    @FXML
    public void targetHit3(MouseEvent mouseEvent){
        onTargetHit(circle3);
        while(targetOverlap(circle3, circle1, circle2)){
            System.out.println("c3 overlapping");
            adjustTargets(scene).specifiedMovement(circle3);
        }
    }
    public void onTargetHit(Circle circle){
        if(!gridCountdown.getText().equals("Click to Begin")){
            //Randomizing placement of circle
            adjustTargets(scene).specifiedMovement(circle);
            //Tracking targets hit and updating comboCounter
            targetsHit += 1;
            //If statement caps the max points earned at 1500, regardless  of combo.
            if(combo<50){
                score += (750+((combo*.005)*750));
            } else {
                score += 1000;
            }
            combo += 1;
            comboCounter.setText("x"+ combo); //set combo to label comboCounter
            pointsCounter.setText(score+""); // set score to label pointsCounter
        }
    }
    //Returns true if primary circle's X and Y coordinates are located within diameter of the other circle objects passed.
    public boolean targetOverlap(Circle circle, Circle alt, Circle alt2){
        if(circle.getLayoutX() >= alt.getLayoutX()-(2*circle.getRadius()) && circle.getLayoutX() <= alt.getLayoutX()+(2*circle.getRadius())
                && circle.getLayoutY() >= alt.getLayoutY()-(2*circle.getRadius()) && circle.getLayoutY() <= alt.getLayoutY()+(2*circle.getRadius())){
            return true;
        } else if(circle.getLayoutX() >= alt2.getLayoutX()-(2*circle.getRadius()) && circle.getLayoutX() <= alt2.getLayoutX()+(2*circle.getRadius())
                && circle.getLayoutY() >= alt2.getLayoutY()-(2*circle.getRadius()) && circle.getLayoutY() <= alt2.getLayoutY()+(2*circle.getRadius())){
            return true;
        }
        return false;
    }
    //targetNotHit method onClick will track EVERY click made inside the pane (including circle/target clicks)
    //Methods such as trackMisses(int) help differentiate pane clicks from target hits.
    //These methods will stay in place until I find a fix to overlapping node events.
    @FXML
    public void targetNotHit(MouseEvent mouseEvent){
        if(!gridCountdown.getText().equals("Click to Begin")){ // checking if timer has started
            int hitAccuracy;
            //Setting label accuracyCounter
            try {
                if(gridCountdown.getText().equals("Game Over")){ // checking if timer has not yet ended
                    throw new ActionExceedsTimerLimitException();
                }
                paneClicked += 1;
                //If paneClicks is higher than targetHits, target was missed.
                if(paneClicked > targetsHit){
                    score -= 250; // losing score points on target miss
                    if(highestCombo<combo){
                        highestCombo = combo; // storing the highest combo on miss
                    }
                    combo = 0; // resetting combo counter on miss
                    comboCounter.setText("x"+ combo);
                }
                trackMisses(paneClicked); //reset paneClicks if paneClicked>targetsHit and update totalMisses value
                hitAccuracy = (targetsHit * 100)/(paneClicked+totalMisses); // calculate hit accuracy percentage
                accuracyCounter.setText(hitAccuracy+"%"); // set hitAccuracy to Label accuracyCounter
                pointsCounter.setText(score+""); // set score to Label pointsCounter
            } catch(ActionExceedsTimerLimitException aetle){
                System.out.println(aetle.getMessage());
            }
        }
    }
    //targetsNotHit() will call this method when paneClicked exceeds targetsHit.
    //If true, it will update totalMisses and reset paneClicked to equal targetsHit.
    public int trackMisses(int paneClicked){
        if(paneClicked > targetsHit){
            totalMisses += 1;
            this.paneClicked = targetsHit;
        }
        return paneClicked;
    }
    //Depending on variable scene, this method will adjust the circle node, and it's movement based on value passed.
    public Target adjustTargets(String scene){
        switch(scene){
            case "Standard":{
                StandardTarget target = new StandardTarget();
                circle1.setRadius(target.getTargetRadius());
                circle2.setRadius(target.getTargetRadius());
                circle3.setRadius(target.getTargetRadius());
                //(aimPane).getChildren().add(new Circle(400, 400, 40, Color.GREEN));
                //(aimPane).getChildren().add(new Circle(600, 400, 40, Color.PURPLE));
                return target;
            }
            case "Precision":{
                circle1.setRadius(15);
                circle2.setRadius(15);
                circle3.setRadius(15);
                break;
            }
            default:{
                return new StandardTarget();
            }
        } return new StandardTarget();
    }
    //Method called by MenuController which allows changes to class variable String scene.
    //This variable is then used to adjust target and target movement depending on variable value.
    public void setScene(String scene){
        this.scene = scene;
    }
    //Method is called once the scene gridCountdown label has fully countdown.
    //This method adjusts nodes values and placement, it will also double-check the combo counter.
    private void endGameSceneUpdate(){
        //Updating timer label to output "Game Over"
        gridCountdown.setFont(new Font("Agency FB", 119));
        gridCountdown.setText("Game Over");
        //Moving buttons into frame, and moving circle out of frame
        circle1.setLayoutX(1000);
        circle2.setLayoutX(1000);
        circle3.setLayoutX(1000);
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
