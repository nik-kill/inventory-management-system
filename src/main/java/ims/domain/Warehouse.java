package ims.domain;

import java.util.Objects;

public class Warehouse {
    private final String code;
    private final String name;

    public Warehouse(String code, String name){
        if(code == null || code.isBlank()) throw new IllegalArgumentException("Warehouse code mandatory");
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Warehouse code mandatory");

        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Warehouse)) return false;
        Warehouse w = (Warehouse) o;
        return code.equals(w.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }


}
