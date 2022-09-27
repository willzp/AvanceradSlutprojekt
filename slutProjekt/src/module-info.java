module slutProjekt {
	requires javafx.controls;
	requires javafx.fxml;
	requires json.simple;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml, json.simple;
}
