package project02;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class MouseListenerFillAllButton implements MouseListener
{
    private GameOfLifeComp comp;
    private JButton button;

    public MouseListenerFillAllButton(JButton button, GameOfLifeComp comp)
    {
	this.comp = comp;
	this.button = button;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
	// TODO Auto-generated method stub

	if (!comp.hasGameStarted() || comp.isGamePaused())
	{
	    comp.fillAll();
	} else
	{
	    button.setEnabled(false);
	}
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e)
    {
	// TODO Auto-generated method stub

    }

}
