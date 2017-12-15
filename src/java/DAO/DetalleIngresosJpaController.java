/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Ingresos;
import entidades.Articulos;
import entidades.DetalleIngresos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author User
 */
public class DetalleIngresosJpaController implements Serializable {

    public DetalleIngresosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleIngresos detalleIngresos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingresos idingreso = detalleIngresos.getIdingreso();
            if (idingreso != null) {
                idingreso = em.getReference(idingreso.getClass(), idingreso.getIdingreso());
                detalleIngresos.setIdingreso(idingreso);
            }
            Articulos idarticulo = detalleIngresos.getIdarticulo();
            if (idarticulo != null) {
                idarticulo = em.getReference(idarticulo.getClass(), idarticulo.getIdarticulo());
                detalleIngresos.setIdarticulo(idarticulo);
            }
            em.persist(detalleIngresos);
            if (idingreso != null) {
                idingreso.getDetalleIngresosList().add(detalleIngresos);
                idingreso = em.merge(idingreso);
            }
            if (idarticulo != null) {
                idarticulo.getDetalleIngresosList().add(detalleIngresos);
                idarticulo = em.merge(idarticulo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleIngresos detalleIngresos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleIngresos persistentDetalleIngresos = em.find(DetalleIngresos.class, detalleIngresos.getIddetalleIngreso());
            Ingresos idingresoOld = persistentDetalleIngresos.getIdingreso();
            Ingresos idingresoNew = detalleIngresos.getIdingreso();
            Articulos idarticuloOld = persistentDetalleIngresos.getIdarticulo();
            Articulos idarticuloNew = detalleIngresos.getIdarticulo();
            if (idingresoNew != null) {
                idingresoNew = em.getReference(idingresoNew.getClass(), idingresoNew.getIdingreso());
                detalleIngresos.setIdingreso(idingresoNew);
            }
            if (idarticuloNew != null) {
                idarticuloNew = em.getReference(idarticuloNew.getClass(), idarticuloNew.getIdarticulo());
                detalleIngresos.setIdarticulo(idarticuloNew);
            }
            detalleIngresos = em.merge(detalleIngresos);
            if (idingresoOld != null && !idingresoOld.equals(idingresoNew)) {
                idingresoOld.getDetalleIngresosList().remove(detalleIngresos);
                idingresoOld = em.merge(idingresoOld);
            }
            if (idingresoNew != null && !idingresoNew.equals(idingresoOld)) {
                idingresoNew.getDetalleIngresosList().add(detalleIngresos);
                idingresoNew = em.merge(idingresoNew);
            }
            if (idarticuloOld != null && !idarticuloOld.equals(idarticuloNew)) {
                idarticuloOld.getDetalleIngresosList().remove(detalleIngresos);
                idarticuloOld = em.merge(idarticuloOld);
            }
            if (idarticuloNew != null && !idarticuloNew.equals(idarticuloOld)) {
                idarticuloNew.getDetalleIngresosList().add(detalleIngresos);
                idarticuloNew = em.merge(idarticuloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleIngresos.getIddetalleIngreso();
                if (findDetalleIngresos(id) == null) {
                    throw new NonexistentEntityException("The detalleIngresos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleIngresos detalleIngresos;
            try {
                detalleIngresos = em.getReference(DetalleIngresos.class, id);
                detalleIngresos.getIddetalleIngreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleIngresos with id " + id + " no longer exists.", enfe);
            }
            Ingresos idingreso = detalleIngresos.getIdingreso();
            if (idingreso != null) {
                idingreso.getDetalleIngresosList().remove(detalleIngresos);
                idingreso = em.merge(idingreso);
            }
            Articulos idarticulo = detalleIngresos.getIdarticulo();
            if (idarticulo != null) {
                idarticulo.getDetalleIngresosList().remove(detalleIngresos);
                idarticulo = em.merge(idarticulo);
            }
            em.remove(detalleIngresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleIngresos> findDetalleIngresosEntities() {
        return findDetalleIngresosEntities(true, -1, -1);
    }

    public List<DetalleIngresos> findDetalleIngresosEntities(int maxResults, int firstResult) {
        return findDetalleIngresosEntities(false, maxResults, firstResult);
    }

    private List<DetalleIngresos> findDetalleIngresosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleIngresos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DetalleIngresos findDetalleIngresos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleIngresos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleIngresosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleIngresos> rt = cq.from(DetalleIngresos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
