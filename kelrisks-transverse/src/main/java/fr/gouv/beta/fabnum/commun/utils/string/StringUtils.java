package fr.gouv.beta.fabnum.commun.utils.string;

import fr.gouv.beta.fabnum.commun.transverse.entities.AbstractEntity;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jaxen.util.SingletonList;

public final class StringUtils {
    
    private static final String HEX_DIGITS = "0123456789abcdef";
    
    public static String generateToken() {
        /*
         * Appelée en boucle, cette méthode est suceptible de s'exécuter
         * deux fois dans la même ms, et donc de renvoyer le même token
         * On le contrôle donc avant de renvoyer le retour.
         */
        String retour = "";
        Random random;
        long   r1;
        long   r2;
        String hash1;
        String hash2;
        random = new Random();
        r1 = random.nextLong();
        r2 = random.nextLong();
        hash1 = Long.toHexString(r1);
        hash2 = Long.toHexString(r2);
        retour = hash1 + hash2;
        return retour;
    }
    
    public static String generateShortToken() {
        /*
         * Version simplifiée qui peut suffire dans certains cas.
         */
        Random random;
        long   r1;
        String hash1;
        random = new Random();
        r1 = random.nextLong();
        hash1 = Long.toHexString(r1);
        return hash1;
    }
    
    /*
     * Une version encore plus courte
     */
    public static String generateVeryShortToken() {
        
        String token = generateShortToken();
        
        return token.substring(0, token.length() / 2);
    }
    
    /**
     * Teste si la chaine de caractère est null ou vide
     *
     * @param aTester
     *
     * @return true si aTester est null ou vide
     */
    public static boolean isEmpty(String aTester) {
        
        if (aTester == null) {
            return true;
        }
        if (aTester.trim().length() == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Teste si la chaine de caractère est null ou vide ou remplie de blanc
     *
     * @param aTester
     *
     * @return true si aTester est null ou aTester.trim est vide
     */
    public static boolean isTrimEmpty(String aTester) {
        
        if (aTester == null) {
            return true;
        }
        if (aTester.trim().length() == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Méthode shaEncode
     *
     * @param toEncode
     *
     * @return : la chaine de caractère encodée en sha1 <br/>
     */
    public static String shaEncode(String toEncode) throws TechniqueException {
        
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(toEncode.getBytes(StandardCharsets.UTF_8));
        }
        catch (NoSuchAlgorithmException e) {
            throw new TechniqueException("StringUtils.shaEncode : alogorithme SHA-1 inexistant", e);
        }
        return toHexString(messageDigest.digest());
    }
    
    /**
     * Méthode beautify
     *
     * @param toBeautify
     *
     * @return : la chaine de caractère toBeautify trimée avec : <br/>
     * Une majuscule au début<br/>
     * Une majuscule après chaque caractère qui n'est pas une lettre
     */
    public static String beautify(String toBeautify) {
        
        if (isEmpty(toBeautify)) { return toBeautify; }
        
        toBeautify = toBeautify.trim().toLowerCase();
        char         ch;
        char         prevCh = '.';
        StringBuffer str    = new StringBuffer();
        for (int i = 0; i < toBeautify.length(); i++) {
            ch = toBeautify.charAt(i);
            if (Character.isLetter(ch) && !(Character.isLetter(prevCh))) {
                str.append(Character.toUpperCase(ch));
            }
            else {
                str.append(ch);
            }
            prevCh = ch;
        }
        return str.toString();
    }
    
    /**
     * Méthode firstCharToLowerCase
     *
     * @param chaine
     *
     * @return : on met en minuscule le premier caractère de la chaine
     */
    public static String firstCharToLowerCase(String chaine) {
        
        char[] chaineTab = chaine.toCharArray();
        chaineTab[0] = Character.toLowerCase(chaineTab[0]);
        
        return new String(chaineTab);
    }
    
    /**
     * Méthode firstCharToLowerCase
     *
     * @param chaine
     *
     * @return : on met en minuscule le premier caractère de la chaine
     */
    public static String firstCharToUpperCase(String chaine) {
        
        char[] chaineTab = chaine.toCharArray();
        chaineTab[0] = Character.toUpperCase(chaineTab[0]);
        
        return new String(chaineTab);
    }
    
    /**
     * Converti le tableau de String en String via un stringBuilder
     *
     * @param strings
     *
     * @return
     */
    public static String buildString(String[] strings) {
        
        StringBuilder strBuilder = new StringBuilder();
        for (String string : strings) {
            strBuilder.append(string);
        }
        return strBuilder.toString();
    }
    
    /**
     * Vérifie si une string respecte un formatage de pattern regex
     *
     * @param chaine de caractère, string de pattern regex
     *
     * @return true ou false
     */
    public static boolean isCorrectFormatPattern(String chaine, String patternRegex) {
        
        boolean retour  = false;
        Pattern pattern = Pattern.compile(patternRegex);
        Matcher matcher = pattern.matcher(chaine);
        if (matcher.find()) { retour = true; }
        return retour;
    }
    
    //    public static boolean isValidEmailAddress(String email) {
    //
    //        boolean result = true;
    //        try {
    //            InternetAddress emailAddr = new InternetAddress(email);
    //            emailAddr.validate();
    //        }
    //        catch (AddressException ex) {
    //            result = false;
    //        }
    //        return result;
    //    }
    
    /**
     * Retourne le toString() de l'objet passé en paramètre s'il n'est pas null ou la chaine "null" sinon
     */
    public static String toStringOrNull(Object o) {
        
        if (o != null) {
            return o.toString();
        }
        else {
            return "null";
        }
    }
    
    /**
     * Retourne le toString() de l'objet passé en paramètre s'il n'est pas null ou la chaine vide "" sinon
     */
    public static String toStringOrEmpty(Object o) {
        
        if (o != null) {
            return o.toString();
        }
        else {
            return "";
        }
    }
    
    /**
     * Retourne le toLightString de l'objet passé en paramètre si la méthode existe, sinon toString si l'objet n'est pas null, sinon ""
     * Si on passe un List d'objet en paramètre, on renvoie toLightString de chaque objet de la liste
     */
    public static String toLightStringOrEmpty(Object o) {
        
        if (o != null) {
            try {
                if (o.getClass() == List.class || o.getClass() == SingletonList.class || o.getClass() == ArrayList.class || o.getClass() == Map.class || o.getClass().getName().equals("java.util" +
                                                                                                                                                                                       ".Arrays$ArrayList")) {
                    List liste = (List) o;
                    if (liste.size() > 0) {
                        Method method;
                        //                        Method method = liste.get(0).getClass().getDeclaredMethod("toLightString");
                        String res = "[";
                        int    i   = 0;
                        for (Object o1 : liste) {
                            method = liste.get(i).getClass().getDeclaredMethod("toLightString");
                            res += method.invoke(liste.get(i++)) + ", ";
                        }
                        return res.substring(0, res.length() - 2) + "]";
                    }
                    else { return ""; }
                }
                else {
                    Method method = o.getClass().getDeclaredMethod("toLightString");
                    return method.invoke(o).toString();
                }
            }
            catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                //                e.printStackTrace();
                //System.out.print(o.getClass().getName());
                return o.toString();
            }
        }
        else {
            return "";
        }
    }
    
    public static String generateNumeroFromEntier(long entier, int precision) {
        
        String finalCode = "";
        int    length    = String.valueOf(entier).length();
        
        if (precision > length) { for (int i = 0; i < precision - length; i++) { finalCode += "0"; } }
        finalCode += entier;
        
        return finalCode;
    }
    
    /**
     * Incrémente la valeur du premier entier rencontré dans la chaine
     */
    public static String incrementePremierEntier(String chaine) {
        
        return incrementeEntier(chaine, "(.*?\\D)?(\\d+)(.*)");
    }
    
    /**
     * Incrémente la valeur du dernier entier rencontré dans la chaine
     */
    public static String incrementeDernierEntier(String chaine) {
        
        return incrementeEntier(chaine, "(.*\\D)?(\\d+)(.*)");
    }
    
    /**
     * Incrémente la valeur l'entier trouvé grâce à la regex
     * La regex fournie doit permettre la capture de 3 blocs.
     * $1 la partie avant l'entier.
     * $2 l'entier.
     * $3 la partie après l'entier.
     */
    public static String incrementeEntier(String chaine, String regex) {
        
        if (chaine == null) { return chaine; }
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chaine);
        if (!matcher.find()) { return chaine; }
        
        String partieAvant = matcher.group(1);
        String entier      = matcher.group(2);
        String partieApres = matcher.group(3);
        
        //        System.out.println(partieAvant + " " + entier + " " + partieApres);
        
        if (entier != null) {
            
            Integer val = Integer.parseInt(entier);
            entier = String.format("%0" + entier.length() + "d", val + 1);
            
            //            System.out.println(partieAvant + entier + partieApres);
            
            chaine = (partieAvant == null ? "" : partieAvant) + entier + (partieApres == null ? "" : partieApres);
            
            return chaine;
        }
        else {
            return chaine;
        }
    }
    
    /**
     * Détecte si une liste contient un doublon
     */
    public static <T> boolean hasDoublonInListe(Iterable<T> all) {
        
        Set<T> set = new HashSet<>();
        for (T each : all) { if (!set.add(each)) { return true; } }
        return false;
    }
    
    public static String toStringBuilder(AbstractEntity entity) {
        
        ReflectionToStringBuilder builder = new ReflectionToStringBuilder(entity, ToStringStyle.SHORT_PREFIX_STYLE) {
            
            @Override
            protected boolean accept(Field field) {
                
                return !AbstractEntity.class.isAssignableFrom(field.getType()) && !Set.class.isAssignableFrom(field.getType());
            }
        };
        
        return builder.toString();
    }
    
    private static String toHexString(byte[] v) {
        
        StringBuffer sb = new StringBuffer(v.length * 2);
        for (int i = 0; i < v.length; i++) {
            int b = v[i] & 0xFF;
            sb.append(HEX_DIGITS.charAt(b >>> 4)).append(HEX_DIGITS.charAt(b & 0xF));
        }
        return sb.toString();
    }
}
