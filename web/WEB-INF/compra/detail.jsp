<%-- 
    Document   : detail
    Created on : 01-dic-2017, 1:05:47
    Author     : User
--%>

<%@page import="entidades.DetalleIngresos"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Ingresos"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Ingresos compra = (Ingresos) request.getAttribute("compra");
            List<DetalleIngresos> lista = compra.getDetalleIngresosList();
        %>
        <h4>Compra</h4>
        <label>Proveedor</label> : <%= compra.getIdproveedor().getNombre()%> <br>
        <label>Tipo de comprobante</label> : <%= compra.getTipoComprobante()%> <br>
        <label>Serie de comprobante</label> : <%= compra.getSerieComprobante()%> <br>
        <label>Numero de comprobante</label> : <%= compra.getNumComprobante()%> <br>
        <label>Fecha de la compra</label> : <%= compra.getFechaHora().getDay() + "/" + compra.getFechaHora().getMonth() + "/" + compra.getFechaHora().getYear()%><br>
        <hr>
        <h4>Detalle de la compra</h4>
        <table>
        <thead>
            <th>#</th>
            <th>Articulo</th>
            <th>Cantidad</th>
            <th>Precio de compra</th>
            <th>Precio de venta</th>
        </thead>
        <tbody>
            <%
                int cont = 0;
                for (DetalleIngresos i : lista) {
                    cont++;
            %>
            <tr>
                <td><%= cont%></td>
                <td><%= i.getIdarticulo().getNombre() %></td>
                <td><%= i.getCantidad() %></td>
                <td><%= i.getPrecioCompra() %></td>
                <td><%= i.getPrecioVenta() %></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>
