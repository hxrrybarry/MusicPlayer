import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SongPlayer {
    private int currentSongIndex;
    private String currentSongName;
    private String currentPlaylistName;
    private String currentPlaylistLocation;
    private String[] currentPlaylist;
    private final String user;
    private final Map<String, File> playlists;

    public SongPlayer(String u) {
        this.user = u;
        this.playlists  = GetPlaylists();
    }

    private Map<String, File> GetPlaylists() {
        // retrieve all directorys within the default playlist folder
        // each directory represents a different playlist containing audio files
        Map<String, File> playlistDictionary = new HashMap<>();

        File playlistDirectory = new File("C:\\Users\\" + user + "\\Playlists");
        File[] folders = playlistDirectory.listFiles();

        // link a file path to an id so the user can easily select a playlist
        for (int i = 0; i < folders.length; i++) {
            playlistDictionary.put(String.valueOf(i), folders[i]);
        }

        return playlistDictionary;
    }

    public void PrintPlaylists() {
        // retrieve ID of playlist for user to easily pick
        for (Map.Entry<String, File> kvPairs : playlists.entrySet()) {
            System.out.println("|" + kvPairs.getKey() + "    - " + '\"' + kvPairs.getValue().getName() + '\"');
        }
    }

    public void PlaylistMenuSelection() {
        // TODO: menu selection screen for playlists (playing and shuffling)
    }

    public String[] ShuffleCurrentPlaylist() {
        String[] placeholderPlaylist = new String[currentPlaylist.length];

        // works by having an initially identical placeholder playlist and-
        // -appending to it by picking a random index from the real playlist and removing that item-
        // -until all of them are gone, and we are left with a shuffled playlist
        Random rnd = new Random();
        for (int i = 0; i < currentPlaylist.length; i++) {
            int randomIndex = rnd.nextInt(currentPlaylist.length);
            String item = "";

            // due to the playlist being an array, it is not possible to "remove" the items
            // therefore we will replace the picked element with an empty string-
            // -and we will pick a new element if an empty string is detected
            while (item.isEmpty()) {
                item = currentPlaylist[randomIndex];
            }

            placeholderPlaylist[i] = item;
            currentPlaylist[randomIndex] = "";
        }

        return placeholderPlaylist;
    }
}
