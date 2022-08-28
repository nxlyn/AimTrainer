package projects.mora.j0927.aimtrainer.Targets;

import javafx.scene.shape.Circle;

public abstract class Target {
    /**** CONSTANTS ****/
    public static final int DEFAULT_RADIUS = 40, DEFAULT_SPEED = 0;
    /**** INSTANCE VARIABLES ****/
    private int targetRadius, targetSpeed;
    /**** CONSTRUCTORS ****/
    public Target(){
        this.setAll(DEFAULT_RADIUS, DEFAULT_SPEED);
    }
    /**** MUTATORS ****/
    public boolean setTargetRadius(int targetRadius){
        if(targetRadius>0 && targetRadius<101){
            this.targetRadius = targetRadius;
            return true;
        } else {
            return false;
        }
    }
    public boolean setTargetSpeed(int targetSpeed) {
        if(targetSpeed > 0 && targetSpeed < 100) {
            this.targetSpeed = targetSpeed;
            return true;
        } else {
            return false;
        }
    }
    public boolean setAll(int targetRadius, int targetSpeed){
        return this.setTargetRadius(targetRadius)
                && this.setTargetSpeed(targetSpeed);
    }
    public int getTargetRadius(){
        return targetRadius;
    }
    public int getTargetSpeed(){
        return targetSpeed;
    }
    public String toString() {
        return String.format("Target radius: %d\nTarget speed: %d\nShots hit: %d", this.targetRadius, this.targetSpeed);
    }
    /**** HELPER METHODS ****/
    public abstract void specifiedMovement(Circle circle);
    public abstract int maxNumTarget();
}
