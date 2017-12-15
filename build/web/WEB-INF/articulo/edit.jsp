<%-- 
    Document   : edit
    Created on : 30-nov-2017, 19:25:11
    Author     : User
--%>

<%@page import="entidades.Articulos"%>
<%@page import="entidades.Units"%>
<%@page import="entidades.Categorias"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%! Articulos art = new Articulos();%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Editar Articulo!</h1>
        <%
            art = (Articulos) request.getAttribute("articulo");
        %>
        <form action="../articulo-update" method="POST">
            <input type="hidden" name="id" value="<%= art.getIdarticulo()%>">
            <div class="row">
                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <input type="text" name="nombre" 
                               class="form-control" value="<%= art.getNombre()%>">
                    </div>
                </div>
                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label>Categoría</label>
                        <select name="idcategoria" class="form-control">
                            <option disabled selected>Seleccionar categoria</option>
                            <%
                                List<Categorias> listaCat = new ArrayList();
                                listaCat = (List<Categorias>) request.getAttribute("categorias");
                                for (Categorias elem : listaCat) {
                                    if (elem.getIdcategoria() == art.getIdcategoria().getIdcategoria()) {
                            %>
                            <option selected value="<%= elem.getIdcategoria()%>"><%= elem.getNombre()%></option>
                            <%
                            } else {
                            %>
                            <option value="<%= elem.getIdcategoria()%>"><%= elem.getNombre()%></option>
                            <%
                                    }
                                }
                            %>

                        </select>
                    </div>
                </div>
                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label>Unidades</label>
                        <select name="unit_id" class="form-control">
                            <option disabled selected>Seleccionar Unidad</option>
                            <%
                                List<Units> listUnits = new ArrayList();
                                listUnits = (List<Units>) request.getAttribute("unidades");
                                for (Units u : listUnits) {
                                    if (u.getId() == art.getUnitId().getId()) {
                                        %>
                                        <option selected value="<%= u.getId()%>"><%= u.getDescripcion()%></option>
                                        <%
                                    } else {
                                        %>
                                        <option value="<%= u.getId()%>"><%= u.getDescripcion()%></option>
                                        <%
                                    }

                                }
                            %>

                        </select>
                    </div>
                </div>
                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label for="codigo">Código</label>
                        <input type="text" name="codigo"   class="form-control" value="<%= art.getCodigo()%>">
                    </div>
                </div>
                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label for="stock">Stock</label>
                        <input disabled type="text" name="stock"  class="form-control" value="<%= art.getStock()%>">
                    </div>
                </div>

                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label for="descripcion">Descripcion</label>
                        <input type="text" name="descripcion" class="form-control" value="<%= art.getDescripcion()%>">
                    </div>
                </div>

                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label for="imagen">Imagen</label>
                        <input type="file" name="imagen" class="form-control" placeholder="Imagen...">
                    </div>
                </div>

                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <button class="btn btn-primary" type="submit">Guardar</button>
                        <button class="btn btn-danger" type="reset">Cancelar</button>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
