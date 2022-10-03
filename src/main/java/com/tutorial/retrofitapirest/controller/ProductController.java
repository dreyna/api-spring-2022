package com.tutorial.retrofitapirest.controller;

import com.tutorial.retrofitapirest.dto.ProductDTO;
import com.tutorial.retrofitapirest.model.Producto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {

    private List<Producto> productos = new ArrayList<>(Arrays.asList(
            new Producto(1, "producto1", 100),
            new Producto(20, "producto2", 200),
            new Producto(23, "producto3", 300),
            new Producto(4, "producto4", 400)
    ));

    @GetMapping
    public ResponseEntity<List<Producto>> getAll() {
        return ResponseEntity.ok(productos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Producto> getOne(@PathVariable("id") int id) {
        Producto producto = findById(id);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<Producto> create(@RequestBody ProductDTO dto) {
        int index = productos.isEmpty()? 1 : getLastIndex() + 1;
        Producto product = Producto.builder().id(index).name(dto.getName()).price(dto.getPrice()).build();
        productos.add(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping("{id}")
    public ResponseEntity<Producto> update(@PathVariable("id") int id, @RequestBody ProductDTO dto) {
        Producto product = findById(id);
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Producto> delete(@PathVariable("id") int id) {
        Producto product = findById(id);
        productos.remove(product);
        return ResponseEntity.ok(product);
    }

    private int getLastIndex() {
        return productos.stream().max(Comparator.comparing(Producto::getId)).get().getId();
    }

    private Producto findById(int id) {
        return productos.stream().filter(p -> p.getId() == id).findAny().orElse(null);
    }
}
