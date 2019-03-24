package project02;

import java.util.Timer;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderListener implements ChangeListener
{
    private Timer timerNextG;
    private GameOfLifeComp comp;

    public SliderListener(Timer timerNextG, GameOfLifeComp comp)
    {
	this.comp = comp;
	this.timerNextG = timerNextG;
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
	// TODO Auto-generated method stub

	JSlider source = (JSlider) e.getSource();
	if (!source.getValueIsAdjusting())
	{
	    timerNextG.cancel();
	    timerNextG = new Timer();
	    TimerTaskNextGeneration task = new TimerTaskNextGeneration(comp);
	    timerNextG.schedule(task, 0, (long) GameOfLifeViewer.convertSpeedToMilliseconds(source.getValue()));
	}
    }

}
