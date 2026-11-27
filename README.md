# Source Files Overview
1. Main.java: runs the simulated Turing Machine
2. readFileAndParse.java: reads file and checks the syntax
3. runMethod.java: sequential or return end result
4. Tape.java: simulates the "Infinite Tape" and holds the tape head value
5. StmtNode.java: holds the blueprint for the different objects for each commands
6. TuringMachine.txt: holds the commands for the simulated Turing Machine
7. SampleCodes.txt: sample commands to be replaced in the main read file (TuringMachine.txt)

# Information about the Code
1. Tape is limited to 200 characters / entries (Tape.infTape)
2. Instruction execution is limited to 10000 steps (runMethod.executionCounter)
3. Asks for the Starting Orientation of the Tape before selecting an Execution Method, use spaces to separate entries and press enter to finalize the input
4. GOTO destination names must end with a colon (:) to be considered a valid destination

# AI Use
1. Helped in creating the parser and tokenizer in readFileAndParse.java
   - asked for help on how to parse and convert from text to tokens
2. Used for consultating bugs in regards to tokens utilized for GOTO destinations
3. Generated some of the sample code in SampleCodes.txt
