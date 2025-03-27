package chiyt.model.strategy;

import chiyt.model.Direction;
import chiyt.model.Map;
import chiyt.model.Role;

public class OrderlessMoveStrategy implements MoveStrategy {
    private final boolean isVertical;

    public OrderlessMoveStrategy(boolean isVertical) {
        this.isVertical = isVertical;
    }

    @Override
    public boolean move(Role role, Direction direction, Map map) {
        // 檢查移動方向是否符合限制
        if (isVertical && (direction == Direction.LEFT || direction == Direction.RIGHT)) {
            return false;
        }
        if (!isVertical && (direction == Direction.UP || direction == Direction.DOWN)) {
            return false;
        }

        int newX = role.getX() + direction.getDx();
        int newY = role.getY() + direction.getDy();

        if (!map.isValidPosition(newX, newY)) {
            return false;
        }

        var target = map.getObject(newX, newY);
        if (target != null && !(target instanceof Treasure)) {
            return false;
        }

        map.removeObject(role.getX(), role.getY());
        role.setX(newX);
        role.setY(newY);
        map.setObject(newX, newY, role);

        if (target instanceof Treasure) {
            ((Treasure) target).applyEffect(role);
        }

        return true;
    }
}
