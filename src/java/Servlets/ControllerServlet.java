/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import DAO.ArticulosJpaController;
import DAO.CategoriasJpaController;
import DAO.DetalleIngresosJpaController;
import DAO.DetalleVentasJpaController;
import DAO.IngresosJpaController;
import DAO.PersonasJpaController;
import DAO.UnitsJpaController;
import DAO.VentasJpaController;
import DAO.exceptions.NonexistentEntityException;
import entidades.ArticuloVenta;
import entidades.Articulos;
import entidades.Categorias;
import entidades.DetalleIngresos;
import entidades.DetalleVentas;
import entidades.Ingresos;
import entidades.Personas;
import entidades.Units;
import entidades.Ventas;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author User
 */
public class ControllerServlet extends HttpServlet {

    EntityManagerFactory emf = null;
    CategoriasJpaController cat = null;
    ArticulosJpaController art = null;
    UnitsJpaController uni = null;
    PersonasJpaController per = null;
    VentasJpaController ven = null;
    IngresosJpaController com = null;
    DetalleIngresosJpaController deta_com = null;
    DetalleVentasJpaController deta_ven = null;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        emf = Persistence.createEntityManagerFactory("App4SistemaPU");
        cat = new CategoriasJpaController(emf);
        art = new ArticulosJpaController(emf);
        uni = new UnitsJpaController(emf);
        per = new PersonasJpaController(emf);
        ven = new VentasJpaController(emf);
        com = new IngresosJpaController(emf);
        deta_com = new DetalleIngresosJpaController(emf);
        deta_ven = new DetalleVentasJpaController(emf);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/categoria":
                    showListCategoria(request, response);
                    break;
                case "/categoria/new":
                    showNewCategoria(request, response);
                    break;
                case "/categoria/delete":
                    deleteCategoria(request, response);
                    break;
                case "/categoria/edit":
                    showEditCategoria(request, response);
                    break;
                case "/categoria-insert":
                    insertCategoria(request, response);
                    break;
                case "/categoria-update":
                    updateCategoria(request, response);
                    break;
                case "/articulo":
                    showListArticulo(request, response);
                    break;
                case "/articulo/new":
                    showNewArticulo(request, response);
                    break;
                case "/articulo/edit":
                    showEditArticulo(request, response);
                    break;
                case "/articulo/delete":
                    deleteArticulo(request, response);
                    break;
                case "/articulo/view":
                    showViewArticulo(request, response);
                    break;
                case "/articulo-insert":
                    insertArticulo(request, response);
                    break;
                case "/articulo-update":
                    updateArticulo(request, response);
                    break;
                case "/cliente":
                    showListCliente(request, response);
                    break;
                case "/cliente/new":
                    showNewCliente(request, response);
                    break;
                case "/cliente/edit":
                    showEditCliente(request, response);
                    break;
                case "/proveedor":
                    showListProveedor(request, response);
                    break;
                case "/proveedor/new":
                    showNewProveedor(request, response);
                    break;
                case "/proveedor/edit":
                    showEditProveedor(request, response);
                    break;
                case "/persona-insert":
                    insertPersona(request, response);
                    break;
                case "/persona-update":
                    updatePersona(request, response);
                    break;
                case "/venta":
                    showListVenta(request, response);
                    break;
                case "/venta/new":
                    showNewVenta(request, response);
                    break;
                case "/venta/view":
                    showViewVenta(request, response);
                    break;
                case "/venta-insert":
                    insertVenta(request, response);
                    break;
                case "/compra":
                    showListCompra(request, response);
                    break;
                case "/compra/new":
                    showNewCompra(request, response);
                    break;
                case "/compra/view":
                    showViewCompra(request, response);
                    break;
                case "/compra-insert":
                    insertCompra(request, response);
                    break;

            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void showListCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categorias> listCategoria = cat.findCategoriasEntities();
        request.setAttribute("listCategoria", listCategoria);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/categoria/list.jsp");
        dispatcher.forward(request, response);
    }

    private void deleteCategoria(HttpServletRequest request, HttpServletResponse response) throws NonexistentEntityException, Exception {
        Categorias one_cat = new Categorias();
        one_cat = cat.findCategorias(Integer.parseInt(request.getParameter("id_cat")));
        one_cat.setCondicion(false);
        cat.edit(one_cat);
        response.sendRedirect("../categoria");

    }

    private void showNewCategoria(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/categoria/new.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Categorias one_cat = new Categorias();
        one_cat = cat.findCategorias(Integer.parseInt(request.getParameter("id_cat")));
        request.setAttribute("categoria", one_cat);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/categoria/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void insertCategoria(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Categorias new_cat = new Categorias();
        new_cat.setNombre(request.getParameter("nombre"));
        new_cat.setDescripcion(request.getParameter("descripcion"));
        new_cat.setCondicion(true);
        cat.create(new_cat);
        response.sendRedirect("categoria");
    }

    private void updateCategoria(HttpServletRequest request, HttpServletResponse response) throws NonexistentEntityException, Exception {
        Categorias edit_cat = new Categorias();
        edit_cat.setIdcategoria(Integer.parseInt(request.getParameter("id")));
        edit_cat.setNombre(request.getParameter("nombre"));
        edit_cat.setDescripcion(request.getParameter("descripcion"));
        edit_cat.setCondicion(true);
        cat.edit(edit_cat);
        response.sendRedirect("categoria");
    }

    private void showListArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Articulos> listArticulo = art.findArticulosEntities();
        request.setAttribute("listArticulo", listArticulo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/articulo/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categorias> categorias = new ArrayList();
        categorias = cat.findCategoriasEntities();
        List<Units> unidades = new ArrayList();
        unidades = uni.findUnitsEntities();
        request.setAttribute("categorias", categorias);
        request.setAttribute("unidades", unidades);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/articulo/new.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Categorias> categorias = new ArrayList();
        categorias = cat.findCategoriasEntities();
        List<Units> unidades = new ArrayList();
        unidades = uni.findUnitsEntities();
        Articulos one_art = new Articulos();
        one_art = art.findArticulos(Integer.parseInt(request.getParameter("id_art")));
        request.setAttribute("categorias", categorias);
        request.setAttribute("unidades", unidades);
        request.setAttribute("articulo", one_art);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/articulo/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void showViewArticulo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Articulos one_art = new Articulos();
        one_art = art.findArticulos(Integer.parseInt(request.getParameter("id_art")));
        request.setAttribute("articulo", one_art);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/articulo/view.jsp");
        dispatcher.forward(request, response);
    }

    private void insertArticulo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Articulos new_art = new Articulos();
        new_art.setIdcategoria(cat.findCategorias(Integer.parseInt(request.getParameter("idcategoria"))));
        new_art.setUnitId(uni.findUnits(Integer.parseInt(request.getParameter("unit_id"))));
        new_art.setCodigo(request.getParameter("codigo"));
        new_art.setNombre(request.getParameter("nombre"));
        new_art.setStock(Integer.parseInt(request.getParameter("stock")));
        new_art.setDescripcion(request.getParameter("descripcion"));
        new_art.setImagen("img/piel.jpg");
        new_art.setEstado("1");
        art.create(new_art);
        response.sendRedirect("articulo");
    }

    private void updateArticulo(HttpServletRequest request, HttpServletResponse response) throws IOException, NonexistentEntityException, Exception {
        Articulos edit_art = new Articulos();
        edit_art.setIdarticulo(Integer.parseInt(request.getParameter("id")));
        edit_art.setIdcategoria(cat.findCategorias(Integer.parseInt(request.getParameter("idcategoria"))));
        edit_art.setUnitId(uni.findUnits(Integer.parseInt(request.getParameter("unit_id"))));
        edit_art.setCodigo(request.getParameter("codigo"));
        edit_art.setNombre(request.getParameter("nombre"));
        edit_art.setStock(Integer.parseInt(request.getParameter("stock")));
        edit_art.setDescripcion(request.getParameter("descripcion"));
        edit_art.setImagen("img/piel.jpg");
        edit_art.setEstado("1");

        art.edit(edit_art);
        response.sendRedirect("articulo");
    }

    private void deleteArticulo(HttpServletRequest request, HttpServletResponse response) throws IOException, NonexistentEntityException, Exception {
        Articulos one_art = new Articulos();
        one_art = art.findArticulos(Integer.parseInt(request.getParameter("id_art")));
        one_art.setEstado("0");
        art.edit(one_art);
        response.sendRedirect("../articulo");
    }

    private void showListCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Personas> listaCliente = per.findClientesEntities();
        request.setAttribute("listaCliente", listaCliente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cliente/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("persona", "Cliente");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/persona/new.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Personas one_per_c = new Personas();
        one_per_c = per.findPersonas(Integer.parseInt(request.getParameter("id_cli")));
        request.setAttribute("per", one_per_c);
        request.setAttribute("persona", "Cliente");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/persona/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void showListProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Personas> listaProveedor = per.findProveedoresEntities();
        request.setAttribute("listaProveedor", listaProveedor);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/proveedor/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("persona", "Proveedor");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/persona/new.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditProveedor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Personas one_per = new Personas();
        one_per = per.findPersonas(Integer.parseInt(request.getParameter("id_pro")));
        request.setAttribute("per", one_per);
        request.setAttribute("persona", "Proveedor");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/persona/edit.jsp");
        dispatcher.forward(request, response);
    }

    private void insertPersona(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Personas new_per = new Personas();
        new_per.setTipoPersona(request.getParameter("tipo_persona"));
        new_per.setNombre(request.getParameter("nombre"));
        new_per.setDireccion(request.getParameter("direccion"));
        new_per.setTipoDocumento(request.getParameter("tipo_documento"));
        new_per.setNumDocumento(request.getParameter("num_documento"));
        new_per.setTelefono(request.getParameter("telefono"));
        new_per.setEmail(request.getParameter("email"));
        per.create(new_per);
        if (request.getParameter("tipo_persona").compareTo("cliente") == 0) {
            response.sendRedirect("cliente");
        } else {
            response.sendRedirect("proveedor");
        }

    }

    private void updatePersona(HttpServletRequest request, HttpServletResponse response) throws IOException, NonexistentEntityException, Exception {
        Personas edit_per = new Personas();
        edit_per.setIdpersona(Integer.parseInt(request.getParameter("id")));
        edit_per.setTipoPersona(request.getParameter("tipo_persona"));
        edit_per.setNombre(request.getParameter("nombre"));
        edit_per.setDireccion(request.getParameter("direccion"));
        edit_per.setTipoDocumento(request.getParameter("tipo_documento"));
        edit_per.setNumDocumento(request.getParameter("num_documento"));
        edit_per.setTelefono(request.getParameter("telefono"));
        edit_per.setEmail(request.getParameter("email"));
        per.edit(edit_per);
        if (request.getParameter("tipo_persona").compareTo("cliente") == 0) {
            response.sendRedirect("cliente");
        } else {
            response.sendRedirect("proveedor");
        }
    }

    private void showListVenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ventas> listVenta = ven.findVentasEntities();
        request.setAttribute("listVenta", listVenta);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/venta/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewVenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Personas> listaCliente = per.findClientesEntities();
        //List listaArticulo = new ArrayList();
        List<ArticuloVenta> listaArticulo = art.findArticulosForVenta();

        request.setAttribute("clientes", listaCliente);
        request.setAttribute("listaArticulo", listaArticulo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/venta/new.jsp");
        dispatcher.forward(request, response);
    }

    private void showViewVenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Ventas venta = ven.findVentas(Integer.parseInt(request.getParameter("id_venta")));
        request.setAttribute("venta", venta );
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/venta/detail.jsp");
        dispatcher.forward(request, response);
    }

    private void insertVenta(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Ventas new_venta = new Ventas();
        new_venta.setIdcliente(per.findPersonas(Integer.parseInt(request.getParameter("idcliente"))));
        new_venta.setTipoComprobante(request.getParameter("tipo_comprobante"));
        new_venta.setSerieComprobante(request.getParameter("serie_comprobante"));
        new_venta.setNumComprobante(request.getParameter("num_comprobante"));
        new_venta.setFechaHora(new Date());
        new_venta.setEstado("1");
        ven.create(new_venta);

        //List<DetalleIngresos> detalles = new ArrayList();
        int num_detalle = request.getParameterValues("idarticulo").length;
        for (int i = 0; i < num_detalle; i++) {
            DetalleVentas deta = new DetalleVentas();
            deta.setIdventa(ven.findVentas(new_venta.getIdventa()));
            deta.setIdarticulo(art.findArticulos(Integer.parseInt(request.getParameterValues("idarticulo")[i])));
            deta.setCantidad(Integer.parseInt(request.getParameterValues("cantidad")[i]));
            deta.setPrecioVenta(new BigDecimal(request.getParameterValues("precio_venta")[i]));
            deta.setDescuento(new BigDecimal(request.getParameterValues("descuento")[i]));
            deta_ven.create(deta);
            //detalles.add(deta);
        }
        response.sendRedirect("venta");

    }

    private void showListCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Ingresos> listaCompra = com.findIngresosEntities();
        request.setAttribute("listaCompra", listaCompra);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/compra/list.jsp");
        dispatcher.forward(request, response);
    }

    private void showViewCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Ingresos compra = com.findIngresos(Integer.parseInt(request.getParameter("id_compra")));
        request.setAttribute("compra", compra);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/compra/detail.jsp");
        dispatcher.forward(request, response);
    }

    private void insertCompra(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Ingresos new_compra = new Ingresos();
        new_compra.setIdproveedor(per.findPersonas(Integer.parseInt(request.getParameter("idproveedor"))));
        new_compra.setTipoComprobante(request.getParameter("tipo_comprobante"));
        new_compra.setSerieComprobante(request.getParameter("serie_comprobante"));
        new_compra.setNumComprobante(request.getParameter("num_comprobante"));
        new_compra.setFechaHora(new Date());
        new_compra.setEstado("1");
        com.create(new_compra);

        //List<DetalleIngresos> detalles = new ArrayList();
        int num_detalle = request.getParameterValues("idarticulo").length;
        for (int i = 0; i < num_detalle; i++) {
            DetalleIngresos deta = new DetalleIngresos();
            deta.setIdingreso(com.findIngresos(new_compra.getIdingreso()));
            deta.setIdarticulo(art.findArticulos(Integer.parseInt(request.getParameterValues("idarticulo")[i])));
            deta.setCantidad(Integer.parseInt(request.getParameterValues("cantidad")[i]));
            deta.setPrecioCompra(Integer.parseInt(request.getParameterValues("precio_compra")[i]));
            deta.setPrecioVenta(Integer.parseInt(request.getParameterValues("precio_venta")[i]));
            deta_com.create(deta);
            //detalles.add(deta);
        }
        //new_compra.setDetalleIngresosList(detalles);

        response.sendRedirect("compra");

    }

    private void showNewCompra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Personas> listaProveedor = per.findProveedoresEntities();
        List<Articulos> listaArticulo = art.findArticulosEntities();
        request.setAttribute("proveedores", listaProveedor);
        request.setAttribute("listaArticulo", listaArticulo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/compra/new.jsp");
        dispatcher.forward(request, response);
    }
}
