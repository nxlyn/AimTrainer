package projects.mora.j0927.aimtrainer;

public class ActionExceedsTimerLimitException extends Exception{
    public ActionExceedsTimerLimitException() {
        super("ERROR: Your timer has ended! Cannot perform any more actions.");
    }
    public ActionExceedsTimerLimitException(String message){
        super(message);
    }
}