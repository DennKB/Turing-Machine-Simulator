import java.util.List;

public class runMethod
{	

	Tape tape;
	readFileAndParse parser; 
	List<StmtNode> parsedProgram;
	char[] infTape;

	runMethod(Tape tape, readFileAndParse parser)
	{
		this.tape = tape;
		this.parser = parser;
		this.parsedProgram = parser.getParsedProgram();
		this.infTape = tape.getTape();
	}

	int counter = 0;

	public void sequential()
	{
		while (tape.getStatus() && counter < 10000)
		{
			StmtNode currentCommand = parser.getCommand();

			currentCommand.execute();
			counter++;
		}

		tape.success();
	}
	
	public void endResult()
	{
		while (tape.getStatus() && counter < 10000)
		{
			StmtNode currentCommand = parser.getCommand();

			currentCommand.execute();
			counter++;
		}

		tape.success();
	}

	public int getPointerValue()
	{
		return tape.getPointerValue();
	}

	public void printTape()
	{
		int temp = 0;
		int limit = 19;

		do {
			if (tape.getEntry(temp + 1) != '_' && temp == limit)
			{
				printTwenty(temp, limit);
				temp = limit;
				limit += 20;
			} else if (tape.getEntry(temp + 1) == '_')
			{
				if (tape.getEntry(temp + 2) != '_') {
					printTwenty(temp, limit);
					printTwenty(temp + 20, limit + 20);
				}
			} else
			{
				printTwenty(temp, limit);
			}
		} while (temp <= limit);
	}

	public void printTwenty(int temp, int limit)
	{
		System.out.println("\nEntry Number " + temp + " to " + limit + ": \n");
		do {
			if (temp + 1 > limit) {
				System.out.print(infTape[temp] + " | ");
			}	System.out.print(" | " + infTape[temp]);

			temp++;
		} while (temp <= limit);
	}
}