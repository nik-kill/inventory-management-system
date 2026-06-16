package ims.domain;

import java.time.Instant;

public class StockMovement {
    private final MovementType type;
    private final String pid;
    private final String fromWarehouse;
    private final String toWarehouse;
    private final int quantity;
    private final Instant timestamp;


    public StockMovement(MovementType type, String pid, String fromWarehouse, String toWarehouse, int quantity, Instant timestamp) {
        this.type = type;
        this.pid = pid;
        this.fromWarehouse = fromWarehouse;
        this.toWarehouse = toWarehouse;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public static StockMovement in(String pid, String warehouse, int qty){
        return new StockMovement(MovementType.IN, pid, null, warehouse, qty, Instant.now());
    }

    public static StockMovement out(String pid, String warehouse, int qty){
        return new StockMovement(MovementType.OUT, pid, warehouse, null, qty, Instant.now());
    }

    public static StockMovement transfer(String pid, String from, String to, int qty){
        return new StockMovement(MovementType.TRANSFER, pid, from, to, qty, Instant.now());
    }

    public MovementType getType() {
        return type;
    }

    public String getPid() {
        return pid;
    }

    public String getFromWarehouse() {
        return fromWarehouse;
    }

    public String getToWarehouse() {
        return toWarehouse;
    }

    public int getQuantity() {
        return quantity;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

}
