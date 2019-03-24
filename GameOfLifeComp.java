package project02;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;

public class GameOfLifeComp extends JComponent
{

    private GameOfLife game;
    private Point first;
    private Point second;

    private boolean gameStarted;
    private boolean paused;

    protected static final int X_BUTTON = 20;
    protected static final int Y_BUTTON = 25;
    protected static final int WIDTH_BUTTON = 100;
    protected static final int HEIGHT_BUTTON = 40;

    private static final double X_OF_BOARD = 10;
    private static final double Y_OF_BOARD = 100;

    public GameOfLifeComp()
    {
	boolean[][] board = new boolean[GameOfLife.NUM_OF_CELLS][GameOfLife.NUM_OF_CELLS];
	game = new GameOfLife(X_OF_BOARD, Y_OF_BOARD, board);

	gameStarted = false;
	paused = false;
	first = new Point(0, 0);
	second = new Point(0, 0);
    }

    public void paintComponent(Graphics g)
    {
	Graphics2D g2 = (Graphics2D) g;

	g2.drawString("Welcome to the Game of Life!", X_BUTTON, Y_BUTTON - 10);

	game.draw(g2);

	// draw the game so that first and second point rectangle is inverted
	// validate the points
	if (game.validatePointXY(new double[] { first.getX(), first.getY() })
		&& game.validatePointXY(new double[] { second.getX(), second.getY() }))
	{
	    // use GameOfLife draw method to invert the cells
	    game.draw(g2, first, second);
	}

	g2.drawString("Current Generation: " + game.getCurrentGeneration(), X_BUTTON, Y_BUTTON + HEIGHT_BUTTON + 15);

    }

    public boolean validatePointXY(double[] xyValue)
    {
	return game.validatePointXY(xyValue);
    }

    public void nextGeneration()
    {
	if (gameStarted && !paused)
	{
	    game.doNextGeneration();

	}
    }

    public void addCell(double[] xyValues)
    {
	if (!gameStarted && game.validatePointXY(xyValues))
	{
	    game.addValueToBoard(xyValues);

	}

    }

    public void addCellIndexes(int[] ijValues)
    {
	if (!gameStarted)
	{
	    game.addValueToBoard(ijValues);

	}
    }

    public void removeCell(double[] xyValues)
    {
	if (!gameStarted && game.validatePointXY(xyValues))
	{
	    game.removeValueFromBoard(xyValues);

	}

    }

    public void pauseGame(boolean value)
    {
	paused = value;
    }

    public boolean isGamePaused()
    {
	return paused;
    }

    public void startGame(boolean value)
    {
	gameStarted = value;
	paused = !value;
    }

    public boolean hasGameStarted()
    {
	return gameStarted;
    }

    public void fillAll()
    {
	for (int i = 0; i < GameOfLife.NUM_OF_CELLS; i++)
	{
	    for (int j = 0; j < GameOfLife.NUM_OF_CELLS; j++)
	    {
		game.addValueToBoard(new int[] { i, j });
	    }
	}
	game.setGeneration(0);
    }

    public void clearAll()
    {

	for (int i = 0; i < GameOfLife.NUM_OF_CELLS; i++)
	{
	    for (int j = 0; j < GameOfLife.NUM_OF_CELLS; j++)
	    {
		game.removeValueFromBoard(new int[] { i, j });
	    }
	}
	game.setGeneration(0);
    }

    /**
     * Sets the first and second points as the inputted values.
     * 
     * @param newFirst
     *            the new first point
     * @param newSecond
     *            the new second point
     */
    public void setNewFirstSecondPoints(Point newFirst, Point newSecond)
    {

	this.first = newFirst;
	this.second = newSecond;
    }

    /**
     * Permanently inverts the cells in the rect between first and second.
     * 
     * @param first
     *            the first point
     * @param second
     *            the second point
     */
    public void fillRectFromTwoPoints(Point first, Point second)
    {
	// invert cells
	fillRectInGame(first, second);

	// reset first and second points
	this.first = new Point(0, 0);
	this.second = new Point(0, 0);
    }

    private void fillRectInGame(Point first, Point second)
    {
	if (game.validatePointXY(new double[] { first.getX(), first.getY() }))
	{
	    // if the second point is out of bounds, set the xy values so that
	    // it is not
	    // first, check the second point's x value
	    if (second.getX() > X_OF_BOARD + GameOfLife.getWidthOfBoard())
	    {
		second.setLocation(X_OF_BOARD + GameOfLife.getWidthOfBoard(), second.getY());
	    } else if (second.getX() < X_OF_BOARD)
	    {
		second.setLocation(X_OF_BOARD, second.getY());
	    }

	    // check the second point's y value
	    if (second.getY() > Y_OF_BOARD + GameOfLife.getHeightOfBoard())
	    {
		second.setLocation(second.getX(), Y_OF_BOARD + GameOfLife.getHeightOfBoard());
	    } else if (second.getY() < Y_OF_BOARD)
	    {
		second.setLocation(second.getX(), Y_OF_BOARD);
	    }

	    if (first.getX() > second.getX())
	    {
		// swap first and second
		Point temp = first;
		first = second;
		second = temp;
	    }
	    // now we know first is behind second if you look left and right

	    // calculate the indexs for the first point and second
	    int[] firstPointIndexes = game.convertPixelToIndex(new double[] { first.getX(), first.getY() });
	    int[] secondPointIndexes = game.convertPixelToIndex(new double[] { second.getX(), second.getY() });

	    // calculate how much across the points are
	    double height = Math.abs(secondPointIndexes[0] - firstPointIndexes[0]) + 1;
	    // calculate how high above the second point is
	    double width = Math.abs(secondPointIndexes[1] - firstPointIndexes[1]) + 1;

	    // if first is above second or on the same horizontal line as second
	    if (first.getY() <= second.getY())
	    {
		for (int a = 0; a < height; a++)
		{
		    int currentI = firstPointIndexes[0] + a;

		    for (int b = 0; b < width; b++)
		    {
			int currentJ = firstPointIndexes[1] + b;

			boolean value = game.getValueOfCell(currentI, currentJ);
			if (!value)
			{
			    game.addValueToBoard(new int[] { currentI, currentJ });
			} else
			{
			    game.removeValueFromBoard(new int[] { currentI, currentJ });
			}
		    }
		}
	    } else // if first is below second
	    {
		for (int a = 0; a < height; a++)
		{
		    int currentI = firstPointIndexes[0] - a;

		    for (int b = 0; b < width; b++)
		    {
			int currentJ = firstPointIndexes[1] + b;

			boolean value = game.getValueOfCell(currentI, currentJ);
			if (!value)
			{
			    game.addValueToBoard(new int[] { currentI, currentJ });
			} else
			{
			    game.removeValueFromBoard(new int[] { currentI, currentJ });
			}
		    }
		}
	    }

	    game.setGeneration(0);
	}
    }

}
