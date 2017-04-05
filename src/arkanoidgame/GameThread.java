package arkanoidgame;

public class GameThread extends Thread {

    private ArkanoidGameWindow game;

    public GameThread(ArkanoidGameWindow game) {
        this.game = game;
    }

    public void run() {
        game.run = true;
        game.pause = false;
        game.lastUpdate = System.nanoTime();
        while (game.run) {
            try {
                if (game.pause) {
                    game.lastUpdate = System.nanoTime();
                    Thread.sleep(1);
                } else {
                    game.tick();
                    game.lastUpdate = System.nanoTime();
                    Thread.sleep((long) (1000.0 / game.tickrate));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
