package at.jesus.goava;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Goava.INSTANCE.init(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
