package oh_heaven.game;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class PropertiesLoader {
    public static final String DEFAULT_DIRECTORY_PATH = "properties/";
    private static final int NBSTARTCARDS = 13;
    private static final int NBROUNDS = 3;
    private static boolean ENFORCERULES= false;

    public static Properties loadPropertiesFile(String propertiesFile) {
        if (propertiesFile == null) {
            try (InputStream input = new FileInputStream( DEFAULT_DIRECTORY_PATH + "runmode.properties")) {

                Properties prop = new Properties();

                // load a properties file
                prop.load(input);

                propertiesFile = DEFAULT_DIRECTORY_PATH + prop.getProperty("current_mode");
                System.out.println(propertiesFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try (InputStream input = new FileInputStream(propertiesFile)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Random loadSeed (Properties properties){
        String seed = properties.getProperty("seed");
        if ( seed != null){
            return new Random(Long.parseLong(seed));
        }
        else {
            return new Random();
        }
    }

    public static int loadStartCards (Properties properties){
        String nbStartCards = properties.getProperty("nbStartCards");

        if (nbStartCards != null) {
            return Integer.parseInt(properties.getProperty("nbStartCards"));
        }
        else {
            return NBSTARTCARDS;
        }
    }

    public static int loadRounds (Properties properties) {
        String nbRounds = properties.getProperty("rounds");

        if (nbRounds != null) {
            return Integer.parseInt(properties.getProperty("rounds"));
        }
        else {
            return NBROUNDS;
        }
    }

    public static boolean loadRules(Properties properties){
        String enforceRules = properties.getProperty("enforceRules");

        if (enforceRules != null) {
            return Boolean.parseBoolean(properties.getProperty("enforceRules"));
        }
        else {
            return ENFORCERULES;
        }

    }

    public static List<Player> loadNPC (Properties properties, int nbPlayers){
        ArrayList<Player> allPlayers = new ArrayList<Player>();

        for (int i=0; i<nbPlayers; i++){
            String NPCType = properties.getProperty("players." + i);
            if (NPCType.equals("human")) {
                Player human = new Human();
                allPlayers.add(human);
            }
            if (NPCType.equals("random")) {
                Player random = new RandomNPC();
                allPlayers.add(random);
            }
            if (NPCType.equals("legal")) {
                Player legal = new Legal();
                allPlayers.add(legal);
            }
            if (NPCType.equals("smart")){
                Player smart = new Smart();
                allPlayers.add(smart);
            }
        }
        return allPlayers;
    }

}
