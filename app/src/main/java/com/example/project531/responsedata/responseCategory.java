package com.example.project531.responsedata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class responseCategory {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("invenCode")
    @Expose
    private String invenCode;
    @SerializedName("invenType")
    @Expose
    private Object invenType;
    @SerializedName("raw_meterial")
    @Expose
    private String raw_meterial;

    @SerializedName("img_path")
    @Expose
    private String img_path;
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
    public String getInvenCode() { return invenCode; }
    public void setInvenCode(String value) { this.invenCode = value; }
    public Object getInvenType() { return invenType; }
    public void setInvenType(String value) { this.invenType = value; }
    public String getRaw_meterial() { return raw_meterial; }
    public void setRaw_meterial(String value) { this.raw_meterial = value; }
    public String getImg_path() { return img_path; }
    public void getImg_path(String value) { this.img_path = value; }


}
