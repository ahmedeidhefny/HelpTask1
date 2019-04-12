package com.hardtask.eid.ahmed.souqtask;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {

    private int Id ;
    private String TitleEN ;
    private String TitleAR ;
    private String Photo ;
    private int ProductCount ;
    private int HaveModel ;
    private List<Category> SubCategories = null ;

    public Category(){}

    public Category(int id, String titleEN, String titleAR, String photo, int productCount, int haveModel, List<Category> subCategories) {
        Id = id;
        TitleEN = titleEN;
        TitleAR = titleAR;
        Photo = photo;
        ProductCount = productCount;
        HaveModel = haveModel;
        this.SubCategories = subCategories;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public String getTitleEN() {
        return TitleEN;
    }

    public String getTitleAR() {
        return TitleAR;
    }

    public String getPhoto() {
        return Photo;
    }

    public int getProductCount() {
        return ProductCount;
    }

    public int getHaveModel() {
        return HaveModel;
    }

    public List<Category> getSubCategories() {
        return SubCategories;
    }
}
