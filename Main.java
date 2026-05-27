package application;
	
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane pane = new BorderPane();
			
			LoginPane pane = new LoginPane(primaryStage);
			Scene scene = new Scene(pane,400,200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Database Management Program");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
