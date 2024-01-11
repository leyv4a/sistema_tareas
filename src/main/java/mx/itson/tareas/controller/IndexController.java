package mx.itson.tareas.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import mx.itson.tareas.model.Tarea;
import mx.itson.tareas.service.TareaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexController implements Initializable {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private TareaService tareaService;

    @FXML
    private TableView<Tarea> tableTareas;

    @FXML
    private TableColumn<Tarea, Integer> idColumna;

    @FXML
    private TableColumn<Tarea, String> tareaColumna;

    @FXML
    private TableColumn<Tarea, String> responsableColumna;

    @FXML
    private TableColumn<Tarea, String> estadoColumna;

    private final ObservableList<Tarea> tareaList = FXCollections.observableArrayList();

    @FXML
    private TextField txtTarea;

    @FXML
    private TextField txtResponsable;

    @FXML
    private TextField txtEstado;

    private Integer idTareaInterno;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableTareas.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configurarColumnas();
        listarTarea();
    }

    private void configurarColumnas(){
        idColumna.setCellValueFactory(new PropertyValueFactory<>("id"));
        tareaColumna.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        responsableColumna.setCellValueFactory(new PropertyValueFactory<>("responsable"));
        estadoColumna.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    public void listarTarea(){
        tareaList.clear();
        tareaList.addAll(tareaService.listarTarea());
        tableTareas.setItems(tareaList);
    }

    public void agregarTarea() {
        if (txtTarea.getText().isEmpty()){
            mostrarMensaje("Error de validacion","Proporcione una tarea");
            txtTarea.requestFocus();
            return;
        }else{
            var tarea = new Tarea();
            recolectarDatos(tarea);
            tarea.setId(null);
            tareaService.guardarTarea(tarea);
            mostrarMensaje("Informacion guardada","¡Tarea guardada con exito!");
            limpiarForma();
            listarTarea();
        }

    }

    private void mostrarMensaje(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.show();
    }

    private void recolectarDatos(Tarea tarea){
        if (idTareaInterno != null) {
            tarea.setId(idTareaInterno);
            tarea.setNombre(txtTarea.getText());
            logger.info(tarea.getNombre());
            tarea.setResponsable(txtResponsable.getText());
            logger.info(tarea.getResponsable());
            tarea.setEstado(txtEstado.getText());
            logger.info(tarea.getEstado()+"aa");
        }
    }

    public void modificarTarea() {
        if (idTareaInterno == null){
            mostrarMensaje("Informacion", "Debe seleccionar una tarea");
            return;
        }
        if (txtTarea.getText().isEmpty()){
            mostrarMensaje("Informacion", "Debe proporcionar una tarea");
            txtTarea.requestFocus();
            return;
        }
            var tarea = new Tarea();
            recolectarDatos(tarea);
            tareaService.guardarTarea(tarea);
            mostrarMensaje("Informacion" , "Tarea modificada");
            limpiarForma();
            listarTarea();
        }

    public void eliminarTarea() {
        var tarea = tableTareas.getSelectionModel().getSelectedItem();
        if (tarea != null){
            tareaService.eliminarTarea(tarea);
            mostrarMensaje("Informacion" , "Tarea eliminada: "+tarea.getId());
            limpiarForma();
            listarTarea();
        }
        if (tarea == null){
            mostrarMensaje("Error", "No se a seleccionada ninguna tarea");
        }
    }

    public void limpiarForma(){
        idTareaInterno = null;
        txtTarea.clear();
        txtEstado.clear();
        txtResponsable.clear();
    }

    public void cargarTareaForm() {
        var tarea =tableTareas.getSelectionModel().getSelectedItem();
        if (tarea != null){
            idTareaInterno = tarea.getId();
            txtTarea.setText(tarea.getNombre());
            txtResponsable.setText(tarea.getResponsable());
            txtEstado.setText(tarea.getEstado());
        }
    }
}
