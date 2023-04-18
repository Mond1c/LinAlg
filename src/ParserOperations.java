public enum ParserOperations {
    ADD('+'),
    SUBTRACT('-'),
    MULTIPLY('*'),
    DIVIDE('/'),
    EQUAL('=');

    private char operation;
    ParserOperations(char operation) {
        this.operation = operation;
    }

    public char getOperation() {
        return operation;
    }
}
