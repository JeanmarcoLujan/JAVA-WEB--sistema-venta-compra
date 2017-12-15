<%-- 
    Document   : new
    Created on : 01-dic-2017, 1:06:10
    Author     : User
--%>

<%@page import="entidades.ArticuloVenta"%>
<%@page import="entidades.Articulos"%>
<%@page import="entidades.Personas"%>
<%@page import="java.util.ArrayList"%>
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
        <h1>Nueva Venta</h1>
        <form method="POST" action="../venta-insert">
            <div class="col-lg-4 col-sm-4 col-md-4 col-xs-12">
                <div class="form-group">
                    <label >Cliente</label>
                    <%
                        List<Personas> clientes = new ArrayList();
                        clientes = (List<Personas>) request.getAttribute("clientes");
                    %>
                    <select id="idcliente" name="idcliente" class="form-control selectpicker" data-live-search="true">
                        <option selected disabled>Seleccione Cliente</option>
                        <%
                            for (Personas c : clientes) {
                        %>
                        <option value="<%= c.getIdpersona()%>"><%= c.getNombre()%></option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>
            <%@ include file='../partial/comprobante.jsp' %>
            <div class="panel panle-primary">
                <div class="panel-body">
                    <div class="col-lg-12 col-sm-12 col-md-12 col-xs-12">
                        <div class="form-group">
                            <button type="button" id="bt_add" class="btn btn-primary">Agregar</button>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-3 col-md-3 col-xs-12">
                        <div class="form-group">
                            <label>Articulo</label>
                            <%
                                List<ArticuloVenta> arts = new ArrayList();
                                arts = (List<ArticuloVenta>) request.getAttribute("listaArticulo");
                            %>
                            <select name="pidarticulo" class="form-control selectpicker" id="pidarticulo" data-live-search="true">
                                <option selected disabled>Seleccione Articulo</option>
                                <%
                                    for (ArticuloVenta a : arts) {

                                %>
                                <option value="<%= a.getIdarticulo() %>_<%= a.getStock()%>_<%= a.getPrecio_venta()%>"><%= a.getNombre()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    
                    <div class="col-lg-2 col-sm-2 col-md-2 col-xs-12">
                        <div class="form-group">
                            <label for="cantidad">Cantidad</label>
                            <input type="number" name="pcantidad" id="pcantidad" class="form-control" placeholder="Cantidad">
                        </div>
                    </div>
                    <div class="col-lg-2 col-sm-2 col-md-2 col-xs-12">
                        <div class="form-group">
                            <label for="stock">Stock</label>
                            <input type="number" disabled="true" name="pstock" id="pstock" class="form-control" placeholder="Stock">
                        </div>
                    </div>
                    <div class="col-lg-2 col-sm-2 col-md-2 col-xs-12">
                        <div class="form-group">
                            <label for="precio_venta">Precio Venta</label>
                            <input type="number" disabled="true" name="pprecio_venta" id="pprecio_venta" class="form-control" placeholder="P. Venta">
                        </div>
                    </div>
                    <div class="col-lg-2 col-sm-2 col-md-2 col-xs-12">
                        <div class="form-group">
                            <label for="descuento">Descuento</label>
                            <input type="number" name="pdescuento" id="pdescuento" class="form-control" placeholder="Descuento">
                        </div>
                    </div>
                    <div class="col-lg-12 col-sm-12 col-md-12 col-xs-12">
                        <table id="detalles" class="table table-striped table-bordered table-condensed table-hover">
                            <thead style="background-color:#A9D0F5">
                            <th>Opciones</th>
                            <th>Articulo</th>
                            <th>Cantidad</th>
                            <th>Precio Venta</th>
                            <th>Descuento</th>
                            <th>Subtotal</th>
                            </thead>
                            <tfoot>
                            <th>TOTAL</th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th><h4 id="total">S/. 0.00</h4><input type="hidden" name="total_venta" id="total_venta"></th>

                            </tfoot>
                            <tbody> </tbody>

                        </table>
                    </div>

                </div>
            </div>
            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12" id="guardar">
                <div class="form-group">

                    <button class="btn btn-primary" type="submit">Guardar</button>
                    <button class="btn btn-danger" type="reset">Cancelar</button>
                </div>
            </div>        
        </form>


        <%@ include file='../partial/_JS.jspf' %>
        <script>
            $(document).ready(function () {
                $('#bt_add').click(function () {
                    agregar();
                });
            });
            total = 0;
            var cont = 0;
            subtotal = [];
            $('#guardar').hide();
            $('#pidarticulo').change(mostrarValores);

            function mostrarValores() {
                datosArticulo = document.getElementById('pidarticulo').value.split('_');
                $("#pprecio_venta").val(datosArticulo[2]);
                $("#pstock").val(datosArticulo[1]);
            }


            function agregar() {

                datosArticulo = document.getElementById('pidarticulo').value.split('_');
                idarticulo = datosArticulo[0];

                articulo = $('#pidarticulo option:selected').text();
                cantidad = $('#pcantidad').val();
                descuento = $('#pdescuento').val();
                precio_venta = $('#pprecio_venta').val();
                stock = $('#pstock').val();



                if (idarticulo != "" && cantidad != "" && cantidad > 0 && descuento != "" && precio_venta != "") {
                    if (stock >= cantidad) {
                        subtotal[cont] = (cantidad * precio_venta - descuento);
                        total = total + subtotal[cont];
                        var fila = '<tr class="selected" id="fila' + cont + '"><td><button type="button" class="btn btn-warning" onclick="eliminar(' + cont + ');">X</button></td><td><input type="hidden" name="idarticulo" value="' + idarticulo + '">' + articulo + '</td><td><input type="number" name="cantidad" value="' + cantidad + '"></td><td><input type="number" name="precio_venta" value="' + precio_venta + '"></td><td><input type="number" name="descuento" value="' + descuento + '"></td><td>' + subtotal[cont] + '</td></tr>';
                        cont++;
                        limpiar();
                        $('#total').html("S/. " + total);
                        $('#total_venta').val(total);
                        evaluar();
                        $('#detalles').append(fila);
                    } else {
                        alert("La cantidad a vender supera el stock");
                    }



                } else {
                    alert("Error al ingresar el detalle de la venta, revise los datos del articulo");
                }
            }


            function limpiar() {
                $('#pcantidad').val("");
                $('#pdescuento').val("");
                $('#pprecio_venta').val("");
            }

            function evaluar() {
                if (total > 0) {
                    $('#guardar').show();
                } else {
                    $('#guardar').hide();
                }
            }

            function eliminar(index) {
                total = total - subtotal[index];
                $("#total").html("S/. " + total);
                $("#total_venta").val(total);
                $("#fila" + index).remove();
                evaluar();
            }
        </script>
    </body>

</html>



