package com.sosa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sosa.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

}
