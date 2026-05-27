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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import java.sql.*;

public class SelectionPane extends VBox{
	public SelectionPane(Connection conn, Stage PrimaryStage) {
		try {
			
			Font font = new Font(18);
			this.setAlignment(Pos.CENTER);
			this.setPrefSize(300, 300);
			Text instruction= new Text("Select a Table:");
			instruction.setFont(font);
			this.getChildren().add(instruction);
			Statement stmt5 = conn.createStatement();
			ResultSet rSet = stmt5.executeQuery("SHOW TABLES");
			ResultSetMetaData rsmd = rSet.getMetaData();
			Text tableList= new Text("TABLES");
			outputTables(tableList, rsmd, rSet, conn, PrimaryStage);
			this.setSpacing(20);
			this.setStyle("-fx-background-color: PALEGOLDENROD");
		} catch (SQLException ex) {
			errorAlert(ex);
		}
		
	}
	private void errorAlert(SQLException ex) {
		Alert SQLerror = new Alert(AlertType.WARNING);
		SQLerror.setHeaderText(null);
		SQLerror.setContentText("SQL Error" + ex.getMessage());
		SQLerror.showAndWait();
	}
	
	private void outputTables(Text tableList, ResultSetMetaData rsmd, ResultSet rSet, Connection conn, Stage PrimaryStage) {
		try {
			int counter = 1;
			ToggleGroup group = new ToggleGroup();
			while(rSet.next()) {
				RadioButton button = new RadioButton(rSet.getString(counter));
				button.setToggleGroup(group);
				//button.setOnAction(null);
				this.getChildren().add(button);
			}
			selectTable(group, conn, PrimaryStage);
		} catch (SQLException ex) {
			
			Alert error = new Alert(AlertType.WARNING);
			error.setHeaderText(null);
			error.setContentText(ex.getMessage());
			error.showAndWait();
		}
	}
	
	private void selectTable(ToggleGroup group, Connection conn, Stage PrimaryStage) {
		Button showStructure = new Button("Show Table Structure");
		Button showData = new Button("Show Data");
		HBox buttons = new HBox(showStructure, showData);
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(50);
		this.getChildren().add(buttons);
		showStructure.setOnAction(event -> showTableStructure(group, conn, PrimaryStage));
		showData.setOnAction(event -> showData(group, conn, PrimaryStage));
	}
	
	private void showTableStructure(ToggleGroup group, Connection conn, Stage PrimaryStage) {
		
		String tableName;
		if(group.getSelectedToggle() == null) {
			Alert noneSelected = new Alert(AlertType.INFORMATION);
			noneSelected.setContentText("No Table Selected");
			noneSelected.showAndWait();
			return;
		}
		RadioButton selected = (RadioButton) group.getSelectedToggle();
		tableName = selected.getText();
		Scene scene = new Scene(new StructurePane(conn, tableName));
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
		
		
	}
	private void showData(ToggleGroup group, Connection conn, Stage PrimaryStage) {
		String tableName;
		if(group.getSelectedToggle() == null) {
			Alert noneSelected = new Alert(AlertType.INFORMATION);
			noneSelected.setContentText("No Table Selected");
			noneSelected.showAndWait();
			return;
		}
		RadioButton selected = (RadioButton) group.getSelectedToggle();
		tableName = selected.getText();
		Scene scene = new Scene(new DataPane(conn, tableName));
		PrimaryStage.setScene(scene);
		PrimaryStage.show();
	}
	
	//private Stage getStage() {
		//return newStage;
	//}
}
