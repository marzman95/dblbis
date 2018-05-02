import javax.swing.*;

/**
 * Root class of the project
 */
// TODO: Create clear boot sequence
class Vesta {

    /**
     * Main void that <b>starts</b> the program
     */
    public static void main(String[] args) {
        System.out.println("Vesta testing");
        System.out.println("Starting screen");
        Screen screen = Screen.getScreen();
        screen.startScreen();
    }
}
