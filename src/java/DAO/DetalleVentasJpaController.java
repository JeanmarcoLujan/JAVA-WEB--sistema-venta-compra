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
import entidades.Ventas;
import entidades.Articulos;
import entidades.DetalleVentas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author User
 */
public class DetalleVentasJpaController implements Serializable {

    public DetalleVentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleVentas detalleVentas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas idventa = detalleVentas.getIdventa();
            if (idventa != null) {
                idventa = em.getReference(idventa.getClass(), idventa.getIdventa());
                detalleVentas.setIdventa(idventa);
            }
            Articulos idarticulo = detalleVentas.getIdarticulo();
            if (idarticulo != null) {
                idarticulo = em.getReference(idarticulo.getClass(), idarticulo.getIdarticulo());
                detalleVentas.setIdarticulo(idarticulo);
            }
            em.persist(detalleVentas);
            if (idventa != null) {
                idventa.getDetalleVentasList().add(detalleVentas);
                idventa = em.merge(idventa);
            }
            if (idarticulo != null) {
                idarticulo.getDetalleVentasList().add(detalleVentas);
                idarticulo = em.merge(idarticulo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleVentas detalleVentas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleVentas persistentDetalleVentas = em.find(DetalleVentas.class, detalleVentas.getIddetalleVenta());
            Ventas idventaOld = persistentDetalleVentas.getIdventa();
            Ventas idventaNew = detalleVentas.getIdventa();
            Articulos idarticuloOld = persistentDetalleVentas.getIdarticulo();
            Articulos idarticuloNew = detalleVentas.getIdarticulo();
            if (idventaNew != null) {
                idventaNew = em.getReference(idventaNew.getClass(), idventaNew.getIdventa());
                detalleVentas.setIdventa(idventaNew);
            }
            if (idarticuloNew != null) {
                idarticuloNew = em.getReference(idarticuloNew.getClass(), idarticuloNew.getIdarticulo());
                detalleVentas.setIdarticulo(idarticuloNew);
            }
            detalleVentas = em.merge(detalleVentas);
            if (idventaOld != null && !idventaOld.equals(idventaNew)) {
                idventaOld.getDetalleVentasList().remove(detalleVentas);
                idventaOld = em.merge(idventaOld);
            }
            if (idventaNew != null && !idventaNew.equals(idventaOld)) {
                idventaNew.getDetalleVentasList().add(detalleVentas);
                idventaNew = em.merge(idventaNew);
            }
            if (idarticuloOld != null && !idarticuloOld.equals(idarticuloNew)) {
                idarticuloOld.getDetalleVentasList().remove(detalleVentas);
                idarticuloOld = em.merge(idarticuloOld);
            }
            if (idarticuloNew != null && !idarticuloNew.equals(idarticuloOld)) {
                idarticuloNew.getDetalleVentasList().add(detalleVentas);
                idarticuloNew = em.merge(idarticuloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleVentas.getIddetalleVenta();
                if (findDetalleVentas(id) == null) {
                    throw new NonexistentEntityException("The detalleVentas with id " + id + " no longer exists.");
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
            DetalleVentas detalleVentas;
            try {
                detalleVentas = em.getReference(DetalleVentas.class, id);
                detalleVentas.getIddetalleVenta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleVentas with id " + id + " no longer exists.", enfe);
            }
            Ventas idventa = detalleVentas.getIdventa();
            if (idventa != null) {
                idventa.getDetalleVentasList().remove(detalleVentas);
                idventa = em.merge(idventa);
            }
            Articulos idarticulo = detalleVentas.getIdarticulo();
            if (idarticulo != null) {
                idarticulo.getDetalleVentasList().remove(detalleVentas);
                idarticulo = em.merge(idarticulo);
            }
            em.remove(detalleVentas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleVentas> findDetalleVentasEntities() {
        return findDetalleVentasEntities(true, -1, -1);
    }

    public List<DetalleVentas> findDetalleVentasEntities(int maxResults, int firstResult) {
        return findDetalleVentasEntities(false, maxResults, firstResult);
    }

    private List<DetalleVentas> findDetalleVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleVentas.class));
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

    public DetalleVentas findDetalleVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleVentas.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleVentas> rt = cq.from(DetalleVentas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
