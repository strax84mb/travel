package com.htec.travel.security;

import lombok.experimental.UtilityClass;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public class MD5HashingUtil {

    public String md5(byte[] bytes) {
        try {
            var md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            var hash = md.digest();
            return DatatypeConverter.printHexBinary(hash).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
