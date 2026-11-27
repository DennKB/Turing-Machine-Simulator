import java.util.Scanner;

public class Main {
	public static void main (String[] args) {
		Tape tape = new Tape();
		readFileAndParse parser = new readFileAndParse(tape);
		runMethod runner = new runMethod(tape, parser);

		Scanner input = new Scanner(System.in);
		boolean running = true;

		System.out.println("\nStarting Tape Orientation: (Space to Separate Entries, Enter to Finalize Input)");
			String startingTape = input.nextLine();

		tape.initializeTape(startingTape);
		//System.out.println(tape.getTape()); //Debugging Purposes

		System.out.println("Choose Running Method\n[1] Sequential\n[2] Skip to Results");
		System.out.print("Choice: ");
			int runMethod = input.nextInt();
		
		do {
			switch (runMethod) {
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

		input.close();

		if (!tape.getStatus()) {
			print(Tape.getTape());
			System.out.println("\n\n=== Simulated Turing System Has Finished Execution ===\n");
			System.out.println("Final Tape Head Position: Node " + (runner.getPointerValue() + 1));
			System.out.println("Operations Done: " + runner.getExecutionCounter() + " Commands Executed");
			System.exit(0);
		} else throw new IllegalStateException("Simulation Timed Out");
	}

	public static void print(char[] tape) {
		System.out.println("\nFinal Tape Status: ");
		for (int x = 0 ; x < tape.length ; x++) {
			System.out.print(tape[x] + " | ");
		}
	}
}
