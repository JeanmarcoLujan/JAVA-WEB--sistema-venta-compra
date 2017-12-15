<%-- 
    Document   : articulos
    Created on : 29-nov-2017, 12:26:19
    Author     : User
--%>

<%@page import="entidades.Units"%>
<%@page import="entidades.Categorias"%>
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
        <h1>Nuevo Articulo!</h1>
        <form action="../articulo-insert" method="POST">
            <div class="row">
                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <input type="text" name="nombre" 
                               class="form-control" placeholder="Nombre...">
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
                            %>
                            <option value="<%= elem.getIdcategoria()%>"><%= elem.getNombre()%></option>
                            <%
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
                            %>
                            <option value="<%= u.getId() %>"><%= u.getDescripcion() %></option>
                            <%
                                }
                            %>

                        </select>
                    </div>
                </div>
                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label for="codigo">Código</label>
                        <input type="text" name="codigo"   class="form-control" placeholder="Código...">
                    </div>
                </div>
                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label for="stock">Stock</label>
                        <input type="text" name="stock"  class="form-control" placeholder="Stock...">
                    </div>
                </div>

                <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                    <div class="form-group">
                        <label for="descripcion">Descripcion</label>
                        <input type="text" name="descripcion" class="form-control" placeholder="Descripcion...">
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
