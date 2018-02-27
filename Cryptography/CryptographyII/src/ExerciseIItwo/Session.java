package ExerciseIItwo;

public class Session {
    /**
     * Wrapper class to represent session used for attack
     * When A tries to connect to B it will actually connect to eve,
     * then eve will generate new session with
     */
    private Side side = new Side();
    private Side sideEve = new Side();

    public Side getSide() {
        return side;
    }

    public Side sideEve(){
        return sideEve;
    }
}
