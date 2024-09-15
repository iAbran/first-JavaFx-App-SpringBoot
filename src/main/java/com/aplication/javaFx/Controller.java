package com.aplication.javaFx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class Controller implements Initializable {

    private Long id;
    private Cliente item;
    @Autowired
    private ClienteRepository repository;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ListView<Cliente> list;
    @FXML
    private TextField textId, textNombre, textNumero;
    @FXML
    private Button btnAgregar, btnActualizar, btnEliminar, btnLimpiar;
    @FXML
    private Label totalRegistros, alerta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textId.setDisable(true);
        list();
        //Muestra en los TextField los Clientes seleccionados
        list.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldValue, newValue) -> {
                    if (newValue != null) {
                        id = newValue.getId();
                        item = item();
                        textId.setText(String.valueOf(item.getId()));
                        textNombre.setText(item.getNombre());
                        textNumero.setText(String.valueOf(item.getNumero()));
                    }
                });
    }

    //Metodo de buscar por ID
    public Cliente item() {
        return repository.findById(id).get();
    }

    public void list() {
        //Muestra los Clientes de la BD en la List
        list.setItems(FXCollections.observableArrayList(repository.findAll()));
        //Total de Registros
        totalRegistros.setText(String.valueOf(list.getItems().size()));
    }

    //Metodo para Guardar/Agregar
    @FXML
    public void save() {
        if (!textNombre.getText().trim().isEmpty() &&
                !textNumero.getText().trim().isEmpty()) {
            Cliente client = new Cliente();
            client.setNombre(textNombre.getText());
            client.setNumero(Integer.parseInt(textNumero.getText()));
            repository.save(client);
            list();
            clear();
        } else {
            alerta.setVisible(true);
        }
    }

    //Metodo limpiar los campos TextField
    @FXML
    public void clear() {
//        textId.clear();
//        textNombre.clear();
//        textNumero.clear();
        anchorPane.getChildren().forEach(node -> {
            if (node instanceof TextField) ((TextField) node).clear();
        });
    }

    //Metodo para actualizar los Clientes
    @FXML
    public void edite() {
        item.setNombre(textNombre.getText());
        item.setNumero(Integer.parseInt(textNumero.getText()));
        repository.save(item);
        list();
    }

    //Metodo Eliminar los Clientes seleccionados
    public void delete() {
        repository.delete(item);
        clear();
        list();
    }
}
