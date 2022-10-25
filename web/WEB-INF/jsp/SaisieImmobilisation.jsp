<%-- 
    Document   : SaisieImmobilisation
    Created on : 23 mai 2022, 21:39:54
    Author     : UCER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="col-12 grid-margin stretch-card">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">INSERTION D'UNE IMMOBILISATION :</h4>
            <% if (request.getAttribute("message") != null) { %>
            <br>
            <div class="alert alert-success"><% out.print(request.getAttribute("message")); %></div>
            <% }%>
            <form action='insert-immobilisation' method='post' class="forms-sample">
                <div class="form-group">
                    <label for="exampleInputName1">Article</label>
                    <input type="text" name='article' class="form-control" id="exampleInputName1" placeholder="Article">
                </div>
                <div class="form-group">
                    <label for="exampleInputName1">Prix d'achat</label>
                    <input type="number" name='prixAchat' class="form-control" id="exampleInputName1" placeholder="Prix d'achat">
                </div>
                <div class="form-group">
                    <label for="exampleInputName1">Date d'achat</label>
                    <input type="date" name='dateAchat' class="form-control" id="exampleInputName1" placeholder="Date d'achat">
                </div>
                <div class="form-group">
                    <label for="exampleInputName1">Date de mise en service</label>
                    <input type="date" name='dateService' class="form-control" id="exampleInputName1" placeholder="Date de mise en service">
                </div>
                <div class="form-group">
                    <label for="exampleSelectGender">Type d'ammortissement</label>
                    <select name="typeAmmortissement" class="form-control" id="exampleSelectGender">
                        <option>LINEAIRE</option>
                        <option>DEGRESSIF</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="exampleInputName1">Duree</label>
                    <input type="number" name='duree' class="form-control" id="exampleInputName1" placeholder="Duree">
                </div>
                <div class="form-group">
                    <label for="exampleInputName1">Detenteur</label>
                    <input type="text" name='detenteur' class="form-control" id="exampleInputName1" placeholder="Detenteur">
                </div>
                <button type="submit" class="btn btn-primary mr-2">Inserer</button>
                <button class="btn btn-dark">Cancel</button>
            </form>
        </div>
    </div>
</div>
</div>