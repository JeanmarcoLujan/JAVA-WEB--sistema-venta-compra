<%-- 
    Document   : edit
    Created on : 30-nov-2017, 23:24:18
    Author     : User
--%>

<%@page import="entidades.Personas"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Editar <%= request.getAttribute("persona") %></h1>
        <% 
            Personas per = new Personas();
            per = (Personas)request.getAttribute("per");
        %>
        <form action="../persona-update" method="POST">
            <input type="hidden" name="id" value="<%= per.getIdpersona()%>" >
            <input type="hidden" name="tipo_persona" value="<%= request.getAttribute("persona").toString().toLowerCase() %>" >
            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                <div class="form-group">
                    <label for="nombre">Nombre</label>
                    <input type="text" name="nombre" required  class="form-control" value="<%= per.getNombre()  %>">
                </div>
            </div>
            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                <div class="form-group">
                    <label for="direccion">Dirección</label>
                    <input type="text" name="direccion" required  class="form-control" value="<%= per.getDireccion()%>">
                </div>
            </div>
            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                <div class="form-group">
                    <label>Documento</label>
                    <select name="tipo_documento" class="form-control">
                        <option value="DNI">DNI</option>
                        <option value="RUC">RUC</option>
                        <option value="PAS">PAS</option>
                    </select>
                </div>
            </div>
            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                <div class="form-group">
                    <label for="num_documento">Número documento</label>
                    <input type="text" name="num_documento" required  class="form-control" value="<%= per.getNumDocumento()%>">
                </div>
            </div>

            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                <div class="form-group">
                    <label for="telefono">Teléfono</label>
                    <input type="text" name="telefono" required  class="form-control" value="<%= per.getTelefono()%>">
                </div>
            </div>

            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                <div class="form-group">
                    <label for="email">Dirección Email</label>
                    <input type="email" name="email" class="form-control" value="<%= per.getEmail() %>">
                </div>
            </div>

            <div class="col-lg-6 col-sm-6 col-md-6 col-xs-12">
                <div class="form-group">
                    <button class="btn btn-primary" type="submit">Guardar</button>
                    <button class="btn btn-danger" type="reset">Cancelar</button>
                </div>
            </div>
        </form>
    </body>
</html>
