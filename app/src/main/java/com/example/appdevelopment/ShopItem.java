package com.example.appdevelopment;

import java.io.Serializable;
import java.util.HashMap;

public class ShopItem implements Serializable{
    private String ShopListTitle, shopitem, checked, userID;

    public ShopItem(){}

    public ShopItem(String ShopListTitle, String shopitem, String checked, String userID){
        this.ShopListTitle = ShopListTitle;
        this.shopitem = shopitem;
        this.checked = checked;
        this.userID = userID;
    }
    public String getShopListTitle() {
        return ShopListTitle;
    }
    public void setShopListTitle(String ShopListTitle) {
        this.ShopListTitle = ShopListTitle;
    }
    public String getShopitem() {
        return shopitem;
    }
    public void setShopitem(String shopitem) {
        this.shopitem = shopitem;
    }
    public String getChecked() {
        return checked;
    }
    public void setChecked(String checked) {
        this.checked = checked;
    }
    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> shoppingItems =  new HashMap<String,String>();
        shoppingItems.put("checked", checked);
        shoppingItems.put("shopitem", shopitem);
        return shoppingItems;
    }
}
