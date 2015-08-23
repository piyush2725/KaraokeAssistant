
import com.piyush.Song;
import com.piyush.SongBook;
import com.piyush.KaraokeMachine;


import java.io.IOException;

public class Karaoke {

    public static void main(String[] args) throws IOException {
        SongBook songBook = new SongBook();
        songBook.importFrom("songs.txt");//importing the songs from the saved file
        KaraokeMachine machine = new KaraokeMachine(songBook);
        machine.run();//running the menu
        System.out.println("Saving book...");
        songBook.exportTo("songs.txt"); //saving any changes to the song file
    }
}
