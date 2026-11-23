# Source Files Overview of Turing Machine Simulator
1. Main.java: runs the simulated Turing Machine
2. readFileAndParse.java: reads file and checks the syntax
3. runMethod.java: sequential or return end result
4. Tape.java: simulates the "Infinite Tape", holds the tape head value and the 6 commands (goto, ifgoto, write, left, right, halt)
5. TuringMachine.txt: holds the commands for the simulated Turing Machine

# Information about the Code
1. Tape is limited to 200 characters / entries (Tape.infTape)
2. Tape Head operation is limited to 10000 executions (Tape.tapeHead)
3. Asks for the Starting Orientation of the Tape before selecting an Execution Method, use spaces to separate entries and press enter to finalize the input

# AI Use
    1. Helped in creating the parser in readFileAndParse.java
        - asked for sample code on how to convert from text to tokens
