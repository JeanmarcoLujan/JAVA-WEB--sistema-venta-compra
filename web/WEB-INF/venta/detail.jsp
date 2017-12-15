<%-- 
    Document   : detail
    Created on : 01-dic-2017, 1:05:47
    Author     : User
--%>

<%@page import="entidades.DetalleVentas"%>
<%@page import="java.util.List"%>
<%@page import="java.util.List"%>
<%@page import="entidades.Ventas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            Ventas venta = (Ventas) request.getAttribute("venta");
            List<DetalleVentas> lista = venta.getDetalleVentasList();
        %>
        <h4>Venta</h4>
        <label>Proveedor</label> : <%= venta.getIdcliente().getNombre()%> <br>
        <label>Tipo de comprobante</label> : <%= venta.getTipoComprobante()%> <br>
        <label>Serie de comprobante</label> : <%= venta.getSerieComprobante()%> <br>
        <label>Numero de comprobante</label> : <%= venta.getNumComprobante()%> <br>
        <label>Fecha de la compra</label> : <%= venta.getFechaHora().getDay() + "/" + venta.getFechaHora().getMonth() + "/" + venta.getFechaHora().getYear()%><br>
        <hr>
        <h4>Detalle de la Venta</h4>
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
                for (DetalleVentas v : lista) {
                    cont++;
            %>
            <tr>
                <td><%= cont%></td>
                <td><%= v.getIdarticulo().getNombre() %></td>
                <td><%= v.getCantidad() %></td>
                <td><%= v.getPrecioVenta() %></td>
                <td><%= v.getDescuento() %></td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</body>
</html>