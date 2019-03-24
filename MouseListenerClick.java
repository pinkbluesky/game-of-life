package project02;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

public class MouseListenerClick extends MouseInputAdapter
{
    private GameOfLifeComp comp;
    private Point firstPoint;

    private boolean isFirstValid;

    public MouseListenerClick(GameOfLifeComp comp)
    {
	this.comp = comp;
	firstPoint = new Point();
	isFirstValid = false;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
	// TODO Auto-generated method stub

	if (comp.validatePointXY(new double[] { e.getX(), e.getY() }))
	{
	    firstPoint = e.getPoint();
	    comp.setNewFirstSecondPoints(firstPoint, firstPoint);
	    isFirstValid = true;
	}

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
	// TODO Auto-generated method stub
	Point secondPoint = e.getPoint();

	if (isFirstValid && (!comp.hasGameStarted() || comp.isGamePaused()))
	{
	    comp.fillRectFromTwoPoints(firstPoint, secondPoint);
	}
    }

    @Override
    public void mouseDragged(MouseEvent e)
    {
	// TODO Auto-generated method stub

	if (isFirstValid && (!comp.hasGameStarted() || comp.isGamePaused()))
	{
	    comp.setNewFirstSecondPoints(firstPoint, e.getPoint());
	}

    }

}
