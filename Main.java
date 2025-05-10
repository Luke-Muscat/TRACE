import java.util.ArrayList;
import java.util.Collections;

class Main {
    static ArrayList<Item> items = new ArrayList<>();
    static boolean toQuit = false;
    final static String[] CATEGORIES = {"General hardware", "fasteners", "mechanical", "electro-mechanical", "electonic", "electrical", "board/modules", "3dPrinting", "development", "chemicals/consumables", "tools", "custom parts", "other"};

    public static void main(String[] args){// main method
        Log.start();
        initializeItems();
        do{
            mainMenu();
        }while(toQuit == false);
    }

    private static void initializeItems(){ // runs to populate 'items' arraylist with data in CSV file
        items.clear();
        Log.log("initializing items");
        String[] file = Csv.read("items");
        for(int i = 0; i < file.length; i++){
            String[] content = file[i].split(",");
            /*
             * content[0] => ID
             * content[1] => name
             * content[2] => Catagory
             * content[3] => type
             * content[4] => qtyAvailable
             * content[5] => StockQty
             * content[6] => location
             * content[7] => storageType
             * content[8] => gridLocation
             * content[9] => barcode
             */
            items.add(new Item(Integer.parseInt(content[0]), content[1], content[2], content[3], Integer.parseInt(content[4]), Integer.parseInt(content[5]), content[6], content[7], content[8], Integer.parseInt(content[9])));
        }
        Log.log("items initialized");
    }

    private static void mainMenu(){ //display main menu and run necessary methods
        Log.log("main menu");
        System.out.print("\f");
        System.out.println(" " + items.get(1).getCatagory());
        System.out.println("T.R.A.C.E");
        System.out.println("---------");
        System.out.println("1. Take item");
        System.out.println("2. Add item");
        System.out.println("3. List all items");
        System.out.println("4. Search items");
        System.out.println("5. Edit item");
        System.out.println("6. Delete item");
        System.out.println("7. sort items");
        System.out.println("8. Exit");
        System.out.println("\nEnter your selection");

        switch(Keyboard.readByte()){
            case 1:
                takeItem();
                initializeItems();
                break;
            case 2: 
                addItem();
                initializeItems();
                break;
            case 3:
                listItems();
                break;
            case 4: 
                searchItems();
                break;
            case 5:
                editItem();
                initializeItems();
                break;
            case 6:
                deleteItem();
                initializeItems();
                break;
            case 7:
                sortItems();
                break;
            case 8:
                toQuit = true;
                break;
            default:
                System.out.println("Err 002: invalid input");
                break;
        }
    }

    private static void listItems(){// list all items
        System.out.println("\f1. Normal list");// displays only name, location, qtyAvailable and grid location
        System.out.println("2. Detailed list");// displays all info about an item

        switch(Keyboard.readInt()){
            case 1:// normal list
                Log.log("normal list");
                System.out.print("\f");

                for(int i = 0; i < items.size(); i++){
                    System.out.println(items.get(i).getName());
                    System.out.println("\t- Catagory:\t" + items.get(i).getCatagory());
                    System.out.println("\t- Location:\t" + items.get(i).getLocation());
                    System.out.println("\t- Qty available:" + items.get(i).getQty());
                    System.out.println("\t- GridLocation: " + items.get(i).getGrid());
                    System.out.println();
                }

                break;
            case 2:
                Log.log("Detailed list");
                System.out.print("\f");
                for(int i = 0; i < items.size(); i++){
                    System.out.println(items.get(i).getName()); 
                    System.out.println("\t- ID:\t\t" + items.get(i).getID());
                    System.out.println("\t- Catagory:\t" + items.get(i).getCatagory());
                    System.out.println("\t- Type: \t" + items.get(i).getType());
                    System.out.println("\t- Qty available:" + items.get(i).getQty());
                    System.out.println("\t- Stock Qty: \t" + items.get(i).getStockQty());
                    System.out.println("\t- Location:\t" + items.get(i).getLocation());
                    System.out.println("\t- Storage Type: " + items.get(i).getStorageType());
                    System.out.println("\t- GridLocation: " + items.get(i).getGrid());
                    System.out.println("\t- Barcode:\t" + items.get(i).getBarcode());
                    System.out.println();
                }
                break;
            default:
                Log.log("Err 002: invalid input");
                break;
        }
        Keyboard.readString();
    }

    private static void takeItem(){// take items from storage
        Log.log("take item");
        System.out.println("\fScan barcode or enter name");
        String search = Keyboard.readString();

        int ID = 0;
        boolean duplicateFlag = false;
        for(int i = 0; i < items.size(); i++){
            if(Integer.toString(items.get(i).getBarcode()) == search || items.get(i).getName() == search){
                if(ID != 0){
                    ID = i;
                }else{
                    duplicateFlag = true;
                    break;
                }
            }
        }

        if(duplicateFlag == true){
            System.out.println("Err 001: multiple instances of an item detected");
            Log.log("Err001");
        }else{
            System.out.println("How much of \"" + items.get(ID).getName() + "\" would you like to take?");
            int qty = items.get(ID).getQty() - Keyboard.readInt();
            if(qty >= 0){

                String newLine = items.get(ID).getID() + "," + items.get(ID).getName() + "," + items.get(ID).getCatagory() + "," + items.get(ID).getType() + "," + Integer.toString(qty) + "," + items.get(ID).getStockQty() + "," + items.get(ID).getLocation() + "," + items.get(ID).getStorageType() + "," + items.get(ID).getGrid() + "," + items.get(ID).getBarcode();
                Csv.updateLine(ID+2, newLine, "items");
                
                if(qty <= items.get(ID).getStockQty()){
                    System.out.println("Item needs to be restocked");
                }else{
                    System.out.println("Items taken successfully");
                }
                Log.log("took " + qty + " of \"" + items.get(ID).getName() + "\""); 
            }else{
                System.out.println("Not enough stock to take");
                Log.log("Tried to take " + (-1*qty) + " too many of " + items.get(ID).getName());
            }
        }
        Keyboard.readString();
    }

    private static void addItem(){
        Log.log("Add item");
        System.out.println("\f1. Add multiple items");
        System.out.println("2. Add 1 item");
        int nextId = Csv.read("items").length + 1;
        switch(Keyboard.readInt()){
            case 1:
                boolean quitFlag = false;
                do{

                    System.out.print("\fName(Leave blank to quit): ");
                    String name = Keyboard.readString();
                    if(name.isEmpty()){
                        quitFlag = true;

                    }else{
                        System.out.println("Chose a catagory");
                        for(byte i = 0; i < CATEGORIES.length; i++){
                            System.out.println("\t" + (i+1) + ". " + CATEGORIES[i]);
                        }
                        System.out.print("Make a selection:");
                        String category = CATEGORIES[Keyboard.readByte() - 1];

                        System.out.print("\fType:");
                        String type = Keyboard.readString();

                        System.out.print("\fqty available:");
                        int qty = Keyboard.readInt();

                        System.out.print("\fStockQty:");
                        int stockQty = Keyboard.readInt();

                        System.out.print("\fLocation:");
                        String location = Keyboard.readString();

                        System.out.print("\fStorage Type:");
                        String storageType = Keyboard.readString();

                        System.out.print("\fGrid Location:");
                        String gridLocation = Keyboard.readString();

                        System.out.print("\fBarcode");
                        int barcode = Keyboard.readInt();

                        System.out.println("\fName: " + name);
                        System.out.println("Category: " + category);
                        System.out.println("Type: " + type);
                        System.out.println("Qty Available: " + qty);
                        System.out.println("Stock Qty: " + stockQty);
                        System.out.println("Location: " + location);
                        System.out.println("Storage Type: " + storageType);
                        System.out.println("Grid Location: " + gridLocation);
                        System.out.println("Barcode: " + barcode);
                        System.out.println("Add item? (y/n)");

                        String confirmation = Keyboard.readString();

                        if(confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")){
                            String writeLine = Integer.toString(Csv.read("items").length + 1) + "," + name + "," + category + "," + type + "," + Integer.toString(qty) + "," + Integer.toString(stockQty) + "," + location + "," + storageType + "," + gridLocation + "," + barcode;
                            Csv.write(writeLine, "items");
                            Log.log("added line to items: " + writeLine);
                        }else if(confirmation.equalsIgnoreCase("n") || confirmation.equalsIgnoreCase("no")){
                            quitFlag = true;
                        }else{
                            System.out.println("Invalid Input");
                        }

                    }
                }while(quitFlag == false);
                break;
            case 2:
                System.out.print("\fName: ");
                String name = Keyboard.readString();

                System.out.println("Chose a catagory");
                for(byte i = 0; i < CATEGORIES.length; i++){
                    System.out.println("\t" + (i+1) + ". " + CATEGORIES[i]);
                }
                System.out.print("Make a selection:");
                String category = CATEGORIES[Keyboard.readByte() - 1];

                System.out.print("\fType:");
                String type = Keyboard.readString();

                System.out.print("\fqty available:");
                int qty = Keyboard.readInt();

                System.out.print("\fStockQty:");
                int stockQty = Keyboard.readInt();

                System.out.print("\fLocation:");
                String location = Keyboard.readString();

                System.out.print("\fStorage Type:");
                String storageType = Keyboard.readString();

                System.out.print("\fGrid Location:");
                String gridLocation = Keyboard.readString();

                System.out.print("\fBarcode");
                int barcode = Keyboard.readInt();

                System.out.println("\fName: " + name);
                System.out.println("Category: " + category);
                System.out.println("Type: " + type);
                System.out.println("Qty Available: " + qty);
                System.out.println("Stock Qty: " + stockQty);
                System.out.println("Location: " + location);
                System.out.println("Storage Type: " + storageType);
                System.out.println("Grid Location: " + gridLocation);
                System.out.println("Barcode: " + barcode);
                System.out.println("Add item? (y/n)");

                String confirmation = Keyboard.readString();

                if(confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")){
                    String writeLine = Integer.toString(Csv.read("items").length + 1) + "," + name + "," + category + "," + type + "," + Integer.toString(qty) + "," + Integer.toString(stockQty) + "," + location + "," + storageType + "," + gridLocation + "," + barcode;
                    Csv.write(writeLine, "items");
                    Log.log("added line to items: " + writeLine);
                }else if(confirmation.equalsIgnoreCase("n") || confirmation.equalsIgnoreCase("no")){

                }else{
                    System.out.println("Invalid Input");
                }
                break;
        }
    }

    private static void deleteItem(){
        Log.log("deleteItem");
        System.out.println("\fEnter ID or Name of item to delete");
        String input = Keyboard.readString();
        boolean foundFlag = false;

        for(int i = 0; i < items.size(); i++){
            try{
                if(items.get(i).getName().equalsIgnoreCase(input) || items.get(i).getID() == Integer.parseInt(input)){
                    System.out.println("Are you sure you want to delete \"" + items.get(i).getName() + "\" (y/n)");

                    String confirmation = Keyboard.readString();

                    if(confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes")){
                        Csv.removeLine( "items", (items.get(i).getID() + 1));
                        Log.log("removed: " + items.get(i).getName());
                        System.out.println("Item successfully removed");
                    }else if(confirmation.equalsIgnoreCase("n") || confirmation.equalsIgnoreCase("no")){

                    }else{
                        System.out.println("Invalid Input");
                    }
                    foundFlag = true;
                }
            }catch(Exception e){

            }
        }

        if(foundFlag == false){
            System.out.println("item not found");
        }
        Keyboard.readString();
    }

    private static void searchItems(){
        Log.log("Search");
        System.out.println("\fEnter ID or Name of item to search");
        String input = Keyboard.readString();
        boolean foundFlag = false;

        for(int i = 0; i < items.size(); i++){
            try{
                if(items.get(i).getName().equalsIgnoreCase(input) || items.get(i).getID() == Integer.parseInt(input)){
                    System.out.print("\f");
                    System.out.println(items.get(i).getName()); 
                    System.out.println("\t- ID:\t\t" + items.get(i).getID());
                    System.out.println("\t- Catagory:\t" + items.get(i).getCatagory());
                    System.out.println("\t- Type: \t" + items.get(i).getType());
                    System.out.println("\t- Qty available:" + items.get(i).getQty());
                    System.out.println("\t- Stock Qty: \t" + items.get(i).getStockQty());
                    System.out.println("\t- Location:\t" + items.get(i).getLocation());
                    System.out.println("\t- Storage Type: " + items.get(i).getStorageType());
                    System.out.println("\t- GridLocation: " + items.get(i).getGrid());
                    System.out.println("\t- Barcode:\t" + items.get(i).getBarcode());
                    System.out.println();
                    foundFlag = true;
                    Log.log("searched for: " + items.get(i).getName());
                    break;
                }
            }catch(Exception e){

            }
        }

        if(foundFlag == false){
            System.out.println("item not found");
        }
        Keyboard.readString();
    }

    private static void editItem(){
        Log.log("Edit item");
        System.out.println("\fWhat item would you like to edit?");
        String input = Keyboard.readString();
        boolean foundFlag = false;
        boolean invalidSelection = false;

        for(int i = 0; i < items.size(); i++){
            try{
                if(items.get(i).getName().equalsIgnoreCase(input) || items.get(i).getID() == Integer.parseInt(input)){
                    System.out.println("\fwhat would you like to edit?");
                    System.out.println("1. ID");
                    System.out.println("2. Name");
                    System.out.println("3. Category");
                    System.out.println("4. Type");
                    System.out.println("5. Qty Available");
                    System.out.println("6. Stock Qty");
                    System.out.println("7. Location");
                    System.out.println("8. Storage Type");
                    System.out.println("9. Grid Location");
                    System.out.println("10. Barcode");
                    System.out.println("Make you selection");
                    String writeLine = "";
                    switch(Keyboard.readByte()){
                        case 1:
                            System.out.print("\fNew ID: ");
                            writeLine = Keyboard.readInt() + "," + items.get(i).getName() + "," + items.get(i).getCatagory() + "," + items.get(i).getType() + "," + items.get(i).getQty() + "," +
                            items.get(i).getStockQty() + "," + items.get(i).getLocation() + "," + items.get(i).getStorageType() + "," + items.get(i).getGrid() + "," + items.get(i).getBarcode();
                            break;
                        case 2: 
                            System.out.print("\fNew Name: ");
                            writeLine = items.get(i).getID() + "," + Keyboard.readString() + "," + items.get(i).getCatagory() + "," + items.get(i).getType() + "," + items.get(i).getQty() + "," +
                            items.get(i).getStockQty() + "," + items.get(i).getLocation() + "," + items.get(i).getStorageType() + "," + items.get(i).getGrid() + "," + items.get(i).getBarcode();
                            break;
                        case 3:
                            System.out.println("\fNew Category: ");
                            for(byte o = 0; o < CATEGORIES.length; o++){
                                System.out.println("\t" + (o+1) + ". " + CATEGORIES[i]);
                            }
                            System.out.println("make your selection");
                            byte choice = Keyboard.readByte();

                            writeLine = items.get(i).getID() + "," + items.get(i).getName() + "," + CATEGORIES[choice - 1] + "," + items.get(i).getType() + "," + items.get(i).getQty() + "," +
                            items.get(i).getStockQty() + "," + items.get(i).getLocation() + "," + items.get(i).getStorageType() + "," + items.get(i).getGrid() + "," + items.get(i).getBarcode();
                            break;
                        case 4:
                            System.out.print("\fNew Type: ");
                            writeLine = items.get(i).getID() + "," + items.get(i).getName() + "," + items.get(i).getCatagory() + "," + Keyboard.readString() + "," + items.get(i).getQty() + "," +
                            items.get(i).getStockQty() + "," + items.get(i).getLocation() + "," + items.get(i).getStorageType() + "," + items.get(i).getGrid() + "," + items.get(i).getBarcode();
                            break;
                        case 5:
                            System.out.print("\fNew Qty: ");
                            writeLine = items.get(i).getID() + "," + items.get(i).getName() + "," + items.get(i).getCatagory() + "," + items.get(i).getType() + "," + Keyboard.readInt() + "," +
                            items.get(i).getStockQty() + "," + items.get(i).getLocation() + "," + items.get(i).getStorageType() + "," + items.get(i).getGrid() + "," + items.get(i).getBarcode();
                            break;
                        case 6:
                            System.out.print("\fNew Stock Qty: ");
                            writeLine = items.get(i).getID() + "," + items.get(i).getName() + "," + items.get(i).getCatagory() + "," + items.get(i).getType() + "," + items.get(i).getQty() + "," +
                            Keyboard.readInt() + "," + items.get(i).getLocation() + "," + items.get(i).getStorageType() + "," + items.get(i).getGrid() + "," + items.get(i).getBarcode();
                            break;
                        case 7:
                            System.out.print("\fNew Location: ");
                            writeLine = items.get(i).getID() + "," + items.get(i).getName() + "," + items.get(i).getCatagory() + "," + items.get(i).getType() + "," + items.get(i).getQty() + "," +
                            items.get(i).getStockQty() + "," + Keyboard.readString() + "," + items.get(i).getStorageType() + "," + items.get(i).getGrid() + "," + items.get(i).getBarcode();
                            break;
                        case 8:
                            System.out.print("\fNew StorageType: ");
                            writeLine = items.get(i).getID() + "," + items.get(i).getName() + "," + items.get(i).getCatagory() + "," + items.get(i).getType() + "," + items.get(i).getQty() + "," +
                            items.get(i).getStockQty() + "," + items.get(i).getLocation() + "," + Keyboard.readString() + "," + items.get(i).getGrid() + "," + items.get(i).getBarcode();
                            break;
                        case 9:
                            System.out.print("\fNew Grid Location: ");
                            writeLine = items.get(i).getID() + "," + items.get(i).getName() + "," + items.get(i).getCatagory() + "," + items.get(i).getType() + "," + items.get(i).getQty() + "," +
                            items.get(i).getStockQty() + "," + items.get(i).getLocation() + "," + items.get(i).getStorageType() + "," + Keyboard.readString() + "," + items.get(i).getBarcode();
                            break;
                        case 10:
                            System.out.print("\fNew barcode: ");
                            writeLine = items.get(i).getID() + "," + items.get(i).getName() + "," + items.get(i).getCatagory() + "," + items.get(i).getType() + "," + items.get(i).getQty() + "," +
                            items.get(i).getStockQty() + "," + items.get(i).getLocation() + "," + items.get(i).getStorageType() + "," + items.get(i).getGrid() + "," + Keyboard.readString();
                            break;
                        default:
                            System.out.println("Err 002: invalid input");
                            invalidSelection = true;
                            break;
                    }

                    if(invalidSelection == false){
                        Csv.updateLine(items.get(i).getID() + 1, writeLine, "items");
                        Log.log("changed item with " + writeLine);
                        System.out.println("item changed");
                    }

                    foundFlag = true;
                }
            }catch(Exception e){
                
            }
        }

        if(foundFlag == false){
            System.out.println("Err 003: no item with search criteria found");
        }
        Keyboard.readString();
    }

    private static void sortItems(){
        Log.log("sort items");
        ArrayList <Item> sort = items;
        System.out.println("\fSort by: ");
        System.out.println("1. Qty available");
        System.out.println("2. location");
        System.out.println("3. Category");

        boolean correctFlag = false;
        boolean invalidInputFlag = false;

        switch(Keyboard.readInt()){
            case 1:
                do{
                    correctFlag = true;
                    for(int i = 0; i < items.size() - 1; i++){
                        if(sort.get(i).getQty() > sort.get(i+1).getQty()){
                            Collections.swap(sort, i, i+1);
                            correctFlag = false;
                        }
                    }
                }while(!correctFlag);
                break;
            case 2:
                do{
                    correctFlag = true;
                    for(int i = 0; i < items.size() - 1; i++){
                        if(sort.get(i).getLocation().compareTo(sort.get(i+1).getLocation()) > 0){
                            Collections.swap(sort, i, i+1);
                            correctFlag = false;
                        }
                    }
                }while(!correctFlag);
                break;
            case 3:
                do{
                    correctFlag = true;
                    for(int i = 0; i < items.size() - 1; i++){
                        if(sort.get(i).getCatagory().compareTo(sort.get(i+1).getCatagory()) > 0){
                            Collections.swap(sort, i, i+1);
                            correctFlag = false;
                        }
                    }
                }while(!correctFlag);
                break;
            default:
                System.out.println("Err 002: invalid input");
                invalidInputFlag = true;
                break;
        }

        System.out.println("\f");

        if(!invalidInputFlag){
            for(int i = 0; i < items.size(); i++){
                System.out.println(sort.get(i).getName()); 
                System.out.println("\t- ID:\t\t" + sort.get(i).getID());
                System.out.println("\t- Catagory:\t" + sort.get(i).getCatagory());
                System.out.println("\t- Type: \t" + sort.get(i).getType());
                System.out.println("\t- Qty available:" + sort.get(i).getQty());
                System.out.println("\t- Stock Qty: \t" + sort.get(i).getStockQty());
                System.out.println("\t- Location:\t" + sort.get(i).getLocation());
                System.out.println("\t- Storage Type: " + sort.get(i).getStorageType());
                System.out.println("\t- GridLocation: " + sort.get(i).getGrid());
                System.out.println("\t- Barcode:\t" + sort.get(i).getBarcode());
                System.out.println();
            }
        }
        Keyboard.readString();
    }
}