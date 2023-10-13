import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        SongPlayer music = new SongPlayer("harib");

        // System.out.println("|ID:    Playlist:");
        // music.PrintPlaylists();

        // ____MENU SYSTEM____ //
        String menu = """
                 /-_-_-_-_-_-_-_- MENU -_-_-_-_-_-_-_-_-\\
                | 0   - Pick playlist                    |
                | 1   - Select playlist for modification |
                | 2   - Play playlist                    |
                 \\_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-/
                """;
        System.out.println(menu);
        System.out.print("Choice: ");

        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine and converting to integer
        int menuChoice = Integer.parseInt(reader.readLine());

        // ____CHOICE SYSTEM____ //
        switch(menuChoice) {
            case 2:
                music.PrintPlaylists();
                break;
            default:
                break;
        }
    }
}