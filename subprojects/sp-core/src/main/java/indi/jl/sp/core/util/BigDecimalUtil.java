package indi.jl.sp.core.util;

import indi.jl.sp.core.exception.SpSystemException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {

    /**
     * 除法计算
     *
     * @param divider  除数
     * @param dividend 被除数
     * @param scale    精度 默认0 四舍五入
     * @return 结果
     */
    public static BigDecimal divide(long divider, long dividend, int scale) {
        if (0 == dividend) {
            throw new SpSystemException("被除数不能为0");
        }
        return new BigDecimal(divider).divide(new BigDecimal(dividend), scale, RoundingMode.HALF_UP);
    }
}
