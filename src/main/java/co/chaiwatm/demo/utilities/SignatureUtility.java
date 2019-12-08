package co.chaiwatm.demo.utilities;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import org.springframework.http.*;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.chaiwatm.demo.models.*;
import lombok.*;

@Data
public class SignatureUtility {
    public static HttpHeaders GetResponseHeader(GenericResponse response) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String json = gson.toJson(response);
        String xSignature = GetSignature(json);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Signature", xSignature);

        return headers;
    }

    private static String GetSignature(String text) {
        String secret = "8a64c35116e906b79d0763d0354b8a5e7ad31515c0774e8cb9a3e2034fc0219f";
        // byte[] byteKey = secret.getBytes();
        byte[] byteKey = hexStringToByteArray(secret);
        Key key = new SecretKeySpec(byteKey, "HMACSHA512");

        try {
            Mac hmacSha512 = Mac.getInstance("HMACSHA512");
            hmacSha512.init(key);
            byte[] result = hmacSha512.doFinal(text.getBytes());

            return toHexString(result);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return formatter.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];

        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }
}