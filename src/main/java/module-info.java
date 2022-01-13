module com.example.rtpgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rtpgui to javafx.fxml;
    exports com.example.rtpgui;
}