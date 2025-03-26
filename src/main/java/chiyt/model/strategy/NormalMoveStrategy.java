package chiyt.model.strategy;

import chiyt.model.Direction;
import chiyt.model.Map;
import chiyt.model.Role;
import chiyt.model.Treasure;

public class NormalMoveStrategy implements MoveStrategy {
    @Override
    public boolean move(Role role, Direction direction, Map map) {
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