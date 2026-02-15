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
    private TableColumn<Tarea, Integer> columnId;
    @FXML
    private TableColumn<Tarea, String> columnTarea;
    @FXML
    private TableColumn<Tarea, String> columnResponsable;
    @FXML
    private TableColumn<Tarea, String> columnEstatus;
    @FXML
    private TextField txtTarea;
    @FXML
    private TextField txtResponsable;
    @FXML
    private TextField txtEstatus;
    private final ObservableList<Tarea> listaTareas =
            FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        tablaTareas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTareas();
        inicializarTabla();
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

    Integer idTarea;

    @FXML
    private void agregarTarea()
    {
        String nombreTarea = txtTarea.getText();
        String responsable = txtResponsable.getText();
        String estatus = txtEstatus.getText();
        if (!datosValidos(nombreTarea, responsable, estatus))
        {
            mostrarMensaje("Error", "Debe llenar todos los campos");
            return;
        }
        Tarea tarea = new Tarea();
        if (idTarea != null) tarea.setIdTarea(idTarea);
        tarea.setNombreTarea(nombreTarea);
        tarea.setResponsable(responsable);
        tarea.setEstatus(estatus);
        tareaServicio.guardarTarea(tarea);
        mostrarMensaje("Exito", "Tarea guardada");
        listarTareas();
    }

    @FXML
    private void modificarTarea()
    {
        if (idTarea == null)
        {
            mostrarMensaje("Error", "Debe seleccionar una tarea");
            return;
        }
        agregarTarea();
        limpiarDatos();
    }

    @FXML
    private void eliminarTarea(){
        if (idTarea==null)
        {
            mostrarMensaje("Error", "Debe seleccionar una tarea");
            return;
        }
        Tarea tareaAeliminar=new Tarea();
        tareaAeliminar.setIdTarea(idTarea);
        tareaServicio.eliminarTarea(tareaAeliminar);
        limpiarDatos();
        listarTareas();
    }
    @FXML
    private void limpiarFormulario(){
        limpiarDatos();
    }

    private void inicializarTabla()
    {
        tablaTareas.setOnMouseClicked(mouseEvent ->
        {
            Tarea tareaSeleccionada = tablaTareas.getSelectionModel().getSelectedItem();
            if (tareaSeleccionada != null)
            {
                idTarea = tareaSeleccionada.getIdTarea();
                txtTarea.setText(tareaSeleccionada.getNombreTarea());
                txtResponsable.setText(tareaSeleccionada.getResponsable());
                txtEstatus.setText(tareaSeleccionada.getEstatus());
            }
        });

    }

    private void limpiarDatos()
    {
        txtTarea.clear();
        txtEstatus.clear();
        txtResponsable.clear();
        idTarea = null;
    }

    private void mostrarMensaje(String titulo, String mensaje)
    {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private boolean datosValidos(String nombreTarea, String responsable, String estatus)
    {
        return !nombreTarea.isEmpty() && !responsable.isEmpty() && !estatus.isEmpty();
    }
}
