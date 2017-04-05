package arkanoidgame;

import java.awt.*;

public class Platform {

    private ArkanoidGameWindow game;
    int height = 10;
    int width = 100;
    Point position = new Point(0, 0);

    public Platform(ArkanoidGameWindow game) {
        this.game = game;
        position = new Point(0, game.height / 2 - height - 20);
    }

    public Point reboundDirection(Rectangle hitbox) {
        Point p = new Point(1, 1);
        Rectangle top = new Rectangle(position.x - width / 2, position.y - height / 2, width, height / 3);
        Rectangle bottom = new Rectangle(position.x - width / 2, position.y + height / 2 - height / 3, width, height / 3);
        Rectangle left = new Rectangle(position.x - width / 2, position.y - height / 2, width / 10, height);
        Rectangle right = new Rectangle(position.x + width / 2 - width / 10, position.y - height / 2, width / 10, height);
        if (top.intersects(hitbox) || bottom.intersects(hitbox)) {
            p.y = -1;
        }
        if (right.intersects(hitbox) || left.intersects(hitbox)) {
            p.x = -1;
        }
        return p;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(position.x - width / 2, position.y - height / 2, width, height);
    }
}
