public abstract class StmtNode {
    // execute method always returns the value of the next instruction to be executed
    int execute(int instructionCounter) {
        return instructionCounter + 1;
    }
}
