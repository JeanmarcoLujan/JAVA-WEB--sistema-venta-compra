<%-- 
    Document   : list
    Created on : 30-nov-2017, 12:56:39
    Author     : User
--%>

<%@page import="entidades.Categorias"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file='../layout.jsp' %>
        <h1>Categorias</h1>
        <a href="categoria/new">Nueva Categoria</a>
        <table>
            <thead>
                <th>#</th>
                <th>Nombre</th>
                <th>Descripcion</th>
                <th colspan="2">Opciones</th>
            </thead>
            <tbody>
                <%
                    List<Categorias> lista = (List<Categorias>)request.getAttribute("listCategoria");
                    for (Categorias c : lista) {
                            %>
                            <tr>
                                <td><%= c.getIdcategoria() %></td>
                                <td><%= c.getNombre()%></td>
                                <td><%= c.getDescripcion()%></td>
                                <td><a href="categoria/edit?id_cat=<%= c.getIdcategoria()%>">Editar</a></td>
                                <td><a href="categoria/delete?id_cat=<%= c.getIdcategoria()%>">Eliminar</a></td>
                            </tr>
                            <%
                        }
                %>
            </tbody>
        </table>
    </body>
</html>
