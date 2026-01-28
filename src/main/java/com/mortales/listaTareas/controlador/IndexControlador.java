package com.mortales.listaTareas.controlador;

import com.mortales.listaTareas.modelo.Tarea;
import com.mortales.listaTareas.servicio.TareaServicioImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexControlador implements Initializable
{
    private static final Logger logger = LoggerFactory.getLogger(IndexControlador.class);
    @Autowired
    private TareaServicioImpl tareaServicio;

    @FXML
    private TableView<Tarea> tablaTareas;
    @FXML
    private TableColumn<Tarea,Integer>columnId;
    @FXML
    private TableColumn<Tarea,String >columnTarea;
    @FXML
    private TableColumn<Tarea,String >columnResponsable;
    @FXML
    private TableColumn<Tarea,String >columnEstatus;

    private final ObservableList<Tarea>listaTareas=
            FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tablaTareas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
    }

    private void configurarColumnas()
    {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idTarea"));
        columnTarea.setCellValueFactory(new PropertyValueFactory<>("nombreTarea"));
        columnResponsable.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        columnEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
    }
    private void listarTareas()
    {
        listaTareas.clear();
        listaTareas.addAll(tareaServicio.listarTareas());
        tablaTareas.setItems(listaTareas);
    }
}
