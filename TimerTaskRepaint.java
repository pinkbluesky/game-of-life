package project02;

import java.util.TimerTask;

public class TimerTaskRepaint extends TimerTask
{
    private GameOfLifeComp comp;

    public TimerTaskRepaint(GameOfLifeComp comp)
    {
	this.comp = comp;

    }

    @Override
    public void run()
    {
	comp.repaint();

    }

}
