module org.main.autoschool {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.desktop;
    requires javafx.swing;
    requires org.hibernate.validator;
    requires org.postgresql.jdbc;
    requires jakarta.validation;
    requires itextpdf;

    opens org.main.autoschoolapp to javafx.fxml;
    opens org.main.autoschoolapp.model to org.hibernate.orm.core;
    exports org.main.autoschoolapp;
    exports org.main.autoschoolapp.controllers;
    exports org.main.autoschoolapp.model;
    opens org.main.autoschoolapp.controllers to javafx.fxml;
    opens org.main.autoschoolapp.util to org.hibernate.orm.core;

}