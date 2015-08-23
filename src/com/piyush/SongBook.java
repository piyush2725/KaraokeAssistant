package com.piyush;


import java.io.*;
import java.util.*;

//This class is the controller view
public class SongBook {

    List<Song> mSongs;

    public SongBook() {
        mSongs = new ArrayList<>();
    }   //create an arraylist of song Objects

    /*To add song to the SongBook
    * @param-song object
    */
    public void addSong(Song song) {
        mSongs.add(song);
    }

    /*To get the no of songs in the mSongs arraylist
    * @returns-size of mSongs
    */
    public int getSongCount() {
        return mSongs.size();
    }

    /*Serializing the songs into file...
    * Creating a file to export the songs*/

    public void exportTo(String fileName) {
        try (FileOutputStream fos = new FileOutputStream(fileName);
             PrintWriter writer = new PrintWriter(fos)) {
            for (Song song : mSongs) {
                writer.printf("%s|%s|%s%n", song.getArtist(), song.getTitle(), song.getUrl());
            }
        } catch (IOException ioe) {
            System.out.printf("Problem saving %s%n", fileName);
            ioe.printStackTrace();
        }
    }

    /*Importing the songs from the file*/
    public void importFrom(String filename) {
        try (FileInputStream fis = new FileInputStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis));) {
            String line;
            while ((line = reader.readLine()) != null) {
                String args[] = line.split("\\|");
                addSong(new Song(args[0], args[1], args[2]));
            }
        } catch (IOException ioe) {
            System.out.printf("Problem importing %s%n", filename);
            ioe.printStackTrace();
        }
    }

    /*To display the songs in the songbook
    */
    public void displaySongs() {
        for (Song song : mSongs) {
            System.out.printf("%s|%s|%s %n", song.getTitle(), song.getArtist(), song.getUrl());
        }
    }

    //List the songs by artist...HashMap that maps artist to his list of songs
    private Map<String, List<Song>> byArtist() {
        Map<String, List<Song>> byArtist = new TreeMap<>();     //Iniatlize a new HashMap
        for (Song song : mSongs) {                              //Parse each song in mSongs
            List<Song> artistSongs = byArtist.get(song.getArtist());    //Check to see if the map contains the song by the artist
            artistSongs = new ArrayList<>();                       //inialize a new arraylist
            if (artistSongs == null) {                                 //If the map does not contain the song
                byArtist.put(song.getArtist(), artistSongs);           // put the artist of the song and empty arraylist
            }
            artistSongs.add(song);                                 //add the song to the arraylist
        }
        return byArtist;
    }

    /*To get the set of all artists in the hashmap
    * @returns-Set of all artists*/
    public Set<String> getArtists() {
        return byArtist().keySet();
    }

    /*To get the list of songs of the artist passed
    * @params-artistName
    * @returns-Set of all songs by the artist*/
    public List<Song> getSongsForArtist(String artistName) {
        List<Song> songs = byArtist().get(artistName);
        songs.sort(new Comparator<Song>() {
            @Override
            public int compare(Song s1, Song s2) {
                if (s1.equals(s2)) {
                    return 0;
                } else
                    return s1.mTitle.compareTo(s2.mTitle);
            }
        });
        return songs;
    }


}
