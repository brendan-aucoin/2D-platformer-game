package game;

import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

import entities.Entity;
import id.ID;

public class Handler 
{
	public LinkedList<Entity> entity;
	/*constructor*/
	public Handler()
	{
		entity = new LinkedList<Entity>();
	}
	/*updating all objects in game*/
	public void update()
	{
		for(int i = 0; i < entity.size(); i++)
		{
			Entity ent = entity.get(i);
			ent.update(entity);
		}
	}
	/*rendering all objects in game*/
	public void render(Graphics g)
	{
		try {
		for(int i =0;i < entity.size(); i++)
		{
			if(Game.gameState == State.Playing|| Game.gameState == State.Paused)
			{
			Entity ent = entity.get(i);
			ent.render(g);
			}
		}
		}
		catch(NullPointerException ex) {
			return;
		}
	}
	public Point firstBlockPoint()
	{
		float x=0,y=0;
		for(int i=0; i < entity.size();i++)
		{
			if(entity.get(i).getId() == ID.Block)
			{
				x = entity.get(i).getX();
				y = entity.get(i).getY();
				break;
			}
		}
		return (new Point((int)x,(int)y));
	}
	public Point lastBlockPoint()
	{
		float x=0,y=0;
		for(int i=0; i < entity.size();i++)
		{
			if(entity.get(i).getId() == ID.Block)
			{
				x = entity.get(i).getX();
				y = entity.get(i).getY();
			}
		}
		return (new Point((int)x,(int)y));
	}
	public void clear()
	{
		entity.clear();
	}
	/*adding and removing*/
	public void addEntity(Entity ent)
	{
		entity.add(ent);
	}
	public void addFirst(Entity ent)
	{
		entity.addFirst(ent);
	}
	public void addLast(Entity ent)
	{
		entity.addLast(ent);
	}
	public void removeEntity(Entity ent)
	{
		entity.remove(ent);
	}
	public void removeEntity(int i)
	{
		entity.remove(i);
	}
	
	public int size()
	{
		return entity.size();
	}
}
