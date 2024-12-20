package com.lab6.crud_lab6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lab6.crud_lab6.model.Categoria;
 

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
