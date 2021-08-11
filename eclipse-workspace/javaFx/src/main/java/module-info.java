module com.demo.javaFx {
	
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires jdk.jshell;
	requires lombok;
	requires java.sql;
	requires javafx.base;
	requires java.desktop;

	requires org.apache.poi.ooxml;

    opens com.demo.javaFx to javafx.fxml  ;
   // opens org.apache.commons.compress.archivers.zip to org.apache.poi.ooxml;
    exports com.demo.javaFx;
}

