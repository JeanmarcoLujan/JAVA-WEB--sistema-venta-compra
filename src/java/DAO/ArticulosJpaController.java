/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import entidades.ArticuloVenta;
import entidades.Articulos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Categorias;
import entidades.Units;
import entidades.DetalleIngresos;
import java.util.ArrayList;
import java.util.List;
import entidades.DetalleVentas;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Join;

/**
 *
 * @author User
 */
public class ArticulosJpaController implements Serializable {

    public ArticulosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulos articulos) {
        if (articulos.getDetalleIngresosList() == null) {
            articulos.setDetalleIngresosList(new ArrayList<DetalleIngresos>());
        }
        if (articulos.getDetalleVentasList() == null) {
            articulos.setDetalleVentasList(new ArrayList<DetalleVentas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias idcategoria = articulos.getIdcategoria();
            if (idcategoria != null) {
                idcategoria = em.getReference(idcategoria.getClass(), idcategoria.getIdcategoria());
                articulos.setIdcategoria(idcategoria);
            }
            Units unitId = articulos.getUnitId();
            if (unitId != null) {
                unitId = em.getReference(unitId.getClass(), unitId.getId());
                articulos.setUnitId(unitId);
            }
            List<DetalleIngresos> attachedDetalleIngresosList = new ArrayList<DetalleIngresos>();
            for (DetalleIngresos detalleIngresosListDetalleIngresosToAttach : articulos.getDetalleIngresosList()) {
                detalleIngresosListDetalleIngresosToAttach = em.getReference(detalleIngresosListDetalleIngresosToAttach.getClass(), detalleIngresosListDetalleIngresosToAttach.getIddetalleIngreso());
                attachedDetalleIngresosList.add(detalleIngresosListDetalleIngresosToAttach);
            }
            articulos.setDetalleIngresosList(attachedDetalleIngresosList);
            List<DetalleVentas> attachedDetalleVentasList = new ArrayList<DetalleVentas>();
            for (DetalleVentas detalleVentasListDetalleVentasToAttach : articulos.getDetalleVentasList()) {
                detalleVentasListDetalleVentasToAttach = em.getReference(detalleVentasListDetalleVentasToAttach.getClass(), detalleVentasListDetalleVentasToAttach.getIddetalleVenta());
                attachedDetalleVentasList.add(detalleVentasListDetalleVentasToAttach);
            }
            articulos.setDetalleVentasList(attachedDetalleVentasList);
            em.persist(articulos);
            if (idcategoria != null) {
                idcategoria.getArticulosList().add(articulos);
                idcategoria = em.merge(idcategoria);
            }
            if (unitId != null) {
                unitId.getArticulosList().add(articulos);
                unitId = em.merge(unitId);
            }
            for (DetalleIngresos detalleIngresosListDetalleIngresos : articulos.getDetalleIngresosList()) {
                Articulos oldIdarticuloOfDetalleIngresosListDetalleIngresos = detalleIngresosListDetalleIngresos.getIdarticulo();
                detalleIngresosListDetalleIngresos.setIdarticulo(articulos);
                detalleIngresosListDetalleIngresos = em.merge(detalleIngresosListDetalleIngresos);
                if (oldIdarticuloOfDetalleIngresosListDetalleIngresos != null) {
                    oldIdarticuloOfDetalleIngresosListDetalleIngresos.getDetalleIngresosList().remove(detalleIngresosListDetalleIngresos);
                    oldIdarticuloOfDetalleIngresosListDetalleIngresos = em.merge(oldIdarticuloOfDetalleIngresosListDetalleIngresos);
                }
            }
            for (DetalleVentas detalleVentasListDetalleVentas : articulos.getDetalleVentasList()) {
                Articulos oldIdarticuloOfDetalleVentasListDetalleVentas = detalleVentasListDetalleVentas.getIdarticulo();
                detalleVentasListDetalleVentas.setIdarticulo(articulos);
                detalleVentasListDetalleVentas = em.merge(detalleVentasListDetalleVentas);
                if (oldIdarticuloOfDetalleVentasListDetalleVentas != null) {
                    oldIdarticuloOfDetalleVentasListDetalleVentas.getDetalleVentasList().remove(detalleVentasListDetalleVentas);
                    oldIdarticuloOfDetalleVentasListDetalleVentas = em.merge(oldIdarticuloOfDetalleVentasListDetalleVentas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articulos articulos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        if (articulos.getDetalleIngresosList() == null) {
            articulos.setDetalleIngresosList(new ArrayList<DetalleIngresos>());
        }
        if (articulos.getDetalleVentasList() == null) {
            articulos.setDetalleVentasList(new ArrayList<DetalleVentas>());
        }
        EntityManager em = null;
        try {
            /*em = getEntityManager();
            em.getTransaction().begin();
            Articulos persistentArticulos = em.find(Articulos.class, articulos.getIdarticulo());
            Categorias idcategoriaOld = persistentArticulos.getIdcategoria();
            Categorias idcategoriaNew = articulos.getIdcategoria();
            Units unitIdOld = persistentArticulos.getUnitId();
            Units unitIdNew = articulos.getUnitId();
            List<DetalleIngresos> detalleIngresosListOld = persistentArticulos.getDetalleIngresosList();
            List<DetalleIngresos> detalleIngresosListNew = articulos.getDetalleIngresosList();
            List<DetalleVentas> detalleVentasListOld = persistentArticulos.getDetalleVentasList();
            List<DetalleVentas> detalleVentasListNew = articulos.getDetalleVentasList();
            List<String> illegalOrphanMessages = null;
            for (DetalleIngresos detalleIngresosListOldDetalleIngresos : detalleIngresosListOld) {
                if (!detalleIngresosListNew.contains(detalleIngresosListOldDetalleIngresos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleIngresos " + detalleIngresosListOldDetalleIngresos + " since its idarticulo field is not nullable.");
                }
            }
            for (DetalleVentas detalleVentasListOldDetalleVentas : detalleVentasListOld) {
                if (!detalleVentasListNew.contains(detalleVentasListOldDetalleVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleVentas " + detalleVentasListOldDetalleVentas + " since its idarticulo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idcategoriaNew != null) {
                idcategoriaNew = em.getReference(idcategoriaNew.getClass(), idcategoriaNew.getIdcategoria());
                articulos.setIdcategoria(idcategoriaNew);
            }
            if (unitIdNew != null) {
                unitIdNew = em.getReference(unitIdNew.getClass(), unitIdNew.getId());
                articulos.setUnitId(unitIdNew);
            }
            List<DetalleIngresos> attachedDetalleIngresosListNew = new ArrayList<DetalleIngresos>();
            for (DetalleIngresos detalleIngresosListNewDetalleIngresosToAttach : detalleIngresosListNew) {
                detalleIngresosListNewDetalleIngresosToAttach = em.getReference(detalleIngresosListNewDetalleIngresosToAttach.getClass(), detalleIngresosListNewDetalleIngresosToAttach.getIddetalleIngreso());
                attachedDetalleIngresosListNew.add(detalleIngresosListNewDetalleIngresosToAttach);
            }
            detalleIngresosListNew = attachedDetalleIngresosListNew;
            articulos.setDetalleIngresosList(detalleIngresosListNew);
            List<DetalleVentas> attachedDetalleVentasListNew = new ArrayList<DetalleVentas>();
            for (DetalleVentas detalleVentasListNewDetalleVentasToAttach : detalleVentasListNew) {
                detalleVentasListNewDetalleVentasToAttach = em.getReference(detalleVentasListNewDetalleVentasToAttach.getClass(), detalleVentasListNewDetalleVentasToAttach.getIddetalleVenta());
                attachedDetalleVentasListNew.add(detalleVentasListNewDetalleVentasToAttach);
            }
            detalleVentasListNew = attachedDetalleVentasListNew;
            articulos.setDetalleVentasList(detalleVentasListNew);
            articulos = em.merge(articulos);
            if (idcategoriaOld != null && !idcategoriaOld.equals(idcategoriaNew)) {
                idcategoriaOld.getArticulosList().remove(articulos);
                idcategoriaOld = em.merge(idcategoriaOld);
            }
            if (idcategoriaNew != null && !idcategoriaNew.equals(idcategoriaOld)) {
                idcategoriaNew.getArticulosList().add(articulos);
                idcategoriaNew = em.merge(idcategoriaNew);
            }
            if (unitIdOld != null && !unitIdOld.equals(unitIdNew)) {
                unitIdOld.getArticulosList().remove(articulos);
                unitIdOld = em.merge(unitIdOld);
            }
            if (unitIdNew != null && !unitIdNew.equals(unitIdOld)) {
                unitIdNew.getArticulosList().add(articulos);
                unitIdNew = em.merge(unitIdNew);
            }
            for (DetalleIngresos detalleIngresosListNewDetalleIngresos : detalleIngresosListNew) {
                if (!detalleIngresosListOld.contains(detalleIngresosListNewDetalleIngresos)) {
                    Articulos oldIdarticuloOfDetalleIngresosListNewDetalleIngresos = detalleIngresosListNewDetalleIngresos.getIdarticulo();
                    detalleIngresosListNewDetalleIngresos.setIdarticulo(articulos);
                    detalleIngresosListNewDetalleIngresos = em.merge(detalleIngresosListNewDetalleIngresos);
                    if (oldIdarticuloOfDetalleIngresosListNewDetalleIngresos != null && !oldIdarticuloOfDetalleIngresosListNewDetalleIngresos.equals(articulos)) {
                        oldIdarticuloOfDetalleIngresosListNewDetalleIngresos.getDetalleIngresosList().remove(detalleIngresosListNewDetalleIngresos);
                        oldIdarticuloOfDetalleIngresosListNewDetalleIngresos = em.merge(oldIdarticuloOfDetalleIngresosListNewDetalleIngresos);
                    }
                }
            }
            for (DetalleVentas detalleVentasListNewDetalleVentas : detalleVentasListNew) {
                if (!detalleVentasListOld.contains(detalleVentasListNewDetalleVentas)) {
                    Articulos oldIdarticuloOfDetalleVentasListNewDetalleVentas = detalleVentasListNewDetalleVentas.getIdarticulo();
                    detalleVentasListNewDetalleVentas.setIdarticulo(articulos);
                    detalleVentasListNewDetalleVentas = em.merge(detalleVentasListNewDetalleVentas);
                    if (oldIdarticuloOfDetalleVentasListNewDetalleVentas != null && !oldIdarticuloOfDetalleVentasListNewDetalleVentas.equals(articulos)) {
                        oldIdarticuloOfDetalleVentasListNewDetalleVentas.getDetalleVentasList().remove(detalleVentasListNewDetalleVentas);
                        oldIdarticuloOfDetalleVentasListNewDetalleVentas = em.merge(oldIdarticuloOfDetalleVentasListNewDetalleVentas);
                    }
                }
            }
            em.getTransaction().commit(); */

            em = getEntityManager();
            em.getTransaction().begin();
            Articulos persistentArticulos = em.find(Articulos.class, articulos.getIdarticulo());

            articulos = em.merge(articulos);

            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = articulos.getIdarticulo();
                if (findArticulos(id) == null) {
                    throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulos articulos;
            try {
                articulos = em.getReference(Articulos.class, id);
                articulos.getIdarticulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleIngresos> detalleIngresosListOrphanCheck = articulos.getDetalleIngresosList();
            for (DetalleIngresos detalleIngresosListOrphanCheckDetalleIngresos : detalleIngresosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the DetalleIngresos " + detalleIngresosListOrphanCheckDetalleIngresos + " in its detalleIngresosList field has a non-nullable idarticulo field.");
            }
            List<DetalleVentas> detalleVentasListOrphanCheck = articulos.getDetalleVentasList();
            for (DetalleVentas detalleVentasListOrphanCheckDetalleVentas : detalleVentasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Articulos (" + articulos + ") cannot be destroyed since the DetalleVentas " + detalleVentasListOrphanCheckDetalleVentas + " in its detalleVentasList field has a non-nullable idarticulo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categorias idcategoria = articulos.getIdcategoria();
            if (idcategoria != null) {
                idcategoria.getArticulosList().remove(articulos);
                idcategoria = em.merge(idcategoria);
            }
            Units unitId = articulos.getUnitId();
            if (unitId != null) {
                unitId.getArticulosList().remove(articulos);
                unitId = em.merge(unitId);
            }
            em.remove(articulos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulos> findArticulosEntities() {
        return findArticulosEntities(true, -1, -1);
    }

    public List<Articulos> findArticulosEntities(int maxResults, int firstResult) {
        return findArticulosEntities(false, maxResults, firstResult);
    }

    private List<Articulos> findArticulosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            //CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            //cq.select(cq.from(Articulos.class));
            //Query q = em.createQuery(cq);
            Query q = em.createQuery("SELECT a FROM Articulos a where a.estado = 1");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Articulos> findArticulosEntitiesWithStock() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT a FROM Articulos a where a.estado = 1 and a.stock>0 ");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<ArticuloVenta> findArticulosForVenta() {
        EntityManager em = getEntityManager();
        try {
            //Query q = em.createQuery("SELECT a.idarticulo,a.nombre,a.stock,AVG(d.precio_venta) as precio_venta FROM articulos a inner join detalle_ingresos d on a.idarticulo = d.idarticulo WHERE a.estado=1 and a.stock>0 GROUP BY a.idarticulo,a.nombre,a.stock ");
            Query q = em.createQuery("SELECT a.idarticulo,a.nombre,a.stock, AVG(s.precioVenta) FROM Articulos a JOIN a.detalleIngresosList s WHERE a.estado=1 and a.stock>0 GROUP BY a.idarticulo ,a.nombre,a.stock ");
            //TypedQuery<ArticuloVenta> q = em.createQuery("SELECT a FROM Articulos a JOIN a.detalle_ingresos d ", ArticuloVenta.class);
            //SELECT p FROM Teacher t JOIN t.phones p
            //Query q = em.createQuery(qlString);
            List<ArticuloVenta> listV = new ArrayList();
            
            
            List<String> list = new ArrayList();

            List<Object[]> results = q.getResultList();
            for (Object[] result : results) {
                ArticuloVenta ar = new ArticuloVenta();
                ar.setIdarticulo( Integer.parseInt(result[0].toString()));
                ar.setNombre(result[1].toString());
                ar.setStock(Integer.parseInt(result[2].toString()));
                ar.setPrecio_venta(Double.parseDouble(result[3].toString()));
                listV.add(ar);
            }
            
            
//            List<Double> lista = q.getResultList();
//            List<String> list = new ArrayList();
//
//            List<Object[]> results = q.getResultList();
//            for (Object[] result : results) {
//                list.add("art: " + result[0] + ", avg: " + result[1]);
//            }

//            Iterator i = lista.iterator();
//            String id;
//            while (i.hasNext()) {
//                id = i.next().toString();
//                list.add(id);
//            }

            return listV;

        } finally {
            em.close();
        }
    }

    public Articulos findArticulos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulos.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticulosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulos> rt = cq.from(Articulos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
