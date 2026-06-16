package ims.domain;

import java.util.Objects;

public final class Product {
    private final String id;
    private final String name;
    private final int reorderThreshold;

    public Product(String id, String name, int reorderThreshold) {

        if(id==null || id.isBlank()) throw new IllegalArgumentException("Id can't be blank");
        if(name==null || name.isBlank()) throw new IllegalArgumentException("Product name can't be blank");
        if(reorderThreshold < 0) throw new IllegalArgumentException("Product name can't be blank");

        this.id = id;
        this.name = name;
        this.reorderThreshold = reorderThreshold;
    }

    public int getReorderThreshold() {
        return reorderThreshold;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Product)) return false;
        Product p = (Product) o;
        return id.equals(p.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
