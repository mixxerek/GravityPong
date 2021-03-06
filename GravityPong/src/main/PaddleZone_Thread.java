package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class PaddleZone_Thread implements Runnable,I_Objects,I_Parameters
{

	Thread LocalThread;
	Shape collisionShape1;
	Shape collisionShape2;
	Shape collisionShape3;
	Shape collisionShape4;
	Shape collisionShape5;
	Shape collisionShape6;
	Shape collisionShape7;
	Shape collisionShape8;
	
	
	public PaddleZone_Thread()
	{
		LocalThread = new Thread(this, "CheckBallZone");
		LocalThread.setDaemon(true);
	}
	private void CheckZone()
	{	
		
		collisionShape1 = Shape.intersect(TestBall_1.get_ball(), Rectangle_Zone_1);		
		paddleZone_Bits.setIntersects_1(!collisionShape1.getBoundsInLocal().isEmpty());
		collisionShape2 = Shape.intersect(TestBall_1.get_ball(), Rectangle_Zone_2);		
		paddleZone_Bits.setIntersects_2(!collisionShape2.getBoundsInLocal().isEmpty());
		collisionShape3 = Shape.intersect(TestBall_1.get_ball(), Rectangle_Zone_3);		
		paddleZone_Bits.setIntersects_3(!collisionShape3.getBoundsInLocal().isEmpty());
		collisionShape4 = Shape.intersect(TestBall_1.get_ball(), Rectangle_Zone_4);		
		paddleZone_Bits.setIntersects_4(!collisionShape4.getBoundsInLocal().isEmpty());
		collisionShape5 = Shape.intersect(TestBall_1.get_ball(), Rectangle_Zone_5);		
		paddleZone_Bits.setIntersects_5(!collisionShape5.getBoundsInLocal().isEmpty());
		collisionShape6 = Shape.intersect(TestBall_1.get_ball(), Rectangle_Zone_6);		
		paddleZone_Bits.setIntersects_6(!collisionShape6.getBoundsInLocal().isEmpty());
		collisionShape7 = Shape.intersect(TestBall_1.get_ball(), Rectangle_Zone_7);		
		paddleZone_Bits.setIntersects_7(!collisionShape7.getBoundsInLocal().isEmpty());
		collisionShape8 = Shape.intersect(TestBall_1.get_ball(), Rectangle_Zone_8);		
		paddleZone_Bits.setIntersects_8(!collisionShape8.getBoundsInLocal().isEmpty());
		
		if (ShowZoneActive)
		{
			if (paddleZone_Bits.isIntersects_1())
			{
				Rectangle_Zone_1.setFill(Color.AZURE);
			} else
			{
				Rectangle_Zone_1.setFill(Color.TRANSPARENT);
			}

			if (paddleZone_Bits.isIntersects_2())
			{
				Rectangle_Zone_2.setFill(Color.AZURE);
			} else
			{
				Rectangle_Zone_2.setFill(Color.TRANSPARENT);
			}

			if (paddleZone_Bits.isIntersects_3())
			{
				Rectangle_Zone_3.setFill(Color.AZURE);
			} else
			{
				Rectangle_Zone_3.setFill(Color.TRANSPARENT);
			}

			if (paddleZone_Bits.isIntersects_4())
			{
				Rectangle_Zone_4.setFill(Color.AZURE);
			} else
			{
				Rectangle_Zone_4.setFill(Color.TRANSPARENT);
			}

			if (paddleZone_Bits.isIntersects_5())
			{
				Rectangle_Zone_5.setFill(Color.AZURE);
			} else
			{
				Rectangle_Zone_5.setFill(Color.TRANSPARENT);
			}

			if (paddleZone_Bits.isIntersects_6())
			{
				Rectangle_Zone_6.setFill(Color.AZURE);
			} else
			{
				Rectangle_Zone_6.setFill(Color.TRANSPARENT);
			}

			if (paddleZone_Bits.isIntersects_7())
			{
				Rectangle_Zone_7.setFill(Color.AZURE);
			} else
			{
				Rectangle_Zone_7.setFill(Color.TRANSPARENT);
			}

			if (paddleZone_Bits.isIntersects_8())
			{
				Rectangle_Zone_8.setFill(Color.AZURE);
			} else
			{
				Rectangle_Zone_8.setFill(Color.TRANSPARENT);
			}
		}	
		else
		{
			Rectangle_Zone_1.setFill(Color.TRANSPARENT);
			Rectangle_Zone_2.setFill(Color.TRANSPARENT);
			Rectangle_Zone_3.setFill(Color.TRANSPARENT);
			Rectangle_Zone_4.setFill(Color.TRANSPARENT);
			Rectangle_Zone_5.setFill(Color.TRANSPARENT);
			Rectangle_Zone_6.setFill(Color.TRANSPARENT);
			Rectangle_Zone_7.setFill(Color.TRANSPARENT);
			Rectangle_Zone_8.setFill(Color.TRANSPARENT);
			
			
		}
	}	
	@Override
	public void run()
	{
		while (true)
		{
			synchronized (this)
			{
				CheckZone();
			}
		}
		// TODO Auto-generated method stub
		
	}
	public void Start()
	{
		LocalThread.start();
		
	}
	

}
