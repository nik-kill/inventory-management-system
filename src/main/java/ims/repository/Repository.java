package ims.repository;

import ims.domain.Product;
import ims.domain.StockItem;
import ims.domain.StockMovement;
import ims.domain.Warehouse;

import java.util.List;

public interface Repository {
    // product
    void save(Product product);
    Product findByID(String id);
    boolean existsById(String id);
    List<Product> findAll();

    //warehouse
    void save(Warehouse warehouse);
    Warehouse findByCode(String code);
    boolean existsByCode(String code);
    List<Warehouse> listAllWarehouses();

    //stock
    StockItem find(String id, String warehouseCode);
    void save(StockItem item);
    List<StockItem> findById(String Id);

    //Movement
    void record(StockMovement movement);
    List<StockMovement> findMovement(String id,String warehouseCode);

    //Alert
    void recordLowStockAudit(String pid, String wCode, int currentQty, int reorderThreshold);
    List<String> getLowStockAudit();

}
