public class Main {
    public static void main(String[] args) throws InterruptedException {
        Menu menu = new Menu();

        // Check if the game is started without blocking
        while (!menu.isGameStarted()) {
            // Pause the thread for a short time to avoid busy waiting
            Thread.sleep(100);
        }
    }
}
