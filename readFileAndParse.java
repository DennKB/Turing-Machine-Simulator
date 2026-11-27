import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class readFileAndParse {

    private static Tape tape;
    private static List<StmtNode> parsedProgram;
    private static List<String> commandLines;
    private List<LabelNode> labels;

    readFileAndParse(Tape tape) {
        readFileAndParse.tape = tape;
        parsedProgram = parse();
    }

    String getNextCommand(int x) {
        return commandLines.get(x);
    }

    public List<StmtNode> getParsedProgram() {
        return parsedProgram;
    }

    public List<LabelNode> getLabels() {
        return labels;
    }

    public StmtNode getCommand(int instructionCounter) {
        return parsedProgram.get(instructionCounter);
    }

    static class LabelNode {
        final String name;
        final int location;

        LabelNode(String name, int location) {
            this.name = name;
            this.location = location;
        }
    }

    // 1. IF Command (Reads/checks a character condition)
    static class IfNode extends StmtNode {
        char symbol;
        String targetName;
        int targetLine;

        IfNode(char symbol, String targetName) {
            this.symbol = symbol;
            this.targetName = targetName;
        }

        void setTargetLine(int targetLine) {
            this.targetLine = targetLine;
        }

        int execute(int instructionCounter) {
            if (tape.getEntry(tape.getPointerValue()) == symbol) {
                return targetLine;
            }
            return instructionCounter + 1;
        }
    }

    // 2. GOTO Command
    static class GotoNode extends StmtNode {
        String targetName;
        int targetLine;

        GotoNode(String targetName) {
            this.targetName = targetName;
        }

        void setTargetLine(int targetLine) {
            this.targetLine = targetLine;
        }

        int execute(int instructionCounter) {
            return targetLine;
        }
    }

    // 3. WRITE Command (Writes a character)
    static class WriteNode extends StmtNode {
        char character;
        WriteNode(char character) { this.character = character; }

        int execute(int instructionCounter) {
            tape.writeAtHead(character);
            return instructionCounter + 1;
        }
    }

    // 4. RIGHT Command
    static class RightNode extends StmtNode {
        int execute(int instructionCounter) {
            tape.moveRight();
            return instructionCounter + 1;
        }
    }

    // 5. LEFT Command
    static class LeftNode extends StmtNode {
        int execute(int instructionCounter) {
            tape.moveLeft();
            return instructionCounter + 1;
        }
    }

    // 6. HALT Command
    static class HaltNode extends StmtNode {
        int execute(int instructionCounter) {
            tape.stop();
            return instructionCounter + 1;
        }
    }

    // --- Parser Implementation ---
    static class Parser {
        private final List<String> tokens;
        private int tapeHead = 0; //move to Tape.java
        private final List<LabelNode> labels = new ArrayList<>();

        public Parser(List<String> tokens) {
            this.tokens = tokens;
        }

        public List<LabelNode> getLabels() {
            return labels;
        }

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
                String token = peek();

                if (token.endsWith(":")) {
                    // A label is not an executable statement. Its location is the
                    // index of the statement that immediately follows it.
                    addLabel(token.substring(0, token.length() - 1), statements.size());
                    consume();
                } else {
                    statements.add(parseStatement());
                }
            }

            resolveTargets(statements);
            return statements;
        }

        private void addLabel(String name, int location) {
            if (name.isEmpty()) {
                throw new RuntimeException("Expected a label name before ':'");
            }
            for (LabelNode label : labels) {
                if (label.name.equals(name)) {
                    throw new RuntimeException("Duplicate label: " + name);
                }
            }
            labels.add(new LabelNode(name, location));
        }

        // Find the label object whose name matches each jump's string target,
        // then give the jump the label's int location as the next instruction index.
        private void resolveTargets(List<StmtNode> statements) {
            for (LabelNode label : labels) {
                if (label.location >= statements.size()) {
                    throw new RuntimeException("Label '" + label.name + "' has no statement to jump to");
                }
            }

            for (StmtNode statement : statements) {
                if (statement instanceof IfNode) {
                    IfNode node = (IfNode) statement;
                    node.setTargetLine(findLocation(node.targetName));
                } else if (statement instanceof GotoNode) {
                    GotoNode node = (GotoNode) statement;
                    node.setTargetLine(findLocation(node.targetName));
                }
            }
        }

        private int findLocation(String targetName) {
            for (LabelNode label : labels) {
                if (label.name.equals(targetName)) {
                    return label.location;
                }
            }
            throw new RuntimeException("GOTO target '" + targetName + "' has no matching label");
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
                String target = consume();
                if (target == null || target.endsWith(":")) {
                    throw new RuntimeException("Expected a GOTO label name");
                }
                return new IfNode(symbol, target);
            }
            else if (token.equalsIgnoreCase("GOTO")) {
                consume();
                String target = consume();
                if (target == null || target.endsWith(":")) {
                    throw new RuntimeException("Expected a GOTO label name");
                }
                return new GotoNode(target);
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

    public List<String> storeCommands(Path filePath) {
        List<String> list = new ArrayList<>();

        try (Scanner read = new Scanner(filePath, "UTF-8")) {
            while (read.hasNextLine()) {
                String line = read.nextLine();
                if (!line.isBlank()) {
                    list.add(line);  
                }
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }   

        return list;
    }

    public List<StmtNode> parse() {
        String sourceCode = "";
        Path filePath = Paths.get("TuringMachine.txt");

        commandLines = storeCommands(filePath);

        try {
            sourceCode = Files.readString(filePath);
        } catch (IOException e) {
            throw new IllegalAccessError("Could not read 'TuringMachine.txt': " + e.getMessage());
        }

        List<String> tokens = sourceCode.isBlank()
                ? new ArrayList<>()
                : Arrays.asList(sourceCode.trim().split("\\s+"));

        Parser parser = new Parser(tokens);
        List<StmtNode> program = parser.parseProgram();
        labels = parser.getLabels();

        System.out.println("Successfully parsed " + program.size() + " statements from TuringMachine.txt");

        return program;
    }
}
