module org.example.froggergame {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens org.example.froggergame to javafx.fxml;
    exports org.example.froggergame;
}