package indi.jl.sp.core.util;


/**
 * Approach to the conversion tool.
 * Now supports the decimal system into 32.
 * The JDK supports 16 - in, 8 - in, 2 - in conversion.
 */
public class HexConvertUtil {


    private static final char[] DIGITS = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };


    private HexConvertUtil() {

    }

    /**
     * Convert the decimal number into 64.
     *
     * @param number Decimal number
     * @return Hexadecimal number 64
     */
    public static String convert10To64(long number) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = DIGITS[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);
        return new String(buf, charPos, 64 - charPos);
    }

    /**
     * Convert 64 into 10.
     *
     * @param theSystem64 64 into the system
     * @return Decimal number
     */
    public static Long convert64To10(String theSystem64) {
        long result = 0;
        for (int i = theSystem64.length() - 1; i >= 0; i--) {
            for (int j = 0; j < DIGITS.length; j++) {
                if (theSystem64.charAt(i) == DIGITS[j]) {
                    result += ((long) j) << 6 * (theSystem64.length() - 1 - i);
                }
            }
        }
        return result;
    }
}
