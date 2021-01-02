module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.postgresql.jdbc;
    requires org.jboss.jandex;
    requires java.naming;
    requires net.bytebuddy;
    requires java.xml.bind;
    requires com.fasterxml.classmate;
    opens main to javafx.fxml;
    exports main;
}