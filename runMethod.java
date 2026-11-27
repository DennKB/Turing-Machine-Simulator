import java.util.Scanner;

public class runMethod {

	Tape tape;
	readFileAndParse parser;

	runMethod(Tape tape, readFileAndParse parser) {
		this.tape = tape;
		this.parser = parser;
	}

	// count for how many commands have been executed
	int executionCounter = 0;
	
	// line by line execution
	public void sequential() {
		Scanner input = new Scanner(System.in); // continuously used to receive inputs whether to proceed or stop
		int instructionCounter = 0;
		System.out.print("\nPress Enter to Proceed, Input Anything Else then Enter to Stop\n");

		// stops when either too many commands are executed (time out) or when the turing machine has executed HALT
		for (executionCounter = 0 ; executionCounter < 10000 && tape.getStatus() ; executionCounter++) {
			try {
				// gets the next command to be executed
				StmtNode currentCommand = parser.getCommand(instructionCounter);
				// returns the value of the next command for the next iteration
				instructionCounter = currentCommand.execute(instructionCounter);

				// prints the current state of the tape (utilizes a temp variable in case of value change)
				int temp = instructionCounter;
				tape.printCurrentState(parser.getNextCommand(temp));

				// gets the input from the user whether to continue or not, continues only if the user does not input anything
				String choice = input.nextLine();
				if (choice != null && !choice.isEmpty()) {
					tape.stop();
				}
			} catch (IndexOutOfBoundsException e) {
				tape.stop();
			}
		}
		input.close();
	}

	// directly prints final tape state
	public void endResult() {
		int instructionCounter = 0;
		// stops when either too many commands are executed (time out) or when the turing machine has executed HALT
		for (executionCounter = 0 ; executionCounter < 10000 && tape.getStatus() ; executionCounter++) {
			StmtNode currentCommand = parser.getCommand(instructionCounter);
			instructionCounter = currentCommand.execute(instructionCounter);
		}
	}

	// getters
	public int getExecutionCounter() {
		return executionCounter;
	}
	public int getPointerValue() {
		return tape.getPointerValue();
	}
}
