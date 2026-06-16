package ims.domain;

public class StockItem {
    private final String id;
    private final String warehouseCode;
    private int quantity;


    public StockItem(String id, String warehouseCode, int initialQty) {
        if(initialQty < 0){
            throw new IllegalArgumentException("Illegal qty <0");
        }
        this.id = id;
        this.warehouseCode = warehouseCode;
        this.quantity = initialQty;
    }

    public String getId() {
        return id;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increase(int cnt){
        if(cnt <= 0){
            throw new IllegalArgumentException("cnt cant be <0");
        }
        this.quantity += cnt;
    }

    public void decrease(int cnt){
        if(cnt <= 0){
            throw new IllegalArgumentException("cnt cant be <0");
        }
        if(cnt > quantity){
            throw new IllegalArgumentException("insuffecient quantity");
        }
        this.quantity -= cnt;
    }
}
