package dev.swaleha.bookmysalonappointment.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "shop")
public class Shop {

    @Id
    private String id;

    @Field("shopName")
    private String shopName;

    @Field("gstNo")
    private String gstNo;

    public Shop() {}

    public Shop(String shopName, String gstNo) {
        this.shopName = shopName;
        this.gstNo = gstNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGstNo() {
        return gstNo;
    }

    public void setGstNo(String gstNo) {
        this.gstNo = gstNo;
    }

    @Override
    public String toString() {
        return "Shop{id='" + id + "', shopName='" + shopName + "', gstNo='" + gstNo + "'}";
    }
}
