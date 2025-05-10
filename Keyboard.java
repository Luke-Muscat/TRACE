// keyboard class. Store it in directory or have a path to it.

import java.io.*;

public class Keyboard{

    public static String readString(){
        BufferedReader br;
        try{
            br = new BufferedReader(new InputStreamReader(System.in));
            return br.readLine();
        }catch (Exception e){

        }
        return null;
    }

    public static int readInt(){
        try{
            return Integer.parseInt(readString());
        }catch(Exception e){
            System.out.println("invalid input");
            readString();
            return 0;
        }
    }

    public static byte readByte(){
        try{
            return Byte.parseByte(readString());
        }catch(Exception e){
            System.out.println("\finvalid input");
            readString();
            return 0;
        }
    }

    public static short readShort(){
        try{
            return Short.parseShort(readString());
        }catch(Exception e){
            System.out.println("\finvalid input");
            readString();
            return 0;
        }
    }

    public static long readLong(){
        try{
            return Long.parseLong(readString());
        }catch(Exception e){
            System.out.println("\finvalid input");
            readString();
            return 0;
        }
    }

    public static float readFloat(){
        try{
            return Float.parseFloat(readString());
        }catch(Exception e){
            System.out.println("\finvalid input");
            readString();
            return 0;
        }
    }

    public static double readDouble(){
        try{
            return Double.parseDouble(readString());
        }catch(Exception e){
            System.out.println("\finvalid input");
            readString();
            return 0;
        }
    }

    public static char readChar(){
        try{
            return readString().charAt(0);
        }catch(Exception e){
            System.out.println("\finvalid input");
            readString();
            return 0;
        }
    }

    public static boolean readBoolean(){
        try{
            return Boolean.parseBoolean(readString());
        }catch(Exception e){
            System.out.println("\finvalid input");
            readString();
            return false;
        }
    }

}