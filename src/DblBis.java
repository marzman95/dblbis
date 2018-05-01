import javax.swing.*;

class DblBis {
    public static void main(String[] args) {
        System.out.println("Starting GUI");
        TestScreen testScreen = new TestScreen();
        testScreen.setTitle("Test");
        testScreen.pack();
        testScreen.setVisible(true);
        System.out.println("Done starting GUI");
    }
}
