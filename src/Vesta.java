/**
 * Root class of the project
 *
 */
// TODO: Create clear boot sequence
class Vesta {

    /**
     * Main void that <b>starts</b> the program
     */
    public static void main(String[] args) {
        // Just some logging
        System.out.println("Vesta testing");
        System.out.println("Starting screen");

        // Starts the frame called startScreen
        Screen screen = Screen.getScreen(); // Starts the main window
        screen.startScreen(); // Opens the startScreen
    }
}
