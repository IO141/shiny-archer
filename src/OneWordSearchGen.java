import java.util.*;


/* Incomplete */
public class OneWordSearchGen {
    private int DIM;
    private Tile[][] grid;

    public OneWordSearchGen(final int DIM) {
        this.DIM = DIM;
        this.grid = new Tile[DIM][DIM];
        for(int i = 0; i < DIM; i++) {
            for(int j = 0; j < DIM; j++) {
                this.grid[i][j] = new Tile(new Coordinate(i, j), this);
            }
        }
        for(int i = 0; i < DIM; i++) {
            for(int j = 0; j < DIM; j++) {
                this.grid[i][j].initNeighbors();
            }
        }
    }

    public void genGrid(String WORD) {
        boolean hasDuplicates = hasDuplicates(WORD);
        HashSet<String> wordSet = new HashSet<>();
        Tile prev = null;
        for(int i = 0; i < WORD.length(); i++) {
            wordSet.add(Character.toString(WORD.charAt(i)));
        }
        for(int i = 0; i < DIM; i++) {
            for(int j = 0; j < DIM; j++) {
                double rand = Math.random();
                if(i == 0 && j ==0) {
                    this.grid[i][j].setLetter(letterToPick(wordSet, rand));
                    prev = tileAtCoord(new Coordinate(i, j));
                } else {
                    String letter = letterToPick(wordSet, rand);
                    if(!hasDuplicates && Objects.equals(prev.getLetter(), letter)) {
                        assert letter != null;
                        char c = letter.charAt(0);
                        rand = Math.random();
                        letter = letterToPick(wordSet, rand);
                        wordSet.add(Character.toString(c));
                    }
                    this.grid[i][j].setLetter(letter);
                    prev = tileAtCoord(new Coordinate(i, j));
                }
            }
        }
    }

    private String letterToPick(HashSet<String> wordSet, double rand) {
        int count = 0;
        int index = (int)Math.round(rand * (wordSet.size() - 1));
        for(String s : wordSet) {
            if(index == count) {
                return s;
            }
            count++;
        }
        return null;
    }

    private boolean hasDuplicates(String WORD) {
        char prev;
        for(int i = 1; i < WORD.length(); i++) {
            prev = WORD.charAt(i-1);
            if(WORD.charAt(i) == prev) {
                return true;
            }
        }
        return false;
    }

    public int findSolutions(String WORD, int solutions, String test,
                             int dir, int index) {
        for(int i = 0; i < DIM; i++) {
            for(int j = 0; j < DIM; j++) {
                Coordinate coor = new Coordinate(i, j);
                Tile tile = tileAtCoord(coor);
                if(Objects.equals(Character.toString(WORD.charAt(index)), tile.getLetter())) {
                    test += tile.getLetter();
                    if(Objects.equals(test, WORD)) {
                        solutions += 1;
                        return solutions;
                    } else {
                        for(Tile nbr : tile.getNeighbors()) {
                            if(Objects.equals(nbr.getLetter(), Character.toString(WORD.charAt(index)))) {
                                if(dir == -1) {
                                    for(int k = 0; k < 8; k++) {
                                        if(tile.neighbors[k] == nbr) {
                                            dir = k;
                                        }
                                    }
                                }
                                findSolutions(WORD, solutions, test+=nbr.getLetter(), dir, index++);
                                return solutions;
                            }
                        }
                        dir = -1;
                        index = 0;
                        test = "";
                    }
                }
            }
        }
        return solutions;
    }

    /**
     *
     * Returns the tile associated with the given coordinate.
     *
     * @param coord - The coordinate of the tile to be returned
     *
     * @return The tile located at the given coordinate
     *
     */
    private Tile tileAtCoord( Coordinate coord ){
        return this.grid[ coord.getRow() ][ coord.getCol() ];
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Enter word: ");
        final String WORD = in.next().toUpperCase();
        final int DIM = WORD.length() * 2;

        OneWordSearchGen wordSearch = new OneWordSearchGen(DIM);
        while(wordSearch.findSolutions(WORD, 0, "", -1, 0) != 1) {
            wordSearch.genGrid(WORD);
        }

        System.out.println(WORD + "  " + DIM);
        for(int i = 0; i < DIM; i++) {
            for(int j = 0; j < DIM; j++) {
                System.out.print(wordSearch.tileAtCoord(new Coordinate(i, j)).getLetter() + " ");
            }
            System.out.print("\n");
        }
    }

    /**
     *
     * Tile Class represents one Square
     * on the Quoridor Board and holds
     * some knowledge about its state such as
     * who is occupying its space, the ID of
     * the occupant, the Board it is a part of
     * and the Coordinate it is at
     *
     * @author - Jared Bosco
     * @author - Brian Duffy
     *
     */
    private class Tile {

        private Tile[] neighbors = new Tile[8]; // The neighbors of the tile, Max: 8
        private Coordinate coor; // The coordinate of the tile
        private OneWordSearchGen grid; // The grid to which the tile belongs
        private String letter; // Letter at tile location

        /**
         *
         * Constructor to make a basic tile.
         *
         * The connections are created later; for now they
         * are all standalone with no occupants, occupant ids or neighbors.
         *
         * @param coor The coordinate location of this tile
         *
         */
        public Tile( Coordinate coor, OneWordSearchGen grid) {

            this.coor = coor;
            this.grid = grid;
            this.letter = "";

            for( int i = 0; i < 8; i++ ){
                neighbors[i] = null;
            }

        }

        /**
         *
         * Performs a Deep Copy of a Tile and the state of
         * all its connections.
         *
         * @param tileToCopy - The Tile being copied
         *
         */
        public Tile ( Tile tileToCopy ){

            this.letter = tileToCopy.getLetter();
            this.coor = tileToCopy.getCoor();
            this.grid = tileToCopy.getGrid();

            for( int i = 0; i < 8; i++ ){
                this.neighbors[i] = tileToCopy.neighbors[i];
            }

        }

        /**
         *
         * Function to have a tile 'look' around it and use a Board method
         * to decide if there is a tile at this position.  If there is, it is added.
         *
         * 0 - North ( above )
         * 1 - East  ( right )
         * 2 - South ( below )
         * 3 - West  ( left  )
         * 4 - NW    ( above-left  )
         * 5 - NE    ( above-right )
         * 6 - SE    ( below-right )
         * 7 - SW    ( below-left  )
         */
        public void initNeighbors() {
            HashSet<Coordinate> set = new HashSet<>();
            if((coor.getRow() - 1) >= 0) {
                set.add(new Coordinate(coor.getRow() - 1, coor.getCol()));
            }
            if((coor.getRow() + 1) < DIM){
                set.add(new Coordinate(coor.getRow() + 1, coor.getCol()));
            }

            if((coor.getCol() + 1) < DIM){
                set.add(new Coordinate(coor.getRow(), coor.getCol() + 1));
            }

            if((coor.getCol() - 1) >= 0){
                set.add(new Coordinate(coor.getRow(), coor.getCol() - 1));
            }
            if(coor.getRow() != 0) {
                if(coor.getCol() != DIM-1) {
                    set.add(new Coordinate(coor.getRow() - 1, coor.getCol() + 1));
                }
                if(coor.getCol() != 0){
                    set.add(new Coordinate(coor.getRow() - 1, coor.getCol() - 1));
                }
            }
            if(coor.getRow() != DIM-1) {
                if(coor.getCol() != DIM-1) {
                    set.add(new Coordinate(coor.getRow() + 1, coor.getCol() + 1));
                }
                if(coor.getCol() != 0) {
                    set.add(new Coordinate(coor.getRow() + 1, coor.getCol() - 1));
                }
            }
            if(coor.getCol() != 0) {
                if(coor.getRow() != DIM-1) {
                    set.add(new Coordinate(coor.getRow() + 1, coor.getCol() - 1));
                }
                if(coor.getRow() != 0) {
                    set.add(new Coordinate(coor.getRow() - 1, coor.getCol() - 1));
                }
            }
            if(coor.getCol() != DIM-1) {
                if(coor.getRow() != DIM-1) {
                    set.add(new Coordinate(coor.getRow() + 1, coor.getCol() + 1));
                }
                if(coor.getRow() != 0) {
                    set.add(new Coordinate(coor.getRow() - 1, coor.getCol() + 1));
                }
            }
            if(set.contains(new Coordinate(coor.getRow() - 1, coor.getCol()))) {
                neighbors[0] = grid.tileAtCoord(new Coordinate(coor.getRow() - 1, coor.getCol()));
            }
            if(set.contains(new Coordinate(coor.getRow(), coor.getCol() + 1))) {
                neighbors[1] = grid.tileAtCoord(new Coordinate(coor.getRow(), coor.getCol() + 1));
            }
            if(set.contains(new Coordinate(coor.getRow() + 1, coor.getCol()))) {
                neighbors[2] = grid.tileAtCoord(new Coordinate(coor.getRow() + 1, coor.getCol()));
            }
            if(set.contains(new Coordinate(coor.getRow(), coor.getCol() - 1))) {
                neighbors[3] = grid.tileAtCoord(new Coordinate(coor.getRow(), coor.getCol() - 1));
            }
            if(set.contains(new Coordinate(coor.getRow() - 1, coor.getCol() - 1))) {
                neighbors[4] = grid.tileAtCoord(new Coordinate(coor.getRow() - 1, coor.getCol() - 1));
            }
            if(set.contains(new Coordinate(coor.getRow() - 1, coor.getCol() + 1))) {
                neighbors[5] = grid.tileAtCoord(new Coordinate(coor.getRow() - 1, coor.getCol() + 1));
            }
            if(set.contains(new Coordinate(coor.getRow() + 1, coor.getCol() + 1))) {
                neighbors[6] = grid.tileAtCoord(new Coordinate(coor.getRow() + 1, coor.getCol() + 1));
            }
            if(set.contains(new Coordinate(coor.getRow() + 1, coor.getCol() - 1))) {
                neighbors[7] = grid.tileAtCoord(new Coordinate(coor.getRow() + 1, coor.getCol() - 1));
            }
        }

        /**
         *
         * This function returns a set of neighbors.
         * It collects non-null neighbors into a set and returns it.
         * (null -- no neighbor, either an edge of the board or wall blocking.)
         *
         * @return A set of tiles that are neighbors to the current tile
         *
         */
        public Set<Tile> getNeighbors() {
            Set<Tile> neighborSet = new HashSet<Tile>();
            for(int i = 0; i < 8; i++){
                if(this.neighbors[i] != null){
                    neighborSet.add(neighbors[i]);
                }
            }

            return neighborSet;
        }

        /**
         *
         * Method to set the letter of a tile.
         *
         * The has letter is now true
         *
         * @param letter - letter of the tile
         *
         */
        public void setLetter( String letter ){
            this.letter = letter;
        }

        public String getLetter() {
            return this.letter;
        }

        /**
         *
         * Method to reset letter of a tile.
         * hasLetter is now false
         *
         */
        public void resetLetter() {

            this.letter = "";

        }

        /**
         *
         * Returns whether the tile is occupied or not.
         *
         * @return Whether the tile is occupied or not
         *
         */
        public boolean hasLetter(){ return !Objects.equals(this.letter, ""); }

        /**
         *
         * Removes a neighbor by setting that 'direction' to null.
         *
         * @param orientation - [0,3] representing which way to 'look' to remove that tile
         *
         */
        public void remove(int orientation){

            this.neighbors[orientation] = null;

        }

        /**
         *
         * Getter method for the coordinate of this tile.
         *
         * @return The coordinate location of this tile
         *
         */
        public Coordinate getCoor(){ return this.coor; }

        /**
         *
         * Getter method for the board this tile belongs to.
         *
         * @return The board this tile belongs to
         *
         */
        public OneWordSearchGen getGrid() { return this.grid; }
    }

    private static class Coordinate {
        public static final int BOARD_DIM = 9;
        private final int row;
        private final int col;

        public Coordinate(Coordinate coordinate) {
            this.row = coordinate.getRow();
            this.col = coordinate.getCol();

            assert this.row >= 0;

            assert this.col >= 0;

            assert this.row <= 9;

            assert this.col <= 9;

        }

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;

            assert this.row >= 0 : "Bad row: " + row;
            assert this.col >= 0 : "Bad column: " + col;
            assert this.row <= 9 : "Bad row: " + row;
            assert this.col <= 9 : "Bad column: " + col;

        }

        public int getRow() {
            return this.row;
        }

        public int getCol() {
            return this.col;
        }

        public boolean equals(Object obj) {
            if(obj == null) {
                return false;
            } else {
                try {
                    Coordinate e = (Coordinate)obj;
                    return this.row == e.getRow() && this.col == e.getCol();
                } catch (ClassCastException var3) {
                    return false;
                }
            }
        }

        public int hashCode() {
            return this.row + 10 * this.col;
        }

        public String toString() {
            return "[" + this.row + ", " + this.col + "]";
        }
    }
}
