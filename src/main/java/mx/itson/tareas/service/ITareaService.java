package mx.itson.tareas.service;

import mx.itson.tareas.model.Tarea;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ITareaService {

    public List<Tarea> listarTarea();

    public Tarea buscarTareaPorId(Integer idTarea);

    public void guardarTarea(Tarea tarea);

    public void eliminarTarea(Tarea tarea);
}
