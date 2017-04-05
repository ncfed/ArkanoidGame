package arkanoidgame;

import java.awt.*;

public class Brick {

    public Point position = new Point(0, 0);
    public int width = 70;
    public int height = 30;

    public Point reboundDirection(Rectangle hitbox) {
        Point point = new Point(1, 1);
        Rectangle top = new Rectangle(position.x, position.y, width, height / 3);
        Rectangle bottom = new Rectangle(position.x, position.y + height - height / 3, width, height / 3);
        Rectangle left = new Rectangle(position.x, position.y, width / 10, height);
        Rectangle right = new Rectangle(position.x + width - width / 10, position.y, width / 10, height);
        if (top.intersects(hitbox) || bottom.intersects(hitbox)) {
            point.y = -1;
        }
        if (right.intersects(hitbox) || left.intersects(hitbox)) {
            point.x = -1;
        }
        return point;
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(position.x, position.y, width, height);
        for (int i = 0; i < height / 4; i++) {
            g.drawLine(position.x + i, position.y + height - i, position.x + width - 1, position.y + height - i);
            g.drawLine(position.x + width - 1 - i, position.y + i, position.x + width - 1 - i, position.y + height);
            g.drawLine(position.x, position.y + i, position.x + width - 1 - i, position.y + i);
            g.drawLine(position.x + i, position.y + height - i, position.x + i, position.y);
        }
    }

}
