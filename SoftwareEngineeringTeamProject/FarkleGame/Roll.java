package FarkleGame;

import java.util.Random;

public class Roll {
	
	Random rand = new Random();
	int rolledNum;
	boolean setAside;
	
	public Roll(int rolledNum, boolean setAside)
	{
		this.rolledNum = rolledNum;
		this.setAside = setAside;
	}
	
	public void diceRoll()
	{
		if (!setAside)
		{
			rolledNum = (int)(Math.random() * 6) + 1;
		}
	}
	
	public void setSetAside()
	{
		setAside = true;
	}
	
	public boolean setAside()
	{
		return setAside;
	}
	
	public int getRolledNum()
	{
		return rolledNum;
	}
	
	
	
}
