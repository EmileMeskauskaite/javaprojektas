module com.kursinis.prif4kursinis {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires spring.web;
    requires spring.core;
    requires spring.context;
    requires com.google.gson;
    requires javafx.base;
    requires spring.security.crypto;

    opens com.kursinis.prif4kursinis to javafx.fxml;
    exports com.kursinis.prif4kursinis;
    opens com.kursinis.prif4kursinis.model to javafx.fxml, org.hibernate.orm.core;
    exports com.kursinis.prif4kursinis.model;
    opens com.kursinis.prif4kursinis.fxControllers to javafx.fxml;
    exports com.kursinis.prif4kursinis.fxControllers to javafx.fxml;
    opens com.kursinis.prif4kursinis.hibernateControllers to javafx.fxml;
    exports com.kursinis.prif4kursinis.hibernateControllers to javafx.fxml;
    opens com.kursinis.prif4kursinis.webControllers to javafx.fxml;
    exports com.kursinis.prif4kursinis.webControllers to javafx.fxml;
    opens com.kursinis.prif4kursinis.fxControllers.tableParameters to javafx.fxml, javafx.base; // Added javafx.base here
    exports com.kursinis.prif4kursinis.fxControllers.tableParameters to javafx.fxml;
}