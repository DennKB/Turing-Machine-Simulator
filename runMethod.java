import java.util.Scanner;

public class runMethod {

	Tape tape;
	readFileAndParse parser;

	runMethod(Tape tape, readFileAndParse parser) {
		this.tape = tape;
		this.parser = parser;
	}

	int executionCounter = 0;

	public void sequential() {
		Scanner input = new Scanner(System.in);
		int instructionCounter = 0;
		System.out.print("\nPress Enter to Proceed, Input Anything Else then Enter to Stop\n");

		for (executionCounter = 0 ; executionCounter < 10000 && tape.getStatus() ; executionCounter++) {
			try {
				StmtNode currentCommand = parser.getCommand(instructionCounter);
				instructionCounter = currentCommand.execute(instructionCounter);

				int temp = instructionCounter;
				tape.printCurrentState(parser.getNextCommand(temp));

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

	public void endResult() {
		int instructionCounter = 0;
		for (executionCounter = 0 ; executionCounter < 10000 && tape.getStatus() ; executionCounter++) {
			StmtNode currentCommand = parser.getCommand(instructionCounter);
			instructionCounter = currentCommand.execute(instructionCounter);
		}
	}

	public int getExecutionCounter() {
		return executionCounter;
	}

	public int getPointerValue() {
		return tape.getPointerValue();
	}
}
