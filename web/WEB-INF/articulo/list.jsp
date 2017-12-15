<%-- 
    Document   : list
    Created on : 30-nov-2017, 19:15:51
    Author     : User
--%>

<%@page import="entidades.Articulos"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file='../partial/_HEAD.jspf' %>
    </head>
    <body>
        <div class="container">
            <%@ include file='../layout.jsp' %>
            <h1>Articulos</h1>
            <a href="articulo/new">Nueva Articulo</a>
            <table class="table">
                <thead>
                <th>#</th>
                <th>Codigo</th>
                <th>Stock</th>
                <th colspan="3">Opciones</th>
                </thead>
                <tbody>
                    <%
                        List<Articulos> lista = (List<Articulos>) request.getAttribute("listArticulo");
                        for (Articulos a : lista) {
                    %>
                    <tr>
                        <td><%= a.getIdarticulo()%></td>
                        <td><%= a.getCodigo()%></td>
                        <td><%= a.getStock()%></td>
                        <td><a href="articulo/view?id_art=<%= a.getIdarticulo()%>">Ver</a></td>
                        <td><a href="articulo/edit?id_art=<%= a.getIdarticulo()%>">Editar</a></td>
                        <td><a href="articulo/delete?id_art=<%= a.getIdarticulo()%>">Eliminar</a></td>
                    </tr>
                    <%
                        }
                    %>

                </tbody>
            </table>
        </div>

    </body>
</html>
