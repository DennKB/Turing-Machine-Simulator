import java.util.Scanner;

public class Main
{
	public static void main (String[] args)
	{
		Tape tape = new Tape();
		readFileAndParse parser = new readFileAndParse(tape);
		runMethod runner = new runMethod(tape, parser);
		Scanner input = new Scanner(System.in);
		boolean running = true;

		System.out.println("Starting Tape Orientation: (Space to Separate Entries, Enter to Finalize Input)");
			String startingTape = input.nextLine();

		tape.initializeTape(startingTape);

		System.out.println("Choose Running Method\n[1] Sequential\n[2] Skip to Results\n");
		System.out.print("Choice: ");
			int runMethod = input.nextInt();
		
		do
		{
			switch (runMethod)
			{
				case 1:
					runner.sequential();
					running = false;
					break;
				case 2:
					runner.endResult();
					running = false;
					break;
				default:
					throw new IllegalArgumentException("Invalid Run Method");
			}
		} while (running);

		System.out.println(tape.getStatus() + " " + runner.getPointerValue());

		input.close();

		if (runner.getPointerValue() > 10000 && tape.getStatus()) 
		{
			System.out.println("\n= ~ = ~ = ~ = ~ = Simulated Turing System Has Finished Execution = ~ = ~ = ~ = ~ =\n");
			System.exit(0);
		} else throw new IllegalStateException("Simulation Timed Out");
	}
}