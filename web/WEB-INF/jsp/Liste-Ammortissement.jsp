<%-- 
    Document   : Tableau-Ammortissement
    Created on : 2 juin 2022, 21:57:29
    Author     : UCER
--%>

<%@page import="spring.web.mvc.immo.model.Ammortissement"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    List<Ammortissement> liste = (List<Ammortissement>) request.getAttribute("liste");
    String an = String.valueOf(request.getAttribute("session"));
%>

<div id="pdf" class="col-lg-12 grid-margin stretch-card">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">TABLEAU D'AMMORTISSEMENT : </h4>
            <% if (request.getAttribute("session") != null) { %>
            <h6> Annee : <% out.print(an.substring(0, 4)); %></h6>
            <% } %>
            <p>
            <form action="immobilisation-search-ammortissement" method="post" class="nav-link mt-56 mt-md-0 d-none d-lg-flex search">
                <input type="text" name="annee" class="form-control" placeholder="Search ammortissement by date...">
                <input type="submit" value="Search" class="btn btn-primary">
            </form>
            </p>
            <div class="table-responsive">

                <table class="table table-dark">
                    <thead>
                        <tr>
                            <th> Annee </th>
                            <th> Libelle </th>
                            <th> Anterieur </th>
                            <th> Exercice </th>
                            <th> Cumul </th>
                            <th> VNC </th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (int i = 0; i < liste.size(); i++) { %>
                        <tr>
                            <td><% out.print(String.valueOf(liste.get(i).getAnnee()).substring(0, 4)); %></td>
                            <td><% out.print(liste.get(i).getLibelle()); %></td>
                            <td><% out.print(liste.get(i).getAnterieur()); %></td>
                            <td><% out.print(liste.get(i).getExercice()); %></td>
                            <td><% out.print(liste.get(i).getCumul()); %></td>
                            <td><% out.print(liste.get(i).getVnc()); %></td>
                        </tr>
                        <% }%>
                    </tbody>
                </table>
                <br>
<!--                <form action="exportPdfTableau?id=<% out.print(request.getAttribute("id")); %>" method="POST">
                    <input type="submit" class="btn btn-primary" value="Download as PDF">&nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn btn-success" onclick="tableToCSV()">Download as CSV </button>
                </form>-->
            </div>
        </div>
    </div>
</div>
<button class="btn btn-success" onclick="generatePDF()">Download as PDF</button>
<button class="btn btn-primary" onclick="tableToCSV()">Download as CSV </button>
<script type="text/javascript">

    function generatePDF() {
        const element = document.getElementById('pdf');
        html2pdf().from(element).save();
    }

    function tableToCSV() {
        var csv_data = [];
        var rows = document.getElementsByTagName('tr');
        for (var i = 0; i < rows.length; i++) {
            var cols = rows[i].querySelectorAll('td,th');
            var csvrow = [];
            for (var j = 0; j < cols.length; j++) {
                csvrow.push(cols[j].innerHTML);
            }
            csv_data.push(csvrow.join(","));
        }
        csv_data = csv_data.join('\n');
        downloadCSVFile(csv_data);
    }

    function downloadCSVFile(csv_data) {
        CSVFile = new Blob([csv_data], {
            type: "text/csv"
        });
        var temp_link = document.createElement('a');
        temp_link.download = "<% out.print("tableau-" + an);%>.csv";
        var url = window.URL.createObjectURL(CSVFile);
        temp_link.href = url;
        temp_link.style.display = "none";
        document.body.appendChild(temp_link);
        temp_link.click();
        document.body.removeChild(temp_link);
    }
</script>
<script type="text/javascript" src="assets/js/html2pdf.bundle.min.js"></script>
</div>