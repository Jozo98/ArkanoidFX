module ArkanoidFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens main to javafx.fxml;
    exports main;
}