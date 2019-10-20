package exentop.sunlite;

public class SunliteJNI {

    public static void load(){
        System.loadLibrary("SunliteJNI");
    }


    public static final native int init();
    public static final native int open();
    public static final native void close();
    public static final native int dmxOut(char[] dmx);

}
