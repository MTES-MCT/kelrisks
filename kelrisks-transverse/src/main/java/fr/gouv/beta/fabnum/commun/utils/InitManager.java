package fr.gouv.beta.fabnum.commun.utils;


import fr.gouv.beta.fabnum.commun.transverse.referentiel.ConstantesCommunes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * On utilise le loader de spring car il est capable de :
 * - charger des fichiers UTF-8
 * - les recharger dynamiquement
 */
public final class InitManager {
    
    /**
     * Le chemin de l'application.
     */
    private static String contextPath;
    
    /**
     * Le Bundle correspondant à <code>application.properties</code>.
     */
    private static ReloadableResourceBundleMessageSource messageSource;
    
    /**
     * Champ : ResourceBundle regleGestionBundle :
     * Bundle contenant le fichier ressource des règles de gestion
     */
    private static ReloadableResourceBundleMessageSource regleGestionMessageSource;
    
    /**
     * Champ : String jasperDirectory :
     * Chemin du répertoire des fichiers jasper
     */
    private static String jasperDirectory;
    
    
    /**
     * Field map.
     */
    private static Map<String, String> map = new HashMap<>();
    private        String              bundleFileName;
    private        String              regleGestionBundleFileName;
    
    
    public InitManager() {
        
        super();
    }
    
    /**
     * Method getBundle.
     *
     * @return ResourceBundle
     */
    public static MessageSource getMessageSource() {
        
        return InitManager.messageSource;
    }
    
    /**
     * Instanciation de l'objet Properties.
     *
     * @param fileName Nom du fichier properties
     */
    public static void initBundle(final String fileName) {
        
        messageSource = new ReloadableResourceBundleMessageSource();
        
        messageSource.setBasenames(fileName);
        setDefaultProperties(messageSource);
    }
    
    public static void initRegleGestionBundle(final String fileName) {
        
        regleGestionMessageSource = new ReloadableResourceBundleMessageSource();
        
        regleGestionMessageSource.setBasenames(fileName);
        setDefaultProperties(regleGestionMessageSource);
    }
    
    /**
     * Récupération de la propriété liée à la clé passée en paramètre.
     *
     * @param key La clé d'accès au libellé associé du fichier properties
     *
     * @return La propriété correspondant à la clé
     */
    public static String getProperty(final String key) {
        
        String value = InitManager.map.get(key);
        if (value == null) {
            value = InitManager.getMessageSource().getMessage(key, null, null);
        }
        return value;
    }
    
    public static String getRegleGestion(final String key) {
        
        return InitManager.getRegleGestionMessageSource().getMessage(key, null, null);
    }
    
    /**
     * @param key   La clé
     * @param value La valeur
     */
    public static void setProperty(final String key, final String value) {
        
        InitManager.map.put(key, value);
    }
    
    /**
     * Getter pour le champ regleGestionBundle :
     *
     * @return la valeur de regleGestionBundle
     */
    public static MessageSource getRegleGestionMessageSource() {
        
        return regleGestionMessageSource;
    }
    
    public static String getJasperRepository() {
        
        return jasperDirectory;
    }
    
    public static void setJasperDirectory(String jasperDirectory) {
        
        InitManager.jasperDirectory = jasperDirectory;
    }
    
    public static String getContextPath() {
        
        return contextPath;
    }
    
    public static void setContextPath(String contextPath) {
        
        InitManager.contextPath = contextPath;
    }
    
    private static void setDefaultProperties(ReloadableResourceBundleMessageSource messageSource) {
        
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(ConstantesCommunes.CACHE_PROPERTIES);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setAlwaysUseMessageFormat(true);
    }
    
    public void initialize() {
        
        initBundle(bundleFileName);
        initRegleGestionBundle(regleGestionBundleFileName);
    }
    
    public void setBundleFileName(String bundleFileName) {
        
        this.bundleFileName = bundleFileName;
    }
    
    public void setRegleGestionBundleFileName(String regleGestionBundleFileName) {
        
        this.regleGestionBundleFileName = regleGestionBundleFileName;
    }
}
