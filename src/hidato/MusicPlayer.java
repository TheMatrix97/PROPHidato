package hidato;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;


public class MusicPlayer extends Thread {

    private Clip clip;
    private AudioInputStream p;
    private boolean play;
    private String namemusic;

    MusicPlayer(String name){
        super("MusicPlayer");
        this.namemusic = name;
    }

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
        this.play = true;
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stopPlayback() {
        this.play = false;
        clip.stop();
    }
}
