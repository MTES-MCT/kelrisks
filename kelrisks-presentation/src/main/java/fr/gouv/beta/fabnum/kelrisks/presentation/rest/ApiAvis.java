package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.commun.facade.dto.JsonInfoDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.ShortUrlDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.IGestionAvisFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionCommuneFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionGeoDataGouvFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionShortUrlFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;

@RestController
@Api(tags = {"API Avis Generator"}, description = "API permettant la génération d'avis")
public class ApiAvis extends AbstractBasicApi {
    
    @Autowired
    IGestionAvisFacade        gestionAvisFacade;
    @Autowired
    IGestionCommuneFacade     gestionCommuneFacade;
    @Autowired
    IGestionShortUrlFacade    gestionShortUrlFacade;
    @Autowired
    IGestionGeoDataGouvFacade gestionGeoDataGouvFacade;
    
    public ApiAvis() {
        // Rien à faire
    }
    
    @GetMapping("/api/url")
    public Response getUrl(@RequestParam("code") String code) {
        
        ShortUrlDTO shortUrlDTO = gestionShortUrlFacade.rechercherResultatAvecCode(code);
    
        if (shortUrlDTO == null) { return Response.status(422).build(); }
        
        return Response.ok(shortUrlDTO).build();
    }
    
    @GetMapping("/api/avis/adresse")
    @ApiOperation(value = "Requête retournant un avis à partir de l'adresse.", response = AvisDTO.class)
    public Response avisParAdresse(@ApiParam(required = true, name = "geolocAdresse", value = "Géolocalisation de l'adresse (x.xxxx|y.yyyy)")
                                   @RequestParam(value = "geolocAdresse") String geolocAdresse,
                                   @ApiParam(required = true, name = "codeINSEE", value = "Code INSEE de la commune.")
                                   @RequestParam("codeINSEE") String codeINSEE,
                                   @ApiParam(name = "nomAdresse", value = "Adresse.")
                                   @RequestParam(value = "nomAdresse", required = false) String nomAdresse,
                                   @ApiParam(name = "nomProprietaire", value = "Nom du propriétaire / Raison sociale.")
                                   @RequestParam(value = "nomProprietaire", required = false) String nomProprietaire) {
        
        return avis(codeINSEE, geolocAdresse, nomAdresse, null, nomProprietaire);
    }
    
    @GetMapping("/api/avis/parcelle")
    @ApiOperation(value = "Requête retournant un avis à partir de la parcelle.", response = AvisDTO.class)
    public Response avisParParcelle(@ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                    @RequestParam("codeParcelle") String codeParcelle,
                                    @ApiParam(required = true, name = "codeINSEE", value = "Code INSEE de la commune.")
                                    @RequestParam("codeINSEE") String codeINSEE,
                                    @ApiParam(name = "nomProprietaire", value = "Nom du propriétaire / Raison sociale.")
                                    @RequestParam(value = "nomProprietaire", required = false) String nomProprietaire) {
        
        return avis(codeINSEE, null, null, codeParcelle, nomProprietaire);
    }
    
    @GetMapping("/api/avis/coordonnees")
    @ApiOperation(value = "Requête retournant un avis à partir de coordonnées (SRID 4326).", response = AvisDTO.class)
    public Response avisParCoordonnees(@ApiParam(required = true, name = "longitude", value = "Longitude.")
                                       @RequestParam("longitude") String longitude,
                                       @ApiParam(required = true, name = "latitude", value = "Latitude.")
                                       @RequestParam("latitude") String latitude) {
        
        CommuneDTO communeDTO = gestionGeoDataGouvFacade.rechercherCommune(latitude, longitude);
        
        return avis(communeDTO.getCodeINSEE(), longitude + "|" + latitude, null, null, null);
    }
    
    @GetMapping("/api/avis/surface")
    @ApiOperation(value = "Requête retournant un avis à partir d'une géométrie (SRID 4326).", response = AvisDTO.class)
    public Response avisParSurface(@ApiParam(required = true, name = "geoJSON", value = "un geoJSON.")
                                   @RequestParam("geojson") String geojson) {
        
        AvisDTO avisDTO = gestionAvisFacade.rendreAvis(geojson);
        
        return Response.ok(avisDTO).build();
    }
    
    @ApiOperation(value = "Requête retournant un avis au format pdf à partir de l'adresse.")
    @GetMapping(value = "/api/avis/adresse/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> avisParAdressePdf(@ApiParam(required = true, name = "geolocAdresse", value = "Géolocalisation de l'adresse (x.xxxx|y.yyyy)")
                                                    @RequestParam(value = "geolocAdresse") String geolocAdresse,
                                                    @ApiParam(required = true, name = "codeINSEE", value = "Code INSEE de la commune.")
                                                    @RequestParam("codeINSEE") String codeINSEE,
                                                    @ApiParam(name = "nomAdresse", value = "Adresse.")
                                                    @RequestParam(value = "nomAdresse", required = false) String nomAdresse,
                                                    @ApiParam(name = "nomProprietaire", value = "Nom du propriétaire / Raison sociale.")
                                                    @RequestParam(value = "nomProprietaire", required = false) String nomProprietaire) {
        
        return avisPdf(null, geolocAdresse, nomAdresse, null, nomProprietaire);
    }
    
    @ApiOperation(value = "Requête retournant un avis au format pdf à partir de la parcelle.")
    @GetMapping(value = "/api/avis/parcelle/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> avisParParcellePdf(@ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                                     @RequestParam("codeParcelle") String codeParcelle,
                                                     @ApiParam(required = true, name = "codeINSEE", value = "Code INSEE de la commune.")
                                                     @RequestParam("codeINSEE") String codeINSEE,
                                                     @ApiParam(name = "nomProprietaire", value = "Nom du propriétaire / Raison sociale.")
                                                     @RequestParam(value = "nomProprietaire", required = false) String nomProprietaire) {
        
        return avisPdf(codeINSEE, null, null, codeParcelle, nomProprietaire);
    }
    
    @GetMapping("/api/avis")
    public Response avis(@RequestParam("codeINSEE") String codeINSEE,
                         @RequestParam(value = "geolocAdresse") String geolocAdresse,
                         @RequestParam(value = "nomAdresse", required = false) String nomAdresse,
                         @RequestParam("codeParcelle") String codeParcelle,
                         @RequestParam(value = "nomProprietaire", required = false) String nomProprietaire) {
        
        String      url         = getUrl(codeINSEE, geolocAdresse, nomAdresse, codeParcelle, nomProprietaire);
        ShortUrlDTO shortUrlDTO = gestionShortUrlFacade.rechercherResultatAvecUrl(url);
        
        if (shortUrlDTO == null) {
            shortUrlDTO = new ShortUrlDTO();
            shortUrlDTO.setCode(RandomStringUtils.random(5, true, true));
            shortUrlDTO.setUrl(url);
        }
        
        if (codeParcelle != null && !codeParcelle.equals("")) {
    
            if (StringUtils.isEmpty(codeINSEE)) {
                JsonInfoDTO jsonInfoDTO = new JsonInfoDTO();
                jsonInfoDTO.addWarning("Dans le cas d'une recherche par code parcelle, merci de choisir une commune parmi les résultats proposés dans le champ.");
                return Response.ok(jsonInfoDTO).build();
            }
            
            codeParcelle = getParcelleCode(codeINSEE, codeParcelle);
    
            if (codeParcelle == null) {
                JsonInfoDTO jsonInfoDTO = new JsonInfoDTO();
                jsonInfoDTO.addError("La parcelle n'a pas été trouvée ¯\\_(ツ)_/¯");
                jsonInfoDTO.addInfo("Il peut arriver que certaines parcelles n'existent pas encore dans Kelrisks. Merci de réessayer plus tard ou de nous le signaler.");
                return Response.ok(jsonInfoDTO).build();
            }
        }
        
        if ((codeParcelle == null || codeParcelle.equals("")) && (geolocAdresse == null || geolocAdresse.equals(""))) {
            
            JsonInfoDTO jsonInfoDTO = new JsonInfoDTO();
            jsonInfoDTO.addError("Merci d'entrer un code parcelle ou de choisir une adresse parmi les résultats proposés dans le champ.");
            return Response.ok(jsonInfoDTO).build();
        }
    
        CommuneDTO communeDTO = gestionCommuneFacade.rechercherCommuneAvecCodeINSEE(codeINSEE);
        
        AvisDTO avisDTO = gestionAvisFacade.rendreAvis(codeParcelle, communeDTO, nomAdresse, geolocAdresse, nomProprietaire);
        
        avisDTO.getSummary().setCodeUrl(shortUrlDTO.getCode());
        
        if (shortUrlDTO.getId() == null) { gestionShortUrlFacade.save(shortUrlDTO); }
        
        return Response.ok(avisDTO).build();
    }
    
    @GetMapping("/api/avis/pdf")
    @ResponseBody
    public ResponseEntity<byte[]> avisPdf(@RequestParam("codeINSEE") String codeINSEE,
                                          @RequestParam(value = "geolocAdresse") String geolocAdresse,
                                          @RequestParam(value = "nomAdresse", required = false) String nomAdresse,
                                          @RequestParam("codeParcelle") String codeParcelle,
                                          @RequestParam(value = "nomProprietaire", required = false) String nomProprietaire) {
        
        codeParcelle = getParcelleCode(codeINSEE, codeParcelle);
    
        CommuneDTO communeDTO = gestionCommuneFacade.rechercherCommuneAvecCodeINSEE(codeINSEE);
        
        AvisDTO avisDTO = gestionAvisFacade.rendreAvis(codeParcelle, communeDTO, nomAdresse, geolocAdresse, nomProprietaire);
        
        try {
            File baseAvis = ResourceUtils.getFile("classpath:avis.html");
            
            org.jsoup.nodes.Document htmlDocument = Jsoup.parse(baseAvis, StandardCharsets.UTF_8.name());
    
            redigerAnalyse(htmlDocument, avisDTO, codeINSEE);
            
            String html = htmlDocument.outerHtml();
            
            ByteArrayInputStream  byteArrayInputStream  = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            
            WriterProperties writerProperties = new WriterProperties();
            PdfWriter        pdfWriter        = new PdfWriter(byteArrayOutputStream, writerProperties);
            
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setCharset(StandardCharsets.UTF_8.name());
            
            HtmlConverter.convertToPdf(byteArrayInputStream, pdfWriter, converterProperties);
    
            return ResponseEntity.ok()
                           .header("Content-Disposition",
                                   "attachment; filename=Kelrisks_Parcelle_" + avisDTO.getSummary().getCodeParcelle() + "_(" + avisDTO.getSummary().getCommune().getCodePostal() + ").pdf")
                           .body(byteArrayOutputStream.toByteArray());
        }
        catch (Exception e) {
            // TODO : Catcher cette exception correctement !
            e.printStackTrace();
        }
        
        return null;
    }
    
    private String getUrl(String codeINSEE,
                          String geolocAdresse,
                          String nomAdresse,
                          String codeParcelle,
                          String nomProprietaire) {
        
        codeINSEE = codeINSEE.equalsIgnoreCase("null") || codeINSEE.equalsIgnoreCase("undefined") ? "" : codeINSEE;
        geolocAdresse = geolocAdresse.equalsIgnoreCase("null") || geolocAdresse.equalsIgnoreCase("undefined") ? "" : geolocAdresse;
        nomAdresse = nomAdresse.equalsIgnoreCase("null") || nomAdresse.equalsIgnoreCase("undefined") ? "" : nomAdresse;
        codeParcelle = codeParcelle.equalsIgnoreCase("null") || codeParcelle.equalsIgnoreCase("undefined") ? "" : codeParcelle;
        nomProprietaire = nomProprietaire.equalsIgnoreCase("null") || nomProprietaire.equalsIgnoreCase("undefined") ? "" : nomProprietaire;
        
        return codeParcelle + "|&|" + codeINSEE + "|&|" + nomAdresse + "|&|" + geolocAdresse + "|&|" + nomProprietaire;
    }
    
    private void redigerAnalyse(Document htmlDocument, AvisDTO avisDTO, String codeINSEE) {
    
        Element element = htmlDocument.select("#date").first();
    
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE);
        element.text("Le " + simpleDateFormat.format(new Date()));
    
        element = htmlDocument.select("#infos").first();
    
        CommuneDTO communeDTO = gestionCommuneFacade.rechercherCommuneAvecCodeINSEE(codeINSEE);
        element.append("n°" + avisDTO.getSummary().getCodeParcelle() + " à " + communeDTO.getNomCommune() + " (" + communeDTO.getCodePostal() + ")");
        
        redigerAnalyseParcelle(htmlDocument, avisDTO);
    
        redigerConclusion(htmlDocument, avisDTO);
        
        redigerAnalyseComplementaire(htmlDocument, avisDTO);
    }
    
    private void redigerConclusion(Document htmlDocument, AvisDTO avisDTO) {
    
        int conclusionNumber = getConclusionNumber(avisDTO);
        
        Element element;
        element = htmlDocument.select("#conclusion").first();
    
        if (conclusionNumber == 0) {
            element.append("<p class=\"indent\">Au regard de ces éléments, le propriétaire ou le bailleur n'est tenu à aucune obligation réglementaire en terme d'information acquéreur locataire au " +
                           "titre des pollutions de sols d’origine industrielle.</p>");
        }
        if (conclusionNumber == 1) {
            element.append("<p class=\"indent\">En cas de vente, le propriétaire est donc tenu de communiquer ces informations à l'acquéreur ou au locataire conformément à la réglementation en " +
                           "vigueur (article L. 514-20 du code de l’environnement et L 125 - 7 du code de l’Environnement si positif SIS)</p>");
            element.append("<p class=\"indent\">En outre, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de " +
                           "destination du bien), la réalisation d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions.  Nous vous" +
                           " rappelons que l'obligation de faire appel à un bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 ne " +
                           "concerne que les attestations prévues aux articles L. 556-1 et L. 556-2 du code de l'environnement.</p>");
            element.append("<p class=\"indent\">Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont " +
                           "répertoriés par le COFRAC (www.cofrac.fr) : à ce jour seul le LNE est accrédité et la liste des bureaux d'études certifiés par le LNE est disponible en cliquant sur ce " +
                           "lien (<a href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'>https://www.lne" +
                           ".fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr</a>)</p>");
        }
        if (conclusionNumber == 2) {
            element.append("<p class=\"indent\">En cas de vente, le propriétaire est donc tenu de communiquer ces informations à l'acquéreur conformément aux articles L. 514-20 du code de " +
                           "l’environnement.</p>");
            element.append("<p class=\"indent\">Par ailleurs, ces informations ne préjugent pas d'une éventuelle pollution de la parcelle pour laquelle la recherche a été faite.</p>");
            element.append("<p class=\"indent\">Toutefois, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de " +
                           "destination du bien), la réalisation d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions.  Nous vous" +
                           " rappelons que l'obligation de faire appel à un bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 ne " +
                           "concerne que les attestations prévues aux articles L. 556-1 et L. 556-2 du code de l'environnement.</p>");
            element.append("<p class=\"indent\">Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont " +
                           "répertoriés par le COFRAC (www.cofrac.fr) : à ce jour seul le LNE est accrédité et la liste des bureaux d'études certifiés par le LNE est disponible en cliquant sur ce " +
                           "lien (<a href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'>https://www.lne" +
                           ".fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr</a>)</p>");
        }
        if (conclusionNumber == 3) {
            element.append("<p class=\"indent\">Ces informations ne préjugent pas d'une éventuelle pollution de la parcelle pour laquelle la recherche a été faite.</p>");
            element.append("<p class=\"indent\">Toutefois, compte tenu de ce qui précède, nous recommandons, en cas de changement d'usage du terrain (travaux, constructions, ou changement de " +
                           "destination du bien), la réalisation d'une étude historique ou d'un diagnostic de sols dans un souci d'une meilleure prise en compte d'éventuelles pollutions.  Nous vous" +
                           " rappelons que l'obligation de faire appel à un bureau d'étude certifié (ou équivalent) dans le domaine des sites et sols pollués conformément à la norme NF X 31-620 ne " +
                           "concerne que les attestations prévues aux articles L. 556-1 et L. 556-2 du code de l'environnement.</p>");
            element.append("<p class=\"indent\">Les bureaux d’études certifiés sont disponibles sur les sites internet du ou des organismes de certification accrédités. Ce ou ces organismes sont " +
                           "répertoriés par le COFRAC (www.cofrac.fr) : à ce jour seul le LNE est accrédité et la liste des bureaux d'études certifiés par le LNE est disponible en cliquant sur ce " +
                           "lien (<a href='https://www.lne.fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr'>https://www.lne" +
                           ".fr/recherche-certificats/search/systems/S1/220/S2/220/S3/239/lang/fr</a>)</p>");
        }
    }
    
    private int getConclusionNumber(AvisDTO avisDTO) {
        
        int conclusionNumber = -1;
        
        if (avisDTO.getSiteIndustrielBasiasSurParcelleDTOs().size() == 0 &&
            avisDTO.getSiteIndustrielBasolSurParcelleDTOs().size() == 0 &&
            avisDTO.getInstallationClasseeSurParcelleDTOs().size() == 0 &&
            avisDTO.getSecteurInformationSolSurParcelleDTOs().size() == 0) { conclusionNumber = 0; }
        
        if (avisDTO.getSiteIndustrielBasiasSurParcelleDTOs().size() == 0 &&
            avisDTO.getSiteIndustrielBasolSurParcelleDTOs().size() == 0 &&
            avisDTO.getInstallationClasseeSurParcelleDTOs().size() == 0 &&
            avisDTO.getSecteurInformationSolSurParcelleDTOs().size() == 0 &&
            avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs().size() > 0) { conclusionNumber = 3; }
        
        if (avisDTO.getSiteIndustrielBasiasSurParcelleDTOs().size() > 0 ||
            avisDTO.getSiteIndustrielBasolSurParcelleDTOs().size() > 0 ||
            avisDTO.getInstallationClasseeSurParcelleDTOs().size() > 0 ||
            avisDTO.getSecteurInformationSolSurParcelleDTOs().size() > 0) { conclusionNumber = 1; }
        
        if (avisDTO.getSiteIndustrielBasiasSurParcelleDTOs().size() > 0 &&
            avisDTO.getSiteIndustrielBasolSurParcelleDTOs().size() == 0 &&
            avisDTO.getInstallationClasseeSurParcelleDTOs().size() == 0 &&
            avisDTO.getSecteurInformationSolSurParcelleDTOs().size() == 0) { conclusionNumber = 2; }
        
        return conclusionNumber;
    }
    
    private void redigerAnalyseParcelle(Document htmlDocument, AvisDTO avisDTO) {
        
        redigerBasiasParcelle(htmlDocument, avisDTO);
        
        redigerBasolParcelle(htmlDocument, avisDTO);
        
        redigerInstallationClasseeParcelle(htmlDocument, avisDTO);
    
        redigerSISParcelle(htmlDocument, avisDTO);
    
        redigerBasiasProximiteParcelle(htmlDocument, avisDTO);
    }
    
    private void redigerSISParcelle(Document htmlDocument, AvisDTO avisDTO) {
        
        Element element;
        element = htmlDocument.select("#sisParcelle").first();
        int numberOf = avisDTO.getInstallationClasseeSurParcelleDTOs().size();
        if (numberOf == 0) {
            element.append("N’est pas située en secteur d’information sur les sols (SIS).");
        }
        else {
            element.append("Est située en secteur d’information sur les sols (SIS).");
        }
    }
    
    private void redigerInstallationClasseeParcelle(Document htmlDocument, AvisDTO avisDTO) {
        
        Element element;
        element = htmlDocument.select("#icParcelle").first();
        int numberOf = avisDTO.getInstallationClasseeSurParcelleDTOs().size();
        if (numberOf == 0) {
            element.append("N’est pas référencée dans l\'inventaire des installations classées en fonctionnement ou arrêtées.");
        }
        else {
            element.append("Est référencée dans l\'inventaire des installations classées sous le nom de :");
            element = element.appendElement("ul");
            for (InstallationClasseeDTO site : avisDTO.getInstallationClasseeSurParcelleDTOs()) {
                element.appendElement("li").append(site.getNom());
            }
        }
    }
    
    private void redigerBasolParcelle(Document htmlDocument, AvisDTO avisDTO) {
        
        Element element;
        element = htmlDocument.select("#basolParcelle").first();
        int numberOf = avisDTO.getSiteIndustrielBasolSurParcelleDTOs().size();
        if (numberOf == 0) {
            element.append("N’est pas référencée dans l\'inventaire des sites pollués BASOL.");
        }
        else {
            element.append("Est référencée dans l\'inventaire des sites pollués BASOL.");
            element = element.appendElement("ul");
            for (SiteIndustrielBasolDTO site : avisDTO.getSiteIndustrielBasolSurParcelleDTOs()) {
                element.appendElement("li").append(" - <a href='https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp=" + site.getNumerobasol() + "'>https://basol" +
                                                   ".developpement-durable.gouv.fr/fiche.php?page=1&index_sp=" + site.getNumerobasol() + "</a>");
            }
        }
    }
    
    private void redigerBasiasProximiteParcelle(Document htmlDocument, AvisDTO avisDTO) {
        
        Element element;
        element = htmlDocument.select("#basiasProximiteParcelle").first();
        int numberOf = avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs().size() + avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().size();
        if (numberOf == 0) {
            element.remove();
        }
        else {
            element.append("Par ailleurs nous identifions, ");
            if (avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs().size() > 0) {
                element.append("dans le voisinage immédiat de la (ou des) parcelle(s), " +
                               (avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs().size() == 1 ? "un site" : "des sites") + " ayant " +
                               "accueilli par le passé une activité susceptible d\'avoir pu générer une pollution des sols (BASIAS). Vous pouvez consulter " +
                               (avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs().size() == 1 ? "la fiche consacrée à cette activité industrielle à l\'adresse suivante : " :
                                "les fiches consacrées à cette activité industrielle aux adresses suivantes : "));
                element = element.appendElement("ul");
                for (SiteIndustrielBasiasDTO site : avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs()) {
                    element.appendElement("li").append(" - <a href='http://fiches-risques.brgm.fr/georisques/basias-synthetique/" + site.getIdentifiant() + "'>http://fiches-risques.brgm" +
                                                       ".fr/georisques/basias-synthetique/" + site.getIdentifiant() + "</a>");
                }
            }
            if (avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().size() > 0) {
                element.append("un site dont la localisation est imprécise mais ayant potentiellement appartenu au même propriétaire (" + avisDTO.getSummary().getNomProprietaire() + ") : ");
                element = element.appendElement("ul");
                for (SiteIndustrielBasiasDTO site : avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs()) {
                    element.appendElement("li").append(" - <a href='http://fiches-risques.brgm.fr/georisques/basias-synthetique/" + site.getIdentifiant() + "'>http://fiches-risques.brgm" +
                                                       ".fr/georisques/basias-synthetique/" + site.getIdentifiant() + "</a>");
                }
            }
        }
    }
    
    private void redigerBasiasParcelle(Document htmlDocument, AvisDTO avisDTO) {
        
        Element element;
        element = htmlDocument.select("#basiasParcelle").first();
        int numberOf = avisDTO.getSiteIndustrielBasiasSurParcelleDTOs().size();
        if (numberOf == 0) {
            element.append("N’est pas référencée dans l\'inventaire des sites ayant accueilli par le passé une activité susceptible d\'avoir pu généré une pollution des sols (BASIAS).");
        }
        else {
            element.append("Est référencée dans l\'inventaire des sites ayant accueilli par le passé une activité susceptible d\'avoir pu généré une pollution des sols (BASIAS).");
            element = element.appendElement("ul");
            for (SiteIndustrielBasiasDTO site : avisDTO.getSiteIndustrielBasiasSurParcelleDTOs()) {
                element.appendElement("li").append(" - <a href='http://fiches-risques.brgm.fr/georisques/basias-synthetique/" + site.getIdentifiant() + "'>http://fiches-risques.brgm" +
                                                   ".fr/georisques/basias-synthetique/" + site.getIdentifiant() + "</a>");
            }
        }
    }
    
    private void redigerAnalyseComplementaire(Document htmlDocument, AvisDTO avisDTO) {
        
        if (avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().size() == 0 &&
            avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().size() == 0 &&
            avisDTO.getInstallationClasseeRayonParcelleDTOs().size() == 0 &&
            avisDTO.getInstallationClasseeNonGeorerenceesDTOs().size() == 0) {
            
            htmlDocument.select("#analyseComplementaireWrapper").first().remove();
        }
        else {
            
            if (avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().size() == 0 &&
                avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().size() == 0 &&
                avisDTO.getInstallationClasseeRayonParcelleDTOs().size() == 0) {
                
                htmlDocument.select("#rayonWrapper").first().remove();
            }
            else {
                redigerAvisRayonParcelle(htmlDocument, avisDTO);
            }
            
            if (avisDTO.getInstallationClasseeNonGeorerenceesDTOs().size() == 0) {
                htmlDocument.select("#icCommuneWrapper").first().remove();
            }
            else {
                redigerAvisInstallationsClasseesCommune(htmlDocument, avisDTO);
            }
        }
    }
    
    private void redigerAvisInstallationsClasseesCommune(Document htmlDocument, AvisDTO avisDTO) {
        
        Element element;
        int     numberOf = avisDTO.getInstallationClasseeNonGeorerenceesDTOs().size();
        element = htmlDocument.select("#icCommune").first();
        element.append("Le résultat de cette recherche ne tient pas compte des " + numberOf + " sites identifiés sur la commune qui n’ont pu être géolocalisés faute d’une information suffisante : ");
        element = element.appendElement("ul");
        for (InstallationClasseeDTO site : avisDTO.getInstallationClasseeNonGeorerenceesDTOs()) {
            element.appendElement("li").append(" - " + site.getNom());
        }
    }
    
    private void redigerAvisRayonParcelle(Document htmlDocument, AvisDTO avisDTO) {
        
        Element element;
        element = htmlDocument.select("#basiasRayon").first();
        int numberOf = avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().size();
        if (numberOf == 0) {
            element.remove();
        }
        else {
            if (numberOf == 1) {
                element.append("Se trouve 1 site Basias dont la fiche est consultable à l'adresse suivante :");
            }
            else {
                element.append("Se trouvent " + numberOf + " sites Basias dont les fiches sont consultables en cliquant sur les liens suivants :");
            }
            element = element.appendElement("ul");
            for (SiteIndustrielBasiasDTO site : avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs()) {
                element.appendElement("li").append(" - <a href='http://fiches-risques.brgm.fr/georisques/basias-synthetique/" + site.getIdentifiant() + "'>http://fiches-risques.brgm" +
                                                   ".fr/georisques/basias-synthetique/" + site.getIdentifiant() + "</a>");
            }
        }
        
        element = htmlDocument.select("#basolRayon").first();
        numberOf = avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().size();
        if (numberOf == 0) {
            element.remove();
        }
        else {
            if (numberOf == 1) {
                element.append("Se trouve 1 site Basol dont la fiche est consultable à l'adresse suivante :");
            }
            else {
                element.append("Se trouvent " + numberOf + " sites Basol dont les fiches sont consultables en cliquant sur les liens suivants :");
            }
            element = element.appendElement("ul");
            for (SiteIndustrielBasolDTO site : avisDTO.getSiteIndustrielBasolRayonParcelleDTOs()) {
                element.appendElement("li").append(" - <a href='https://basol.developpement-durable.gouv.fr/fiche.php?page=1&index_sp=" + site.getNumerobasol() + "'>https://basol" +
                                                   ".developpement-durable.gouv.fr/fiche.php?page=1&index_sp=" + site.getNumerobasol() + "</a>");
            }
        }
        
        element = htmlDocument.select("#icRayon").first();
        numberOf = avisDTO.getInstallationClasseeRayonParcelleDTOs().size();
        if (numberOf == 0) {
            element.remove();
        }
        else {
            if (numberOf == 1) {
                element.append("Se trouve 1 installation classée :");
            }
            else {
                element.append("Se trouvent " + numberOf + " installations classées :");
            }
            element = element.appendElement("ul");
            for (InstallationClasseeDTO site : avisDTO.getInstallationClasseeRayonParcelleDTOs()) {
                element.appendElement("li").append(" - " + site.getNom());
            }
        }
    }
}