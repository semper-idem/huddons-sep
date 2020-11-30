package sep.config;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;


public class SEPConfig {
    public enum Mode {
        INLINE,
        CLASSIC
    }

    public enum XAxis {
        LEFT,
        CENTER,
        RIGHT
    }

    public enum YAxis {
        UP,
        CENTER,
        DOWN
    }
    private static File configFile = new File(Paths.get("").toAbsolutePath().toString() + "/config/statuseffectsplus.properties");

    public static boolean potionGlint;
    public static Mode mode;
    public static float xOffset;
    public static float yOffset;
    public static XAxis xAlignment;
    public static XAxis xArrangement;
    public static YAxis yArrangement;
    public static int xSpace;
    public static int ySpace;
    public static boolean colorAuto;
    public static int colorBackground;
    public static int colorName;
    public static int colorDuration;

    public static void saveConfig() throws IOException {
        FileOutputStream fos = new FileOutputStream(configFile, false);
        fos.write(("# Status Effect Plus Config | " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "\n").getBytes());
        fos.write(("potionGlint=" + potionGlint+"\n").getBytes());
        fos.write(("mode=" + mode+"\n").getBytes());
        fos.write(("xOffset=" + xOffset+"\n").getBytes());
        fos.write(("yOffset=" + yOffset+"\n").getBytes());
        fos.write(("xAlignment=" + xAlignment+"\n").getBytes());
        fos.write(("xArrangement=" + xArrangement+"\n").getBytes());
        fos.write(("yArrangement=" + yArrangement+"\n").getBytes());
        fos.write(("xSpace=" + xSpace+"\n").getBytes());
        fos.write(("ySpace=" + ySpace+"\n").getBytes());
        fos.write(("colorAuto=" + colorAuto+"\n").getBytes());
        fos.write(("colorBackgroundA=" + ((colorBackground >> 24) & 255)+"\n").getBytes());
        fos.write(("colorBackgroundR=" + ((colorBackground >> 16) & 255)+"\n").getBytes());
        fos.write(("colorBackgroundB=" + ((colorBackground >> 8) & 255)+"\n").getBytes());
        fos.write(("colorBackgroundG=" + ((colorBackground) & 255)+"\n").getBytes());
        fos.write(("colorNameA=" + ((colorName >> 24) & 255)+"\n").getBytes());
        fos.write(("colorNameR=" + ((colorName >> 16) & 255)+"\n").getBytes());
        fos.write(("colorNameB=" + ((colorName >> 8) & 255)+"\n").getBytes());
        fos.write(("colorNameG=" + ((colorName) & 255)+"\n").getBytes());
        fos.write(("colorDurationA=" + ((colorDuration >> 24) & 255)+"\n").getBytes());
        fos.write(("colorDurationR=" + ((colorDuration >> 16) & 255)+"\n").getBytes());
        fos.write(("colorDurationB=" + ((colorDuration >> 8) & 255)+"\n").getBytes());
        fos.write(("colorDurationG=" + ((colorDuration) & 255)+"\n").getBytes());
        fos.close();

    }

    public static void loadConfig(){
        try{
            if (!configFile.exists() || !configFile.canRead())
                saveConfig();

            Properties properties = new Properties();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)))) {
                String line = br.readLine();
                if (!line.startsWith("# Status Effect Plus Config")) {
                    initWithDefault();
                    saveConfig();
                }
                properties.load(br);

            try{
                potionGlint = ((String) properties.computeIfAbsent("potionGlint", a -> "true")).equalsIgnoreCase("true");
                mode = Mode.valueOf((String) properties.computeIfAbsent("mode", o -> Mode.CLASSIC));
                xOffset = Float.parseFloat((String) properties.computeIfAbsent("xOffset", o -> "99.9"));
                yOffset = Float.parseFloat((String) properties.computeIfAbsent("yOffset", o -> "0.75"));
                xAlignment = XAxis.valueOf((String)properties.computeIfAbsent("xAlignment", o -> XAxis.RIGHT));
                xArrangement =XAxis.valueOf((String)properties.computeIfAbsent("xArrangement", o -> XAxis.LEFT));
                yArrangement =YAxis.valueOf((String)properties.computeIfAbsent("yArrangement", o -> YAxis.CENTER));
                xSpace = Integer.parseInt((String) properties.computeIfAbsent("xSpace", o -> "2"));
                ySpace =  Integer.parseInt((String) properties.computeIfAbsent("ySpace", o -> "0"));
                colorAuto = ((String) properties.computeIfAbsent("colorAuto", o -> "false")).equalsIgnoreCase("true");
                {
                    int a, r, g, b;
                    a = Integer.parseInt((String) properties.computeIfAbsent("colorBackgroundA", o -> "48"));
                    r = Integer.parseInt((String) properties.computeIfAbsent("colorBackgroundR", o -> "0"));
                    g = Integer.parseInt((String) properties.computeIfAbsent("colorBackgroundG", o -> "0"));
                    b = Integer.parseInt((String) properties.computeIfAbsent("colorBackgroundB", o -> "0"));
                    colorBackground = (a << 24) + (r << 16) + (g << 8) + b;
                }
                {
                    int a, r, g, b;
                    a = Integer.parseInt((String) properties.computeIfAbsent("colorNameA", o -> "128"));
                    r = Integer.parseInt((String) properties.computeIfAbsent("colorNameR", o -> "255"));
                    g = Integer.parseInt((String) properties.computeIfAbsent("colorNameG", o -> "255"));
                    b = Integer.parseInt((String) properties.computeIfAbsent("colorNameB", o -> "255"));
                    colorName = (a << 24) + (r << 16) + (g << 8) + b;
                }
                {
                    int a, r, g, b;
                    a = Integer.parseInt((String) properties.computeIfAbsent("colorDurationA", o -> "170"));
                    r = Integer.parseInt((String) properties.computeIfAbsent("colorDurationR", o -> "255"));
                    g = Integer.parseInt((String) properties.computeIfAbsent("colorDurationG", o -> "255"));
                    b = Integer.parseInt((String) properties.computeIfAbsent("colorDurationB", o -> "255"));
                    colorDuration = (a << 24) + (r << 16) + (g << 8) + b;
                }
            } catch (Exception e){
                initWithDefault();
                saveConfig();
            }

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initWithDefault(){
        potionGlint = false;
        mode = Mode.CLASSIC;
        xOffset = 99.9f;
        yOffset = 0.75f;
        xAlignment = XAxis.RIGHT;
        xArrangement = XAxis.LEFT;
        yArrangement = YAxis.CENTER;
        xSpace = 2;
        ySpace = 0;
        colorAuto = false;
        colorBackground = 0x30000000;
        colorName = 0x80FFFFFF;
        colorDuration = 0xaaFFFFFF;
    }

    private static int getXAxis(XAxis direction){
        switch (direction){
            case RIGHT:
                return 1;
            case LEFT:
                return -1;
            default:
                return 0;
        }
    }
    public static int getXAlignment(){
        return getXAxis(xAlignment);
    }
    public static int getXArrangement(){
        return getXAxis(xArrangement);
    }
    public static int getYArrangement(){
        switch (yArrangement){
            case UP:
                return -1;
            case DOWN:
                return 1;
            default:
                return 0;
        }
    }

}
