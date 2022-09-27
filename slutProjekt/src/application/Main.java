package application;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			TextField field = new TextField();
			Text text = new Text();
			Button button = new Button("Get Weather");
			button.setOnMouseClicked(e -> btnClicked(field.getText(), text));
			BorderPane root = new BorderPane();
			root.setTop(field);
			root.setCenter(button);
			root.setBottom(text);
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Weather application");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void btnClicked(String city, Text text) {
		String urlToSend = "https://api.openweathermap.org/data/2.5/weather?q=" + city
				+ "&appid=03542354196d153b0ef15837cf340dd4";

		try {
			URL url = new URL(urlToSend);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.connect();

			String response = "";
			Scanner scanner = new Scanner(url.openStream());

			while (scanner.hasNext()) {
				String row = scanner.nextLine();
				response = response + row;
			}
			scanner.close();

			JSONParser jsonParser = new JSONParser();

			JSONObject jsonObject = (JSONObject) jsonParser.parse(response);

			JSONObject mainObject = (JSONObject) jsonObject.get("main");

			String temp = mainObject.get("temp").toString();

			JSONArray weatherArray = (JSONArray) jsonObject.get("weather");

			JSONObject weatherObject = (JSONObject) weatherArray.get(0);

			String description = weatherObject.get("description").toString();

			double kelvin = Double.parseDouble(temp);
			double celsius = kelvin - 273.15;
			DecimalFormat numberFormat = new DecimalFormat("#.00");
			text.setText("The temerature in " + city + " is " + numberFormat.format(celsius) + " °C \n"
					+ "Weather description : " + description);
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
