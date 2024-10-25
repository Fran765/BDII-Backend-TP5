package ar.unrn.tp.web.controllers;

import ar.unrn.tp.api.SaleService;
import ar.unrn.tp.domain.dto.ProductRequestIds;
import ar.unrn.tp.domain.dto.SaleDTO;
import ar.unrn.tp.exceptions.ApplicationException;
import ar.unrn.tp.exceptions.CardException;
import ar.unrn.tp.exceptions.SaleException;
import ar.unrn.tp.web.contracts.SaleContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/ventas")
public class    SaleController implements SaleContract {

    @Autowired
    private SaleService saleService;

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

        List sales = this.saleService.ventas();

        return ResponseEntity.ok(sales);
    }
}
