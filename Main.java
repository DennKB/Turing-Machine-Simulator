import java.util.Scanner;

public class Main {
	public static void main (String[] args) {
		// important oject initializations
		Tape tape = new Tape();
		readFileAndParse parser = new readFileAndParse(tape);
		runMethod runner = new runMethod(tape, parser);

		Scanner input = new Scanner(System.in);
		boolean running = true;

		// initializes how the tape should be before the turing machine code is ran
		System.out.println("\nStarting Tape Orientation: (Space to Separate Entries, Enter to Finalize Input)");
			String startingTape = input.nextLine();
		tape.initializeTape(startingTape);
		//System.out.println(tape.getTape()); //Debugging Purposes

		// choose whether to directly input the final tape state or to go line per line
		// on the commands that are run and the current tape state
		System.out.println("Choose Running Method\n[1] Sequential\n[2] Skip to Results");
		System.out.print("Choice: ");
			int runMethod = input.nextInt();
		
		do {
			switch (runMethod) {
				case 1: // line by line execution
					runner.sequential();
					running = false;
					break;
				case 2: // direct output of final tape state
					runner.endResult();
					running = false;
					break;
				default:
					throw new IllegalArgumentException("Invalid Run Method");
			}
		} while (running);

		input.close();

		// prints the final tape state, location of the tape head and how many commands were executed
		if (!tape.getStatus()) {
			print(Tape.getTape());
			System.out.println("\n\n=== Simulated Turing System Has Finished Execution ===\n");
			System.out.println("Final Tape Head Position: Node " + (runner.getPointerValue() + 1));
			System.out.println("Operations Done: " + runner.getExecutionCounter() + " Commands Executed");
			System.exit(0);
		} else throw new IllegalStateException("Simulation Timed Out");
	}

	// TODO (maybe): move this to tape.java
	public static void print(char[] tape) {
		System.out.println("\nFinal Tape Status: ");
		for (int x = 0 ; x < tape.length ; x++) {
			System.out.print(tape[x] + " | ");
		}
	}
}
