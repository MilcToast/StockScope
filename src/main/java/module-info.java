module org.example {
    requires com.google.gson;
    requires java.net.http;
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example to javafx.fxml;

    exports org.example;
}