package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.commun.facade.dto.JsonInfoDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.ShortUrlDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
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
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;

@RestController
@Api(tags = {"API Avis Generator"}, value = "API permettant la génération d'avis")
public class ApiAvis extends AbstractBasicApi {
    
    @Autowired
    IGestionAvisFacade        gestionAvisFacade;
    @Autowired
    IGestionCommuneFacade     gestionCommuneFacade;
    @Autowired
    IGestionShortUrlFacade    gestionShortUrlFacade;
    @Autowired
    IGestionGeoDataGouvFacade gestionGeoDataGouvFacade;
    @Autowired
    PdfRedactor               pdfRedactor;
    
    public ApiAvis() {
        // Rien à faire
    }
    
    @ApiOperation(value = "Requête retournant une recherche à partir d'une URL courte.", hidden = true)
    @GetMapping("/api/url")
    public Response getUrl(@RequestParam("code") String code) {
        
        ShortUrlDTO shortUrlDTO = gestionShortUrlFacade.rechercherResultatAvecCode(code);
        
        if (shortUrlDTO == null) { return Response.status(422).build(); }
        
        return Response.ok(shortUrlDTO).build();
    }
    
    @GetMapping("/api/avis/parcelle")
    @ApiOperation(value = "Requête retournant un avis à partir de la parcelle.", response = AvisDTO.class)
    public Response avisParParcelle(@ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                    @RequestParam("codeParcelle") String codeParcelle,
                                    @ApiParam(required = true, name = "codeINSEE", value = "Code INSEE de la commune.")
                                    @RequestParam("codeINSEE") String codeINSEE,
                                    @ApiParam(name = "nomProprietaire", value = "Nom du propriétaire / Raison sociale.")
                                    @RequestParam(value = "nomProprietaire", required = false) String nomProprietaire) {
    
        return avis(codeINSEE, null, codeParcelle);
    }
    
    @GetMapping("/api/avis/coordonnees")
    @ApiOperation(value = "Requête retournant un avis à partir de coordonnées (SRID 4326).", response = AvisDTO.class)
    public Response avisParCoordonnees(@ApiParam(required = true, name = "longitude", value = "Longitude.")
                                       @RequestParam("longitude") String longitude,
                                       @ApiParam(required = true, name = "latitude", value = "Latitude.")
                                       @RequestParam("latitude") String latitude) {
    
        CommuneDTO communeDTO = gestionGeoDataGouvFacade.rechercherCommune(latitude, longitude);
    
        if (communeDTO == null) { return Response.status(Response.Status.BAD_REQUEST).entity("Les coordonnées sont probablement erronées, aucune commune n'a été trouvé.").build(); }
    
        ParcelleDTO parcelleDTO = gestionParcelleFacade.rechercherParcelleAvecCoordonnees(Double.parseDouble(longitude), Double.parseDouble(latitude));
    
        return avis(communeDTO.getCodeINSEE(), "", parcelleDTO.getCode());
    }
    
    @GetMapping("/api/avis/surface")
    @ApiOperation(value = "Requête retournant un avis à partir d'une géométrie au format GeoJSON (SRID 4326).", response = AvisDTO.class, hidden = true)
    public Response avisParSurface(@ApiParam(required = true, name = "geoJSON", value = "un geoJSON.")
                                   @RequestParam("geojson") String geojson) {
        
        AvisDTO avisDTO = gestionAvisFacade.rendreAvis(geojson);
        
        return Response.ok(avisDTO).build();
    }
    
    @ApiOperation(value = "Requête permettant de rendre un avis.", hidden = true)
    @GetMapping("/api/avis")
    public Response avis(@RequestParam("codeINSEE") String codeINSEE,
                         @RequestParam(value = "nomAdresse", required = false) String nomAdresse,
                         @RequestParam("codeParcelle") String codeParcelle) {
        
        if (codeParcelle == null || codeParcelle.equals("")) {
            
            JsonInfoDTO jsonInfoDTO = new JsonInfoDTO();
            jsonInfoDTO.addError("Merci d'entrer un code parcelle ou de choisir une adresse parmi les résultats proposés dans le champ.");
            return Response.ok(jsonInfoDTO).build();
        }
        
        String      url         = getUrl(codeINSEE, nomAdresse, codeParcelle);
        ShortUrlDTO shortUrlDTO = gestionShortUrlFacade.rechercherResultatAvecUrl(url);
        
        if (shortUrlDTO == null) {
            shortUrlDTO = new ShortUrlDTO();
            shortUrlDTO.setCode(RandomStringUtils.random(5, true, true));
            shortUrlDTO.setUrl(url);
        }
        
        List<ParcelleDTO> parcelleDTOs = getParcelles(codeParcelle);
        
        if (parcelleDTOs == null || parcelleDTOs.isEmpty()) {
            JsonInfoDTO jsonInfoDTO = new JsonInfoDTO();
            jsonInfoDTO.addError("Une parcelle n'a pas été trouvée ¯\\_(ツ)_/¯");
            jsonInfoDTO.addInfo("Il peut arriver que certaines parcelles n'existent pas encore dans Kelrisks. Merci de réessayer plus tard ou de nous le signaler.");
            return Response.ok(jsonInfoDTO).build();
        }
        
        CommuneDTO communeDTO = gestionCommuneFacade.rechercherCommuneComplete(codeINSEE);
        
        AvisDTO avisDTO = gestionAvisFacade.rendreAvis(parcelleDTOs, communeDTO, nomAdresse);
        
        avisDTO.getSummary().setCodeUrl(shortUrlDTO.getCode());
        
        if (shortUrlDTO.getId() == null) { gestionShortUrlFacade.save(shortUrlDTO); }
        
        return Response.ok(avisDTO).build();
    }
    
    @ApiOperation(value = "Requête permettant de rendre un avis pdf.", hidden = true)
    @PostMapping("/api/avis/pdf")
    @ResponseBody
    public ResponseEntity<byte[]> avisPdf(@RequestBody List<PdfRedactor.Png> pngs,
                                          @RequestParam("codeINSEE") String codeINSEE,
                                          @RequestParam(value = "nomAdresse", required = false) String nomAdresse,
                                          @RequestParam("codeParcelle") String codeParcelle,
                                          @RequestParam(value = "errial", required = false) String choixErrial) {
    
        if (codeParcelle == null || codeParcelle.equals("")) {
        
            JsonInfoDTO jsonInfoDTO = new JsonInfoDTO();
            jsonInfoDTO.addError("Merci d'entrer un code parcelle ou de choisir une adresse parmi les résultats proposés dans le champ.");
            return null;
        }
    
        List<ParcelleDTO> parcelleDTOs = getParcelles(codeParcelle);
    
        if (parcelleDTOs == null || parcelleDTOs.isEmpty()) {
            return null;
        }
    
        CommuneDTO communeDTO = gestionCommuneFacade.rechercherCommuneComplete(codeINSEE);
    
        AvisDTO avisDTO = gestionAvisFacade.rendreAvis(parcelleDTOs, communeDTO, nomAdresse);
    
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
        String           fileName         = "ERRIAL_Parcelle_" + codeParcelle + "_" + simpleDateFormat.format(new Date());
    
        try {
            File baseAvis = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "avis.html");
        
            org.jsoup.nodes.Document htmlDocument = Jsoup.parse(baseAvis, StandardCharsets.UTF_8.name());
        
            pdfRedactor.redigerAnalyse(htmlDocument, avisDTO, codeINSEE);
            pdfRedactor.ajouterImages(htmlDocument, pngs);
            pdfRedactor.ajouterQRCode(htmlDocument, avisDTO);
            pdfRedactor.ajouterChoixUtilisateur(htmlDocument, choixErrial);
            pdfRedactor.setFileName(htmlDocument, fileName);
            
            String html = htmlDocument.outerHtml();
            
            ByteArrayInputStream  byteArrayInputStream  = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            
            WriterProperties writerProperties = new WriterProperties();
            PdfWriter        pdfWriter        = new PdfWriter(byteArrayOutputStream, writerProperties);
            
            ConverterProperties converterProperties = new ConverterProperties();
            converterProperties.setCreateAcroForm(true);
            converterProperties.setCharset(StandardCharsets.UTF_8.name());
            
            HtmlConverter.convertToPdf(byteArrayInputStream, pdfWriter, converterProperties);
            
            return ResponseEntity.ok()
                           .header("Content-Disposition",
                                   "attachment; filename=" + fileName + ".pdf")
                           .body(byteArrayOutputStream.toByteArray());
        }
        catch (Exception e) {
            // TODO : Catcher cette exception correctement !
            e.printStackTrace();
        }
        
        return null;
    }
    
    private String getUrl(String codeINSEE,
                          String nomAdresse,
                          String codeParcelle) {
        
        codeINSEE = codeINSEE == null || codeINSEE.equalsIgnoreCase("null") || codeINSEE.equalsIgnoreCase("undefined") ? "" : codeINSEE;
        nomAdresse = nomAdresse == null || nomAdresse.equalsIgnoreCase("null") || nomAdresse.equalsIgnoreCase("undefined") ? "" : nomAdresse;
        codeParcelle = codeParcelle == null || codeParcelle.equalsIgnoreCase("null") || codeParcelle.equalsIgnoreCase("undefined") ? "" : codeParcelle;
        
        return codeParcelle + "|&|" + codeINSEE + "|&|" + nomAdresse;
    }
}