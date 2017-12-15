<%-- 
    Document   : view
    Created on : 30-nov-2017, 19:25:20
    Author     : User
--%>


<%@page import="entidades.Articulos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! Articulos art = new Articulos(); %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Detalles del Articulo</h1>
        <%
            art = (Articulos) request.getAttribute("articulo");
        %>
        Codigo : <%= art.getCodigo() %><br>
        Nombre : <%= art.getNombre()%><br>
        Descripcion : <%= art.getDescripcion()%><br>
        Categoria: <%= art.getIdcategoria().getNombre() %><br>
        Stock <%= art.getStock() %><br>
        Unidades <%= art.getUnitId().getDescripcion() %><br>
        Fotografia del Articulo:<br>
    </body>
    
</html>
