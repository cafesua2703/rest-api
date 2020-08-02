package com.thuyhn.restapi;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.thuyhn.Greeting;
import com.thuyhn.customer.Customer;
import com.thuyhn.customer.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.ui.Model;

/**
 * The type Greeting controller.
 */
@RestController 
public class GreetingController {
    
    private final AtomicLong counter = new AtomicLong();
    

    /**
     * Greeting greeting.
     *
     * @param paymentId the payment id
     * @param userId    the user id
     * @return the greeting
     * @throws NoSuchPaddingException             the no such padding exception
     * @throws InvalidKeyException                the invalid key exception
     * @throws NoSuchAlgorithmException           the no such algorithm exception
     * @throws IllegalBlockSizeException          the illegal block size exception
     * @throws BadPaddingException                the bad padding exception
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     */
    @GetMapping("/encrypt") public Greeting greeting(
            @RequestParam(value = "paymentId", defaultValue = "123") String paymentId,
            @RequestParam(value = "userId", defaultValue = "1") long userId,
            @RequestParam(value = "type", defaultValue = "Adyen") String type)
            throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidAlgorithmParameterException {
        Map<String, Object> createdSessionMap = getCreateSessionMap(paymentId, type);
        JSONObject createdSessionMapJson = new JSONObject(createdSessionMap);
        String data1 = encrypt(createdSessionMapJson.toString());

        Map<String, Object> paymentproceedMap = getPaymentProcessedMap(paymentId, type);
        JSONObject paymentproceedMapJson = new JSONObject(paymentproceedMap);
        String data2 = encrypt(paymentproceedMapJson.toString());

        Map<String, Object> mapData = new HashMap<>();
        mapData.put("data1", data1);
        mapData.put("data2", data2);
        JSONObject mapDataJson = new JSONObject(mapData);

        Map<String, Object> mapDecrpypt = new HashMap<>();
        mapDecrpypt.put("decrpypt1", decrpypt(data1));
        mapDecrpypt.put("decrpypt2", decrpypt(data2));
        JSONObject mapDecrpyptJson = new JSONObject(mapDecrpypt);
        return new Greeting(hashUserId(userId), mapDataJson, mapDecrpyptJson);
    }

    private Map<String, Object> getPaymentProcessedMap(String paymentId, String type) {
        Map<String, Object> accountMap = new HashMap<>();
        String typeProcessed = "Adyen".equalsIgnoreCase(type) ? Constant.ADYEN_AUTHORISED : Constant.GCASH_AUTHORISED;
        accountMap.put("accountNumber", Constant.accountNumber);
        accountMap.put("status", typeProcessed);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(accountMap);
        Map<String, Object> payLoadMap2 = new HashMap<>();
        payLoadMap2.put("accounts", list);
        payLoadMap2.put("paymentId", paymentId);
        Map<String, Object> notificationMap2 = getNotificationMap(Constant.PAYMENT_PROCESSED, payLoadMap2);
        Map<String, Object> paymentproceedMap = new HashMap<>();
        paymentproceedMap.put("notification", notificationMap2);
        return paymentproceedMap;
    }

    private Map<String, Object> getCreateSessionMap(String paymentId, String type) {
        Map<String, Object> payLoadMap1 = new HashMap<>();
        payLoadMap1.put("paymentSession", "paymentSessionTest");
        payLoadMap1.put("paymentId", paymentId);
        String typeProcessed = "Adyen".equalsIgnoreCase(type) ?
                Constant.ADYEN_PAYMENT_SESSION_CREATED :
                Constant.PAYMENT_SESSION_GCASH_CREATED;
        Map<String, Object> notificationMap1 = getNotificationMap(typeProcessed, payLoadMap1);
        Map<String, Object> createdSessionMap = new HashMap<>();
        createdSessionMap.put("notification", notificationMap1);
        return createdSessionMap;
    }

    /**
     * Gets notification map.
     *
     * @param type       the type create
     * @param payLoadMap the pay load map
     * @return the notification map
     */
    private Map<String, Object> getNotificationMap(String type, Map<String, Object> payLoadMap) {
        Map<String, Object> notificationMap = new HashMap<>();
        notificationMap.put("name", type);
        notificationMap.put("payload", payLoadMap);
        return notificationMap;
    }

    /**
     * Hash user id string.
     *
     * @param uid the uid
     * @return the string
     */
    public static String hashUserId(long uid) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((uid + Constant.SALT).getBytes());
            byte[] data = md.digest();
            BigInteger n = new BigInteger(1, data);
            StringBuilder text = new StringBuilder(n.toString(16));
            while (text.length() < 32) {
                text.insert(0, "0");
            }
            return text.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Encrypt string.
     *
     * @param original the original
     * @return the string
     * @throws NoSuchPaddingException             the no such padding exception
     * @throws NoSuchAlgorithmException           the no such algorithm exception
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     * @throws InvalidKeyException                the invalid key exception
     * @throws BadPaddingException                the bad padding exception
     * @throws IllegalBlockSizeException          the illegal block size exception
     */
    public static String encrypt(String original)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        IvParameterSpec iv = new IvParameterSpec(Constant.IV_KEY.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(Constant.SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] result = cipher.doFinal(original.getBytes());
        return Base64.getEncoder().encodeToString(result);
    }

    /**
     * Decrpypt string.
     *
     * @param encrypted the encrypted
     * @return the string
     * @throws NoSuchPaddingException             the no such padding exception
     * @throws NoSuchAlgorithmException           the no such algorithm exception
     * @throws InvalidAlgorithmParameterException the invalid algorithm parameter exception
     * @throws InvalidKeyException                the invalid key exception
     * @throws BadPaddingException                the bad padding exception
     * @throws IllegalBlockSizeException          the illegal block size exception
     */
    public static String decrpypt(String encrypted)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        IvParameterSpec iv = new IvParameterSpec(Constant.IV_KEY.getBytes());
        SecretKeySpec skeySpec = new SecretKeySpec(Constant.SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes()));
        return new String(result);
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }
}
