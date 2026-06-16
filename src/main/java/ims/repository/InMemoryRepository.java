package ims.repository;

import ims.domain.Product;
import ims.domain.StockItem;
import ims.domain.StockMovement;
import ims.domain.Warehouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Repository
public class InMemoryRepository implements Repository{

    //product
    private final Map<String, Product> byId = new HashMap<>();

    @Override
    public void save(Product product) {
        byId.put(product.getId(), product);
    }

    @Override
    public Product findByID(String id) {

        return byId.get(id);
    }

    @Override
    public boolean existsById(String id) {
        return byId.containsKey(id);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(byId.values());
    }

    //warehouse
    private final Map<String, Warehouse> byCode = new HashMap<>();

    @Override
    public void save(Warehouse warehouse) {
        byCode.put(warehouse.getCode(), warehouse);
    }

    @Override
    public Warehouse findByCode(String code) {
        return byCode.get(code);
    }

    @Override
    public boolean existsByCode(String code) {
        return byCode.containsKey(code);
    }

    @Override
    public List<Warehouse> listAllWarehouses(){
        return new ArrayList<>(byCode.values());
    }

    //stockRepo
    private final Map<String, StockItem> items = new HashMap<>();

    private String key(String id, String wh){
        return id+"|"+wh;
    }

    @Override
    public StockItem find(String id, String warehouseCode) {
        return items.get(key(id,warehouseCode));
    }

    @Override
    public void save(StockItem item) {
        items.put(key(item.getId(),item.getWarehouseCode()), item);
    }

    @Override
    public List<StockItem> findById(String id) {
        List<StockItem> result = new ArrayList<>();
        for(StockItem it : items.values())
            if(it.getId().equals(id)) result.add(it);
        return result;
    }

    //Movement

    private final List<StockMovement> log = new ArrayList<>();

    @Override
    public void record(StockMovement movement) {
        log.add(movement);
    }

    @Override
    public List<StockMovement> findMovement(String id, String warehouseCode) {
        List<StockMovement> res = new ArrayList<>();
        for(StockMovement m : log){
            boolean idPresent = id == null || id.equals(m.getPid());
            boolean whPresent = warehouseCode == null || warehouseCode.equals(m.getFromWarehouse()) || warehouseCode.equals(m.getToWarehouse());

            if(idPresent && whPresent) res.add(m);
        }
        return res;
    }

    //Audit & Alert
    private final List<String> lowStockAuditQueue = new ArrayList<>();

    @Override
    public void recordLowStockAudit(String pid, String wCode, int currentQty, int reorderThreshold){
        lowStockAuditQueue.add(pid +"|"+ wCode +"|"+ wCode +"|"+ currentQty +"|"+ reorderThreshold);
    }

    @Override
    public List<String> getLowStockAudit(){
        return lowStockAuditQueue;
    }
}
