package com.piyush;



import java.io.*;
import java.util.*;

public class SongBook {

    List<Song> mSongs;

    public SongBook() {
        mSongs =new ArrayList<>();
    }

    /*To add song to the SongBook
    * @param-song object
    */
    public void addSong(Song song){
        mSongs.add(song);
    }

    public int getSongCount(){
        return mSongs.size();
    }

    //Serializing the songs into file...

    public void exportTo(String fileName){
        try(FileOutputStream fos=new FileOutputStream(fileName);
            PrintWriter writer=new PrintWriter(fos))
        {
            for(Song song:mSongs){
                writer.printf("%s|%s|%s%n", song.getArtist(), song.getTitle(), song.getUrl());
            }
        }
        catch(IOException ioe){
            System.out.printf("Problem saving %s%n",fileName);
            ioe.printStackTrace();
        }
    }

    public void importFrom(String filename){
        try(FileInputStream fis=new FileInputStream(filename);
        BufferedReader reader=new BufferedReader(new InputStreamReader(fis));)
        {
            String line;
            while((line=reader.readLine()) !=null){
                String args[]=line.split("\\|");
                addSong(new Song(args[0],args[1],args[2]));
            }
        }
        catch(IOException ioe){
            System.out.printf("Problem importing %s%n",filename);
            ioe.printStackTrace();
        }
    }

    /*To display the songs in the songbook
    */
    public void displaySongs(){
        for(Song song: mSongs){
            System.out.printf("%s|%s|%s %n",song.getTitle(),song.getArtist(),song.getUrl());
        }
    }

    //List the songs by artist...HashMap that maps artist to his list of songs
    private Map<String,List<Song>> byArtist(){
        Map<String,List<Song>> byArtist=new TreeMap<>();
        for(Song song:mSongs){
            List<Song> artistSongs=byArtist.get(song.getArtist());
            if(artistSongs==null){
                artistSongs=new ArrayList<>();
                byArtist.put(song.getArtist(),artistSongs);
            }
            artistSongs.add(song);
        }
        return byArtist;
    }

    public Set<String> getArtists(){
        return byArtist().keySet();
    }

    public List<Song> getSongsForArtist(String artistName)
    {
        List<Song> songs= byArtist().get(artistName);
        songs.sort(new Comparator<Song>(){
            @Override
            public int compare(Song s1, Song s2) {
                if(s1.equals(s2)){
                    return 0;
                }
                else
                    return s1.mTitle.compareTo(s2.mTitle);
            }
        });
        return songs;
    }




}
