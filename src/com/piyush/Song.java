package com.piyush;


public class Song {

    protected String mTitle;
    protected String mArtist;
    protected String mUrl;

    public Song(String artist,String title,String url){
        mTitle=title;
        mArtist=artist;
        mUrl=url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString(){
        return String.format("%s by %s", mTitle,mArtist);
    }
}
