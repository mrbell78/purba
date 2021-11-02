package com.example.project531.responsedata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
public class responseProduct implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("productType")
    @Expose
    private String productType;
    @SerializedName("productName")
    @Expose
    private Object productName;
    @SerializedName("pro_name")
    @Expose
    private String pro_name;
    @SerializedName("part_no")
    @Expose
    private String part_no;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("totalStock")
    @Expose
    private String totalStock;
    @SerializedName("unit")
    @Expose
    private Object unit;
    @SerializedName("reorder")
    @Expose
    private Object reorder;
    @SerializedName("unitPrice")
    @Expose
    private Object unitPrice;
    @SerializedName("salePrice")
    @Expose
    private int salePrice;
    @SerializedName("dept")
    @Expose
    private String dept;
    @SerializedName("imageFile")
    @Expose
    private String imageFile;
    @SerializedName("postDate")
    @Expose
    private String postDate;
    private int numberInCart;
    public int getNumberInCart() {
        return numberInCart;
    }
    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
    public String getId() { return id; }
    public void setId(String value) { this.id = value; }
    public String getProductType() { return productType; }
    public void setProductType(String value) { this.productType = value; }
    public Object getProductName() { return productName; }
    public void setProductName(String value) { this.productName = value; }
    public String getPro_name() { return pro_name; }
    public void setPro_name(String value) { this.pro_name = value; }
    public String getPart_no() { return part_no; }
    public void setPart_no(String value) { this.part_no = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }
    public String getTotalStock() { return totalStock; }
    public void setTotalStock(String value) { this.totalStock = value; }
    public Object getUnit() { return unit; }
    public void setUnit(Object value) { this.unit = value; }
    public Object getReorder() { return reorder; }
    public void setReorder(Object value) { this.reorder = value; }
    public Object getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Object value) { this.unitPrice = value; }
    public int getSalePrice() { return salePrice; }
    public void setSalePrice(int value) { this.salePrice = value; }
    public String getDept() { return dept; }
    public void setDept(String value) { this.dept = value; }
    public String getImageFile() { return imageFile; }
    public void setImageFile(String value) { this.imageFile = value; }
    public String getStatus() { return postDate; }
    public void setStatus(String value) { this.postDate = value; }
    public int getFee() {
        return (int) unitPrice;
    }



}
