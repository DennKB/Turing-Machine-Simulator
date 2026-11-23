import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class readFileAndParse {

    private static Tape tape;
    private List<StmtNode> parsedProgram;

    readFileAndParse(Tape tape) {
        readFileAndParse.tape = tape;
        parsedProgram = parse();
    }

    public List<StmtNode> getParsedProgram() {
        return parsedProgram;
    }

    public StmtNode getCommand() {
        return parsedProgram.get(tape.getPointerValue());
    }

    // 1. IF Command (Reads/checks a character condition)
    static class IfNode extends StmtNode {
        char symbol;
        int targetLine;
        IfNode(char symbol, int targetLine) {
            this.symbol = symbol;
            this.targetLine = targetLine;
        }

        void execute() {
            tape.conditionJump(symbol, targetLine);
        }
    }

    // 2. GOTO Command
    static class GotoNode extends StmtNode {
        int targetLine;
        GotoNode(int targetLine) { this.targetLine = targetLine; }

        void execute() {
            tape.jump(targetLine);
        }
    }

    // 3. WRITE Command (Writes a character)
    static class WriteNode extends StmtNode {
        char character;
        WriteNode(char character) { this.character = character; }

        void execute() {
            tape.writeAtHead(character);
        }
    }

    // 4. RIGHT Command
    static class RightNode extends StmtNode {
        void execute() {
            tape.moveRight();
        }
    }

    // 5. LEFT Command
    static class LeftNode extends StmtNode {
        void execute() {
            tape.moveLeft();
        }
    }

    // 6. HALT Command
    static class HaltNode extends StmtNode {
        void execute() {
            tape.stop();
        }
    }

    // --- Parser Implementation ---
    static class Parser {
        private List<String> tokens;
        private int tapeHead = 0; //move to Tape.java

        public Parser(List<String> tokens) {
            this.tokens = tokens;
        }

        //TODO: Modify and Move the following 2 methods to Tape.java
        private String peek() {
            if (tapeHead < tokens.size()) return tokens.get(tapeHead);
            return null;
        }

        private String consume() {
            if (tapeHead < tokens.size()) return tokens.get(tapeHead++);
            return null;
        }

        public List<StmtNode> parseProgram() {
            List<StmtNode> statements = new ArrayList<>();
            while (peek() != null) {
                statements.add(parseStatement());
            }
            return statements;
        }

        public StmtNode parseStatement() {
            String token = peek();
            if (token == null) throw new RuntimeException("Unexpected end of code");

            if (token.equalsIgnoreCase("IF")) {
                consume(); // consume 'IF'
                String varToken = consume();
                if (varToken == null || varToken.length() != 1) {
                    throw new RuntimeException("Expected a single character after IF");
                }
                char symbol = varToken.charAt(0);

                String jumpKeyword = consume(); // expect 'GOTO'
                if (jumpKeyword == null || !jumpKeyword.equalsIgnoreCase("GOTO")) {
                    throw new RuntimeException("Expected 'GOTO' after IF condition");
                }
                int line = Integer.parseInt(consume());
                return new IfNode(symbol, line);
            } 
            else if (token.equalsIgnoreCase("GOTO")) {
                consume();
                int line = Integer.parseInt(consume());
                return new GotoNode(line);
            } 
            else if (token.equalsIgnoreCase("WRITE")) {
                consume();
                String valToken = consume();
                if (valToken == null || valToken.length() != 1) {
                    throw new RuntimeException("Expected a single character after WRITE");
                }
                char character = valToken.charAt(0);
                return new WriteNode(character);
            } 
            else if (token.equalsIgnoreCase("RIGHT")) {
                consume();
                return new RightNode();
            } 
            else if (token.equalsIgnoreCase("LEFT")) {
                consume();
                return new LeftNode();
            } 
            else if (token.equalsIgnoreCase("HALT")) {
                consume();
                return new HaltNode();
            } 
            else {
                throw new RuntimeException("Unknown command token: " + token);
            }
        }
    }

    public List<StmtNode> parse() {
        String sourceCode = "";

        try {
            sourceCode = Files.readString(Paths.get("TuringMachine.txt"));
        } catch (IOException e) {
            throw new IllegalAccessError("Could not read 'TuringMachine.txt': " + e.getMessage());
        }

        List<String> tokens = Arrays.asList(sourceCode.split("\\s+"));

        Parser parser = new Parser(tokens);
        List<StmtNode> program = parser.parseProgram();

        System.out.println("Successfully parsed " + program.size() + " statements from TuringMachine.txt");

        return program;
    }
}