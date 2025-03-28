package chiyt.model.strategy;

import chiyt.model.Direction;
import chiyt.model.Map;
import chiyt.model.Role;

public class NormalMoveStrategy implements MoveStrategy {
    @Override
    public boolean move(Role role, Direction dir) {
        int newX = role.getX(), newY = role.getY();
		if(dir.equals(Direction.UP))
			newY -= 1;
		else if (dir.equals(Direction.DOWN)) 
			newY += 1;
		else if (dir.equals(Direction.RIGHT)) 
			newX += 1;
		else if (dir.equals(Direction.LEFT)) 
			newX -= 1;

		Map map = role.getMap();
		map.moveObject(role, newX, newY);
		return true;
    }
}
