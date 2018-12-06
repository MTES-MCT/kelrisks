package fr.gouv.beta.fabnum.commun.metier.util;

import fr.gouv.beta.fabnum.commun.transverse.referentiel.ConstantesCommunes;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Classe rassemblant un ensemble de methodes utilitaires pour les messages.
 *
 * @author jean-marc.pelle
 */
public final class MessageUtils {
    
    /** The Constant BUNDLES. */
    private static final Map<String, ResourceBundle> BUNDLES = new HashMap<>();
    
    /**
     * Constructeur.
     */
    private MessageUtils() {
    
    }
    
    /**
     * Renvoie une valeur du fichier properties de messages de validation metier.
     *
     * @param code code
     * @param args args
     *
     * @return msgMetier
     */
    public static String getMsgValidationMetier(final String code, final Object... args) {
        
        return MessageUtils.getPropertyBundle(ConstantesCommunes.BASE_NAME_BUNDLE_MSG_VALIDATION_METIER, code, args);
    }
    
    /**
     * Renvoie une valeur d'un fichier properties.
     *
     * @param bundle bundle
     * @param code   code
     * @param args   args
     *
     * @return propertyBundle
     */
    private static String getPropertyBundle(final String bundle, final String code, final Object... args) {
        
        if (!MessageUtils.BUNDLES.containsKey(code)) {
            final ResourceBundle resourceBundle = ResourceBundle.getBundle(bundle, Locale.FRANCE);
            MessageUtils.BUNDLES.put(bundle, resourceBundle);
        }
        if (MessageUtils.BUNDLES.get(bundle).containsKey(code)) {
            final String valeurBrute = MessageUtils.BUNDLES.get(bundle).getString(code);
            return MessageFormat.format(valeurBrute, args);
        }
        
        return code;
    }
}
