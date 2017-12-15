<%-- 
    Document   : list
    Created on : 01-dic-2017, 1:05:38
    Author     : User
--%>

<%@page import="entidades.Ventas"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@ include file='../layout.jsp' %>
        <h1>Ventas</h1>
        <a href="venta/new">Nueva venta</a>
        <table>
            <thead>
            <th>#</th>
            <th>Cliente</th>
            <th>Tipo Comp.</th>
            <th>Serie Comp.</th>
            <th>Numero Comp.</th>
            <th>Fecha Compra</th>
            <th colspan="2">Opciones</th>
        </thead>
        <tbody>
            <%
                List<Ventas> ventas = new ArrayList();
                ventas = (List<Ventas>) request.getAttribute("listVenta");
                int cont = 0;
                for (Ventas i : ventas) {
                    cont++;
            %>
            <tr>
                <td><%= cont%></td>
                <td><%= i.getIdcliente().getNombre()%></td>
                <td><%= i.getTipoComprobante()%></td>
                <td><%= i.getSerieComprobante()%></td>
                <td><%= i.getNumComprobante()%></td>
                <td><%= i.getFechaHora().getDay() + "/" + i.getFechaHora().getMonth() + "/" + i.getFechaHora().getYear()%></td>
                <td><a href="venta/view?id_venta=<%= i.getIdventa()%>">Ver</a></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

</body>
</html>
