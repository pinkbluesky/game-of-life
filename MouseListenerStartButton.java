package project02;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class MouseListenerStartButton implements MouseListener
{
    private JButton button;
    private JButton pauseButton;
    private JButton clearAllButton;
    private JButton fillAllButton;
    private GameOfLifeComp comp;

    public MouseListenerStartButton(JButton button, JButton pauseButton, JButton clearAllButton, JButton fillAllButton,
	    GameOfLifeComp comp)
    {
	this.comp = comp;
	this.button = button;
	this.pauseButton = pauseButton;
	this.clearAllButton = clearAllButton;
	this.fillAllButton = fillAllButton;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
	// TODO Auto-generated method stub

	comp.startGame(true);
	comp.pauseGame(false);
	button.setEnabled(false);
	pauseButton.setEnabled(true);
	clearAllButton.setEnabled(false);
	fillAllButton.setEnabled(false);
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
