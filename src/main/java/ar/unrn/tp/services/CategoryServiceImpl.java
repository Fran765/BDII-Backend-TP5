package ar.unrn.tp.services;

import ar.unrn.tp.api.CategoryService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.dto.CategoryDTO;
import ar.unrn.tp.domain.models.CategoryEntity;
import ar.unrn.tp.exceptions.CategoryException;
import ar.unrn.tp.exceptions.ProductException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private TransactionService transactionService;

    @Override
    public List<CategoryDTO> listarCategorias() {
        List<CategoryEntity> categories = new ArrayList<>();

        try {

            this.transactionService.executeInTransaction(em -> {
                String queryGetProducts = "SELECT c FROM CategoryEntity c";
                TypedQuery<CategoryEntity> query = em.createQuery(queryGetProducts, CategoryEntity.class);

                categories.addAll(query.getResultList());

            });

            if (categories.isEmpty())
                throw new CategoryException("No hay categorias cargados");

            return categories.stream()
                    .map(CategoryDTO::fromDomain)
                    .collect(Collectors.toList());

        } catch (Exception e){
            throw new CategoryException("Eror al querer recuperar la lista de categorias." + e.getMessage());
        }
    }
}
