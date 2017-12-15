<%-- 
    Document   : list
    Created on : 01-dic-2017, 1:05:38
    Author     : User
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="entidades.Ingresos"%>
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
        <h1>Compras</h1>
        <a href="compra/new">Nueva Compra</a>
        <table>
            <thead>
            <th>#</th>
            <th>Proveedor</th>
            <th>Tipo Comp.</th>
            <th>Serie Comp.</th>
            <th>Numero Comp.</th>
            <th>Fecha Compra</th>
            <th colspan="2">Opciones</th>
        </thead>
        <tbody>
            <%
                List<Ingresos> compras = new ArrayList();
                compras = (List<Ingresos>) request.getAttribute("listaCompra");
                int cont = 0;
                for (Ingresos i : compras) {
                    cont++;
            %>
            <tr>
                <td><%= cont%></td>
                <td><%= i.getIdproveedor().getNombre()%></td>
                <td><%= i.getTipoComprobante()%></td>
                <td><%= i.getSerieComprobante()%></td>
                <td><%= i.getNumComprobante()%></td>
                <td><%= i.getFechaHora().getDay() + "/" + i.getFechaHora().getMonth()+ "/" + i.getFechaHora().getYear()%></td>
                <td><a href="compra/view?id_compra=<%= i.getIdingreso() %>">Ver</a></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

</body>
</html>
