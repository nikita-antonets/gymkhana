module com.typingtest.unjeugenial {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires eu.hansolo.tilesfx;

    opens com.typingtest.unjeugenial to javafx.fxml;
    exports com.typingtest.unjeugenial;
}