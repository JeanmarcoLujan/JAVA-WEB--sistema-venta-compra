<%-- 
    Document   : new
    Created on : 01-dic-2017, 1:06:10
    Author     : User
--%>

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
        <h1>Nueva Compra</h1>
        <form action="../compra-insert" method="POST">
            <div class="col-lg-4 col-sm-4 col-md-4 col-xs-12">
                <div class="form-group">
                    <label >Proveedor</label>
                    <%
                        List<Personas> proveedores = new ArrayList();
                        proveedores = (List<Personas>) request.getAttribute("proveedores");
                    %>
                    <select id="idproveedor" name="idproveedor" class="form-control selectpicker" data-live-search="true">
                        <option selected disabled>Seleccione Proveedor</option>
                        <%
                            for (Personas p : proveedores) {
                        %>
                        <option value="<%= p.getIdpersona()%>"><%= p.getNombre()%></option>
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
                                List<Articulos> arts = new ArrayList();
                                arts = (List<Articulos>) request.getAttribute("listaArticulo");
                            %>
                            <select name="pidarticulo" class="form-control selectpicker" id="pidarticulo" data-live-search="true">
                                <option selected disabled>Seleccione Articulo</option>
                                <%
                                    for (Articulos a : arts) {
                                %>
                                <option value="<%= a.getIdarticulo()%>"><%= a.getNombre()%></option>
                                <%
                                    }
                                %>
                            </select>
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-3 col-md-3 col-xs-12">
                        <div class="form-group">
                            <label for="cantidad">Cantidad</label>
                            <input type="number" name="pcantidad" id="pcantidad" class="form-control" placeholder="Cantidad">
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-3 col-md-3 col-xs-12">
                        <div class="form-group">
                            <label for="precio_compra">Precio Compra</label>
                            <input type="number" name="pprecio_compra" id="pprecio_compra" class="form-control" placeholder="P. Compra">
                        </div>
                    </div>
                    <div class="col-lg-3 col-sm-3 col-md-3 col-xs-12">
                        <div class="form-group">
                            <label for="precio_venta">Precio Venta</label>
                            <input type="number" name="pprecio_venta" id="pprecio_venta" class="form-control" placeholder="P. Venta">
                        </div>
                    </div>

                    <div class="col-lg-12 col-sm-12 col-md-12 col-xs-12">
                        <table id="detalles" class="table table-striped table-bordered table-condensed table-hover">
                            <thead style="background-color:#A9D0F5">
                            <th>Opciones</th>
                            <th>Articulo</th>
                            <th>Cantidad</th>
                            <th>Precio Compra</th>
                            <th>Precio Venta</th>
                            <th>Subtotal</th>
                            </thead>
                            <tfoot>
                            <th>TOTAL</th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th></th>
                            <th><h4 id="total">S/. 0.00</h4></th>

                            </tfoot>
                            <tbody> </tbody>

                        </table>
                    </div>

                </div>
            </div>
            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12" id="guardar">
                <div class="form-group">
                    <input name="_token" value="{{ csrf_token() }}" type="hidden">
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
            function agregar() {
                idarticulo = $('#pidarticulo').val();
                articulo = $('#pidarticulo option:selected').text();
                cantidad = $('#pcantidad').val();
                precio_compra = $('#pprecio_compra').val();
                precio_venta = $('#pprecio_venta').val();

                if (idarticulo != "" && cantidad != "" && cantidad > 0 && precio_compra != "" && precio_venta != "") {
                    subtotal[cont] = (cantidad * precio_compra);
                    total = total + subtotal[cont];

                    var fila = '<tr class="selected" id="fila' + cont + '"><td><button type="button" class="btn btn-warning" onclick="eliminar(' + cont + ');">X</button></td><td><input type="hidden" name="idarticulo" value="' + idarticulo + '">' + articulo + '</td><td><input type="number" name="cantidad" value="' + cantidad + '"></td><td><input type="number" name="precio_compra" value="' + precio_compra + '"></td><td><input type="number" name="precio_venta" value="' + precio_venta + '"></td><td>' + subtotal[cont] + '</td></tr>';
                    cont++;
                    limpiar();
                    $('#total').html("S/. " + total);
                    evaluar();
                    $('#detalles').append(fila);
                } else {
                    alert("Error al ingresar el detalle del ingreso, revise los satos del articulo");
                }
            }


            function limpiar() {
                $('#pcantidad').val("");
                $('#pprecio_compra').val("");
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
                $("#fila" + index).remove();
                evaluar();
            }

        </script>
    </body>
</html>
