package ar.unrn.tp.services;

import ar.unrn.tp.api.BrandService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.dto.BrandDTO;
import ar.unrn.tp.domain.dto.CategoryDTO;
import ar.unrn.tp.domain.models.BrandEntity;
import ar.unrn.tp.domain.models.CategoryEntity;
import ar.unrn.tp.exceptions.BrandException;
import ar.unrn.tp.exceptions.CategoryException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private TransactionService transactionService;

    @Override
    public List<BrandDTO> listarMarcas() {
        List<BrandEntity> brands = new ArrayList<>();

        try {

            this.transactionService.executeInTransaction(em -> {
                String queryGetProducts = "SELECT b FROM BrandEntity b";
                TypedQuery<BrandEntity> query = em.createQuery(queryGetProducts, BrandEntity.class);

                brands.addAll(query.getResultList());

            });

            if (brands.isEmpty())
                throw new BrandException("No hay marcas cargadas");

            return brands.stream()
                    .map(BrandDTO::fromDomain)
                    .collect(Collectors.toList());

        } catch (Exception e){
            throw new BrandException("Error al querer recuperar la lista de marcas." + e.getMessage());
        }
    }
}
