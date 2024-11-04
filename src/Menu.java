import org.academiadecodigo.simplegraphics.mouse.Mouse;
import org.academiadecodigo.simplegraphics.mouse.MouseEvent;
import org.academiadecodigo.simplegraphics.mouse.MouseEventType;
import org.academiadecodigo.simplegraphics.mouse.MouseHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Menu implements MouseHandler {

    private Picture background;
    private boolean gameStarted = false;
    private boolean isMenuActive = true; // Flag to track menu state
    private Clip clickClip;
    private Clip backgroundClip;

    public Menu() {
        // Display the menu options
        showMenu();

        // Register mouse handler
        Mouse mouse = new Mouse(this);
        mouse.addEventListener(MouseEventType.MOUSE_CLICKED);
    }

    private void showMenu() {
        background = new Picture(10, 10, "Background2.jpg");
        background.draw();
        playBackgroundMusic("/Backgroundmusic.wav"); // Ensure path is correct
        loadClickSound("/Click.wav"); // Load click sound
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        playClickSound(); // Play sound on click
        int x = (int) event.getX();
        int y = (int) event.getY();

        // Adjust click areas for "Start Game"
        if (isMenuActive) {
            if (x >= 740 && x <= 950 && y >= 570 && y <= 620) { // Increased width and height range
                startGame();
            }

            // Adjust click areas for "Exit"
            if (x >= 800 && x <= 880 && y >= 680 && y <= 710) {
                System.exit(0);
            }
        }
    }

    private void startGame() {
        gameStarted = true;
        clearMenu(); // Clear the menu screen
        new GameWindow(); // Start the game by initializing the game window
        isMenuActive = false;
    }

    private void clearMenu() {
        stopBackgroundMusic();
        background.delete();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        // Not used
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public void playBackgroundMusic(String soundFile) {
        try {
            // Get the resource as a URL
            URL soundURL = getClass().getResource(soundFile);
            if (soundURL == null) {
                System.err.println("Sound file not found: " + soundFile);
                return;
            }

            // Open the audio input stream using the URL
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);

            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioInputStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Method to stop the music
    public void stopBackgroundMusic() {
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop();
        }
    }

    private void loadClickSound(String soundFile) {
        try {
            // Get the resource as a URL
            URL soundURL = getClass().getResource(soundFile);
            if (soundURL == null) {
                System.err.println("Click sound file not found: " + soundFile);
                return;
            }

            // Open the audio input stream using the URL
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundURL);

            clickClip = AudioSystem.getClip();
            clickClip.open(audioInputStream);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    private void playClickSound() {
        if (clickClip != null) {
            if (clickClip.isRunning()) {
                clickClip.stop(); // Stop if already playing
            }
            clickClip.setFramePosition(0); // Rewind to the beginning
            clickClip.start(); // Play the sound
        }
    }
}
