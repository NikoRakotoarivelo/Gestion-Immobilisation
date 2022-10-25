<%-- 
    Document   : Tableau-Ammortissement
    Created on : 2 juin 2022, 21:57:29
    Author     : UCER
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<script>
    function generatePDF() {
        const element = document.getElementById('body');
        html2pdf().from(element).save();
    }
</script>
<%
    List annee = (List) request.getAttribute("Annee");
    List anterieur = (List) request.getAttribute("Anterieur");
    List exercice = (List) request.getAttribute("Exercice");
    List cumul = (List) request.getAttribute("Cumul");
    List vnc = (List) request.getAttribute("Vnc");
    String article = (String) request.getAttribute("Article");
    float prixAchat = (Float) request.getAttribute("PrixAchat");
    String dateService = (String) request.getAttribute("DateService");
%>

<div id="pdf" class="col-lg-12 grid-margin stretch-card">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">TABLEAU D'AMMORTISSEMENT : </h4>
            <h6>Article : <% out.print(article); %></h6>
            <h6>Prix d'achat : <% out.print(prixAchat); %> Ariary</h6>
            <h6>Date du mise en service : <% out.print(dateService); %></h6><br>
            <div class="table-responsive">
                <table class="table table-dark">
                    <thead>
                        <tr>
                            <th> Annee </th>
                            <th> Libelle </th>
                            <th> Prix d'achat </th>
                            <th> Anterieur </th>
                            <th> Exercice </th>
                            <th> Cumul </th>
                            <th> VNC </th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (int i = 0; i < vnc.size(); i++) { %>
                        <tr>
                            <td><% out.print(annee.get(i).toString().substring(0, 4)); %></td>
                            <td><% out.print(article); %></td>
                            <td><% out.print(prixAchat); %></td>
                            <td><% out.print(anterieur.get(i)); %></td>
                            <td><% out.print(exercice.get(i)); %></td>
                            <td><% out.print(cumul.get(i)); %></td>
                            <td><% out.print(vnc.get(i).toString()); %></td>

                        </tr>
                        <% }%>
                    </tbody>
                </table>
            </div>
            <br>
            <form action="exportPdfTableau?id=<% out.print(request.getAttribute("id")); %>" method="POST">
                <input type="submit" class="btn btn-primary" value="Download as PDF">&nbsp;&nbsp;&nbsp;&nbsp;
                <!--<button class="btn btn-success" onclick="tableToCSV()">Download as CSV </button>-->
            </form>
        </div>
    </div>
</div>
<button class="btn btn-success" onclick="generatePDF()">Download as PDF</button>&nbsp;&nbsp;&nbsp;&nbsp;
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
        temp_link.download = "<% out.print("tableau-" + article);%>.csv";
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