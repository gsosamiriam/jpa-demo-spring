package com.sosa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.sosa.model.Categoria;

//public interface CategoriasRepository extends CrudRepository<Categoria, Integer> 

public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {

}
