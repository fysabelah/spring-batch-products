package com.spring.batch.api.products.frameworks.web;

import com.spring.batch.api.products.interfaceadapters.controllers.ProductController;
import com.spring.batch.api.products.interfaceadapters.presenters.dto.ProductDto;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.Genre;
import com.spring.batch.api.products.utils.enums.ProductSize;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import com.spring.batch.api.products.utils.pagination.PagedResponse;
import com.spring.batch.api.products.utils.pagination.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/v1/product")
@Tag(name = "Produtos", description = "Métodos para operações com produtos")
public class ProductWeb {

    private final ProductController controller;

    public ProductWeb(ProductController controller) {
        this.controller = controller;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @Operation(summary = "Cadastrar produto")
    public ResponseEntity<ProductDto> insert(@RequestBody @Valid ProductDto body) throws BusinessException {
        return ResponseEntity.ok(controller.insert(body));
    }

    @GetMapping(produces = "application/json", path = "/sku/{sku}")
    @Operation(summary = "Recuperar produto por sku")
    public ResponseEntity<ProductDto> findBySku(@PathVariable String sku) {
        return ResponseEntity.ok(controller.findBySku(sku));
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Mudar o status do produto")
    public ResponseEntity<Void> changeStatus(@PathVariable String id,
                                             @RequestParam boolean status) {
        controller.updateStatus(id, status);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/stock/{sku}")
    @Operation(summary = "Atualizar quantidade disponível")
    public ResponseEntity<Void> updateQuantityAvailable(@RequestParam @PositiveOrZero(message = "AVAILABLE_QUANTITY_DO_NOT_SHOULD_BE_NEGATIVE") Integer quantity,
                                                        @RequestParam LocalDateTime updatedAt,
                                                        @PathVariable String sku,
                                                        @RequestParam @PositiveOrZero(message = "PROTECTION_DO_NOT_SHOULD_BE_NEGATIVE") Integer protection) throws BusinessException {
        controller.changeQuantity(sku, quantity, updatedAt, protection);

        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/update/{id}")
    @Operation(summary = "Atualizar informações de preço")
    public ResponseEntity<ProductDto> update(@PathVariable String id,
                                             @RequestBody @PositiveOrZero BigDecimal value) throws BusinessException {
        return ResponseEntity.ok(controller.updateValue(id, value));
    }

    @DeleteMapping(value = "/delete/{id}")
    @Operation(summary = "Deletar produto")
    public ResponseEntity<ProductDto> delete(@PathVariable String id) {
        controller.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/books")
    @Operation(summary = "Recuperar produtos do tipo livro")
    public ResponseEntity<PagedResponse> findAll(@RequestParam(required = false) String title,
                                                 @RequestParam(required = false) Genre genre,
                                                 @RequestParam boolean status,
                                                 @Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
                                                 @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage) {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(controller.findAllBooks(title, genre, status, page));
    }

    @GetMapping(value = "/electronics")
    @Operation(summary = "Recuperar produtos do tipo eletrônicos")
    public ResponseEntity<PagedResponse> findAll(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String brand,
                                                 @RequestParam(required = false) String model,
                                                 @RequestParam(required = false) ElectronicType electronicType,
                                                 @RequestParam boolean status,
                                                 @Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
                                                 @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage) {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(controller.findAllElectronics(name, brand, model, electronicType, status, page));
    }

    @GetMapping(value = "/clothes")
    @Operation(summary = "Recuperar produtos do tipo roupas")
    public ResponseEntity<PagedResponse> findAll(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String brand,
                                                 @RequestParam(required = false) String model,
                                                 @RequestParam(required = false) ProductSize size,
                                                 @RequestParam boolean status,
                                                 @Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
                                                 @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage) {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(controller.findAllClothes(name, brand, model, size, status, page));
    }

    @GetMapping(value = "/shoes")
    @Operation(summary = "Recuperar produtos do tipo roupas")
    public ResponseEntity<PagedResponse> findAll(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String brand,
                                                 @RequestParam(required = false) @Pattern(regexp = "\\d{2}") String size,
                                                 @RequestParam boolean status,
                                                 @Parameter(description = "Default value 10. Max value 1000", example = "10") @RequestParam(required = false) Integer pageSize,
                                                 @Parameter(description = "Default value 0", example = "0") @RequestParam(required = false) Integer initialPage) {
        Pagination page = new Pagination(initialPage, pageSize);

        return ResponseEntity.ok(controller.findAllShoes(name, brand, size, status, page));
    }
}
