package chiyt.model.State;

import chiyt.model.Role;
import chiyt.model.Character;
import chiyt.model.Monster;

public abstract class State {
	private String name = "";
	private int duration = 0;
	protected Role target;

	public State(Role target, String name, int duration) {
		this.target = target;
		this.name = name;
		this.duration = duration;
	}

	public String getName(){
		return name;
	}

	public int getDuration() {
		return duration;
	}

	public void startTurn(){}

	public void executeTurnAction(){
		target.executeTurnAction();
	}

	public void endTurn(){
		if(target.getDuration()==0)
			target.setState(new NormalState(target));
	}

	public void handleDamage(int damage){
		int hp = Math.max(0, target.getHp() - damage);
		target.setHp(hp);
		if (hp == 0) {
			System.out.println(String.format("%s(%d,%d) dies!", target.getSimpleName(), target.getY(), target.getX()));
			target.getMap().removeObj(target);
		}
	}

	public void handleAttack(Role attackedTarget){
		Role attacker = this.target;
		Role target = attackedTarget;
		System.out.println(
			String.format("%s(%d,%d) attacks %s (%d,%d)", 
			attacker.getSimpleName(), attacker.getY(), attacker.getX(), 
			target.getSimpleName(), target.getY(), target.getX()
		));
		target.takeDamage(attacker.getAttackPower());
	}
}
