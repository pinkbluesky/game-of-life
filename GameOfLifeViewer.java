package project02;

import java.util.Hashtable;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class GameOfLifeViewer
{
    private final static int MAX_SPEED = 10;
    private final static int MIN_SPEED = 0;

    private final static int INITIAL_SPEED = 5;

    public static void main(String[] args)
    {
	JFrame frame = new JFrame();
	frame.setSize(2000, 3000);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	GameOfLifeComp comp = new GameOfLifeComp();

	// timer task for repainting
	Timer timerRepaint = new Timer();
	TimerTaskRepaint taskRepaint = new TimerTaskRepaint(comp);
	timerRepaint.schedule(taskRepaint, 0, 1);

	// JLabel that labels the jslider
	JLabel labelSlider = new JLabel("Speed of Animation");
	labelSlider.setBounds(GameOfLifeComp.X_BUTTON + GameOfLifeComp.WIDTH_BUTTON * 4 + 90,
		GameOfLifeComp.Y_BUTTON - 20, 150, 20);

	// JSlider that controls the speed of doing the next generation
	frame.add(labelSlider);
	JSlider slider = new JSlider(JSlider.HORIZONTAL, MIN_SPEED, MAX_SPEED, 5);
	slider.setMajorTickSpacing(5);
	slider.setMinorTickSpacing(1);
	slider.setPaintTicks(true);
	// put labels (slow-fast)
	Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
	labels.put(MIN_SPEED, new JLabel("Slow"));
	labels.put(INITIAL_SPEED, new JLabel("Medium"));
	labels.put(MAX_SPEED, new JLabel("Fast"));
	slider.setLabelTable(labels);
	slider.setPaintLabels(true);

	// timer task for doing the next generation
	Timer timerNextG = new Timer();
	TimerTaskNextGeneration taskNextG = new TimerTaskNextGeneration(comp);
	timerNextG.schedule(taskNextG, (long) 0, (long) convertSpeedToMilliseconds(INITIAL_SPEED));

	// Slider Listener
	SliderListener sliderListener = new SliderListener(timerNextG, comp);
	slider.addChangeListener(sliderListener);

	// create panel to put slider on
	JPanel panel = new JPanel();
	panel.setBounds(GameOfLifeComp.X_BUTTON + GameOfLifeComp.WIDTH_BUTTON * 4 + 40, GameOfLifeComp.Y_BUTTON, 200,
		GameOfLifeComp.HEIGHT_BUTTON + 20);
	panel.add(slider);
	frame.add(panel);

	// mouse listener to fill cells
	MouseListenerClick mouseListenerClick = new MouseListenerClick(comp);
	comp.addMouseMotionListener(mouseListenerClick);
	comp.addMouseListener(mouseListenerClick);

	// create pause button
	JButton buttonStart = new JButton("Start");
	JButton buttonPause = new JButton("Pause");
	JButton clearAllButton = new JButton("Clear All");
	JButton fillAllButton = new JButton("Fill All");

	// mouse listener and button for the start
	MouseListenerStartButton mLStartButton = new MouseListenerStartButton(buttonStart, buttonPause, clearAllButton,
		fillAllButton, comp);
	buttonStart.addMouseListener(mLStartButton);
	buttonStart.setBounds(GameOfLifeComp.X_BUTTON, GameOfLifeComp.Y_BUTTON, GameOfLifeComp.WIDTH_BUTTON,
		GameOfLifeComp.HEIGHT_BUTTON);
	frame.add(buttonStart);

	// mouse listener and button for pause
	MouseListenerPauseButton mLPauseButton = new MouseListenerPauseButton(buttonPause, buttonStart, clearAllButton,
		fillAllButton, comp);
	buttonPause.addMouseListener(mLPauseButton);
	buttonPause.setBounds(GameOfLifeComp.X_BUTTON + GameOfLifeComp.WIDTH_BUTTON + 10, GameOfLifeComp.Y_BUTTON,
		GameOfLifeComp.WIDTH_BUTTON, GameOfLifeComp.HEIGHT_BUTTON);
	frame.add(buttonPause);
	buttonPause.setEnabled(false);

	// mouse listener and button for clear all
	MouseListenerClearAllButton mlClearAllButton = new MouseListenerClearAllButton(clearAllButton, comp);
	clearAllButton.addMouseListener(mlClearAllButton);
	clearAllButton.setBounds(GameOfLifeComp.X_BUTTON + GameOfLifeComp.WIDTH_BUTTON * 2 + 20,
		GameOfLifeComp.Y_BUTTON, GameOfLifeComp.WIDTH_BUTTON, GameOfLifeComp.HEIGHT_BUTTON);
	frame.add(clearAllButton);

	// mouse listener and button for fill all
	MouseListenerFillAllButton mlFillAllButton = new MouseListenerFillAllButton(fillAllButton, comp);
	fillAllButton.addMouseListener(mlFillAllButton);
	fillAllButton.setBounds(GameOfLifeComp.X_BUTTON + GameOfLifeComp.WIDTH_BUTTON * 3 + 30, GameOfLifeComp.Y_BUTTON,
		GameOfLifeComp.WIDTH_BUTTON, GameOfLifeComp.HEIGHT_BUTTON);
	frame.add(fillAllButton);

	frame.add(comp);
	frame.setVisible(true);
    }

    public static double convertSpeedToMilliseconds(int speed)
    {
	return ((MAX_SPEED - speed) + 1) * 100;
    }

}
