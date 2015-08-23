package com.piyush;

import com.piyush.Song;
import com.piyush.SongBook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/*
This class represents the UI
*/
public class KaraokeMachine {
    Map<String, String> mMenu;
    SongBook mSongBook;
    BufferedReader mReader;
    Queue<Song> mQueue;


    public KaraokeMachine(SongBook songBook) {
        mMenu = new HashMap<>();    //Initialize a new hashmap mapping option to description
        mSongBook = songBook;
        mQueue = new ArrayDeque<>();
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mMenu.put("add", "Add songs to the songbook");
        mMenu.put("choose", "Choose a song from songbook");
        mMenu.put("play", "Play next song");
        mMenu.put("quit", "Give up...Exit the program");
    }

    /*choose option from the menu
    * @returns-choice made by the user*/
    public String promptAction() throws IOException {
        System.out.printf("There are %d songs available and %d songs in the queue. Your options are: %n", mSongBook.getSongCount()
                , mQueue.size());
        for (Map.Entry option : mMenu.entrySet()) {
            System.out.printf("%s - %s%n", option.getKey(), option.getValue());
        }
        System.out.print("Enter your choice: ");
        String choice = mReader.readLine();
        return choice.toLowerCase().trim();
    }

    //run the menu at least once
    public void run() throws IOException {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "add":
                        addSong();  //add song to the the songBook
                        break;
                    case "choose":
                        String artist = promptArtist();     //get the artist
                        Song artistSong = promptSong(artist);//get the song of the artist
                        mQueue.add(artistSong);//add to the queue
                        System.out.printf("You chose: %s %n", artistSong);
                        break;
                    case "play":
                        pullSong();//play the next song in the queue
                        break;
                    case "quit":
                        System.out.println("Thanks for playing!");
                        break;
                    default:
                        System.out.printf("Unknown choice: %s. Try again!! %n%n", choice);
                }
            } catch (IOException ioe) {
                System.out.print("Problem with input!!");
                ioe.printStackTrace();
            }
        } while (!choice.equals("quit"));
    }

    private void addSong() throws IOException {
        System.out.print("Enter the songs artist: ");
        String artist = mReader.readLine();
        System.out.print("Enter the songs title: ");
        String title = mReader.readLine();
        System.out.print("Enter the songs url: ");
        String url = mReader.readLine();
        mSongBook.addSong(new Song(title, artist, url));
    }


    /*Make a choice of the list of artists
    * @returns-the chosen artist's index as a string*/
    private String promptArtist() throws IOException {
        System.out.println("Available artists:");
        List<String> artists = new ArrayList<>(mSongBook.getArtists());
        int index = promptForIndex(artists);
        return artists.get(index);
    }

    /*Make a choice of the song from the list of songtitles by the artist available
    * @params-takes in the artist
    * @returns the song selected of the artist passed*/
    private Song promptSong(String artist) throws IOException {
        List<Song> artistSongs = mSongBook.getSongsForArtist(artist);
        List<String> songTitles = new ArrayList<>();
        for (Song song : artistSongs) {
            songTitles.add(song.getTitle());
        }
        int index = promptForIndex(songTitles);
        return artistSongs.get(index);
    }

    //helper function that takes in a list of options and returns the choice as an integer
    private int promptForIndex(List<String> options) throws IOException {
        int counter = 1;
        for (String option : options) {
            System.out.printf("%d.) %s %n", counter, option);
            counter++;
        }
        System.out.print("Enter your choice: ");
        String choiceString = mReader.readLine();
        int choice = Integer.parseInt(choiceString);
        return choice - 1;
    }

    //For the kj to pull the song from the queue and play the video
    private void pullSong() {
        Song song = mQueue.poll();
        if (song == null) {         //boundary case
            System.out.println("There is no song in the queue. " + "Use add option from the menu");
        } else {
            System.out.printf("The next song is %s by %s. Open %s to hear!!%n%n", song.getTitle(),
                    song.getArtist(), song.getUrl());
        }
    }


}
