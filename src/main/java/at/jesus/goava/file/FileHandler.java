package at.jesus.goava.file;

import at.jesus.goava.Goava;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.val;

import java.io.File;
import java.io.IOException;

public class FileHandler {
    @Getter
    private SaveFile saveFile = new SaveFile();
    private File saveFileLocation = new File("data.json");

    public FileHandler() throws IOException {
        if (saveFileLocation.exists()) {
            val objectMapper = new ObjectMapper();
            saveFile = objectMapper.readValue(saveFileLocation, SaveFile.class);
        } else {
            saveFileLocation.createNewFile();
            save();
        }

        // start save thread
        val tr = new Thread(() -> {
            while (Goava.INSTANCE.isRunning()) {
                try {
                    Thread.sleep(5000);
                    save();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }, "SAVE_THREAD");
        tr.setDaemon(true);
        tr.start();
    }

    public void save() throws IOException {
        if (saveFile == null) return;
        val objectMapper = new ObjectMapper();
        objectMapper.writeValue(saveFileLocation, saveFile);
    }
}
