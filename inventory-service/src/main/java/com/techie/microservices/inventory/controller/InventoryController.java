package com.techie.microservices.inventory.controller;

import com.techie.microservices.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public boolean inStock(@RequestParam String skuCode, @RequestParam Integer quantity){
        log.info("Inventory controller "+skuCode+" "+quantity);
        return inventoryService.inStock(skuCode, quantity);
    }

}
