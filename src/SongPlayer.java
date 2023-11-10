import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * SongPlayer.java
 *
 * This file contains the playlist class
 * Other Files: Main.java
 *
 * @author Harrison O'Leary
 * Date: November 10, 2023
 */

public class SongPlayer {
    private int currentSongIndex;
    private String currentSongName;
    public File currentPlaylist;
    private final String playlistDirectoryPath;
    public final Map<String, File> playlists;
    public String songArtist;

    public SongPlayer(String user) {
        this.playlistDirectoryPath = "C:\\Users\\" + user + "\\Playlists";
        this.playlists  = GetPlaylists();
    }

    private Map<String, File> GetPlaylists() {
        // retrieve all directories within the default playlist folder
        // each directory represents a different playlist containing audio files
        Map<String, File> playlistDictionary = new HashMap<>();

        File playlistDirectory = new File(playlistDirectoryPath);
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

    public String GetAllSongs() {
        // acquire all playlists in the playlist directory
        File[] currentPlaylists = new File(playlistDirectoryPath).listFiles();

        // loop through all folders in currentPlaylists and then retrieve all files-
        // - within that
        StringBuilder allSongs = new StringBuilder();
        for (File playlist : currentPlaylists) {
            // allSongs.append('\n' + playlist.getName() + ":\n");
            File[] songs = playlist.listFiles();

            // loop through each file (song) and append to a string
            for (File song : songs) {
                String[] songParts = song.getName().split("-");
                String artist = songParts[0];
                String songName = songParts[1];
                int playCount = new Random().nextInt(1000000000);

                allSongs.append(artist + '-' + songName + ',' + " Views: " + playCount + '\n');
            }
        }

        return allSongs.toString();
    }

    public String AddSong(String songPath) {
        File song = new File(songPath);

        // file.renameTo() also works to move / copy files to a given location
        boolean wasFileMovingSuccess = song.renameTo(new File(currentPlaylist.getPath() + '\\' + song.getName()));

        if (wasFileMovingSuccess) {
            return "Successfully added song \"" + song.getName() + "\" to playlist \"" + currentPlaylist.getName() + '"';
        }
        else {
            return "Unable to add song!";
        }
    }

    public String RemoveSong(String songName) {
        File song = new File(currentPlaylist.getPath() + '\\' + songName);

        boolean wasFileDeletionSuccess = song.delete();

        if (wasFileDeletionSuccess) {
            return "Successfully deleted song \"" + songName + '"';
        }
        else {
            return "Unable to delete song!";
        }
    }

    public File[] ShuffleCurrentPlaylist() {
        File[] songs = currentPlaylist.listFiles();
        File[] placeholderPlaylist = new File[songs.length];

        // works by having an initially identical placeholder playlist and-
        // -appending to it by picking a random index from the real playlist and removing that item-
        // -until all of them are gone, and we are left with a shuffled playlist
        Random rnd = new Random();
        for (int i = 0; i < songs.length; i++) {
            int randomIndex = rnd.nextInt(songs.length);
            File item = null;

            // due to the playlist being an array, it is not possible to "remove" the items
            // therefore we will replace the chosen element with an empty string-
            // -and we will pick a new element if an empty string is detected
            while (item == null) {
                randomIndex = rnd.nextInt(songs.length);
                item = songs[randomIndex];
            }

            placeholderPlaylist[i] = item;
            songs[randomIndex] = null;
        }

        return placeholderPlaylist;
    }
}
