import java.util.*;
import java.io.*;

class Item {
    int ID;
    String name;
    String catagory;
    String type;
    int qtyAvailable;
    int stockQty;
    String location;
    String storageType;
    String gridLocation;
    int barcode;

    Item(int ID, String name, String catagory, String type, int qtyAvailable, int stockQty, String location, String storageType, String gridLocation, int barcode){
        if(ID != 0){
            this.ID = ID;
        }else{
            this.ID = Csv.read("items").length;
        }
        this.name = name;
        this.catagory = catagory;
        this.type = type; 
        this.qtyAvailable = qtyAvailable;
        this.stockQty = stockQty;
        this.location = location;
        this.storageType = storageType;
        this.gridLocation = gridLocation;
        this.barcode = barcode;
        Log.log("created item \"" + name + "\"");
    }

    void changeValue(int value, String newValue){
        switch(value){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            default:
        }
    }
    
    int getID(){
        return ID;
    }
    String getName(){
        return name;
    }
    String getCatagory(){
        return catagory;
    }
    String getType(){
        return type;
    }
    int getQty(){
        return qtyAvailable;
    }
    int getStockQty(){
        return stockQty;
    }
    String getLocation(){
        return location;
    }
    String getStorageType(){
        return storageType;
    }
    String getGrid(){
        return gridLocation;
    }
    int getBarcode(){
        return barcode;
    }
}

