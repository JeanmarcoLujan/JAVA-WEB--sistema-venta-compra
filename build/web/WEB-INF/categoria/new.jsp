<%-- 
    Document   : new
    Created on : 30-nov-2017, 12:56:49
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Nueva Categoria!</h1>
        <form action="../categoria-insert" method="POST">
            <div class="form-group">
            	<label for="nombre">Nombre</label>
                <input type="text" name="nombre" class="form-control" placeholder="Nombre..." required="true">
            </div>
            <div class="form-group">
            	<label for="descripcion">Descripción</label>
                <input type="text" name="descripcion" class="form-control" placeholder="Descripción..." required="true">
            </div>
            <div class="form-group">
            	<button class="btn btn-primary" type="submit">Guardar</button>
            	<button class="btn btn-danger" type="reset">Cancelar</button>
            </div>
        </form>
    </body>
</html>
