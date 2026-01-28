package com.mortales.listaTareas.Repositorio;

import com.mortales.listaTareas.modelo.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaRepositorio extends JpaRepository<Tarea,Integer>
{
}
