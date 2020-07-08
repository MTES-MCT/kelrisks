package fr.gouv.beta.fabnum.commun.metier.util;

import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityHelper {
    
    private static final int         KEY_LENGTH          = 128;
    private static final int         PASSWORD_ITERATIONS = 1000; // vs brute force
    public               CipherSpecs cipherSpecs         = new CipherSpecs();
    //    private              String      passPhrase;
    private              Cipher      cipher;
    private              char[]      pass;
    
    public SecurityHelper(CipherSpecs cipherSpecs, String passPhrase, boolean encryptMode) {
        
        try {
            this.cipherSpecs = cipherSpecs;
            this.pass = passPhrase.toCharArray();
            this.cipher = createCipher(cipherSpecs, encryptMode);
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
    
    public SecurityHelper(String passPhrase) {
        
        try {
            initSalt();
            this.pass = passPhrase.toCharArray();
            this.cipher = createCipher(cipherSpecs, true);
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
    
    //        activity.securityHelperEncoder = new SecurityHelper(account.cipherSpecs, this.passPhrase, true);
    //        activity.securityHelperDecoder = new SecurityHelper(account.cipherSpecs, this.passPhrase, false);
    
    public static void main(String[] args) {
        
        // Encode
        SecurityHelper encoder = new SecurityHelper("***securitypassphrase***");
        String         hash    = encoder.encodeAndPrependIVSalt("Un ange passe.");
        System.out.println(hash);
        
        // Decode
        CipherSpecs cipherSpecsDecode = new CipherSpecs();
        cipherSpecsDecode.IV = Base64.getDecoder().decode(hash.split("###")[0]);
        cipherSpecsDecode.salt = Base64.getDecoder().decode(hash.split("###")[1]);
        String encodedText = hash.split("###")[2];
        
        SecurityHelper decoder     = new SecurityHelper(cipherSpecsDecode, "***securitypassphrase***", false);
        String         decodedText = decoder.decode(encodedText);
        
        System.out.println(decodedText);
        
        // Decode
        hash = "VfRFWdHquIPU+4XmI27IUQ==###nPWfc6u25YLko1FtYEVLpA==###XXLBul+85gc9YUJnpESTlCBDiulqY6z5f6uYbsGTkmYAHOCbs2gV8M4BKNT23k0rQXzn1AZb59uw9IMxBQQrDtolYl76IsJQLs4CZaB7isaXMDHCrGcJEWdXdl" +
               "+NdHWsJEaDlexFoMvgFcYfJkQSFtx/ol7tDGp09CeipwEACQhf0dfuQbMgBSHQE3J32CMvte308H5hdilwelvanHkSA94G/FnT8Qx0W6vQJPtZtKo=";
        cipherSpecsDecode.IV = Base64.getDecoder().decode(hash.split("###")[0]);
        cipherSpecsDecode.salt = Base64.getDecoder().decode(hash.split("###")[1]);
        encodedText = hash.split("###")[2];
        
        decoder = new SecurityHelper(cipherSpecsDecode, "***securitypassphrase***", false);
        decodedText = decoder.decode(encodedText);
        
        System.out.println(decodedText);
    }
    
    public String decode(String encodedText) {
        
        if (encodedText == null) { return null; }
        
        try {
            return new String(cipher.doFinal(Base64.getDecoder().decode(encodedText)), StandardCharsets.UTF_8);
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
            System.out.println("Could not decode !");
        }
        
        return encodedText;
    }
    
    public String encode(String plainText) {
        
        if (plainText == null) { return null; }
        
        try {
            return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        }
        catch (GeneralSecurityException e) {
            e.printStackTrace();
            System.out.println("Could not encode !");
        }
        
        return "";
    }
    
    public String encodeAndPrependIVSalt(String plainText) {
        
        String encodedText = encode(plainText);
        
        String encoded = Base64.getEncoder().encodeToString(cipherSpecs.IV) + "###" +
                         Base64.getEncoder().encodeToString(cipherSpecs.salt) + "###" +
                         encodedText;
        
        System.out.println(encoded);
        
        return encoded;
    }
    
    private Cipher createCipher(CipherSpecs cipherSpecs, boolean encryptMode) throws GeneralSecurityException {
        
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec       spec    = new PBEKeySpec(pass, cipherSpecs.salt, PASSWORD_ITERATIONS, KEY_LENGTH);
        
        SecretKey     secretKey = factory.generateSecret(spec);
        SecretKeySpec secret    = new SecretKeySpec(secretKey.getEncoded(), "AES");
        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        int    mode   = encryptMode ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE;
        
        if (cipherSpecs.IV == null) {
            
            cipher.init(mode, secret);
            AlgorithmParameters params = cipher.getParameters();
            cipherSpecs.IV = params.getParameterSpec(IvParameterSpec.class).getIV();
        }
        else {
            
            cipher.init(mode, secret, new IvParameterSpec(cipherSpecs.IV));
        }
        
        return cipher;
    }
    
    private void initSalt() {
        
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(cipherSpecs.salt);
    }
}