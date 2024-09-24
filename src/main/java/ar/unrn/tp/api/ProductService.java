package ar.unrn.tp.api;

import ar.unrn.tp.domain.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    //validar que sea una categoría existente y que codigo no se repita
    void crearProducto(String codigo, String descripcion, double precio, Long IdCategoria, Long IdBrand);

    //validar que sea un producto existente
    void modificarProducto(Long idProducto, String descripcion, Long IdCategoria, Long IdBrand, double price);

    //Devuelve todos los productos
    List<ProductDTO> listarProductos();

}
