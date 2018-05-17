package hidato;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;


/**
 *
 * @author marc.catrisse
 */
public class MusicPlayer extends Thread {
    private Clip clip;
    private AudioInputStream p;
    private String namemusic;
    private boolean loop;

    MusicPlayer(String name, boolean loop){
        super("MusicPlayer");
        this.namemusic = name;
        this.loop = loop;
    }

    @Override
    public void run() {
        String soundName = "res/music/" + this.namemusic;
        try {
            p = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(p);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        startPlayback();
    }


    public void startPlayback() {
        if(loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }else clip.start();
    }

    public void stopPlayback() {
        clip.stop();
    }
}
