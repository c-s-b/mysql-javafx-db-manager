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

public class StructurePane extends VBox{
	public StructurePane(Connection conn, String tableName) {
		try {
			this.setStyle("-fx-background-color: PALEGOLDENROD");

			Statement stmt5 = conn.createStatement();
			ResultSet rSet = stmt5.executeQuery("SHOW COLUMNS FROM " + tableName);
			ResultSetMetaData rsmd = rSet.getMetaData();
			int numColumns = rsmd.getColumnCount();
			int rowCount = 1;
			Text output = new Text();
			StringBuilder resultString = new StringBuilder();

			if (numColumns > 0)
			{
				HBox columns = new HBox();
				//columns.setAlignment();
				columns.setSpacing(60);
				Text titleTop = new Text("Table: Student\n" +
						"=======================================================" +
						"=====================================================\n");
				Text titleBottom = new Text("\n=======================================================" +
						"=======================================================\n");
				for (int colNum = 1; colNum <= numColumns; colNum++) {
					columns.getChildren().add(new Text(rsmd.getColumnLabel(colNum)));
				}
				this.getChildren().add(titleTop);
				this.getChildren().add(columns);
				this.getChildren().add(titleBottom);
					while(rSet.next()) {
						this.getChildren().add(addRow(rSet, numColumns));
						this.getChildren().add(new Text("\n-------------------------------------------------------" 
								+ "------------------------------------------\n"));
					}

			}
		} catch (SQLException ex) {
			Alert connectFailed = new Alert(AlertType.INFORMATION);
			connectFailed.setHeaderText(null);
			connectFailed.setContentText(ex.getMessage());
			connectFailed.showAndWait();
		}

	}
	
	private HBox addRow(ResultSet rSet, int numColumns) {
		HBox row = new HBox();
		//row.setAlignment(Pos.CENTER);
		row.setSpacing(30);
		try{
			for (int colNum = 1; colNum <= numColumns; colNum++) {
				row.getChildren().add(new Text(rSet.getString(colNum)));
			}
		} catch (SQLException ex) {
			Alert connectFailed = new Alert(AlertType.INFORMATION);
			connectFailed.setHeaderText(null);
			connectFailed.setContentText(ex.getMessage());
			connectFailed.showAndWait();
		}
		return row;
	}
}
