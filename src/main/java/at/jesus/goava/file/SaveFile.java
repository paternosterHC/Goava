package at.jesus.goava.file;

import lombok.Data;

@Data
public class SaveFile {
    private int count = 0;
    private long totalTime = 0;

    public int increment() {
        return count = count + 1;
    }

    public long addTime(long time) {
        return totalTime = totalTime + time;
    }
}
