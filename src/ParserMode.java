public enum ParserMode {
    MATRIX_CALCULATION("matrix-calc"),
    FIND_MATRIX_DETERMINANT("det"),
    INVERSE_MATRIX("inverse"),
    TRANSPOSE_MATRIX("transpose"),
    RANK_MATRIX("rank"),
    TRIANGLE_MATRIX("triangle"),
    SOLVE_SYSTEM_OF_EQUATIONS("solve"),
    NONE("none");

    private String value;

    ParserMode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
