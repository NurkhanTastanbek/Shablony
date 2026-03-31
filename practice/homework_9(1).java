import java.util.*;

class TV {
    public void on() { System.out.println("TV is ON"); }
    public void off() { System.out.println("TV is OFF"); }
    public void setChannel(int channel) { System.out.println("TV channel set to " + channel); }
    public void setInput(String input) { System.out.println("TV input set to " + input); }
}

class AudioSystem {
    public void on() { System.out.println("Audio System is ON"); }
    public void off() { System.out.println("Audio System is OFF"); }
    public void setVolume(int level) { System.out.println("Audio volume set to " + level); }
}

class DVDPlayer {
    public void on() { System.out.println("DVD Player is ON"); }
    public void off() { System.out.println("DVD Player is OFF"); }
    public void play(String movie) { System.out.println("DVD Player playing: " + movie); }
    public void pause() { System.out.println("DVD Player paused"); }
    public void stop() { System.out.println("DVD Player stopped"); }
}

class GameConsole {
    public void on() { System.out.println("Game Console is ON"); }
    public void off() { System.out.println("Game Console is OFF"); }
    public void startGame(String gameName) { System.out.println("Starting game: " + gameName); }
}

class HomeTheaterFacade {
    private TV tv;
    private AudioSystem audio;
    private DVDPlayer dvd;
    private GameConsole console;

    public HomeTheaterFacade(TV tv, AudioSystem audio, DVDPlayer dvd, GameConsole console) {
        this.tv = tv;
        this.audio = audio;
        this.dvd = dvd;
        this.console = console;
    }

    public void watchMovie(String movie) {
        System.out.println("\n--- Preparing to watch a movie ---");
        tv.on();
        tv.setInput("DVD");
        audio.on();
        audio.setVolume(20);
        dvd.on();
        dvd.play(movie);
    }

    public void endMovie() {
        System.out.println("\n--- Shutting down movie theater ---");
        dvd.stop();
        dvd.off();
        audio.off();
        tv.off();
    }

    public void playGame(String game) {
        System.out.println("\n--- Preparing for gaming session ---");
        tv.on();
        tv.setInput("HDMI 1");
        audio.on();
        audio.setVolume(15);
        console.on();
        console.startGame(game);
    }

    public void listenToMusic() {
        System.out.println("\n--- Preparing for music listening ---");
        tv.on();
        tv.setInput("Audio Input");
        audio.on();
        audio.setVolume(30);
    }

    public void setSystemVolume(int level) {
        System.out.println("\n--- Adjusting system volume ---");
        audio.setVolume(level);
    }

    public void shutdown() {
        System.out.println("\n--- Full System Shutdown ---");
        tv.off();
        audio.off();
        dvd.off();
        console.off();
    }
}

public class Main {
    public static void main(String[] args) {
        TV tv = new TV();
        AudioSystem audio = new AudioSystem();
        DVDPlayer dvd = new DVDPlayer();
        GameConsole console = new GameConsole();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(tv, audio, dvd, console);

        homeTheater.watchMovie("Inception");
        
        homeTheater.setSystemVolume(25);

        homeTheater.endMovie();

        homeTheater.playGame("The Witcher 3");

        homeTheater.listenToMusic();

        homeTheater.shutdown();
    }
}
