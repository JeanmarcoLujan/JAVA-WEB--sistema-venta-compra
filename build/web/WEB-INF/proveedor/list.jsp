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
        <h1>Proveedores</h1>
        <a href="proveedor/new">Nuevo Proveedor</a>
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
                List<Personas> proveedores = new ArrayList();
                proveedores = (List<Personas>) request.getAttribute("listaProveedor");
                for (Personas p : proveedores) {
            %>
            <tr>
                <td><%= p.getNombre()%></td>
                <td><%= p.getTipoDocumento()%></td>
                <td><%= p.getNumDocumento()%></td>
                <td><%= p.getDireccion()%></td>
                <td><%= p.getTelefono()%></td>
                <td><%= p.getEmail()%></td>
                <td><a href="proveedor/edit?id_pro=<%= p.getIdpersona()%>">Editar</a></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
