package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.commun.metier.util.CipherSpecs;
import fr.gouv.beta.fabnum.commun.metier.util.SecurityHelper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.QueryParam;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiQrCode {
    
    @Value("${kelrisks.app.security.passphrase}")
    String passphrase;
    
    public ApiQrCode() {
        // Rien à faire
    }
    
    @GetMapping(value = "/api/qrcode/check", produces = MediaType.TEXT_HTML_VALUE)
    public String checkQrCode(@QueryParam("hash") String hash) {
        
        CipherSpecs cipherSpecs = new CipherSpecs();
        cipherSpecs.IV = Base64.getDecoder().decode(hash.split("###")[0]);
        cipherSpecs.salt = Base64.getDecoder().decode(hash.split("###")[1]);
        String encodedText = hash.split("###")[2];
        
        SecurityHelper securityHelper = new SecurityHelper(cipherSpecs, passphrase, false);
        String         decodedText    = securityHelper.decode(encodedText);
        
        String[] values = decodedText.split("\\|\\|");
        
        try {
            File baseAvis = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "qrcode.html");
            
            org.jsoup.nodes.Document htmlDocument = Jsoup.parse(baseAvis, StandardCharsets.UTF_8.name());
            
            String html = getQrCodeContent(values);
            
            htmlDocument.select("#qrcode_content").append(html);
            
            return htmlDocument.outerHtml();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return "<!DOCTYPE html>\n" +
               "<html lang=\"fr\">\n" +
               "    <head>\n" +
               "        <meta content=\"text/html; charset=utf-8\"\n" +
               "              http-equiv=\"Content-Type\"/>" +
               "    </head>\n" +
               "    <body>\n" +
               "        <h1>Erreur lors de la vérification du Code QR</h1>\n" +
               "    <body>\n" +
               "</html>";
    }
    
    private String getQrCodeContent(String[] values) {
        
        String html = "";
        
        html += "      <p>" + "Nombre de Basias sur la parcelle" + " : " + values[0].split("==")[0] + "</p>\n";
        if (values[0].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[0].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Basias dans un rayon de 500m" + " : " + values[1].split("==")[0] + "</p>\n";
        if (values[1].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[1].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Basias dans une parcelle contigüe" + " : " + values[2].split("==")[0] + "</p>\n";
        if (values[2].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[2].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Basias non géoréférencés dans la commune" + " : " + values[3].split("==")[0] + "</p>\n";
        if (values[3].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[3].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Basol sur la parcelle" + " : " + values[4].split("==")[0] + "</p>\n";
        if (values[4].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[4].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Basol dans un rayon de 500m" + " : " + values[5].split("==")[0] + "</p>\n";
        if (values[5].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[5].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Basol dans une parcelle contigüe" + " : " + values[6].split("==")[0] + "</p>\n";
        if (values[6].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[6].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Basol non géoréférencés dans la commune" + " : " + values[7].split("==")[0] + "</p>\n";
        if (values[7].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[7].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Secteurs d'Information Sol sur la parcelle" + " : " + values[8].split("==")[0] + "</p>\n";
        if (values[8].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[8].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Secteurs d'Information Sol dans un rayon de 500m" + " : " + values[9].split("==")[0] + "</p>\n";
        if (values[9].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[9].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Secteurs d'Information Sol dans une parcelle contigüe" + " : " + values[10].split("==")[0] + "</p>\n";
        if (values[10].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[10].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Secteurs d'Information Sol non géoréférencés dans la commune" + " : " + values[11].split("==")[0] + "</p>\n";
        if (values[11].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[11].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Installations classées sur la parcelle" + " : " + values[12].split("==")[0] + "</p>\n";
        if (values[12].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[12].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Installations classées dans un rayon de 500m" + " : " + values[13].split("==")[0] + "</p>\n";
        if (values[13].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[13].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Installations classées dans une parcelle contigüe" + " : " + values[14].split("==")[0] + "</p>\n";
        if (values[14].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[14].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de Installations classées non géoréférencés dans la commune" + " : " + values[15].split("==")[0] + "</p>\n";
        if (values[15].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[15].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Niveau d'argile" + " : " + values[16] + "</p>\n";
        html += "      <p>" + "Nombre de Nombre de PPRs" + " : " + values[17].split("==")[0] + "</p>\n";
        if (values[17].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[17].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Nombre de TRIs" + " : " + values[18] + "</p>\n";
        html += "      <p>" + "Nombre de AZIs" + " : " + values[19] + "</p>\n";
        html += "      <p>" + "Nombre de canalisations" + " : " + values[20] + "</p>\n";
        html += "      <p>" + "Nombre d'installations nucléaires" + " : " + values[21].split("==")[0] + "</p>\n";
        if (values[21].split("==").length > 1) {
            html += "      <ul>" + Stream.of(values[21].split("==")[1].split(",")).map(s -> "         <li>" + s + "</li>\n").collect(Collectors.joining()) + "</ul>\n";
        }
        html += "      <p>" + "Zonage exposition au bruit" + " : " + values[22] + "</p>\n";
        return html;
    }
}
