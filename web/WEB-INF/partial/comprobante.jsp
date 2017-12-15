<%-- 
    Document   : comprobante
    Created on : 04-dic-2017, 13:57:56
    Author     : User
--%>

<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12">
    <div class="form-group">
        <label >Tipo Comprobante</label>
        <select name="tipo_comprobante" class="form-control">
            <option value="Boleta">Boleta</option>
            <option value="Factura">Factura</option>

        </select>
    </div>
</div>
<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12">
    <div class="form-group">
        <label for="serie_comprobante">Serie Comprobante</label>
        <input type="text" name="serie_comprobante" required  class="form-control" placeholder="Serie del comprobante">
    </div>
</div>
<div class="col-lg-4 col-sm-4 col-md-4 col-xs-12">
    <div class="form-group">
        <label for="num_comprobante">Numero Comprobante</label>
        <input type="text" name="num_comprobante" required  class="form-control" placeholder="Numero del comprobante">
    </div>
</div>
