package projects.mora.j0927.aimtrainer.Targets;

import javafx.application.Platform;
import javafx.scene.shape.Circle;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.PathTransition;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;


public class TrackingTarget extends Target {
    /**** CONSTANTS ****/
    public static final int DEFAULT_RADIUS = 40, DEFAULT_SPEED = 6;
    /**** CONSTRUCTORS ****/
    public TrackingTarget(){
        this.setAll(TrackingTarget.DEFAULT_RADIUS, TrackingTarget.DEFAULT_SPEED);
    }
    /**** HELPER METHODS ****/
    @Override
    public void specifiedMovement(Circle circle){
        Timer time = new Timer();
        Random random = new Random();

        time.scheduleAtFixedRate(new TimerTask(){
            int i = 60;
            public void run() {
                Platform.runLater(() -> {
                    if(i%6 == 0){
                        circle.setLayoutX(400.0);
                        circle.setLayoutY(400.0);
                        Polyline pathing= new Polyline();
                        for(int j = 0; j<6; j++){
                            pathing.getPoints().add(random.nextDouble(360 + 360) - 360);
                            pathing.getPoints().add(random.nextDouble(360 + 286) - 286);
                        }
                        PathTransition transition = new PathTransition();
                        transition.setNode(circle);
                        transition.setDuration(Duration.seconds(DEFAULT_SPEED));
                        transition.setPath(pathing);
                        transition.setCycleCount(1);
                        transition.play();
                        if(i == 0){
                            transition.stop();
                            circle.setLayoutX(5000); // moving out of frame when finished
                        }
                    }
                    i--;
                    if (i < 0) {
                        time.cancel();

                    }
                });
            }
        }, 0, 1000);
    }
    @Override
    public int maxNumTarget(){
        return 1;
    }

}
