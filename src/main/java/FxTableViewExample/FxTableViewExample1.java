package FxTableViewExample;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


public class FxTableViewExample1
        extends Application {

	private TableView<MyBook> table;
	private ObservableList<MyBook> data;
	private Text actionStatus;

	public static void main(String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("Table View Example 1");

		// Books label
		Label label = new Label("Books");
		label.setTextFill(Color.DARKBLUE);
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 36));
		HBox labelHb = new HBox();
		labelHb.setAlignment(Pos.CENTER);
		labelHb.getChildren().add(label);

		// Table view, data, columns and properties

		table = new TableView<>();
		data = getInitialTableData();
		table.setItems(data);

		TableColumn titleCol = new TableColumn("Title");
		titleCol.setCellValueFactory(new PropertyValueFactory<MyBook, String>("title"));
		TableColumn authorCol = new TableColumn("Author");
		authorCol.setCellValueFactory(new PropertyValueFactory<MyBook, String>("author"));

		table.getColumns().setAll(titleCol, authorCol);
		table.setPrefWidth(450);
		table.setPrefHeight(300);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		table.getSelectionModel().selectedIndexProperty().addListener(
				new RowSelectChangeListener());

		// Status message text
		actionStatus = new Text();
		actionStatus.setFill(Color.FIREBRICK);

		// Vbox
		VBox vbox = new VBox(20);
		vbox.setPadding(new Insets(25, 25, 25, 25));;
		vbox.getChildren().addAll(labelHb, table, actionStatus);

		// Scene
		Scene scene = new Scene(vbox, 500, 475); // w x h
		primaryStage.setScene(scene);
		primaryStage.show();

		// Select the first row
		table.getSelectionModel().select(0);
		MyBook book = table.getSelectionModel().getSelectedItem();
		actionStatus.setText(book.toString());
		
	} // start()

	private class RowSelectChangeListener implements ChangeListener<Number> {

		@Override
		public void changed(ObservableValue<? extends Number> ov, 
				Number oldVal, Number newVal) {

			int ix = newVal.intValue();

			if ((ix < 0) || (ix >= data.size())) {
	
				return; // invalid data
			}

			MyBook book = data.get(ix);
			actionStatus.setText(book.toString());	
		}
	}
	
	private ObservableList<MyBook> getInitialTableData() {
		
		List<MyBook> list = new ArrayList<>();
		
		list.add(new MyBook("The Thief", "Fuminori Nakamura"));
		list.add(new MyBook("Of Human Bondage", "Somerset Maugham"));
		list.add(new MyBook("The Bluest Eye", "Toni Morrison"));
		list.add(new MyBook("I Am Ok You Are Ok", "Thomas Harris"));
		list.add(new MyBook("Magnificent Obsession", "Lloyd C Douglas"));
		list.add(new MyBook("100 Years of Solitude", "Gabriel Garcia Marquez"));
		list.add(new MyBook("What the Dog Saw", "Malcolm Gladwell"));
		list.add(new MyBook("The Fakir", "Ruzbeh Bharucha"));
		list.add(new MyBook("The Hobbit", "J.R.R. Tolkien"));
		list.add(new MyBook("Strange Life of Ivan Osokin", "P.D. Ouspensky"));
		list.add(new MyBook("The Hunt for Red October", "Tom Clancy"));
		list.add(new MyBook("Coma", "Robin Cook"));

		ObservableList<MyBook> data = FXCollections.observableList(list);

		return data;
	}
}
