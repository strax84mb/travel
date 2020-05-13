package com.htec.travel.airports;

import lombok.experimental.UtilityClass;

import java.math.BigInteger;

@UtilityClass
public class CSVUtil {

    BigInteger toBigInteger(String number) {
        return new BigInteger(number);
    }

    Double toDouble(String val) {
        return "\\N".equals(val) ? null : Double.parseDouble(val);
    }

    Integer toInt(String val) {
        return "\\N".equals(val) ? null : Integer.parseInt(val);
    }

    String textOrNull(String text) {
        return "\\N".equals(text) ? null : text;
    }

    AirportDst toDST(String text) {
        return "\\N".equals(text) ? null : AirportDst.valueOf(text);
    }

    boolean toBoolean(String yesNo) {
        return "Y".equals(yesNo);
    }
}
