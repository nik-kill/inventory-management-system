package ims.controller;

import ims.ImsService.*;
import ims.domain.Product;
import ims.domain.StockItem;
import ims.domain.StockMovement;
import ims.domain.Warehouse;
import ims.domain.payloads.RegisterProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/ims")
public class ImsController {

    @Autowired
    public ImsService imsService;

    @GetMapping("/test")
    public ResponseEntity<String> getTest() {

        return ResponseEntity.ok("Testing 1 2 3 ...");
    }

    @PostMapping("/registerProduct")
    public ResponseEntity<Product> registerProduct(@RequestBody Product product){
        Product p = imsService.registerProduct(product.getId(), product.getName(), product.getReorderThreshold());
        System.out.println("Product saved" + p);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/listAllProducts")
    public ResponseEntity<List<Product>> listAllProducts(){
        List<Product> p = imsService.listAllProducts();
        System.out.println("Product Types" + p.toString());
        return ResponseEntity.ok(p);
    }

    @PostMapping("/registerWarehouse")
    public ResponseEntity<Warehouse> registerWarehouse(@RequestBody Warehouse w){
        Warehouse warehouse = imsService.registerWarehouse(w.getCode(), w.getName());
        System.out.println("Warehouse saved" + warehouse);
        return ResponseEntity.ok(warehouse);
    }

    @GetMapping("/listAllWarehouses")
    public ResponseEntity<List<Warehouse>> listAllWarehouses(){
        List<Warehouse> w = imsService.listAllWarehouses();
        System.out.println("Warehouses " + w.toString());
        return ResponseEntity.ok(w);
    }

    @PostMapping("/stockIn")
    public ResponseEntity<StockItem> stockIn(@RequestBody StockItem item){
        StockItem si = imsService.stockIn(item.getId(), item.getWarehouseCode(), item.getQuantity());
        System.out.println("StockItem saved" + si);
        return  ResponseEntity.ok(si);
    }

    @PostMapping("/stockOut")
    public ResponseEntity<StockItem> stockOut(@RequestBody StockItem item){
        StockItem si = imsService.stockOut(item.getId(), item.getWarehouseCode(), item.getQuantity());
        System.out.println("StockItem saved" + si);
        return  ResponseEntity.ok(si);
    }

    @PostMapping("/transfer")
    public ResponseEntity<List<StockItem>> transfer(String pid, String fromWCode, String toWCode, int qty){
        List<StockItem> si = imsService.transfer(pid,fromWCode,toWCode,qty);
        System.out.println("StockItem saved" + si);
        return  ResponseEntity.ok(si);

    }

    @PostMapping("/addConsoleListener")
    public ResponseEntity<Set<StockAlert>> addConsoleStockAlertListener(){
        return ResponseEntity.ok(imsService.addConsoleStockAlertListener());
    }

    @GetMapping("/alertAudit")
    public ResponseEntity<List<String>> getLowStockAudit(){
        return ResponseEntity.ok(imsService.getLowStockAudit());
    }

    //reorder
    @PostMapping("/setReorderPolicy")
    public ResponseEntity<ReorderPolicy> setReorderPolicy(int policy, int reorderQty){
        return ResponseEntity.ok(imsService.setReorderPolicy(policy, reorderQty));
    }

    @PostMapping("/reorder")
    public ResponseEntity<List<StockItem>> reorder(String pid){
        return ResponseEntity.ok(imsService.reorder(pid));
    }

}
