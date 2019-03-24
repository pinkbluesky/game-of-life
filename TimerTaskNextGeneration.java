package project02;

import java.util.TimerTask;

public class TimerTaskNextGeneration extends TimerTask
{

    private GameOfLifeComp comp;

    public TimerTaskNextGeneration(GameOfLifeComp comp)
    {
	this.comp = comp;
    }

    @Override
    public void run()
    {
	comp.nextGeneration();

    }

}
