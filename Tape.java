public class Tape
{
	private char[] infTape;
	private int tapeHead;
	private boolean running = true;

	public Tape()
	{
		infTape = new char[200];
		tapeHead = 0;
	}

	public int getEntry(int pos)
	{
		return infTape[pos];
	}

	public int getPointerValue()
	{
		return tapeHead;
	}

	public boolean getStatus()
	{
		return running;
	}

	public void success() {
		running = true;
	}

	public char[] getTape() {
		return infTape;
	}

	//TODO: create method for initializing the tape
	public void initializeTape(String input)
	{
		char[] start = input.toCharArray();
		int temp;
		int last = 0;

		for (temp = 0; temp < start.length ; temp++) {
			infTape[temp] = start[temp];
			last = temp;
		}

		for (int x = last ; x < infTape.length ; x++) {
			infTape[x] = '_';
		}

	}

	public void moveRight() //RIGHT
	{
		tapeHead++;
	}

	public void moveLeft() //LEFT
	{
		tapeHead--;
	}
	
	public void writeAtHead(char x) //WRITE
	{
		infTape[tapeHead] = x;
	}

	public void jump(int targetLine) //GOTO
	{
		tapeHead = targetLine;
	}

	public void conditionJump(char symbol, int targetLine) //IF_GOTO
	{
		if (infTape[tapeHead] == symbol)
		{
			jump(targetLine);
		} else moveRight();
	}

	public void stop() //HALT
	{
		running = false;
	}
}