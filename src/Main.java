import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.util.NoSuchElementException;

/**
 * Main.java
 *
 * This file contains the main class for the song application
 * Other Files: SongPlayer.java
 *
 * @author Harrison O'Leary
 * Date: November 10, 2023
 */

public class Main {
    private static String GetInput(String prompt) throws IOException {
        System.out.print(prompt);

        // Enter data using BufferReader
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        return reader.readLine();
    }

    private static String PickPlaylistChoice(SongPlayer musicPlayer) throws IOException {
        musicPlayer.PrintPlaylists();

        String choice = GetInput("Playlist choice: ");

        // attempt to get the file object of the user's choice
        // if it doesn't exist, simply return to the menu
        File playlistChoice;
        try {
            playlistChoice = musicPlayer.playlists.get(choice);
        } catch (NoSuchElementException e) {
            return "Playlist does not exist!";
        }

        musicPlayer.currentPlaylist = playlistChoice;
        return "Selected playlist \"" + playlistChoice.getName() + "\"!";
    }

    private static String AddSongChoice(SongPlayer musicPlayer) throws IOException {
        if (musicPlayer.currentPlaylist == null) {
            return "No currently selected playlist, please select one.";
        }

        String songPath = GetInput("Song file path: ");
        return musicPlayer.AddSong(songPath);
    }

    private static String DeleteSongChoice(SongPlayer musicPlayer) throws IOException {
        if (musicPlayer.currentPlaylist == null) {
            return "No currently selected playlist, please select one.";
        }

        String songName = GetInput("Song name: ");
        return musicPlayer.RemoveSong(songName);
    }

    public static void main(String[] args) throws IOException {
        SongPlayer musicPlayer = new SongPlayer("harib");

        // ____MENU SYSTEM____ //
        final String menu = """
                  /-_-_-_-_-_-_-_- MENU -_-_-_-_-_-_-_-_\\
                 | 0   - Pick playlist                   |
                 | 1   - List all songs                  |
                 | 2   - Add song                        |
                 | 3   - Delete song                     |
                 | 4   - Shuffle current playlist        |
                 | 5   - Exit                            |
                  \\_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_/
                """;

        int menuChoice = 0;
        while (menuChoice != 5)  {
            // SongPlayer.currentPlaylist holds nothing at the start and will-
            // - therefore throw a NullPointerException error
            // we have to do a quick try catch to sort this business out
            String currentPlaylistName;
            try {
                currentPlaylistName = musicPlayer.currentPlaylist.getName();
            } catch (NullPointerException e) {
                currentPlaylistName = "None";
            }

            System.out.println("Selected playlist: " + currentPlaylistName);
            System.out.println(menu);

            menuChoice = Integer.parseInt(GetInput("Choice: "));
            // ____MENU SYSTEM____ //

            // ____CHOICE SYSTEM____ //
            switch(menuChoice) {
                case 0:
                    System.out.println(PickPlaylistChoice(musicPlayer));
                    break;
                case 1:
                    System.out.println(musicPlayer.GetAllSongs());
                    break;
                case 2:
                    System.out.println(AddSongChoice(musicPlayer));
                    break;
                case 3:
                    System.out.println(DeleteSongChoice(musicPlayer));
                    break;
                case 4:
                    if (currentPlaylistName.equals("None")) {
                        System.out.println("No currently selected playlist, please select one.");
                    }
                    else {
                        musicPlayer.ShuffleCurrentPlaylist();
                        System.out.println("Shuffled currently selected playlist.");
                    }

                    break;
                default:
                    break;
            }
            // ____CHOICE SYSTEM____ //
        }
    }
}