<%-- 
    Document   : Liste-Immobilisation
    Created on : 27 mai 2022, 16:29:26
    Author     : UCER
--%>

<%@page import="spring.web.mvc.immo.model.Immobilisation"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.ui.Model"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script>
    function generatePDF() {
        const element = document.getElementById('body');
        html2pdf().from(element).save();
    }
</script>
<div id="body" class="col-lg-12 grid-margin stretch-card">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">LISTES D'IMMOBILISATIONS : </h4>
            <div class="table-responsive">
                <table class="table table-dark">
                    <thead>
                        <tr>
                            <th> # </th>
                            <th> Article </th>
                            <th> Prix d'achat (Ariary)</th>
                            <th> Date d'achat </th>
                            <th> Date de mise en service </th>
                            <th> Type d'amortissement </th>
                            <th> Duree </th>
                            <th> Detenteur </th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<Immobilisation> liste = (List<Immobilisation>) request.getAttribute("liste");
//                            String artic = liste.get(0).getArticle();
                            for(int i=0; i<liste.size(); i++){
                                System.out.println(liste.get(i).getArticle());
                            }
                        %>
                        <% for (int i = 0; i < liste.size(); i++) { %>
                        <tr>
                            <td><% out.print(i + 1); %></td>
                            <td><a href='fiche-immobilisation?id=<% out.print(liste.get(i).getId()); %>'><% out.print(liste.get(i).getArticle()); %></a></td>
                            <td> <% out.print(liste.get(i).getPrixAchat()); %> </td>
                            <td> <% out.print(liste.get(i).getDateAchat()); %> </td>
                            <td> <% out.print(liste.get(i).getDateDeMiseEnService()); %> </td>
                            <td> <% out.print(liste.get(i).getTypeAmmortissement()); %> </td>
                            <td> <% out.print(liste.get(i).getDuree()); %> ans</td>
                            <td> <% out.print(liste.get(i).getDetenteur()); %> </td>
                            <td>
                                <a href="tableau-ammortissement?id=<% out.print(liste.get(i).getId()); %>">
                                    <input type="submit" value="Tableau A/ts" class="btn btn-success">
                                </a>
                            </td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
                <!--<p><a href="pagination-immobilisation?page=1">1</a></p>-->
                <br>
                <div class="template-demo" style="float:right">
                    <div class="btn-group" role="group" aria-label="Basic example">
                        <button type="button" class="btn btn-outline-secondary">1</button>
                        <button type="button" class="btn btn-outline-secondary">2</button>
                        <button type="button" class="btn btn-outline-secondary">3</button>
                    </div>
                </div>
                <form action="exportPdf" method="POST">
                    <input type="submit" class="btn btn-primary" value="Download as PDF">
                </form>
                <br>
                <button class="btn btn-success" onclick="generatePDF()">Download as PDF (js)</button>
                <script type="text/javascript" src="assets/js/html2pdf.bundle.min.js"></script>
                <br><br><br>
            </div>
        </div>
    </div>

</div>