module projects.mora.j0927.aimtrainer {
    requires javafx.controls;
    requires javafx.fxml;


    opens projects.mora.j0927.aimtrainer to javafx.fxml;
    exports projects.mora.j0927.aimtrainer;
    exports projects.mora.j0927.aimtrainer.Controllers;
    opens projects.mora.j0927.aimtrainer.Controllers to javafx.fxml;
    exports projects.mora.j0927.aimtrainer.Targets;
    opens projects.mora.j0927.aimtrainer.Targets to javafx.fxml;
}