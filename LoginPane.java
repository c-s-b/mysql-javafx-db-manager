package application;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.*;

public class LoginPane extends GridPane {
	public LoginPane(Stage primaryStage) {
		Font font = new Font(18);
		Label hostNameLabel = new Label("Hostname:");
		hostNameLabel.setFont(font);
		GridPane.setHalignment(hostNameLabel, HPos.RIGHT);
		Label userNameLabel = new Label("User:");
		userNameLabel.setFont(font);
		GridPane.setHalignment(userNameLabel, HPos.RIGHT);
		Label passwordLabel = new Label("Password:");
		passwordLabel.setFont(font);
		GridPane.setHalignment(passwordLabel, HPos.RIGHT);
		Label dbNameLabel = new Label("Database Name:");
		dbNameLabel.setFont(font);
		GridPane.setHalignment(dbNameLabel, HPos.RIGHT);
		Button connect = new Button("Connect");
		connect.setFont(font);
		//GridPane.setValignment(connect, VPos.BOTTOM);
		GridPane.setHalignment(connect, HPos.LEFT);
		
		
		
		TextField hostNameField = new TextField();
		//hostNameField.setFont(font);
		hostNameField.setMinWidth(100);
		hostNameField.setAlignment(Pos.CENTER);
		TextField userNameField = new TextField();
		//userNameField.setFont(font);
		userNameField.setMinWidth(100);
		userNameField.setAlignment(Pos.CENTER);
		TextField passwordField = new TextField();
		//passwordField.setFont(font);
		passwordField.setMinWidth(100);
		passwordField.setAlignment(Pos.CENTER);
		TextField dbNameField = new TextField();
		//dbNameField.setFont(font);
		dbNameField.setMinWidth(100);
		dbNameField.setAlignment(Pos.CENTER);

		connect.setOnAction((event) -> {
			String host = hostNameField.getText();
			String db = dbNameField.getText();
			String user = userNameField.getText();
			String pass = passwordField.getText();
			attemptConnection(host, db, user, pass, primaryStage);
		});
		
		 setAlignment(Pos.CENTER);
	     setHgap(20);
	     setVgap(10);
	     setStyle("-fx-background-color: PALEGOLDENROD");
	     
	     add(hostNameLabel, 0, 0);
	     add(hostNameField, 1, 0);
	     add(userNameLabel, 0, 1);
	     add(userNameField, 1, 1);
	     add(passwordLabel, 0, 2);
	     add(passwordField, 1, 2);
	     add(dbNameLabel, 0, 3);
	     add(dbNameField, 1, 3);
	     add(connect, 1,4);
	     
	        
	}
	
	public void attemptConnection(String host, String db, String user, String pass, Stage PrimaryStage) {
		try {
			Connection conn = null;
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + host + "/" + db + "?user=" + user + "&password=" + pass);
			if (conn != null)
			{
				manageDatabase(conn, PrimaryStage);
			}
		} catch (SQLException ex) {
			failedConnection();
			ex.printStackTrace();
		} catch (Exception ex) {
			failedConnection();
			ex.printStackTrace();
		}
	}
	
	public void manageDatabase(Connection conn, Stage primaryStage) {
		Alert connectSuccess = new Alert(AlertType.INFORMATION);
		connectSuccess.setHeaderText(null);
		connectSuccess.setContentText("Connection Succesful");
		connectSuccess.showAndWait();
		Scene scene = new Scene(new SelectionPane(conn, primaryStage));
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void failedConnection() {
		Alert connectFailed = new Alert(AlertType.INFORMATION);
		connectFailed.setHeaderText(null);
		connectFailed.setContentText("Connection Failed");
		connectFailed.showAndWait();
	}
	
}
