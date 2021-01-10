module main {
    requires javafx.controls;
    requires javafx.fxml;
     requires java.persistence;
    requires java.sql;
    requires com.fasterxml.classmate;
    requires java.xml.bind; 
    requires org.hibernate.orm.core;
    requires org.postgresql.jdbc;
    requires org.jboss.jandex;
    requires java.naming;
    requires net.bytebuddy;

    opens main to javafx.fxml;
    exports main;
}