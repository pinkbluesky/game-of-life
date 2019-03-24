package project02;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class MouseListenerPauseButton implements MouseListener
{
    private JButton button;
    private JButton startButton;
    private JButton clearAllButton;
    private JButton fillAllButton;
    private GameOfLifeComp comp;

    public MouseListenerPauseButton(JButton button, JButton startButton, JButton clearAllButton, JButton fillAllButton,
	    GameOfLifeComp comp)
    {
	this.comp = comp;
	this.button = button;
	this.startButton = startButton;
	this.clearAllButton = clearAllButton;
	this.fillAllButton = fillAllButton;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
	// TODO Auto-generated method stub

	comp.pauseGame(true);
	comp.startGame(false);
	button.setEnabled(false);
	startButton.setEnabled(true);
	clearAllButton.setEnabled(true);
	fillAllButton.setEnabled(true);
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
