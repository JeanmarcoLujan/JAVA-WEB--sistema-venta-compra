<%-- 
    Document   : edit
    Created on : 30-nov-2017, 12:56:56
    Author     : User
--%>

<%@page import="entidades.Categorias"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! Categorias edit_cat = new Categorias(); %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Editar Categoria!</h1>
        <% edit_cat= (Categorias)request.getAttribute("categoria"); %>
        <form action="../categoria-update" method="POST">
            <input type="hidden" name="id" value="<%= edit_cat.getIdcategoria()%>">
            <div class="form-group">
            	<label for="nombre">Nombre</label>
                <input type="text" name="nombre" class="form-control" placeholder="Nombre..." value="<%= edit_cat.getNombre() %>">
            </div>
            <div class="form-group">
            	<label for="descripcion">Descripción</label>
            	<input type="text" name="descripcion" class="form-control" placeholder="Descripción..." value="<%= edit_cat.getDescripcion()%>">
            </div>
            <div class="form-group">
            	<button class="btn btn-primary" type="submit">Guardar</button>
            	<button class="btn btn-danger" type="reset">Cancelar</button>
            </div>
        </form>
    </body>
</html>
