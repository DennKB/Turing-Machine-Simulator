public class Tape {
	private static char[] infTape;
	private static int tapeHead;
	private boolean running = true;

	public Tape() {
		infTape = new char[200];
		tapeHead = 0;
	}

	public int getEntry(int pos) {
		return infTape[pos];
	}

	public int getPointerValue() {
		return tapeHead;
	}

	public boolean getStatus() {
		return running;
	}

	public static char[] getTape() {
		return infTape;
	}

	public void initializeTape(String input) {
		char[] start = input.replaceAll("\\s+", "").toCharArray();
		//System.out.println(start); //Debugging
		int temp;
		int last = 0;

		for (temp = 0; temp < start.length ; temp++) {
			infTape[temp] = start[temp];
			last = temp + 1;
		}

		for (int x = last ; x < infTape.length ; x++) {
			infTape[x] = '_';
		}

	}

	public void printCurrentState(String nextCommand) {
		int counter;
		for (counter = 0 ; counter < infTape.length ; counter++) {
			if (infTape[counter] != '_' || (infTape[counter + 1] != '_' && infTape[counter + 2] != '_')) {
				System.out.print(infTape[counter] + " | ");
			} else {
				System.out.println(infTape[counter + 1] + " | ");
				break;
			}
		}
		System.out.println("Tape Head is at Node #" + (tapeHead + 1) + "\nNext Command: " + nextCommand);
	}

	public void moveRight() { //RIGHT
		tapeHead++;
	}

	public void moveLeft() { //LEFT
		tapeHead--;
	}
	
	public void writeAtHead(char x) { //WRITE
		infTape[tapeHead] = x;
	}

	public void stop() { //HALT
		running = false;
	}
}
