package com.mortales.listaTareas.servicio;

import com.mortales.listaTareas.Repositorio.TareaRepositorio;
import com.mortales.listaTareas.modelo.Tarea;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaServicioImpl implements ITareaServicio
{
    @Autowired
    private TareaRepositorio tareaRepositorio;

    @Override
    public List<Tarea> listarTareas()
    {
        return tareaRepositorio.findAll();
    }

    @Override
    public Tarea buscarTareaPorId(Integer id)
    {
        Optional<Tarea> tareaOpt = tareaRepositorio.findById(id);
        return tareaOpt.orElse(null);
    }

    @Override
    public void guardarTarea(Tarea tarea)
    {
        tareaRepositorio.save(tarea);
    }

    @Override
    public void eliminarTarea(Tarea Tarea)
    {
        tareaRepositorio.delete(Tarea);
    }
}
