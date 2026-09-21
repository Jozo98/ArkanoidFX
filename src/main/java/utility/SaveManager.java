package utility;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class SaveManager {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void saveGame(int aktuellesLevel) throws IOException {
        objectMapper.writeValue(new File("src/main/resources/aktuellesLevel.json"), aktuellesLevel);
    }

    public static Integer loadGame() throws IOException {
        return objectMapper.readValue(new File("src/main/resources/aktuellesLevel.json"), Integer.class);

    }

    public static void resetGame() throws IOException {
        objectMapper.writeValue(new File("src/main/resources/aktuellesLevel.json"), 0);
    }
}
