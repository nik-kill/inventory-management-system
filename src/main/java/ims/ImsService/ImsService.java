package ims.ImsService;

import ims.domain.Product;
import ims.domain.StockItem;
import ims.domain.StockMovement;
import ims.domain.Warehouse;
import ims.repository.Repository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ImsService {

    private final Set<StockAlert> listeners = new HashSet<>();
    private ReorderPolicy reorderPolicy = new ThresholdReorderPolicy();

    private final Repository repository;
    public ImsService(Repository repository){
        this.repository = repository;
    }

    public Product registerProduct(String pid, String pname, int reorderThreshold){
        if(repository.existsById(pid)) throw new RuntimeException("Product already exists");
        Product product = new Product(pid, pname, reorderThreshold);
        repository.save(product);
        System.out.println("Product saved" + product);
        return product;
    }

    public List<Product> listAllProducts(){
        return repository.findAll();
    }

    public Warehouse registerWarehouse(String wid, String wname){
        if(repository.existsByCode(wid)) throw new RuntimeException("Warehouse already exists");
        Warehouse warehouse = new Warehouse(wid, wname);
        repository.save(warehouse);
        System.out.println("Warehouse saved" + warehouse);
        return warehouse;
    }

    public List<Warehouse> listAllWarehouses(){
        return repository.listAllWarehouses();
    }

    public StockItem stockIn(String pid, String wCode, int qty){

        StockItem item = repository.find(pid, wCode);
        if(item==null) item = new StockItem(pid, wCode, 0);
        item.increase(qty);
        repository.save(item);
        repository.record(StockMovement.in(pid,wCode,qty));
        return item;
    }

    public StockItem stockOut(String pid, String wCode, int qty){

        StockItem item = repository.find(pid, wCode);
        if(item==null) throw new RuntimeException("No stock for pid " + pid + " at warehouse " +  wCode);
        item.decrease(qty);
        repository.save(item);
        repository.record(StockMovement.out(pid,wCode,qty));

        checkLowStock(pid, wCode, item.getQuantity());
        return item;
    }

    public List<StockItem> transfer(String pid, String fromWCode, String toWCode, int qty){

        StockItem item = repository.find(pid, fromWCode);
        if(item==null) throw new RuntimeException("No stock for pid " + pid + " at warehouse " +  fromWCode);

        StockItem dest = repository.find(pid, toWCode);
        if(dest==null) dest = new StockItem(pid, toWCode, 0);

        item.decrease(qty);
        item.increase(qty);
        repository.save(item);
        repository.save(dest);
        repository.record(StockMovement.transfer(pid,fromWCode, toWCode,qty));

        checkLowStock(pid, fromWCode, item.getQuantity());
        return Stream.of(item, dest)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Set<StockAlert> addConsoleStockAlertListener(){
        StockAlert listener = new ConsoleStockAlert();
        addListener(listener);
        return listeners;
    }

    public List<String> getLowStockAudit(){
        return repository.getLowStockAudit();
    }

    public ReorderPolicy setReorderPolicy(int policy, int reorderQty){
        if(policy==1){
            reorderPolicy = new FixedReorderPolicy(reorderQty);
        }
        else{
            reorderPolicy = new ThresholdReorderPolicy();
        }
        return reorderPolicy;
    }

    public List<StockItem> reorder(String pid){
        List<StockItem> productsReordered= new ArrayList<>();
        for(String lowStock : repository.getLowStockAudit()){
            if(lowStock.startsWith(pid)){
                //find Products by pid
                Product p = repository.findByID(pid);
                StockItem st = repository.find(pid, "w1");
                int qty = reorderPolicy.reorderQuantity(p, st.getQuantity());
                // get quantity to get based on reorder policy
                // update quantity
                stockIn(pid, "w1", qty);
                productsReordered.add(st);
            }
        }
        return productsReordered;
    }

//    ------------------------------------------------------------------------------------------------------------
    private void addListener(StockAlert listener) {
        listeners.add(listener);
    }

    private void removeListener(StockAlert listener) {
        listeners.remove(listener);
    }


    private void checkLowStock(String pid, String wCode, int currentQty){
        Product p = repository.findByID(pid);
        if(p==null) throw new RuntimeException("product not found");
        if(currentQty < p.getReorderThreshold()){
            repository.recordLowStockAudit(pid, wCode, currentQty, p.getReorderThreshold());
            for(StockAlert listener : listeners){
                listener.onLowStock(p, wCode, currentQty);
            }
        }
    }

}
