/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Personas;
import entidades.DetalleVentas;
import entidades.Ventas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author User
 */
public class VentasJpaController implements Serializable {

    public VentasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ventas ventas) {
        if (ventas.getDetalleVentasList() == null) {
            ventas.setDetalleVentasList(new ArrayList<DetalleVentas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas idcliente = ventas.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getIdpersona());
                ventas.setIdcliente(idcliente);
            }
            List<DetalleVentas> attachedDetalleVentasList = new ArrayList<DetalleVentas>();
            for (DetalleVentas detalleVentasListDetalleVentasToAttach : ventas.getDetalleVentasList()) {
                detalleVentasListDetalleVentasToAttach = em.getReference(detalleVentasListDetalleVentasToAttach.getClass(), detalleVentasListDetalleVentasToAttach.getIddetalleVenta());
                attachedDetalleVentasList.add(detalleVentasListDetalleVentasToAttach);
            }
            ventas.setDetalleVentasList(attachedDetalleVentasList);
            em.persist(ventas);
            if (idcliente != null) {
                idcliente.getVentasList().add(ventas);
                idcliente = em.merge(idcliente);
            }
            for (DetalleVentas detalleVentasListDetalleVentas : ventas.getDetalleVentasList()) {
                Ventas oldIdventaOfDetalleVentasListDetalleVentas = detalleVentasListDetalleVentas.getIdventa();
                detalleVentasListDetalleVentas.setIdventa(ventas);
                detalleVentasListDetalleVentas = em.merge(detalleVentasListDetalleVentas);
                if (oldIdventaOfDetalleVentasListDetalleVentas != null) {
                    oldIdventaOfDetalleVentasListDetalleVentas.getDetalleVentasList().remove(detalleVentasListDetalleVentas);
                    oldIdventaOfDetalleVentasListDetalleVentas = em.merge(oldIdventaOfDetalleVentasListDetalleVentas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ventas ventas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ventas persistentVentas = em.find(Ventas.class, ventas.getIdventa());
            Personas idclienteOld = persistentVentas.getIdcliente();
            Personas idclienteNew = ventas.getIdcliente();
            List<DetalleVentas> detalleVentasListOld = persistentVentas.getDetalleVentasList();
            List<DetalleVentas> detalleVentasListNew = ventas.getDetalleVentasList();
            List<String> illegalOrphanMessages = null;
            for (DetalleVentas detalleVentasListOldDetalleVentas : detalleVentasListOld) {
                if (!detalleVentasListNew.contains(detalleVentasListOldDetalleVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleVentas " + detalleVentasListOldDetalleVentas + " since its idventa field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdpersona());
                ventas.setIdcliente(idclienteNew);
            }
            List<DetalleVentas> attachedDetalleVentasListNew = new ArrayList<DetalleVentas>();
            for (DetalleVentas detalleVentasListNewDetalleVentasToAttach : detalleVentasListNew) {
                detalleVentasListNewDetalleVentasToAttach = em.getReference(detalleVentasListNewDetalleVentasToAttach.getClass(), detalleVentasListNewDetalleVentasToAttach.getIddetalleVenta());
                attachedDetalleVentasListNew.add(detalleVentasListNewDetalleVentasToAttach);
            }
            detalleVentasListNew = attachedDetalleVentasListNew;
            ventas.setDetalleVentasList(detalleVentasListNew);
            ventas = em.merge(ventas);
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getVentasList().remove(ventas);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getVentasList().add(ventas);
                idclienteNew = em.merge(idclienteNew);
            }
            for (DetalleVentas detalleVentasListNewDetalleVentas : detalleVentasListNew) {
                if (!detalleVentasListOld.contains(detalleVentasListNewDetalleVentas)) {
                    Ventas oldIdventaOfDetalleVentasListNewDetalleVentas = detalleVentasListNewDetalleVentas.getIdventa();
                    detalleVentasListNewDetalleVentas.setIdventa(ventas);
                    detalleVentasListNewDetalleVentas = em.merge(detalleVentasListNewDetalleVentas);
                    if (oldIdventaOfDetalleVentasListNewDetalleVentas != null && !oldIdventaOfDetalleVentasListNewDetalleVentas.equals(ventas)) {
                        oldIdventaOfDetalleVentasListNewDetalleVentas.getDetalleVentasList().remove(detalleVentasListNewDetalleVentas);
                        oldIdventaOfDetalleVentasListNewDetalleVentas = em.merge(oldIdventaOfDetalleVentasListNewDetalleVentas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ventas.getIdventa();
                if (findVentas(id) == null) {
                    throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.");
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
            Ventas ventas;
            try {
                ventas = em.getReference(Ventas.class, id);
                ventas.getIdventa();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ventas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleVentas> detalleVentasListOrphanCheck = ventas.getDetalleVentasList();
            for (DetalleVentas detalleVentasListOrphanCheckDetalleVentas : detalleVentasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ventas (" + ventas + ") cannot be destroyed since the DetalleVentas " + detalleVentasListOrphanCheckDetalleVentas + " in its detalleVentasList field has a non-nullable idventa field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Personas idcliente = ventas.getIdcliente();
            if (idcliente != null) {
                idcliente.getVentasList().remove(ventas);
                idcliente = em.merge(idcliente);
            }
            em.remove(ventas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ventas> findVentasEntities() {
        return findVentasEntities(true, -1, -1);
    }

    public List<Ventas> findVentasEntities(int maxResults, int firstResult) {
        return findVentasEntities(false, maxResults, firstResult);
    }

    private List<Ventas> findVentasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ventas.class));
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

    public Ventas findVentas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ventas.class, id);
        } finally {
            em.close();
        }
    }

    public int getVentasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ventas> rt = cq.from(Ventas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
