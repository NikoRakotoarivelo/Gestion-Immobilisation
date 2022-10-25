/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.web.mvc.immo.controller;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.DocumentException;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import spring.web.mvc.immo.dao.HibernateDAO;
import spring.web.mvc.immo.model.Ammortissement;
import spring.web.mvc.immo.model.ExportPdf;
import spring.web.mvc.immo.model.ExportPdfFiche;
import spring.web.mvc.immo.model.ExportPdfTableau;
import spring.web.mvc.immo.model.Immobilisation;
import spring.web.mvc.immo.service.AmmortissementService;
import spring.web.mvc.immo.service.ImmobilisationService;

/**
 *
 * @author UCER
 */
@Controller
public class MainController {

    @Autowired
    private ImmobilisationService immobilisationService;

    @Autowired
    private AmmortissementService ammortissementService;

    @Autowired
    private HibernateDAO hibernateDao;

    @RequestMapping(value = "/accueil", method = RequestMethod.GET)
    public String accueil() throws Exception {
        return "Accueil";
    }

    @RequestMapping(value = "/saisi-immobilisation", method = RequestMethod.GET)
    public String saisiImmobilisation(HttpServletRequest request) throws Exception {
        if (request.getAttribute("message") != null) {
            System.out.println(request.getAttribute("message"));
        }
        request.setAttribute("pageinclude", "SaisieImmobilisation");
        return "Accueil";
    }

    @RequestMapping(value = "/insert-immobilisation", method = RequestMethod.POST)
    public String saisiImmobilisation(HttpServletRequest request, PrintWriter out,
         @RequestParam String article,
         @RequestParam float prixAchat,
         @RequestParam String dateAchat,
         @RequestParam String typeAmmortissement,
         @RequestParam int duree,
         @RequestParam String detenteur,
         @RequestParam String dateService
    ) throws Exception {
        Immobilisation immobilisation = new Immobilisation(article, prixAchat, dateAchat, typeAmmortissement, duree, detenteur, dateService);
        int id = immobilisationService.insertImmobilisation(immobilisation);
//        out.print("ito ilay id=="+id);
        out.print("article:" + article + " - prixAchat:" + prixAchat + " - dateAchat:" + dateAchat + " - dateService: " + dateService + " - type:" + typeAmmortissement + " - duree:" + duree + " - detenteur:" + detenteur);
        out.print("article:" + immobilisation.getArticle()
             + " - prixAchat:" + immobilisation.getPrixAchat()
             + " - dateAchat:" + immobilisation.getDateAchat()
             + " - dateService:" + immobilisation.getDateDeMiseEnService()
             + " - type:" + immobilisation.getTypeAmmortissement()
             + " - duree:" + immobilisation.getDuree()
             + " - detenteur:" + immobilisation.getDetenteur());

////////////////////////////////////////////////////////////////////////////////
        Immobilisation immo = new Immobilisation();
        Immobilisation immob = new Immobilisation();

//        immob = (Immobilisation)hibernateDao.findById(immob);
        immo.setId(id);
        immo = (Immobilisation) hibernateDao.findById(immo);

        out.print(immo.getId() + " - " + immo.getArticle() + " - " + immo.getDateAchat() + " - " + immo.getDateDeMiseEnService() + " - " + immo.getTypeAmmortissement() + " - " + immo.getDetenteur());
        //Date de mise en service :
        String dateServic = immo.getDateDeMiseEnService();
        out.println("Date de service : " + dateServic);

        //Article
        String articl = immo.getArticle();

        //Prix d'achat :
        float prixAcha = immo.getPrixAchat();
        out.println("Prix d'achat : " + prixAcha);

        //Duree d'ammortissement :
        int dure = immo.getDuree();
        out.println("Duree : " + dure);

        List annee = new ArrayList();
        List anterieur = new ArrayList();
        List exercice = new ArrayList();
        List cumul = new ArrayList();
        List vnc = new ArrayList();

        LocalDate local = LocalDate.parse(dateServic);
        float day = local.getDayOfMonth();
        float month = local.getMonthValue();
        float year = local.getYear();
        out.println("Day : " + day);
        out.println("Month : " + month);
        out.println("Year : " + year);

        float mois = 0;
        float joursMois = 0;
        float jours = 0;

        if (day != 01 || month != 01) {
            mois = mois + (12 - month + 1);
            joursMois = joursMois + (mois * 30);
            jours = jours + (joursMois - day + 1);
            out.println("A/ts avec prorata");
        } else {
            jours = jours + 360;
            out.println("A/ts normal");
        }

        //Calcul d'ammortissement :
        float ammortissement = (jours * prixAcha) / (360 * dure);
        float ammortissementNormal = prixAcha / dure;
        float ammortissementDernier = ammortissementNormal - ammortissement;
        out.println("Ammortissement : " + ammortissement);
        out.println("Ammortissement Normal : " + ammortissementNormal);
        out.println("Ammortissement Dernier : " + ammortissementDernier);

        //Ammortissement en premier ligne
        annee.add(year);
        anterieur.add(0);
        exercice.add(ammortissement);
        float e0 = Float.valueOf(String.valueOf(exercice.get(0)));
        float an0 = Float.valueOf(String.valueOf(anterieur.get(0)));
        float cu0 = an0 + e0;
        cumul.add(cu0);
        float valeurNet = prixAcha - cu0;
        vnc.add(valeurNet);

        //Ammortissement normal
        for (float i = 0; i < dure - 1; i++) {
            float an = (float) annee.get(annee.size() - 1) + 1;
            annee.add(an);
            anterieur.add(cumul.get(cumul.size() - 1));
            exercice.add(ammortissementNormal);
            cumul.add((float) exercice.get(exercice.size() - 1) + (float) anterieur.get(anterieur.size() - 1));
            vnc.add(prixAcha - (float) cumul.get(cumul.size() - 1));
        }

        //Ammortissement en dernier ligne
        float an = (float) annee.get(annee.size() - 1) + 1;
        annee.add(an);
        anterieur.add(cumul.get(cumul.size() - 1));
        exercice.add(ammortissementDernier);
        cumul.add((float) exercice.get(exercice.size() - 1) + (float) anterieur.get(anterieur.size() - 1));
        vnc.add(prixAcha - (float) cumul.get(cumul.size() - 1));

        for (int k = 0; k < cumul.size(); k++) {
            out.println(k + "-- - Annee : " + annee.get(k) + " - Anterieur : " + anterieur.get(k) + " - Exercice : " + exercice.get(k) + " - Cumul : " + cumul.get(k) + " - ValeurNet : " + vnc.get(k));
        }

        request.setAttribute("Article", articl);
        request.setAttribute("PrixAchat", prixAcha);
        request.setAttribute("DateService", dateServic);
        request.setAttribute("Annee", annee);
        request.setAttribute("Anterieur", anterieur);
        request.setAttribute("Exercice", exercice);
        request.setAttribute("Cumul", cumul);
        request.setAttribute("Vnc", vnc);

        for (int z = 0; z < annee.size(); z++) {
            Ammortissement ammort = new Ammortissement();
            ammort.setAnnee((float) annee.get(z));
            ammort.setLibelle(articl);
            ammort.setAnterieur(Float.parseFloat(String.valueOf(anterieur.get(z))));
            ammort.setExercice((float) exercice.get(z));
            ammort.setCumul((float) cumul.get(z));
            ammort.setVnc((float) vnc.get(z));

//            out.print("\n" + ammort.getAnnee() + " - ");
//            out.print("\n" + ammort.getLibelle() + " - ");
//            out.print("\n" + ammort.getAnterieur() + " - ");
//            out.print("\n" + ammort.getExercice() + " - ");
//            out.print("\n" + ammort.getCumul() + " - ");
            ammortissementService.insertAmmortissement(ammort);
        }

////////////////////////////////////////////////////////////////////////////////
        request.setAttribute("message", "INSERT SUCCESSFULLY!");
        request.setAttribute("pageinclude", "SaisieImmobilisation");
        return "Accueil";
    }

    @RequestMapping(value = "/liste-immobilisation", method = RequestMethod.GET)
    public String listeImmobilisation(HttpServletRequest request, PrintWriter out) {
        Immobilisation immo = new Immobilisation();
        try {
            List<Immobilisation> liste = immobilisationService.getListeImmobilisation(immo);
            request.setAttribute("liste", liste);
            System.out.println(liste.get(1).getArticle());

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("pageinclude", "Liste-Immobilisation");
        return "Accueil";
    }

    @RequestMapping(value = "/fiche-immobilisation", method = RequestMethod.GET)
    public String listeImmobilisationById(HttpServletRequest request, PrintWriter out, @RequestParam("id") int id) {
        Immobilisation immo = new Immobilisation();
        immo.setId(id);
        try {
            immo = immobilisationService.getImmobilisationById(immo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("immo", immo);
        request.setAttribute("pageinclude", "Fiche-Immobilisation");
        return "Accueil";
    }

    @RequestMapping(value = "/immobilisation-search-immobilisation", method = RequestMethod.POST)
    public String listeImmobilisationBySearch(HttpServletRequest request, PrintWriter out) {
        Immobilisation immo = new Immobilisation();
        String search = request.getParameter("search");
        List<Immobilisation> liste = new ArrayList();

        if (search != null) {
            immo.setArticle(search);
//            immo.setDateAchat(search);
            try {
                liste = (List<Immobilisation>) immobilisationService.getImmobilisationBySearch(immo);
                request.setAttribute("liste", liste);
                request.setAttribute("pageinclude", "Liste-Immobilisation");
                return "Accueil";

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute("pageinclude", "Accueil");
            return "Accueil";
        }
        request.setAttribute("pageinclude", "Accueil");
        return "Accueil";
    }

    @RequestMapping(value = "/immobilisation-search-ammortissement", method = RequestMethod.POST)
    public String listeAmmortissementByAnnee(HttpServletRequest request, PrintWriter out) {
        Ammortissement ammort = new Ammortissement();
        float annee = Float.valueOf(request.getParameter("annee"));

        List<Ammortissement> liste = new ArrayList();
        request.setAttribute("session", annee);
        if (annee != 0) {
            ammort.setAnnee(annee);
            try {
                liste = (List<Ammortissement>) ammortissementService.getAmmortissementByAnnee(ammort);
                request.setAttribute("liste", liste);
                request.setAttribute("pageinclude", "Liste-Ammortissement");
                
                return "Accueil";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            request.setAttribute("pageinclude", "Accueil");
            return "Accueil";
        }
        request.setAttribute("pageinclude", "Accueil");
        return "Accueil";
    }

    @RequestMapping(value = "/tableau-ammortissement", method = RequestMethod.GET)
    public String tableauAmmortissement(HttpServletRequest request, PrintWriter out, @RequestParam("id") int id) throws Exception {

//        HttpSession session = request.getSession();
        request.setAttribute("id", id);

        Immobilisation immo = new Immobilisation();
        immo.setId(id);

        immo = immobilisationService.getImmobilisationById(immo);

        //Date de mise en service :
        String dateService = immo.getDateDeMiseEnService();
        out.println("Date de service : " + dateService);

        //Article
        String article = immo.getArticle();

        //Prix d'achat :
        float prixAchat = immo.getPrixAchat();
        out.println("Prix d'achat : " + prixAchat);

        //Duree d'ammortissement :
        int duree = immo.getDuree();
        out.println("Duree : " + duree);

        List annee = new ArrayList();
        List anterieur = new ArrayList();
        List exercice = new ArrayList();
        List cumul = new ArrayList();
        List vnc = new ArrayList();

        LocalDate local = LocalDate.parse(dateService);
        float day = local.getDayOfMonth();
        float month = local.getMonthValue();
        float year = local.getYear();
        out.println("Day : " + day);
        out.println("Month : " + month);
        out.println("Year : " + year);

        float mois = 0;
        float joursMois = 0;
        float jours = 0;

        if (day != 01 || month != 01) {
            mois = mois + (12 - month + 1);
            joursMois = joursMois + (mois * 30);
            jours = jours + (joursMois - day + 1);
            out.println("A/ts avec prorata");
        } else {
            jours = jours + 360;
            out.println("A/ts normal");
        }

        //Calcul d'ammortissement :
        float ammortissement = (jours * prixAchat) / (360 * duree);
        float ammortissementNormal = prixAchat / duree;
        float ammortissementDernier = ammortissementNormal - ammortissement;
        out.println("Ammortissement : " + ammortissement);
        out.println("Ammortissement Normal : " + ammortissementNormal);
        out.println("Ammortissement Dernier : " + ammortissementDernier);

        //Ammortissement en premier ligne
        annee.add(year);
        anterieur.add(0);
        exercice.add(ammortissement);
        float e0 = Float.valueOf(String.valueOf(exercice.get(0)));
        float an0 = Float.valueOf(String.valueOf(anterieur.get(0)));
        float cu0 = an0 + e0;
        cumul.add(cu0);
        float valeurNet = prixAchat - cu0;
        vnc.add(valeurNet);

        //Ammortissement normal
        for (float i = 0; i < duree - 1; i++) {
            float an = (float) annee.get(annee.size() - 1) + 1;
            annee.add(an);
            anterieur.add(cumul.get(cumul.size() - 1));
            exercice.add(ammortissementNormal);
            cumul.add((float) exercice.get(exercice.size() - 1) + (float) anterieur.get(anterieur.size() - 1));
            vnc.add(prixAchat - (float) cumul.get(cumul.size() - 1));
        }

        //Ammortissement en dernier ligne
        float an = (float) annee.get(annee.size() - 1) + 1;
        annee.add(an);
        anterieur.add(cumul.get(cumul.size() - 1));
        exercice.add(ammortissementDernier);
        cumul.add((float) exercice.get(exercice.size() - 1) + (float) anterieur.get(anterieur.size() - 1));
        vnc.add(prixAchat - (float) cumul.get(cumul.size() - 1));

        for (int k = 0; k < cumul.size(); k++) {
            out.println(k + "-- - Annee : " + annee.get(k) + " - Anterieur : " + anterieur.get(k) + " - Exercice : " + exercice.get(k) + " - Cumul : " + cumul.get(k) + " - ValeurNet : " + vnc.get(k));
        }

        request.setAttribute("Article", article);
        request.setAttribute("PrixAchat", prixAchat);
        request.setAttribute("DateService", dateService);
        request.setAttribute("Annee", annee);
        request.setAttribute("Anterieur", anterieur);
        request.setAttribute("Exercice", exercice);
        request.setAttribute("Cumul", cumul);
        request.setAttribute("Vnc", vnc);

        for (int z = 0; z < annee.size(); z++) {
            Ammortissement ammort = new Ammortissement();
            ammort.setAnnee((float) annee.get(z));
            ammort.setLibelle(article);
            ammort.setAnterieur(Float.parseFloat(String.valueOf(anterieur.get(z))));
            ammort.setExercice((float) exercice.get(z));
            ammort.setCumul((float) cumul.get(z));
            ammort.setVnc((float) vnc.get(z));
            out.print("\n" + ammort.getAnnee() + " - ");
            out.print("\n" + ammort.getLibelle() + " - ");
            out.print("\n" + ammort.getAnterieur() + " - ");
            out.print("\n" + ammort.getExercice() + " - ");
            out.print("\n" + ammort.getCumul() + " - ");

//            ammortissementService.insertAmmortissement(ammort);
        }

        request.setAttribute("pageinclude", "Tableau-Ammortissement");
        return "Accueil";
    }

    @RequestMapping(value = "/liste-ammortissement", method = RequestMethod.GET)
    public String listeAmmortissement(HttpServletRequest request, PrintWriter out) {
        Ammortissement ammort = new Ammortissement();
        try {
            List<Ammortissement> liste = ammortissementService.getListeAmmortissement(ammort);
            request.setAttribute("liste", liste);

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("pageinclude", "Liste-Ammortissement");
        return "Accueil";
    }

    @RequestMapping(value = "/pagination-immobilisation", method = RequestMethod.GET)
    public void listeImmobilisationPagination(HttpServletRequest request, PrintWriter out) {
        Immobilisation immobilisation = new Immobilisation();
        List<Immobilisation> liste = null;
        List<Immobilisation> listeImmo = new ArrayList();

        //get nbr page
        int nbrPage = 0;
        try {
            listeImmo = immobilisationService.getListeImmobilisation(immobilisation);
            nbrPage = nbrPage + listeImmo.size();
            out.print("nbrPage = " + nbrPage + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }

        int first = 0;
        int max = 6;

        try {
            liste = (List<Immobilisation>) hibernateDao.pagination(immobilisation, first, max);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < liste.size(); i++) {
            out.print(liste.get(i).getId() + " - " + liste.get(i).getArticle() + "\n");
        }
        request.setAttribute("liste", liste);
        request.setAttribute("pageinclude", "Liste-Immobilisation");
//        return "Accueil";
    }

    @RequestMapping(value = "/exportPdf", method = RequestMethod.POST)
    public void exportToPdf(HttpServletResponse response) throws DocumentException, IOException, Exception {

        Immobilisation immo = new Immobilisation();
        List<Immobilisation> liste = new ArrayList<Immobilisation>();
        liste = immobilisationService.getListeImmobilisation(immo);

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";

        String headerValue = "attachment; filename=Immo-" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        ExportPdf exporter = new ExportPdf(liste);
        exporter.export(response);
    }

    @RequestMapping(value = "/exportPdfTableau", method = RequestMethod.POST)
    public void exportToPdfTableau(HttpServletResponse response, HttpServletRequest request, PrintWriter out) throws DocumentException, IOException, Exception {

        Immobilisation immo = new Immobilisation();
        List<Immobilisation> liste = new ArrayList<Immobilisation>();
        liste = immobilisationService.getListeImmobilisation(immo);

        int id = Integer.parseInt(request.getParameter("id"));
        immo.setId(id);

////////////////////////////////////////////////////////////////////////////////
        immo = immobilisationService.getImmobilisationById(immo);

        //Date de mise en service :
        String dateService = immo.getDateDeMiseEnService();
        out.println("Date de service : " + dateService);

        //Article
        String article = immo.getArticle();

        //Prix d'achat :
        float prixAchat = immo.getPrixAchat();
        out.println("Prix d'achat : " + prixAchat);

        //Duree d'ammortissement :
        int duree = immo.getDuree();
        out.println("Duree : " + duree);

        List annee = new ArrayList();
        List anterieur = new ArrayList();
        List exercice = new ArrayList();
        List cumul = new ArrayList();
        List vnc = new ArrayList();

        LocalDate local = LocalDate.parse(dateService);
        float day = local.getDayOfMonth();
        float month = local.getMonthValue();
        float year = local.getYear();
        out.println("Day : " + day);
        out.println("Month : " + month);
        out.println("Year : " + year);

        float mois = 0;
        float joursMois = 0;
        float jours = 0;

        if (day != 01 || month != 01) {
            mois = mois + (12 - month + 1);
            joursMois = joursMois + (mois * 30);
            jours = jours + (joursMois - day + 1);
            out.println("A/ts avec prorata");
        } else {
            jours = jours + 360;
            out.println("A/ts normal");
        }

        //Calcul d'ammortissement :
        float ammortissement = (jours * prixAchat) / (360 * duree);
        float ammortissementNormal = prixAchat / duree;
        float ammortissementDernier = ammortissementNormal - ammortissement;
        out.println("Ammortissement : " + ammortissement);
        out.println("Ammortissement Normal : " + ammortissementNormal);
        out.println("Ammortissement Dernier : " + ammortissementDernier);

        //Ammortissement en premier ligne
        annee.add(year);
        anterieur.add(0);
        exercice.add(ammortissement);
        float e0 = Float.valueOf(String.valueOf(exercice.get(0)));
        float an0 = Float.valueOf(String.valueOf(anterieur.get(0)));
        float cu0 = an0 + e0;
        cumul.add(cu0);
        float valeurNet = prixAchat - cu0;
        vnc.add(valeurNet);

        //Ammortissement normal
        for (float i = 0; i < duree - 1; i++) {
            float an = (float) annee.get(annee.size() - 1) + 1;
            annee.add(an);
            anterieur.add(cumul.get(cumul.size() - 1));
            exercice.add(ammortissementNormal);
            cumul.add((float) exercice.get(exercice.size() - 1) + (float) anterieur.get(anterieur.size() - 1));
            vnc.add(prixAchat - (float) cumul.get(cumul.size() - 1));
        }

        //Ammortissement en dernier ligne
        float an = (float) annee.get(annee.size() - 1) + 1;
        annee.add(an);
        anterieur.add(cumul.get(cumul.size() - 1));
        exercice.add(ammortissementDernier);
        cumul.add((float) exercice.get(exercice.size() - 1) + (float) anterieur.get(anterieur.size() - 1));
        vnc.add(prixAchat - (float) cumul.get(cumul.size() - 1));

        for (int k = 0; k < cumul.size(); k++) {
            out.println(k + "-- - Annee : " + annee.get(k) + " - Anterieur : " + anterieur.get(k) + " - Exercice : " + exercice.get(k) + " - Cumul : " + cumul.get(k) + " - ValeurNet : " + vnc.get(k));
        }

        ////////////////////////////////////////////////////////////////////////
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";

        String headerValue = "attachment; filename=Tableau-" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        ExportPdfTableau exporter = new ExportPdfTableau(article, prixAchat, dateService, annee, anterieur, exercice, cumul, vnc);
        exporter.exportTab(response);
    }

    @RequestMapping(value = "/exportPdfFiche", method = RequestMethod.GET)
    public void exportToPdfFiche(HttpServletResponse response, HttpServletRequest request, PrintWriter out) throws DocumentException, IOException, Exception {

        Immobilisation immo = new Immobilisation();

        int id = Integer.parseInt(request.getParameter("id"));
        immo.setId(id);
        try {
            immo = immobilisationService.getImmobilisationById(immo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ////////////////////////////////////////////////////////////////////////
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";

        String headerValue = "attachment; filename=Fiche-" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);

        ExportPdfFiche exporter = new ExportPdfFiche(immo);
        exporter.export(response);
    }

}
