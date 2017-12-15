<%-- 
    Document   : list
    Created on : 30-nov-2017, 23:24:06
    Author     : User
--%>

<%@page import="entidades.Personas"%>
<%@page import="java.util.ArrayList"%>
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
        <h1>Clientes</h1>
        <a href="cliente/new">Nuevo Cliente</a>
        <table>
            <thead>
            <th>Nombre</th>
            <th>Tipo Doc</th>
            <th># Doc</th>
            <th>Direccion</th>
            <th>Telefono</th>
            <th>E-mail</th>
            <th>Opciones</th>
        </thead>
        <tbody>
            <%
                List<Personas> clientes = new ArrayList();
                clientes = (List<Personas>) request.getAttribute("listaCliente");
                for (Personas c : clientes) {
                    %>
                    <tr>
                        <td><%= c.getNombre() %></td>
                        <td><%= c.getTipoDocumento()%></td>
                        <td><%= c.getNumDocumento()%></td>
                        <td><%= c.getDireccion()%></td>
                        <td><%= c.getTelefono()%></td>
                        <td><%= c.getEmail()%></td>
                        <td><a href="cliente/edit?id_cli=<%= c.getIdpersona()%>">Editar</a></td>
                    </tr>
                    <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
