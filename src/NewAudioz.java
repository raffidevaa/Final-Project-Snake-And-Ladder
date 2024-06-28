import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class NewAudioz {
    private Clip clip;
    private String filePath;

    public void playAudio(String filePath, boolean loop) {
        try {
            this.filePath = filePath;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (loop) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop audio continuously
            } else {
                clip.loop(0); // Play only once
            }
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopAudio() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void restartAudio() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
}
