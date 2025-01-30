module org.example.mylearningproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    opens org.example.mylearningproject to javafx.fxml;

    opens org.example.mylearningproject.view to javafx.fxml;

    opens Model to javafx.base;
    exports Model;
    exports Controller;
    opens Controller to javafx.fxml;
    exports org.example.mylearningproject;
}