package com.demo.javaFx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author FERGANI
 *
 */
public class CrudController {
	@FXML
	private ImageView id_imgBar;
	@FXML
	private TextField id_site;
	@FXML
	private TextField id_bloc;
	@FXML
	private TextField id_piece;
	@FXML
	private TextField id_code;
	@FXML
	private TextField id_name;
	@FXML
	private TextField id_marque;
	@FXML
	private TextField id_type;
	@FXML
	private TextField id_serie;
	@FXML
	private TableView<Item> tableview_id;
	@FXML
	private TableColumn<Item, String> tab_name_id;
	@FXML
	private TableColumn<Item, String> tab_code_id;
	@FXML
	private TableColumn<Item, String> tab_marque_id;
	@FXML
	private TableColumn<Item, String> tab_type_id;
	@FXML
	private TableColumn<Item, String> tab_serie_id;
	@FXML
	private Button btuInsert;
	@FXML
	private Button btuDelete;
	@FXML
	private Button btuUpdate;
	@FXML
	private Button btuShow;
	@FXML
	private Button btuImoprt;
	@FXML
	private Button btuExport;
	Jdbc database = new Jdbc();
 

	@SuppressWarnings("exports")
	@FXML
	public void Add(ActionEvent event) {
		Window owner = btuInsert.getScene().getWindow();

		if (id_site.getText().isEmpty() || id_bloc.getText().isEmpty() || id_piece.getText().isEmpty()
				|| id_code.getText().isEmpty() || id_name.getText().isEmpty()) {

			showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Please enter your Inf is Empty !");
			return;
		}

		String site = id_site.getText();
		String bloc = id_bloc.getText();
		String piece = id_piece.getText();
		String code = id_code.getText();
		String name = id_name.getText();
		String marque = id_marque.getText();
		String type = id_type.getText();
		String serie = id_serie.getText();

		try {
			database.insertItem(site, bloc, piece, code, name, marque, type, serie);
			showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!", "Registration Successful ");
			ShowImg();
			CleanInterface();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("this is information :" + site + bloc);

	}
	
	@SuppressWarnings("exports")
	@FXML
	public void Delete(ActionEvent event) throws SQLException {
		Window owner = btuDelete.getScene().getWindow();
		String code = id_code.getText();
		database.deleteItem(code);
		showAlert(Alert.AlertType.ERROR, owner, "DELETE", "Delete Successful!");

	}

	@SuppressWarnings("exports")
	@FXML
	public void Update(ActionEvent event) throws SQLException {
		String site = id_site.getText();
		String bloc = id_bloc.getText();
		String piece = id_piece.getText();
		String code = id_code.getText();
		String name = id_name.getText();
		String marque = id_marque.getText();
		String type = id_type.getText();
		String serie = id_serie.getText();
		database.UpdateItem(site, bloc, piece, code, name, marque, type, serie);

	}

	@SuppressWarnings("exports")
	@FXML
	public void Export(ActionEvent event) throws SQLException {
		try {
			database.ExportExcelFile();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("exports")
	@FXML
	public void Import(ActionEvent event) throws SQLException, IOException {
		System.out.println("start import ..");
		//database.ImportCSV();
		database.ImportXlsx();

		
	}
	@FXML
	public void SelectItem(MouseEvent event) {
		Item item = tableview_id.getSelectionModel().getSelectedItem();
		id_site.setText(item.getSite());
		id_bloc.setText(item.getBloc());
		id_piece.setText(item.getPiece());
		id_code.setText(item.getCode());
		id_name.setText(item.getName());
		id_marque.setText(item.getMarque());
		id_type.setText(item.getType());
		id_serie.setText(item.getSerie());
		
	}
	@SuppressWarnings("exports")
	@FXML
	private void showAll(ActionEvent event) throws SQLException {

		ObservableList<Item> List = database.getAllItem();
		tab_name_id.setCellValueFactory(new PropertyValueFactory<Item, String>("name"));
		tab_code_id.setCellValueFactory(new PropertyValueFactory<Item, String>("code"));
		tab_marque_id.setCellValueFactory(new PropertyValueFactory<Item, String>("marque"));
		tab_type_id.setCellValueFactory(new PropertyValueFactory<Item, String>("type"));
		tab_serie_id.setCellValueFactory(new PropertyValueFactory<Item, String>("serie"));
		tableview_id.setItems(List);

	}

	private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(owner);
		alert.show();
	}
	
	private void CleanInterface() {
		// make TextField null
		id_site.setText("");
		id_bloc.setText("");
		id_piece.setText("");
		id_code.setText("");
		id_name.setText("");
		id_marque.setText("");
		id_type.setText("");
		id_serie.setText("");
	}

	private void ShowImg() {
		id_imgBar.setVisible(true);
		System.out.println("img");
	}
}
