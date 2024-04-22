public final class CameraState {

    public static final int STANDSTILL = 0;
    public static final int FORWARD_WALK = 1;
    public static final int BACKWARD_WALK = 2;
    public static final int FORWARD_RUN = 16;
    public static final int LEFT_TURN = 4;
    public static final int RIGHT_TURN = 8;

    private CameraState() {
    }
    public static boolean isFlagEnabled(final int flag, final int mask) {
        return (flag & mask) != 0;
    }
}
