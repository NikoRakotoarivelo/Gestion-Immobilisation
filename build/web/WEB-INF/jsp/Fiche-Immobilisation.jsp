<%-- 
    Document   : Liste-Immobilisation-test
    Created on : 1 juin 2022, 12:33:34
    Author     : UCER
--%>

<%@page import="spring.web.mvc.immo.model.Immobilisation"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="col-lg-12 grid-margin stretch-card">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">FICHE D'UNE IMMOBILISATION : </h4>
            <%
                Immobilisation immo = (Immobilisation) request.getAttribute("immo");
            %>
            <p> ID :  <% out.print(immo.getId()); %> </p>
            <p> ARTICLE : <% out.print(immo.getArticle()); %> </p>
            <p> PRIX D'ACHAT : <% out.print(immo.getPrixAchat()); %> Ariary </p>
            <p> DATE D'ACHAT : <% out.print(immo.getDateAchat()); %> </p>
            <p> DATE DE MISE EN SERVICE : <% out.print(immo.getDateAchat()); %> </p>
            <p> TYPE D'AMMORTISSEMENT : <% out.print(immo.getTypeAmmortissement()); %> </p>
            <p> DUREE : <% out.print(immo.getDuree()); %> </p>
            <p> DETENTEUR : <% out.print(immo.getDetenteur());%> </p>
        </div>
        <br>
    </div>
</div>
<form action="exportPdfFiche?id=<% out.print(immo.getId());%>" method="GET">
    <input type="submit" class="btn btn-primary" value="Download as PDF">&nbsp;&nbsp;&nbsp;&nbsp;
</form>
<!--<button class="btn btn-success" onclick="generatePDF()">Download as PDF</button>&nbsp;&nbsp;&nbsp;&nbsp;-->
<!--<button class="btn btn-success" onclick="tableToCSV()">Download as CSV </button>-->
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
        temp_link.download = "<% out.print("Fiche-");%>.csv";
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