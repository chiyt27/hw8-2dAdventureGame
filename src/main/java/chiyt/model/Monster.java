package chiyt.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monster extends Role {
	
	public Monster(int x, int y, Map map) {
		super(x, y, 1, map);
	}

	@Override
	public String getSymbol() {
		return "M";
	}

	@Override
	public void executeTurnAction() {
		// 如果主角在怪物的攻擊範圍內，怪物會站在原地攻擊主角
		List<MapObject> aroundObjs = new ArrayList<>();
		try{ aroundObjs.add(map.getObject(getX()-1, getY())); }catch(Exception e){}
		try{ aroundObjs.add(map.getObject(getX()+1, getY())); }catch(Exception e){}
		try{ aroundObjs.add(map.getObject(getX(), getY()-1)); }catch(Exception e){}
		try{ aroundObjs.add(map.getObject(getX(), getY()+1)); }catch(Exception e){}
		for(MapObject obj : aroundObjs) {
			if(obj != null && obj instanceof Character) {
				attack((Character)obj);
				return;
			}
		}

		// 如果主角沒有位於怪物的攻擊範圍之內的話
		//怪物將會自主決定要往哪一個方向移動一格，否則怪物會站在原地攻擊主角。
		// 將怪物執行的詳細動作內容印出。
		Random rand = new Random();
		Direction nextDir = null;
		int dir = rand.nextInt(4);
		switch (dir) {
			case 0: nextDir = Direction.UP; break;
			case 1: nextDir = Direction.DOWN; break;
			case 2: nextDir = Direction.LEFT; break;
			case 3: nextDir = Direction.RIGHT; break;
		}
		super.move(nextDir);
	}

	@Override
	public int getAttackPower() {
		return 50;
	}
}
