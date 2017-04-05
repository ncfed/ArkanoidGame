package arkanoidgame;

import java.awt.*;

public class Ball {

    private ArkanoidGameWindow game;
    private int radius = 10;
    Point position = new Point(0, 0);
    Point move = new Point(1, 1);
    float speed = 0.3f;

    public Ball(ArkanoidGameWindow game) {
        this.game = game;
    }

    public void tick(double deltatime) {
        position.translate((int) (move.x * (speed * deltatime)), (int) (move.y * (speed * deltatime)));
        if (Math.abs(position.x) >= Math.abs(game.width / 2)) {
            move.x = -move.x;
        }
        if (position.y <= -game.height / 2) {
            move.y = -move.y;
        }
        if (position.y >= game.height / 2) {
            game.ballFlewOff();
        }
        Rectangle hitbox = new Rectangle(position.x - radius, position.y - radius, radius * 2, radius * 2);
        Point pv = game.platform.reboundDirection(hitbox);
        move.x *= pv.x;
        move.y *= pv.y;

        for (int i = 0; i < game.bricks.size(); i++) {
            Brick b = game.bricks.get(i);
            pv = b.reboundDirection(hitbox);
            move.x *= pv.x;
            move.y *= pv.y;
            if (pv.x < 0 || pv.y < 0) {
                game.brickBroken();
                game.bricks.remove(b);
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(position.x - radius, position.y - radius, radius * 2, radius * 2);
    }

}
