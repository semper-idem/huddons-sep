package sep.config;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class SEPConfig {
    //I really dont know how to config, its just freestyle B)
    private final static String propertiesPath =  (Paths.get("").toAbsolutePath().toString() + "/config/statuseffectsplus.properties");
    private static Properties settings = new Properties();
    private static int potion_glint;
    private static int mode;
    private static int x_offset;
    private static int y_offset;
    private static int x_alignment;
    private static int x_arrangement;
    private static int y_arrangement;
    private static int x_space;
    private static int y_space;
    private static int color_auto;
    private static int color_background;
    private static int color_name;
    private static int color_duration;
    private static boolean dirty = true;

    public static void init(){
        if(Files.exists(Paths.get(propertiesPath))){
            loadSettings();
        }else{
            createDefaultConfig();
        }
    }

    private static Properties getDefaultProperties(){
        Properties _default = new Properties();
        _default.setProperty("potion_glint",              String.valueOf(1));
        _default.setProperty("mode",              String.valueOf(0));
        _default.setProperty("x_offset",                 String.valueOf(5));
        _default.setProperty("y_offset",                 String.valueOf(5));
        _default.setProperty("x_alignment",             String.valueOf(0));
        _default.setProperty("x_arrangement",             String.valueOf(1));
        _default.setProperty("y_arrangement",             String.valueOf(2));
        _default.setProperty("x_space",              String.valueOf(0));
        _default.setProperty("y_space",              String.valueOf(1));
        _default.setProperty("color_auto",       String.valueOf(1));
        _default.setProperty("color_background",   String.valueOf(0x30000000));
        _default.setProperty("color_name",         String.valueOf(0x80FFFFFF));
        _default.setProperty("color_duration",     String.valueOf(0x40FFFFFF));
        return _default;
    }

    public static void setDefaultProperties(){
        try (OutputStream outputStream = new FileOutputStream(propertiesPath)) {
            dirty = true;
            settings = getDefaultProperties();
            settings.store(outputStream, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static void createDefaultConfig(){
        try (OutputStream outputStream = new FileOutputStream(propertiesPath)) {
            setDefaultProperties();
            settings.store(outputStream, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static void loadSettings(){
        try (InputStream inputStream = new FileInputStream(propertiesPath)) {
            settings.load(inputStream);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static void update(){
        potion_glint = getProperty("potion_glint");
        mode = getProperty("mode");
        x_offset = getProperty("x_offset");
        y_offset = getProperty("y_offset");
        x_alignment = getProperty("x_alignment");
        x_arrangement = getProperty("x_arrangement");
        y_arrangement = getProperty("y_arrangement");
        x_space = getProperty("x_space");
        y_space = getProperty("y_space");
        color_auto = getProperty("color_auto");
        color_background = getProperty("color_background");
        color_name = getProperty("color_name");
        color_duration = getProperty("color_duration");
        dirty = false;
    }

    public static boolean isDirty(){
        return dirty;
    }

    public static int getProperty(String key){
        return Integer.parseInt((String) settings.getOrDefault(key, "1"));
    }

    public static void setProperty(String key, String value){
        try (OutputStream outputStream = new FileOutputStream(propertiesPath)) {
            dirty = true;
            settings.setProperty(key, value);
            settings.store(outputStream, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static boolean isPotionGlintEnabled(){
        return potion_glint == 1;
    }

    public static int getBackgroundColor(){
        return color_background;
    }
    public static int getXAlignment() {
        return (x_alignment - 1);
    }
    public static float getXOffset(){
        return x_offset / 1000.0F;
    }
    public static float getYOffset(){
        return y_offset / 1000.0F;
    }

    public static int getSize(){
        return mode == 1 ? 16 : 12;
    }

    public static int getMode(){
        return mode;
    }

    public static int getXArrangement() {
        return (x_arrangement - 1);
    }
    public static int getYArrangement() {
        return (y_arrangement - 1);
    }

    public static int getXSpace() {
        return x_space;
    }
    public static int getYSpace() {
        return y_space;
    }

    public static int getNameColor() {
        return color_name;
    }

    public static int getDurationColor() {
        return color_duration;
    }

    public static int getAutoColor() {
        return color_auto;
    }
}
