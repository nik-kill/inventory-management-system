package ims.domain.payloads;

public class RegisterProduct {
    String pid;
    String pname;
    int reorderThreshold;

    public String getPid(){
        return pid;
    }

    public String getPname(){
        return pname;
    }

    public int getReorderThreshold(){
        return reorderThreshold;
    }
}
