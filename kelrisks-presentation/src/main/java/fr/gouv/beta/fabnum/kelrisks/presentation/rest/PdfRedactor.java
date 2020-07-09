package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.commun.metier.util.QRCodeUtils;
import fr.gouv.beta.fabnum.commun.metier.util.SecurityHelper;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationNucleaireDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesGasparDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SecteurInformationSolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionCommuneFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionGeorisquesFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedCatNat;
import lombok.Data;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PdfRedactor {
    
    private static final int ROWS_PER_PAGE = 18;
    @Value("${kelrisks.app.back.path}")
    String                   appPath;
    @Autowired
    IGestionCommuneFacade    gestionCommuneFacade;
    @Autowired
    IGestionGeorisquesFacade gestionGeorisquesFacade;
    @Autowired
    SecurityHelper           securityHelper;
    
    public void redigerAnalyse(Document htmlDocument, AvisDTO avisDTO, String codeINSEE) {
        
        Element element = htmlDocument.select("#date").first();
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM yyyy", Locale.FRANCE);
        element.text("Établi le " + simpleDateFormat.format(new Date()));
        
        element = htmlDocument.select("#commune").first();
        CommuneDTO communeDTO = gestionCommuneFacade.rechercherCommuneAvecCodeINSEE(codeINSEE);
        element.html(communeDTO.getCodePostal().toUpperCase() + " " + communeDTO.getNomCommune());
        
        element = htmlDocument.select("#code-parcelle").first();
        element.html(avisDTO.getSummary().getCodeParcelle());
        
        redigerRisquesPrincipaux(htmlDocument, avisDTO);
        redigerRecommandations(htmlDocument, avisDTO);
        redigerParcelleNonConcerneePar(htmlDocument, avisDTO);
        redigerInformationsAPreciser(htmlDocument, avisDTO);
        redigerAnnexe1AutresRisques(htmlDocument, avisDTO);
        redigerAnnexe2ArretesCatNat(htmlDocument, avisDTO);
        redigerAnnexe3PollutionRayon(htmlDocument, avisDTO);
        
        int pagesNumberOf = htmlDocument.select(".page").size() + 1; // La première page n’a pas de class .page
        htmlDocument.select(".number-of-pages").html(String.valueOf(pagesNumberOf));
    }
    
    public void ajouterImages(Document htmlDocument, List<Png> pngs) {
        
        for (Png png : pngs) {
            
            Elements img = htmlDocument.select("#" + png.name + " .carte img");
            img.attr("src", png.png);
        }
    }
    
    public void ajouterQRCode(Document htmlDocument, AvisDTO avisDTO) throws Exception {
    
        String encodedText = securityHelper.encodeAndPrependIVSalt(avisDTO.toString());
    
        String escapedEncodedText = URLEncoder.encode(encodedText, StandardCharsets.UTF_8.name());
    
        String base64png = QRCodeUtils.generateQRCodePng(appPath + "/api/qrcode/check?hash=" + escapedEncodedText);
    
        Elements img = htmlDocument.select("#qrcode_wrapper img");
        img.attr("src", base64png);
    }
    
    private void redigerAnnexe3PollutionRayon(Document htmlDocument, AvisDTO avisDTO) {
        
        Element page  = addPage(htmlDocument);
        Element tbody;
        int     lines = 0;
        
        page.append("<div><h2>ANNEXE 3 : SITUATION DU RISQUE DE POLLUTION DES SOLS DANS UN RAYON DE 500M AUTOUR DE VOTRE BIEN</h2></div>");
        
        if (avisDTO.getInstallationClasseeRayonParcelleDTOs().size() > 0) {
            page.append("<p>Inventaire des installations classées soumises à autorisation ou à enregistement</p>");
            
            tbody = addTableHtml(page);
            
            for (InstallationClasseeDTO installationClasseeRayonParcelleDTO : avisDTO.getInstallationClasseeRayonParcelleDTOs()) {
                
                addTableBodyRow(tbody, installationClasseeRayonParcelleDTO.getNom(), installationClasseeRayonParcelleDTO.getCode());
                lines++;
            }
            
            if (lines + avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().size() > ROWS_PER_PAGE) {
                page = addPage(htmlDocument);
                lines = 0;
            }
        }
        
        if (avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().size() > 0) {
            page.append("<p>Inventaire des sites ayant accueilli par le passé une activité qui a pu générer une pollution des sols (BASIAS)</p>");
            
            tbody = addTableHtml(page);
            
            for (SiteIndustrielBasiasDTO siteIndustrielBasiasDTO : avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs()) {
                
                addTableBodyRow(tbody, siteIndustrielBasiasDTO.getRaisonSociale(), siteIndustrielBasiasDTO.getIdentifiant());
                lines++;
            }
            
            if (lines + avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().size() + avisDTO.getSecteurInformationSolRayonParcelleDTOs().size() > ROWS_PER_PAGE) {
                page = addPage(htmlDocument);
            }
        }
        
        if (avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().size() + avisDTO.getSecteurInformationSolRayonParcelleDTOs().size() > 0) {
            page.append("<p>Inventaire des sites pollués (Basol, SIS, SUP)</p>");
            
            tbody = addTableHtml(page);
            
            for (SiteIndustrielBasolDTO siteIndustrielBasolDTO : avisDTO.getSiteIndustrielBasolRayonParcelleDTOs()) {
                
                addTableBodyRow(tbody, siteIndustrielBasolDTO.getProprietaire(), siteIndustrielBasolDTO.getIdentifiantbasias());
            }
            
            for (SecteurInformationSolDTO secteurInformationSolDTO : avisDTO.getSecteurInformationSolRayonParcelleDTOs()) {
                
                addTableBodyRow(tbody, secteurInformationSolDTO.getNom(), secteurInformationSolDTO.getFicheRisque());
            }
        }
    }
    
    private void addTableBodyRow(Element tbody, String nom, String url) {
        
        tbody.append("<tr class=\"" + (tbody.select("tr").size() % 2 == 0 ? "" : "even") + "\">" +
                     "<td>" + nom + "</td>" +
                     "<td>" + url + "</td>" +
                     "</tr>\n");
    }
    
    private void addTableBodyCatNatRow(Element tbody, String dateCode, String dateDebut, String dateFin, String dateArrete, String dateJO) {
        
        tbody.append("<tr class=\"" + (tbody.select("tr").size() % 2 == 0 ? "" : "even") + "\">" +
                     "<td>" + dateCode + "</td>" +
                     "<td>" + dateDebut + "</td>" +
                     "<td>" + dateFin + "</td>" +
                     "<td>" + dateArrete + "</td>" +
                     "<td>" + dateJO + "</td>" +
                     "</tr>\n");
    }
    
    private Element addTableHtml(Element page) {
        
        page.append("<table>\n" +
                    "    <thead>\n" +
                    "        <tr>\n" +
                    "            <td><b>Nom du site</b></td>\n" +
                    "            <td><b>Fiche détaillée</b></td>\n" +
                    "        </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody>\n</tbody>\n" +
                    "</table>");
        
        return page.select("table tbody").last();
    }
    
    private Element addTableCatNatHtml(Element page) {
        
        page.append("<table>\n" +
                    "    <thead>\n" +
                    "        <tr>\n" +
                    "            <td><b>Code national CATNAT</b></td>\n" +
                    "            <td><b>Début le</b></td>\n" +
                    "            <td><b>Fin le</b></td>\n" +
                    "            <td><b>Arrêté du</b></td>\n" +
                    "            <td><b>Sur le JO du</b></td>\n" +
                    "        </tr>\n" +
                    "    </thead>\n" +
                    "    <tbody>\n</tbody>\n" +
                    "</table>");
        
        return page.select("table tbody").last();
    }
    
    private void redigerAnnexe2ArretesCatNat(Document htmlDocument, AvisDTO avisDTO) {
        
        Element page         = addPage(htmlDocument);
        int     rowNumbers   = 0;
        int     blockNumbers = 0;
    
        page.append("<div><h2>ANNEXE 2 : LISTE DES ARRÊTÉS PRIS SUR LA COMMUNE</h2></div>");
        
        GeorisquePaginatedCatNat georisquePaginatedCatNat = gestionGeorisquesFacade.rechercherCatNatCommune(avisDTO.getSummary().getCommune().getCodeINSEE());
        
        if (!georisquePaginatedCatNat.getData().isEmpty()) {
            
            page.append("<p>" + "Nombre d'arrêtés de catastrophes naturelles : " + georisquePaginatedCatNat.getData().size() + "</p>");
            
            List<TypeCatNat> typeCatNatList = groupCatNatByType(georisquePaginatedCatNat.getData());
            
            for (TypeCatNat typeCatNat : typeCatNatList) {
                
                if (rowNumbers + typeCatNat.getCatNatList().size() > ROWS_PER_PAGE - blockNumbers * 2) {
                    page = addPage(htmlDocument);
                    page.append("<br/>");
                    rowNumbers = 0;
                    blockNumbers = 0;
                }
                
                page.append("<p>" + typeCatNat.libelleRisqueJO + " : " + typeCatNat.getCatNatList().size() + "</p>");
                
                Element tableBody = addTableCatNatHtml(page);
                
                for (GeorisquePaginatedCatNat.CatNat catNat : typeCatNat.getCatNatList()) {
                    
                    addTableBodyCatNatRow(tableBody,
                                          catNat.getCode_national_catnat(),
                                          catNat.getDate_debut_evt(),
                                          catNat.getDate_fin_evt(),
                                          catNat.getDate_publication_arrete(),
                                          catNat.getDate_publication_jo());
                    
                    rowNumbers++;
                }
                
                blockNumbers++;
            }
        }
    }
    
    private List<TypeCatNat> groupCatNatByType(List<GeorisquePaginatedCatNat.CatNat> catNatList) {
        
        List<TypeCatNat> typeCatNatList = new ArrayList<>();
        
        for (GeorisquePaginatedCatNat.CatNat catNat : catNatList) {
            
            TypeCatNat typeCatNat = getTypeCatNatByType(typeCatNatList, catNat.getLibelle_risque_jo());
            typeCatNat.getCatNatList().add(catNat);
        }
        
        return typeCatNatList;
    }
    
    private TypeCatNat getTypeCatNatByType(List<TypeCatNat> typeCatNatList, String libelleRisqueJo) {
        
        TypeCatNat typeCatNat;
        
        for (TypeCatNat tcn : typeCatNatList) {
            
            if (tcn.libelleRisqueJO.equals(libelleRisqueJo)) { return tcn; }
        }
        
        typeCatNat = new TypeCatNat(libelleRisqueJo);
        typeCatNatList.add(typeCatNat);
        
        return typeCatNat;
    }
    
    private void redigerAnnexe1AutresRisques(Document htmlDocument, AvisDTO avisDTO) {
    
        Element page = addPage(htmlDocument);
    
        page.append("<div><h2>ANNEXE 1 : RISQUES NE FAISANT PAS L’OBJET D’UNE OBLIGATION D’INFORMATION</h2></div>");
    
        if (hasRadonMoyen(avisDTO)) {
            addRisque(htmlDocument,
                      "RADON",
                      appPath + "/pictogrammes_risque/ic_rn_bleu.png",
                      "<p>Le radon est un gaz radioactif naturel inodore, incolore et inerte. Ce gaz est présent partout dans les sols et il s’accumule dans les espaces clos, " +
                      "notamment dans les bâtiments.</p>");
        }
    
        if (hasPollutionNonReglementaire(avisDTO)) {
            addRisque(htmlDocument,
                      "POLLUTION DES SOLS",
                      appPath + "/pictogrammes_risque/ic_basias_bleu.png",
                      "<p>Les pollutions des sols peuvent présenter un risque sanitaire lors des changements d’usage des sols (travaux, aménagements changement " +
                      "d’affectation des terrains) si elles ne sont pas prises en compte dans le cadre du projet.<br/></p><p>Dans un rayon de 500 m autour de " +
                      "votre parcelle, sont identifiés :</p>" +
                      (avisDTO.getInstallationClasseeRayonParcelleDTOs().size() > 0 ? "<p>- " + avisDTO.getInstallationClasseeRayonParcelleDTOs().size() + " " +
                                                                                      "sites référencés dans l’inventaire des installations classées pour la " +
                                                                                      "protection de l’environnement (ICPE)</p>" : "") +
                      (avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().size() > 0 ? "<p>- " + avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().size() + " " +
                                                                                       "sites potentiellement pollués, référencés dans l’inventaire des sites " +
                                                                                       "ayant accueilli par le passé une activité qui a pu générer une " +
                                                                                       "pollution des sols (BASIAS).</p>" : "") +
                      (avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().size() > 0 ? "<p>- " + avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().size() + " " +
                                                                                      "sites pollués (BASOL - terrain pollué appelant une action des pouvoirs " +
                                                                                      "publics à titre curatif ou préventif, SIS - terrain placé en secteur " +
                                                                                      "d’information sur les sols, SUP - terrain pollué affecté d’une " +
                                                                                      "servitude d’utilité publique)</p>" : "</p>") +
                      (!hasPollutionPrincipale(avisDTO) && numberOfParcelleMatches(avisDTO) > 0 ? "<p>" + numberOfParcelleMatches(avisDTO) + " site(s) " +
                                                                                                  "présente(nt) une proximité forte avec votre parcelle. Dans " +
                                                                                                  "le cas où vous souhaiteriez en savoir davantage, il est " +
                                                                                                  "recommandé de faire réaliser une étude historique et, le " +
                                                                                                  "cas échéant, des analyses de sols par un bureau d’étude " +
                                                                                                  "spécialisé dans le domaine des sols pollués.</p>" : "") +
                      (hasPollutionCentroidCommune(avisDTO) ? "<p>Les données disponibles mentionnent enfin la présence d’anciennes activités qui ont " +
                                                              "localisées dans le centre de la commune par défaut. La présente analyse n’en tient donc pas " +
                                                              "compte. Le détail de ces données est consultable ici.</p>" : ""));
        }
        
        if (hasTRI(avisDTO) && !hasPPRi(avisDTO)) {
            addRisque(htmlDocument,
                      "INONDATIONS",
                      appPath + "/pictogrammes_risque/ic_inondation_bleu.png",
                      "<p>Votre bien est situé dans un territoire exposé à un risque important d'inondation (TRI) sur lequel l'Etat et les collectivités territoriales ont engagé une démarche " +
                      "d'identification et de gestion de ce risque pour anticiper et réduire l’impact d'une éventuelle inondation. Pour plus d'information, renseignez-vous auprès de la commune ou " +
                      "consultez le Plan de Gestion des Risques d'Inondation (PGRI)</p>");
        }
        
        if (hasAZI(avisDTO) && !hasTRI(avisDTO) && !hasPPRi(avisDTO)) {
            addRisque(htmlDocument,
                      "INONDATIONS",
                      appPath + "/pictogrammes_risque/ic_inondation_bleu.png",
                      "<p>Votre bien est situé sur une commune figurant dans un atlas des zones inondables qui modélisent les potentiels risques à partir des dernières inondations connues.</p>");
        }
        
        if (avisDTO.getInstallationNucleaireDTOS().size() > 0) {
            addRisque(htmlDocument,
                      "INSTALLATIONS NUCLÉAIRES DE BASE",
                      appPath + "/pictogrammes_risque/ic_nucleaires_bleu.png",
                      "<p>Votre bien est situé à moins de " + (avisDTO.isHasCentraleNucleaire() ? "20 km" : "10 km") + " d’une installation nucléaire" +
                      " de base, installation dans laquelle une certaine quantité de substance ou de matière radioactives est présente (ex. " +
                      "réacteurs nucléaires de production d’électricité (centrale nucléraire), installations de préparation, enrichissement, " +
                      "fabrication, traitement ou entreposage de combustibles nucléaires ; etc.).<p>Ces installations sont contrôlées par " +
                      "l’Autorité de Sureté Nucléaire.</p>" +
                      "<p>Installation(s) concerné(e) : <br/>" + getLibelleInstallationsNucleaires(avisDTO) + "</p>");
        }
        
        if (hasArgile(avisDTO)) {
            addRisque(htmlDocument,
                      "ARGILE",
                      appPath + "/pictogrammes_risque/ic_terre_bleu.png",
                      "<p>Les sols argileux évoluent en fonction de leur teneur en eau. De fortes variations d'eau (sécheresse ou d’apport massif d’eau) peuvent donc fragiliser progressivement les " +
                      "constructions (notamment les maisons individuelles aux fondations superficielles) suite à des gonflements et des tassements du sol, et entrainer des dégâts pouvant être " +
                      "importants. Le zonage \"argile\" identifie les zones exposées à ce phénomène de retrait-gonflement selon leur degré d’aléa.</p>" +
                      (avisDTO.getNiveauArgile() == 3 ? "<p>Aléa fort : La probabilité de survenue d’un sinistre est élevée et l’intensité des phénomènes attendus est forte. Les constructions, " +
                                                        "notamment les maisons individuelles, doivent être réalisées en suivant des prescriptions constructives ad hoc. Pour plus de détails :</br>" +
                                                        "https://www.cohesion-territoires.gouv.fr/sols-argileux-secheresse-et-construction#e3"
                                                      : "") +
                      (avisDTO.getNiveauArgile() == 2 ? "<p>Aléa moyen : La probabilité de survenue d’un sinistre est moyenne, l’intensité attendue étant modérée.  Les constructions, notamment les" +
                                                        " maisons individuelles, doivent être réalisées en suivant des prescriptions constructives ad hoc. Pour plus de détails :</br>" +
                                                        "https://www.cohesion-territoires.gouv.fr/sols-argileux-secheresse-et-construction#e3"
                                                      : "") +
                      (avisDTO.getNiveauArgile() == 1 ? "<p>la survenance de sinistres est possible en cas de sécheresse importante, mais ces désordres ne toucheront qu’une faible proportion des " +
                                                        "bâtiments (en priorité ceux qui présentent des défauts de construction ou un contexte local défavorable, avec par exemple des arbres proches" +
                                                        " ou une hétérogénéité du sous-sol). Il est conseillé, notamment pour la construction d’une maison individuelle, de réaliser une étude de " +
                                                        "sols pour déterminer si des prescriptions constructives spécifiques sont nécessaires. Pour plus de détails :</br>" +
                                                        "https://www.cohesion-territoires.gouv.fr/sols-argileux-secheresse-et-construction#e3" : "") +
                      (avisDTO.getNiveauArgile() == 0 ? "<p>Aléa nul : aucune présence de sols argileux n’a été identifiée selon les cartes géologiques actuelles. Toutefois il peut y avoir des " +
                                                        "poches ponctuelles de sols argileux." : ""));
        }
        
        if (avisDTO.getGeogCanalisations().size() > 0) {
            addRisque(htmlDocument,
                      "CANALISATIONS TRANSPORT DE MATIÈRES DANGEREUSES",
                      appPath + "/pictogrammes_risque/ic_reseaux_canalisation_bleu.png",
                      "<p>Une canalisation de matières dangereuses (gaz naturel, produits pétroliers ou chimiques) est située dans un rayon de 500m autour de" +
                      " votre parcelle. La carte représente les implantations présentes autour de votre localisation.</p>");
        }
    }
    
    private void redigerInformationsAPreciser(Document htmlDocument, AvisDTO avisDTO) {
    
        Element page = addPage(htmlDocument);
    
        page.append("<div id=\"informations-a-preciser\"><h2>INFORMATIONS À PRÉCISER</h2></div>\n");
    
        for (PlanPreventionRisquesGasparDTO planPreventionRisquesDTO : avisDTO.getPlanPreventionRisquesDTOs()) {
        
            page.append("<h3>" + planPreventionRisquesDTO.getAlea().getFamilleAlea().getLibelle().toUpperCase() + "</h3/>\n" +
                        "<p>Rappel du risque : " + planPreventionRisquesDTO.getAlea().getFamilleAlea().getLibelle() + ", " + planPreventionRisquesDTO.getAlea().getLibelle() + ".</p>\n" +
                        "<div class=\"text_wrapper\"><b>Le bien est il concerné par des prescriptions de travaux ?</b></div>\n" +
                        "<div class=\"input_wrapper\">\n" +
                        "    <label><input type=\"checkbox\">Oui</label>\n" +
                        "    <label><input type=\"checkbox\">Non</label>\n" +
                        "</div>\n" +
                        "<div class=\"text_wrapper\"><b>Si oui, les travaux prescrits ont été réalisés ?</b></div>\n" +
                        "<div class=\"input_wrapper\">\n" +
                        "    <label><input type=\"checkbox\">Oui</label>\n" +
                        "    <label><input type=\"checkbox\">Non</label>\n" +
                        "</div>\n");
        }
    
        page.append("<div><h3>INFORMATION RELATIVE AUX SINISTRES INDEMNISÉS PAR L'ASSURANCE SUITE À UNE CATASTROPHE NATURELLE, MINIÈRE OU TECHNOLOGIQUE</h3></div>\n");
    
        page.append("<p>Le bien a-t-il fait l'objet d'indemnisation par une assurance suite à des défâtes liés à une catastrophe ?</p>\n" +
                    "<div class=\"text_wrapper\" ><b>L'information est mentionnée dans l' acte de vente</b></div>\n" +
                    "<div class=\"input_wrapper\">\n" +
                    "   <label><input type=\"checkbox\">Oui</label>\n" +
                    "   <label><input type=\"checkbox\">Non</label>\n" +
                    "</div>\n");
    
        page.append("<p style=\"padding-top : 30px;\">Les parties signataires à l'acte certifient avoir pris connaissance des informations restituées dans ce document et certifient avoir été en " +
                    "mesure de les corriger et le cas " +
                    "échéant de les compléter à partir des informations disponibles sur le site internet de la Préfecture ou d'informations concernant le bien, notamment les sinistres que le bien a" +
                    " subis.</p>");
    
        page.append("<h4 id=\"signatures_title\">SIGNATURES</h4>");
    
        page.append("<div id=\"signatures\"><p>Vendeur / Bailleur</p><p>Date et lieu</p><p>Acheteur / Locataire</p></div>");
    }
    
    private void redigerParcelleNonConcerneePar(Document htmlDocument, AvisDTO avisDTO) {
        
        Element page = addPage(htmlDocument);
    
        page.append("<div><h2>CETTE PARCELLE N’EST PAS CONCERNÉE PAR :</h2></div>");
        
        if (!hasTypePPR(avisDTO, "PPRN")) {
            addRisque(htmlDocument,
                      "NATURELS",
                      appPath + "/pictogrammes_risque/ic_seisme_bleu.png",
                      "<p>Il n’existe pas de Plan de Prévention des Risques recensé sur les risques naturels.</p>");
        }
        
        if (!hasTypePPR(avisDTO, "PPRM")) {
            addRisque(htmlDocument,
                      "MINIERS",
                      appPath + "/pictogrammes_risque/ic_cavite_bleu.png",
                      "<p>Il n’existe pas de Plan de Prévention des Risques recensé sur les risques miniers.</p>");
        }
        
        if (!hasTypePPR(avisDTO, "PPRT")) {
            addRisque(htmlDocument,
                      "TECHNOLOGIQUES",
                      appPath + "/pictogrammes_risque/ic_industrie_bleu.png",
                      "<p>Il n’existe pas de Plan de Prévention des Risques recensé sur les risques technologiques.</p>");
        }
        
        if (!hasPollutionPrincipale(avisDTO)) {
            addRisque(htmlDocument,
                      "POLLUTION DES SOLS",
                      appPath + "/pictogrammes_risque/ic_basias_bleu.png",
                      "<p>Votre parcelle ne figure pas dans l’inventaire :</p><p>- des installations classées soumises à enregistrement ou à autorisation</br>- des secteurs d’information sur les " +
                      "sols</br>- des terrains pollués affectés d’une servitude d’utilité publique.</p>.");
        }
        
        if (!hasPlanExpositionBruit(avisDTO)) {
            addRisque(htmlDocument,
                      "BRUIT",
                      appPath + "/pictogrammes_risque/ic_bruit_bleu.png",
                      "<p>La parcelle n’est pas concernée par un plan d’exposition au bruit.</p>");
        }
    }
    
    private boolean hasPlanExpositionBruit(AvisDTO avisDTO) {
        
        return !StringUtils.isEmpty(avisDTO.getZonePlanExpositionBruit());
    }
    
    private boolean hasTypePPR(AvisDTO avisDTO, String type) {
        
        for (PlanPreventionRisquesGasparDTO plan : avisDTO.getPlanPreventionRisquesDTOs()) {
            if (plan.getAlea().getFamilleAlea().getFamillePPR().getCode().equals(type)) { return true; }
        }
        return false;
    }
    
    private void redigerRecommandations(Document htmlDocument, AvisDTO avisDTO) {
        
        Element page = addPage(htmlDocument);
        
        page.append("<div id=\"recommandations\"><h2>RECOMMANDATIONS</h2></div>");
        
        page.append("Pourquoi l’Etat des risques est important ?</p>");
        page.append("<p>A chaque vente ou location d’un bien, le propriétaire est tenu d’informer l’acquéreur ou le locataire de son bien immobilier (bâti et non bâti) de l’état des risques et " +
                    "pollutions auxquelles le bien immobilier est exposé. Cette obligation d’information est prévue par la loi du 30 juillet 2003. L’état des risques doit dater de " +
                    "moins de six mois lors de la signature de l’acte de vente ou du bail.</p><br/>");
        page.append("<p>L’État des risques et pollution permet de faire un bilan des principaux risques pouvant affecter ce bien afin de bien informer les parties prenantes et de mettre en œuvre " +
                    "les mesures de protection éventuelles.</p>");
        
        if (hasPPR(avisDTO)) {
            page.append("<h4 id=\"recommendations_PPR\">Plans de Prévention des Risques</h4>");
            page.append("<p>Certains risques peuvent nécessiter de réaliser des travaux obligatoires de mise en conformité de votre habitation. Pour le savoir, vous devez prendre connaissance du " +
                        "plan de prévention, consultable sur le site internet de votre préfecture.");
            page.append("<p>Si votre bien est concerné par une obligation de travaux, vous pouvez bénéficier d'une aide de l'Etat, dans le cadre du Fonds de prévention des risques naturels majeurs " +
                        "(FPRNM). Pour plus de renseignements, contacter la direction départementale des territoires (DDT) de votre département.</p>");
            page.append("<p>Pour savoir ce qu'il faut faire en cas de survenance du risque, consulter le Dossier d'information communal sur les risques majeurs (DICRIM) auprès de votre commune.</p>");
        }
    
        if (hasSismiciteHaute(avisDTO)) {
            page.append("<h4 id=\"recommendations_sismicite\">Sismicité</h4>");
            page.append("<p>Pour le bâti neuf et pour certains travaux lourds sur le bâti existant, en fonction de la zone de sismicité et du type de construction (habitation individuelle, " +
                        "habitations collectives, établissement recevant du public) des dispositions spécifiques à mettre en oeuvre s'appliquent lors de la construction.</p>");
            page.append("<p>Un guide interactif est proposé sur le site Plan Séisme pour identifier précisément les dispositions à prendre en compte selon votre localisation, type d'habitat et " +
                        "projet. Il est consultable à l'adresse suivante : http://www.planseisme.fr/-Didacticiel-.html</p>");
            page.append("<p>Pour connaitre les consignes à appliquer en cas de séisme , vous pouvez consulter le site : http://www.planseisme.fr/Que-faire-en-cas-de-seisme.html</p>");
        }
    
        if (hasSismiciteMoyenne(avisDTO)) {
            page.append("<h4 id=\"recommendations_sismicite\">Sismicité</h4>");
            page.append("<p>Pour certains bâtiments de taille importante ou sensibles des dispositions spécifiques s’appliquent selon la réglementation (arrêté du 22 octobre 2010).</p>");
            page.append("<p>Un guide interactif est proposé sur le site Plan Séisme pour identifier précisément les dispositions à prendre en compte selon votre localisation, type d'habitat et " +
                        "projet. Il est consultable à l'adresse suivante : http://www.planseisme.fr/-Didacticiel-.html</p>");
        }
    
        if (hasRadonHaut(avisDTO)) {
            page.append("<h4 id=\"recommendations_radon\">Radon</h4>");
            page.append("<p>Le bien est situé dans une zone à potentiel radon significatif. Il donc est fortement recommandé de procéder au mesurage du radon dans le bien afin de s’assurer que sa " +
                        "concentration est inférieure au niveau de référence fixé à 300 Bq/m3, et idéalement la plus basse raisonnablement possible. Il est conseillé de faire appel à des " +
                        "professionnels du bâtiment pour réaliser un diagnostic de la situation et vous aider à choisir les solutions les plus adaptées selon le type de logement et la mesure. Ces " +
                        "solutions peuvent être mises en œuvre progressivement en fonction des difficultés de réalisation ou de leur coût. À l’issue des travaux, vous devrez réaliser de nouvelles " +
                        "mesures de radon pour vérifier leur efficacité.</p>");
        }
        
        if (hasPollutionPrincipale(avisDTO)) {
            page.append("<h4 id=\"recommendations_pollution\">Pollution des sols</h4>");
            page.append("<p>En cas de vente ou de location, le propriétaire est tenu de communiquer les informations relatives aux pollutions des sols, à l’acquéreur ou au locataire. (article L. " +
                        "514-20 du Code de l’Environnement et L 125-7 du Code de l’Environnement).</p>");
            page.append("<p>En cas de changement d’usage du terrain (travaux, constructions, changement d’affectation du bien), le maître d’ouvrage doit faire appel à un bureau d’étude qui devra " +
                        "attester de la mise en oeuvre de mesures de gestion de la pollution des sols. Si elle est exigée lors d’un dépôt de permis de construire ou d’aménager " +
                        "(Article L.556-1 du Code de l’Environnement), l’attestation devra être délivrée par une bureau d’étude certifiée.</p>");
        }
    }
    
    private boolean hasPollutionPrincipale(AvisDTO avisDTO) {
        
        return avisDTO.getInstallationClasseeSurParcelleDTOs().size() > 0 || avisDTO.getSecteurInformationSolSurParcelleDTOs().size() > 0;
    }
    
    private boolean hasRadonHaut(AvisDTO avisDTO) {
        
        return avisDTO.getSummary().getCommune().getClassePotentielRadon() != null && avisDTO.getSummary().getCommune().getClassePotentielRadon().equals("3");
    }
    
    private boolean hasRadonMoyen(AvisDTO avisDTO) {
        
        return avisDTO.getSummary().getCommune().getClassePotentielRadon() != null && avisDTO.getSummary().getCommune().getClassePotentielRadon().equals("2");
    }
    
    private boolean hasPollutionNonReglementaire(AvisDTO avisDTO) {
        
        return avisDTO.getInstallationClasseeRayonParcelleDTOs().size() > 0 || avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().size() > 0 || avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().size() > 0;
    }
    
    private boolean hasSismicite(AvisDTO avisDTO) {
        
        return avisDTO.getSummary().getCommune().getCodeZoneSismicite() != null && !avisDTO.getSummary().getCommune().getCodeZoneSismicite().equals("1");
    }
    
    private boolean hasSismiciteMoyenne(AvisDTO avisDTO) {
        
        return avisDTO.getSummary().getCommune().getCodeZoneSismicite() != null && avisDTO.getSummary().getCommune().getCodeZoneSismicite().equals("2");
    }
    
    private boolean hasSismiciteHaute(AvisDTO avisDTO) {
        
        return hasSismicite(avisDTO) && !hasSismiciteMoyenne(avisDTO);
    }
    
    private boolean hasPPR(AvisDTO avisDTO) {
        
        return avisDTO.getPlanPreventionRisquesDTOs().size() > 0;
    }
    
    private void redigerRisquesPrincipaux(Document htmlDocument, AvisDTO avisDTO) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        
        Element page = addPage(htmlDocument);
        
        page.append("<div id=\"risque-principaux\"><h2>RISQUES PRINCIPAUX</h2></div>");
        
        for (PlanPreventionRisquesGasparDTO ppr : avisDTO.getPlanPreventionRisquesDTOs()) {
    
            addRisquePrincipal(htmlDocument,
                               ppr.getAlea().getFamilleAlea().getCode(),
                               ppr.getAlea().getFamilleAlea().getLibelle().toUpperCase(),
                               appPath + "/pictogrammes_risque/" + getLogoRisque(ppr.getAlea().getFamilleAlea().getCode()) + ".png",
                               "<p>L’immeuble est situé dans le périmètre d’un " + ppr.getAlea().getFamilleAlea().getFamillePPR().getLibelle() + " de type " + ppr.getAlea().getFamilleAlea().getLibelle() + " - " + ppr.getAlea().getLibelle() +
                               (ppr.getDateApprobation() != null ? ", approuvé le " + sdf.format(ppr.getDateApprobation()) : ", prescrit le " + sdf.format(ppr.getDatePrescription())) + "." + "<br/>" +
                               (ppr.getDateApprobation() != null ? "Un PPR approuvé est un PPR définitivement adopté." :
                                ppr.getDateApplicationAnticipee() != null ? "Un PPR anticipé est un PPR non encore approuvé mais dont les règles sont  déjà à appliquer, par anticipation." :
                                "Un PPR prescrit est un PPR en cours d’élaboration sur la commune dont le périmètre et les règles sont en cours d’élaboration."
                               ) +
                               "<br/><br/>" +
                               "Le plan de prévention des risques est un document réalisé par l’État qui réglemente l’utilisation des sols en fonction des risques auxquels ils sont soumis.</p>");
        }
        
        if (hasSismicite(avisDTO)) {
            addRisquePrincipal(htmlDocument,
                               "SISMICITE",
                               "SISMICITÉ",
                               appPath + "/pictogrammes_risque/ic_seisme_bleu.png",
                               "<p>Un tremblement de terre ou séisme, est un ensemble de secousses et de déformations brusques de l'écorce terrestre (surface de la Terre). Le zonage sismique " +
                               "détermine l'importance de l'exposition au risque sismique..</p>");
        }
        
        if (hasRadonHaut(avisDTO) || hasRadonMoyen(avisDTO)) {
            addRisquePrincipal(htmlDocument,
                               "RADON",
                               "RADON",
                               appPath + "/pictogrammes_risque/ic_rn_bleu.png",
                               "<p>Le radon est un gaz radioactif naturel inodore, incolore et inerte. Ce gaz est présent partout dans les sols et " +
                               "il s’accumule dans les espaces clos, notamment dans " +
                               "les " +
                               "bâtiments.</p>");
        }
        
        if (hasPollutionPrincipale(avisDTO)) {
            addRisquePrincipal(htmlDocument,
                               "POLLUTIONS",
                               "POLLUTIONS DES SOLS",
                               appPath + "/pictogrammes_risque/ic_basias_bleu.png",
                               "<p>Les pollutions des sols peuvent présenter un risque sanitaire lors des changements d’usage des sols (travaux, aménagements " +
                               "changement d’affectation des terrains) si " +
                               "elles ne sont pas prises en compte dans le cadre du projet.</p>" +
                               (avisDTO.getInstallationClasseeSurParcelleDTOs().size() > 0 ? "<p>- La parcelle a accueilli une activité industrielle ou agricole " +
                                                                                             "relevant de la réglementation des " +
                                                                                             "installations classées pour la protection de l’environnement. Cette " +
                                                                                             "activité a pu provoquer des pollutions," +
                                                                                             " notamment des sols des eaux souterraines ou des eaux superficielles" +
                                                                                             ".</br>Installation(s) concerné(e)  : " +
                                                                                             "<br/>" + getLibelleInstallationsNucleaires(avisDTO) + "</p>" : "") +
                               (avisDTO.getSecteurInformationSolSurParcelleDTOs().size() > 0 ? "<p>- La parcelle est située en secteur d’information sur les sols" +
                                                                                               ".</p>" : "") +
                               (false ? "- La parcelle est affectée d’une servitude d’utilité publique au titre des installations classées au titre du L 515-12 du " +
                                        "code de l’environnement." : ""));
        }
        
        if (avisDTO.getZonePlanExpositionBruit() != null) {
            addRisquePrincipal(htmlDocument,
                               "PEB",
                               "BRUIT",
                               appPath + "/pictogrammes_risque/ic_bruit_bleu.png",
                               "<p>La parcelle est concernée par un plan d’exposition au bruit car elle est exposée aux nuisances sonores d’un aéroport.</p>" +
                               (avisDTO.getZonePlanExpositionBruit().equals("A") ? "<p>Le niveau d’exposition au bruit de la parcelle est très fort (zone A en rouge). La zone A est principalement " +
                                                                                   "inconstructible.</p>" : "") +
                               (avisDTO.getZonePlanExpositionBruit().equals("B") ? "<p>Le niveau d’exposition au bruit de la parcelle est fort (zone B en orange). La zone B est principalement " +
                                                                                   "inconstructible.</p>" : "") +
                               (avisDTO.getZonePlanExpositionBruit().equals("C") ? "<p>Le niveau d’exposition au bruit de la parcelle est modéré (zone C en jaune). Certaines constructions sont " +
                                                                                   "autorisées sous conditions.</p>" : "") +
                               (avisDTO.getZonePlanExpositionBruit().equals("D") ? "<p>Le niveau d’exposition au bruit de la parcelle est faible (zone D en verte). Dans la zone D, les nouveaux " +
                                                                                   "logements sont autorisés à condition qu’ils fassent l’objet d’une isolation phonique.</p>" : ""));
        }
    }
    
    private void addRisquePrincipal(Document htmlDocument, String id, String libelle, String url, String text) {
        
        Element page = htmlDocument.select(".page .content").last();
        
        int risquePrincipalNumberOf = page.select(".risque-principal").size();
        
        if (risquePrincipalNumberOf >= 2) { page = addPage(htmlDocument); }
        
        Element description = addRisquePrincipalHtml(page, id);
        
        description.select(".libelle").html(libelle);
        description.select(".icon img").attr("src", url);
        description.select(".text").html(text);
    }
    
    private void addRisque(Document htmlDocument, String libelle, String url, String text) {
        
        Element page = htmlDocument.select(".page .content").last();
        
        int risquePrincipalNumberOf = page.select(".risque").size();
        
        if (risquePrincipalNumberOf >= 4) { page = addPage(htmlDocument); }
        
        Element description = addRisqueHtml(page);
        
        description.select(".libelle").html(libelle);
        description.select(".icon img").attr("src", url);
        description.select(".text").html(text);
    }
    
    private Element addRisquePrincipalHtml(Element page, String id) {
        
        // @formatter:off
        page.append("<div id=\"" + id + "\"class=\"risque-principal\">\n" +
                    "    <div class=\"description\">\n" +
                    "        <div><b><h3 class=\"libelle\"></h3></b></div>\n" +
                    "        <div class=\"icon\"><img height=\"80px\"\n" +
                    "                                 src=\"\"\n" +
                    "                                 style=\"margin : 0;\"/></div>\n" +
                    "        <div class=\"text\"></div>\n" +
                    "    </div>\n" +
                    "    <div class=\"carte\"><img src=\"\"\n" +
                    "                              style=\"margin : 0;\"/></div>\n" +
                    "</div>");
        // @formatter:on
        
        return page.select(".description").last();
    }
    
    private Element addRisqueHtml(Element page) {
        
        // @formatter:off
        page.append("<div class=\"risque\">\n" +
                    "    <div><b><h3 class=\"libelle\"></h3></b></div>\n" +
//                    "    <div class=\"icon\"><img height=\"80px\"\n" +
//                    "                             src=\"\"\n" +
//                    "                             style=\"margin : 0;\"/></div>\n" +
                    "    <div class=\"text\"></div>\n" +
                    "</div>");
        // @formatter:on
        
        return page.select(".risque").last();
    }
    
    private Element addPage(Document htmlDocument) {
        
        int pagesNumberOf = htmlDocument.select(".page").size() + 1; // La première page n’a pas de class .page
        
        Elements body = htmlDocument.select("body");
        
        // @formatter:off
        body.append("<div class=\"page\">\n" +
                    "    <div class=\"header\"><img height=\"80px\"\n" +
                    "                               src=\"" + appPath + "/mte.png\"\n" +
                    "                               style=\"margin : 0;\"/></div>\n" +
                    "    <div class=\"content\"></div>\n" +
                    "    <div class=\"footer\">\n" +
                    "        <p style=\"width: 100%; text-align: center; font-size: 15px\"><span class=\"page-number\">" + (pagesNumberOf + 1) + "</span> / <span class=\"number-of-pages\">8</span> " +
                    "pages</p>\n" +
                    "    </div>\n" +
                    "</div>");
        // @formatter:on
        
        return body.select(".page .content").last();
    }
    
    private String getLogoRisque(String codeAlea) {
        
        switch (codeAlea) {
            case "11":
                return "ic_inondation_bleu";
            case "12":
                return "ic_terre_bleu";
            case "21":
                return "ic_industrie_bleu";
            default:
                return "ic_basias_bleu";
        }
    }
    
    private String getLibelleInstallationsNucleaires(AvisDTO avisDTO) {
        
        StringBuilder libelle = new StringBuilder();
        
        for (InstallationNucleaireDTO installationNucleaireDTO : avisDTO.getInstallationNucleaireDTOS()) {
            libelle.append("- ").append(installationNucleaireDTO.getNomInstallation()).append(" (").append(installationNucleaireDTO.getLibCommune()).append(")<br/>");
        }
        
        return libelle.toString();
    }
    
    private int numberOfParcelleMatches(AvisDTO avisDTO) {
        
        return avisDTO.getInstallationClasseeSurParcelleDTOs().size() +
               avisDTO.getSiteIndustrielBasolSurParcelleDTOs().size() +
               avisDTO.getSiteIndustrielBasiasSurParcelleDTOs().size() +
               avisDTO.getSecteurInformationSolSurParcelleDTOs().size();
    }
    
    private boolean hasPollutionCentroidCommune(AvisDTO avisDTO) {
        
        return avisDTO.getSiteIndustrielBasiasNonGeorerenceesDTOs().size() > 0 ||
               avisDTO.getSiteIndustrielBasolNonGeorerenceesDTOs().size() > 0 ||
               avisDTO.getInstallationClasseeNonGeorerenceesDTOs().size() > 0 ||
               avisDTO.getSecteurInformationSolNonGeorerenceesDTOs().size() > 0;
    }
    
    private boolean hasPPRi(AvisDTO avisDTO) {
        
        for (PlanPreventionRisquesGasparDTO plan : avisDTO.getPlanPreventionRisquesDTOs()) {
            if (plan.getAlea().getFamilleAlea().getCode().equals("11")) {
                return true; // ('11', 'Inondation');
            }
        }
        return false;
    }
    
    private boolean hasTRI(AvisDTO avisDTO) {
        
        if (avisDTO.getTRIs() == null) { return false; }
        return avisDTO.getTRIs().size() > 0;
    }
    
    private boolean hasAZI(AvisDTO avisDTO) {
    
        if (avisDTO.getAZIs() == null) { return false; }
        return avisDTO.getAZIs().size() > 0;
    }
    
    private boolean hasArgile(AvisDTO avisDTO) {
        
        return avisDTO.getLentillesArgile() != null && avisDTO.getLentillesArgile().size() > 0;
    }
    
    @Data
    public static class Png {
        
        private String name;
        private String png;
    }
    
    @Data
    private static class TypeCatNat {
        
        private final String                                libelleRisqueJO;
        private       List<GeorisquePaginatedCatNat.CatNat> catNatList = new ArrayList<>();
        
        public TypeCatNat(String libelleRisqueJO) {
            
            this.libelleRisqueJO = libelleRisqueJO;
        }
    }
}
