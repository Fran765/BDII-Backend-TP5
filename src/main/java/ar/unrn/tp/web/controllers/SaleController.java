package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.SaleService;
import ar.unrn.tp.domain.dto.CreditCardDTO;
import ar.unrn.tp.domain.dto.ProductDTO;
import ar.unrn.tp.domain.dto.ProductRequestIds;
import ar.unrn.tp.domain.dto.SaleDTO;
import ar.unrn.tp.web.contracts.SaleContract;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class SaleController implements SaleContract {

    private final SaleService saleService;

    @Override
    public ResponseEntity<Void> realizarVenta(Long idCliente, Long idTarjeta, ProductRequestIds productos) {

        this.saleService.realizarVenta(idCliente, productos.getIdProducts(), idTarjeta);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<Double> calcularMonto(Long idTarjeta, ProductRequestIds productos) {

        double response = this.saleService.calcularMonto(productos.getIdProducts(), idTarjeta);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<SaleDTO>> ventas() {
        List<SaleDTO> sales = this.saleService.ventas();

        return ResponseEntity.ok(sales);
    }
}
