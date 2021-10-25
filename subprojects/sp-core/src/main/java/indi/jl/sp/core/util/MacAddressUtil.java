package indi.jl.sp.core.util;

import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Get the tool class for the server MAC address.
 */
public class MacAddressUtil {

    private static final int MAC_DIGIT = 16;
    private static final int MAC_LENGTH = 12;
    private static final String FULL_ADDRESS;

    static {
        FULL_ADDRESS = getMachineId();
    }

    private MacAddressUtil() {

    }

    /**
     * get full macAddresses
     *
     * @return full macAddresses
     */
    public static String getFullMacAddress() {
        return FULL_ADDRESS;
    }

    /**
     * get First six macAddresses
     *
     * @return compressed  macAddresses
     */
    public static String getCompressedMacAddress() {
        try {
            return compress(getFullMacAddress());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get full macAddresses
     *
     * @return full macAddresses
     */
    private static String getMachineId() {
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                StringBuilder sb = new StringBuilder();
                NetworkInterface ni = e.nextElement();
                byte[] mac = ni.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                for (byte macByte : mac) {
                    String s = Integer.toHexString(macByte & 0xFF);
                    sb.append(s.length() == 1 ? 0 + s : s);
                }
                if (sb.toString().length() <= MAC_LENGTH) {
                    return sb.toString();
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static String compress(String full) {
        Long decimal = Long.parseLong(full, MAC_DIGIT);
        return HexConvertUtil.convert10To64(decimal);
    }

    public static String decompress(String compressed) {
        Long decimal = HexConvertUtil.convert64To10(compressed);
        return StringUtil.leftPad(Long.toHexString(decimal), MAC_LENGTH, '0');
    }

}
