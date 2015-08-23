
import com.piyush.Song;
import com.piyush.SongBook;
import com.piyush.KaraokeMachine;


import java.io.IOException;

public class Karaoke {

    public static void main(String[] args) throws IOException{
        SongBook songBook=new SongBook();
        songBook.importFrom("songs.txt");
        KaraokeMachine machine=new KaraokeMachine(songBook);
        machine.run();
        System.out.println("Saving book...");
        songBook.exportTo("songs.txt");
    }
}
