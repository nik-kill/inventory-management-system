package ims.ImsService;

import ims.domain.Product;

public class ConsoleStockAlert implements StockAlert {

    public String getName() {
        return "ConsoleStockAlert";
    }

    @Override
    public void onLowStock(Product product, String warehouseCode, int currentQty) {
        System.out.println(String.format("LOW STOCK ALERT %s (%s) at warehouse %s is down to %d : reorder threshold %d",
                product.getName(), product.getId(), warehouseCode, currentQty, product.getReorderThreshold()));
    }
}
