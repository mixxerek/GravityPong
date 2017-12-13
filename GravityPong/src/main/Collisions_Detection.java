package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Collisions_Detection implements I_Objects, I_Parameters
{
	
	//Zabezpiecznie przed wielokrotnym powtorzeniem
	static boolean loopTrigger_1=false;
	static boolean loopTrigger_2=false;
	static boolean loopTrigger_3=false;
	
	public static void NoIntersects()
	{
		// Nie dzia�a
		// ma rejon 2 i zderza si� z cofnij do lini g�rnej
		if (collisionsBits.isIntersects_2() && zonesBit.isIntersects_2())
		{
			TestBall_1.setPositionX(primaryPaddle.PaddleLimitUp);
		}
	}
	public static void CreateCollisionDetectObjects()
	{
		// *********************************************** //
		// Proba Stworzenia obiektu Shape z kazdej pi�ki 
		// Jezeli obiekt powstaie to znaczy ze nastapi�o zdezenie
		// Sprawdzanie kazdej pi�ki z kazda za duzo oblicen potzebnych
		// Warto doda� listenera
		// *********************************************** //
		
		
		// Tworzenie pilek
		
		for(int i=0;i<NumbersOfBalls;i++)
		{
			Ball _Ball = new Ball((double)Random.nextInt((int)primaryScene.getWidth()),(double)Random.nextInt((int)primaryScene.getHeight()), (double)Random.nextInt(50), Color.AQUAMARINE);
			Balls.add(_Ball);
		}
		
		// Tworzenie obiektow do wykrywania zdezenia
		
		for (Ball index : Balls)
		{
			for (Ball index2 : Balls)
			{
				if (!index.equals(index2))
				{					
					Shape collisionShape = Shape.intersect(index.get_ball(), index2.get_ball());
					CollisionShapes.add(collisionShape);
				}
			}
		}
		
		// Dodanie pi�ek na ekran
		
		for(Ball index:Balls)
		{
			primaryPane.getChildren().addAll(index.getNodes());
		}
	}
	public static void CollisionBallsDetection_1()
	{
		// *********************************************** //
		// Sprawdzanie co milisekunde czy ktory� z obiekt�w si� zdezy�
		//
		// *********************************************** //
		for(Shape index:CollisionShapes)
		{
			boolean intersects = index.getBoundsInLocal().isEmpty();
			if(!intersects)
			{
				System.out.println("Collision");
				//CollisionVelocityCalculation();
			}
		}
		
	}
	public static void CollisionBallsDetection_2()
	{
		// *********************************************** //
		// Sprawdzanie ka�dej pi�ki z ka�d�, 2 dzialaja wiecej nie
		// *********************************************** //

		for (Ball index : Balls)
		{
			for (Ball index2 : Balls)
			{
				if (!index.equals(index2))
				{
					Shape collisionShape = Shape.intersect(index.get_ball(), index2.get_ball());
					boolean intersects = collisionShape.getBoundsInLocal().isEmpty();
					if (!intersects)
					{
						if (!loopTrigger_1)
						{
							CollisionTwoBall(index,index2);

							System.out.println("Collision");
							loopTrigger_1 = true;
						}
					} else
					{
						loopTrigger_1 = false;
					}
				}
			}
		}

	}
	public static void CollisionBallsDetection_3()
	{		
		Shape collisionShape = Shape.intersect(TestBall_1.get_ball(), primaryPaddle.getPaddle());		
		boolean intersects_1 = collisionShape.getBoundsInLocal().isEmpty();
		
		if (!intersects_1)
		{
			primaryPaddle.getPaddle().setFill(Color.RED);
			if (!loopTrigger_1)
			{
				CollisionWithPeddle(TestBall_1,primaryPaddle);
				loopTrigger_1 = true;
			}
		}
		else
		{
			primaryPaddle.getPaddle().setFill(Color.CHARTREUSE);
			loopTrigger_1 = false;
		}
		collisionShape = Shape.intersect(TestBall_2.get_ball(), primaryPaddle.getPaddle());		
		boolean intersects_2 = collisionShape.getBoundsInLocal().isEmpty();
		
		if (!intersects_2)
		{
			if (!loopTrigger_2)
			{
				CollisionWithPeddle(TestBall_2,primaryPaddle);
				loopTrigger_2 = true;
			}
		}
		else
		{
			loopTrigger_2 = false;
		}		
		
		collisionShape = Shape.intersect(TestBall_1.get_ball(), TestBall_2.get_ball());					
		boolean intersects_3 = collisionShape.getBoundsInLocal().isEmpty();
		
		if (!intersects_3)
		{
			if (!loopTrigger_3)
			{			
				CollisionTwoBall(TestBall_1,TestBall_2);	
				//System.out.println("Balls Collision");
				loopTrigger_3 = true;
			}
		} else
		{
			loopTrigger_3 = false;
		}	
	}
	public static void CollisionTwoBall(Ball Ball_1, Ball Ball_2)
	{
		// *********************************************** //
		// Obliczanie predkosci dwoch pilek na podstawie energi i p�du
		// mV^/2=E i p=mv => Wikipedia
		// *********************************************** //
		double Ux1 = Ball_1.getVxActual();
		double Ux2 = Ball_2.getVxActual();

		double Uy1 = Ball_1.getVyActual();
		double Uy2 = Ball_2.getVyActual();

		double m1 = Ball_1.getWeight();
		double m2 = Ball_2.getWeight();

		double Vx1 = 0, Vx2 = 0, Vy1 = 0, Vy2 = 0;

		Vx1 = (Ux1 * (m1 - m2) + 2 * m2 * Ux2) / (m1 + m2);
		Vy1 = (Uy1 * (m1 - m2) + 2 * m2 * Uy2) / (m1 + m2);

		Vx2 = (Ux2 * (m2 - m1) + 2 * m1 * Ux1) / (m2 + m1);
		Vy2 = (Uy2 * (m2 - m1) + 2 * m1 * Uy1) / (m2 + m1);

		Ball_1.setVxActual(Vx1);
		Ball_2.setVxActual(Vx2);

		Ball_1.setVyActual(Vy1);
		Ball_2.setVyActual(Vy2);
		//CalculateAngelAndPointOfCollision();
		

	}	
	public static void CollisionWithPeddle(Ball _Ball, Paddle _Paddle)
	{
		// *********************************************** //
		// Obliczanie predkosci pilki odbitej od paletki
		// mV^/2=E i p=mv => Wikipedia
		// *********************************************** //
		
		double Ux1 = _Ball.getVxActual();
		double Ux2 = _Paddle.getVxActual();

		double Uy1 = _Ball.getVyActual();
		double Uy2 = _Paddle.getVyActual();

		double m1 = _Ball.getWeight();
		double m2 = _Paddle.getWeight();

		double Vx1, Vx2, Vy1 = 0, Vy2 = 0;
		double Vx = 0;

		
// Spr�zyste rowanie pedu
		
		Vx1 = (Ux1 * (m1 - m2) + 2 * m2 * Ux2) / (m1 + m2);
		Vx2 = (Ux2 * (m2 - m1) + 2 * m1 * Ux1) / (m2 + m1);

		
		Vy1 = (Uy1 * (m1 - m2) + 2 * m2 * Uy2) / (m1 + m2);		
		Vy2 = (Uy2 * (m2 - m1) + 2 * m1 * Uy1) / (m2 + m1);
		
		
// Nie sprezyste rownanie p�du
// Czyli cia�a si� "��cz�" po zderzeniu
		
		Vx=(Ux1*m1+Ux2*m2)/(m1+m2);		
		
//		1  |	 2		| 3
//		___|____________|___
//	4   ___|____________|___  5
//		   |            |
//		6  |	7    	| 8
		   
		// Rownania dla odpowiednich zderze�

		
		if ((zonesBit.isIntersects_4() && (zonesBit.isIntersects_1() || zonesBit.isIntersects_6()))
				|| (zonesBit.isIntersects_5() && (zonesBit.isIntersects_3() || zonesBit.isIntersects_8())))
		{
			System.out.println("Warunek 1");
			if (Vx1 > 0 && Vx2 > 0)
			{
				Vx1 = Vx1 * -1;
			}
			if (Vx1 < 0 && Vx2 < 0)
			{
				Vx1 = Vx1 * -1;
			}
			_Ball.setVxActual(Vx1);
			_Paddle.setVxActual(Vx2);
		} else
		{
			System.out.println("Warunek 2");
			// Vx tylko jak uderza od gory i od do�u
			_Ball.setVxActual(Vx1);
			_Paddle.setVxActual(Vx2);
		}
		_Ball.setVyActual(Vy1);
		_Paddle.setVyActual(Vy2);

		//NoIntersects();
	}
}
//	public void CalculateAngelAndPointOfCollision()
//	{
//		// *********************************************** //
//		// Obliczenia konta i punktu zderzenia si� pi�ek 
//		// Do testow uzywane s� Ball_1, Ball_2.
//		// *********************************************** //
//		
//		Shape collisionShape = Shape.intersect(TestBall_1.get_ball(), TestBall_2.get_ball());
//
//		boolean intersects = collisionShape.getBoundsInLocal().isEmpty();
//		if (!intersects)
//		{
//
//			if (!loopTrigger_1)
//			{
//
//				// Obliczenia trygonometryczne
//
//				double sumaX = 0, sumaY, sumaR, AlfaDegree, AlfaRadians, SinAlfa, CosAlfa;
//				sumaX = TestBall_1.getPositionX() - TestBall_2.getPositionX();
//
//				if (TestBall_1.getPositionX() > TestBall_2.getPositionX())
//				{
//					sumaX = TestBall_1.getPositionX() - TestBall_2.getPositionX();
//				} else if (TestBall_1.getPositionX() < TestBall_2.getPositionX())
//				{
//					sumaX = TestBall_2.getPositionX() - TestBall_1.getPositionX();
//				} else
//				{
//					sumaX = 0;
//				}
//
//				sumaY = TestBall_1.getPositionY() - TestBall_2.getPositionY();
//				if (TestBall_1.getPositionY() > TestBall_2.getPositionY())
//				{
//					sumaY = TestBall_1.getPositionY() - TestBall_2.getPositionY();
//				} else if (TestBall_1.getPositionY() < TestBall_2.getPositionY())
//				{
//					sumaY = TestBall_2.getPositionY() - TestBall_1.getPositionY();
//				} else
//				{
//					sumaY = 0;
//				}
//
//				sumaR = TestBall_1.get_ball().getRadius() + TestBall_2.get_ball().getRadius();
//
//				SinAlfa = sumaY / sumaR;
//				CosAlfa = sumaX / sumaR;
//
//				double X1, X2, Y1, Y2;
//
//				X1 = TestBall_1.get_ball().getRadius() * CosAlfa;
//				X2 = TestBall_2.get_ball().getRadius() * CosAlfa;
//
//				Y1 = TestBall_1.get_ball().getRadius() * SinAlfa;
//				Y2 = TestBall_2.get_ball().getRadius() * SinAlfa;
//
//				if (TestBall_1.getPositionX() > TestBall_2.getPositionX())
//				{
//					CollisionPoint.setCenterX(TestBall_2.getPositionX() + X2);
//
//				} else if (TestBall_1.getPositionX() < TestBall_2.getPositionX())
//				{
//					CollisionPoint.setCenterX(TestBall_2.getPositionX() - X2);
//
//				} else
//				{
//					CollisionPoint.setCenterX(0);
//				}
//
//				if (TestBall_1.getPositionY() > TestBall_2.getPositionY())
//				{
//					CollisionPoint.setCenterY(TestBall_2.getPositionY() + Y2);
//
//				} else if (TestBall_1.getPositionY() < TestBall_2.getPositionY())
//				{
//					CollisionPoint.setCenterY(TestBall_2.getPositionY() - Y2);
//
//				} else
//				{
//					CollisionPoint.setCenterY(0);
//				}
//
//				CollisionPoint.setRadius(3);
//				CollisionPoint.setFill(Color.RED);
//
//				loopTrigger_1 = true;
//
//			}
//		} else
//		{
//			loopTrigger_1 = false;
//		}
//
//	}
//
//}