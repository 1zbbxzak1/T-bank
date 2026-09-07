package backend.academy;

import backend.academy.labyrinths.settings.MazeSettings;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        MazeSettings settings = new MazeSettings(System.out, System.in);
        settings.start();
    }
}
