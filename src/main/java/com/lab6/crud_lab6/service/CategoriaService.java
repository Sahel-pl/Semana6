package com.lab6.crud_lab6.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lab6.crud_lab6.model.Categoria;
import com.lab6.crud_lab6.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerCategorias() {
        return categoriaRepository.findAll();
    }

    public void registrarCategoria(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    public Optional<Categoria> buscarCategoria(Long id) {
        return categoriaRepository.findById(id);
    }

    public void editarCategoria(Categoria categoria) {
        categoriaRepository.findById(categoria.getId()).ifPresent(categoriaExistente -> {
            categoriaExistente.setTalla(categoria.getTalla());
            categoriaExistente.setTipoTela(categoria.getTipoTela());
            categoriaExistente.setColor(categoria.getColor());
            categoriaExistente.setEstilo(categoria.getEstilo());
            categoriaRepository.save(categoriaExistente);
        });
    }

    public void eliminarCategoria(Long id) {
        categoriaRepository.findById(id).ifPresent(categoria -> {
            categoriaRepository.delete(categoria);
        });
    }
}
