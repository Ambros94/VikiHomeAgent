package Brain.Confidence;


public enum Discrete {
    _ONE, _TWO, _THREE, _FOUR, _FIVE,
    ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN;

    public static Discrete discrete(double value) {
        int x10 = (int) (value * 10);
        return discrete(x10);
    }


    public static Discrete discrete(int value) {
        switch (value) {
            case -5:
                return _FIVE;
            case -4:
                return _FOUR;
            case -3:
                return _THREE;
            case -2:
                return _TWO;
            case -1:
                return _ONE;
            case 0:
                return ZERO;
            case 1:
                return ONE;
            case 2:
                return TWO;
            case 3:
                return THREE;
            case 4:
                return FOUR;
            case 5:
                return FIVE;
            case 6:
                return SIX;
            case 7:
                return SEVEN;
            case 8:
                return EIGHT;
            case 9:
                return NINE;
            case 10:
                return TEN;
            default:
                System.err.println("Value:\t" + value);
                throw new RuntimeException("The code should not arrive here");
        }
    }
}