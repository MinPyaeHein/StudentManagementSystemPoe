module org.example.mylearningproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;
    requires jdk.jshell;
    requires transitive org.postgresql.jdbc;

    opens org.example.mylearningproject to javafx.fxml;
    opens org.example.mylearningproject.view to javafx.fxml;
    opens Model to javafx.base;

    exports Model;
    exports Controller;
    opens Controller to javafx.fxml;
    exports Dto;
    opens Dto to javafx.base;

}