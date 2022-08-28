package projects.mora.j0927.aimtrainer.Targets;

import javafx.scene.shape.Circle;

public class StandardTarget extends Target{
    /**** CONSTANTS ****/
    public static final int DEFAULT_RADIUS = 40, DEFAULT_SPEED = 0;
    /**** CONSTRUCTORS ****/
    public StandardTarget(){
        this.setAll(StandardTarget.DEFAULT_RADIUS, StandardTarget.DEFAULT_SPEED);
    }
    /**** HELPER METHODS ****/
    @Override
    public void specifiedMovement(Circle circle){
        int range = (800-(2*this.getTargetRadius()));
        circle.setLayoutX((Math.random() * range) + (this.getTargetRadius()));
        circle.setLayoutY((Math.random() * (range - 73)) + (this.getTargetRadius() + 73));
    }
    @Override
    public int maxNumTarget(){
        return 3;
    }
}
