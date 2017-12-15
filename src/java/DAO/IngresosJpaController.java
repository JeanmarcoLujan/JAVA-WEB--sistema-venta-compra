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
import entidades.DetalleIngresos;
import entidades.Ingresos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author User
 */
public class IngresosJpaController implements Serializable {

    public IngresosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ingresos ingresos) {
        if (ingresos.getDetalleIngresosList() == null) {
            ingresos.setDetalleIngresosList(new ArrayList<DetalleIngresos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas idproveedor = ingresos.getIdproveedor();
            if (idproveedor != null) {
                idproveedor = em.getReference(idproveedor.getClass(), idproveedor.getIdpersona());
                ingresos.setIdproveedor(idproveedor);
            }
            List<DetalleIngresos> attachedDetalleIngresosList = new ArrayList<DetalleIngresos>();
            for (DetalleIngresos detalleIngresosListDetalleIngresosToAttach : ingresos.getDetalleIngresosList()) {
                detalleIngresosListDetalleIngresosToAttach = em.getReference(detalleIngresosListDetalleIngresosToAttach.getClass(), detalleIngresosListDetalleIngresosToAttach.getIddetalleIngreso());
                attachedDetalleIngresosList.add(detalleIngresosListDetalleIngresosToAttach);
            }
            ingresos.setDetalleIngresosList(attachedDetalleIngresosList);
            em.persist(ingresos);
            if (idproveedor != null) {
                idproveedor.getIngresosList().add(ingresos);
                idproveedor = em.merge(idproveedor);
            }
            for (DetalleIngresos detalleIngresosListDetalleIngresos : ingresos.getDetalleIngresosList()) {
                Ingresos oldIdingresoOfDetalleIngresosListDetalleIngresos = detalleIngresosListDetalleIngresos.getIdingreso();
                detalleIngresosListDetalleIngresos.setIdingreso(ingresos);
                detalleIngresosListDetalleIngresos = em.merge(detalleIngresosListDetalleIngresos);
                if (oldIdingresoOfDetalleIngresosListDetalleIngresos != null) {
                    oldIdingresoOfDetalleIngresosListDetalleIngresos.getDetalleIngresosList().remove(detalleIngresosListDetalleIngresos);
                    oldIdingresoOfDetalleIngresosListDetalleIngresos = em.merge(oldIdingresoOfDetalleIngresosListDetalleIngresos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ingresos ingresos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ingresos persistentIngresos = em.find(Ingresos.class, ingresos.getIdingreso());
            Personas idproveedorOld = persistentIngresos.getIdproveedor();
            Personas idproveedorNew = ingresos.getIdproveedor();
            List<DetalleIngresos> detalleIngresosListOld = persistentIngresos.getDetalleIngresosList();
            List<DetalleIngresos> detalleIngresosListNew = ingresos.getDetalleIngresosList();
            List<String> illegalOrphanMessages = null;
            for (DetalleIngresos detalleIngresosListOldDetalleIngresos : detalleIngresosListOld) {
                if (!detalleIngresosListNew.contains(detalleIngresosListOldDetalleIngresos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleIngresos " + detalleIngresosListOldDetalleIngresos + " since its idingreso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idproveedorNew != null) {
                idproveedorNew = em.getReference(idproveedorNew.getClass(), idproveedorNew.getIdpersona());
                ingresos.setIdproveedor(idproveedorNew);
            }
            List<DetalleIngresos> attachedDetalleIngresosListNew = new ArrayList<DetalleIngresos>();
            for (DetalleIngresos detalleIngresosListNewDetalleIngresosToAttach : detalleIngresosListNew) {
                detalleIngresosListNewDetalleIngresosToAttach = em.getReference(detalleIngresosListNewDetalleIngresosToAttach.getClass(), detalleIngresosListNewDetalleIngresosToAttach.getIddetalleIngreso());
                attachedDetalleIngresosListNew.add(detalleIngresosListNewDetalleIngresosToAttach);
            }
            detalleIngresosListNew = attachedDetalleIngresosListNew;
            ingresos.setDetalleIngresosList(detalleIngresosListNew);
            ingresos = em.merge(ingresos);
            if (idproveedorOld != null && !idproveedorOld.equals(idproveedorNew)) {
                idproveedorOld.getIngresosList().remove(ingresos);
                idproveedorOld = em.merge(idproveedorOld);
            }
            if (idproveedorNew != null && !idproveedorNew.equals(idproveedorOld)) {
                idproveedorNew.getIngresosList().add(ingresos);
                idproveedorNew = em.merge(idproveedorNew);
            }
            for (DetalleIngresos detalleIngresosListNewDetalleIngresos : detalleIngresosListNew) {
                if (!detalleIngresosListOld.contains(detalleIngresosListNewDetalleIngresos)) {
                    Ingresos oldIdingresoOfDetalleIngresosListNewDetalleIngresos = detalleIngresosListNewDetalleIngresos.getIdingreso();
                    detalleIngresosListNewDetalleIngresos.setIdingreso(ingresos);
                    detalleIngresosListNewDetalleIngresos = em.merge(detalleIngresosListNewDetalleIngresos);
                    if (oldIdingresoOfDetalleIngresosListNewDetalleIngresos != null && !oldIdingresoOfDetalleIngresosListNewDetalleIngresos.equals(ingresos)) {
                        oldIdingresoOfDetalleIngresosListNewDetalleIngresos.getDetalleIngresosList().remove(detalleIngresosListNewDetalleIngresos);
                        oldIdingresoOfDetalleIngresosListNewDetalleIngresos = em.merge(oldIdingresoOfDetalleIngresosListNewDetalleIngresos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ingresos.getIdingreso();
                if (findIngresos(id) == null) {
                    throw new NonexistentEntityException("The ingresos with id " + id + " no longer exists.");
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
            Ingresos ingresos;
            try {
                ingresos = em.getReference(Ingresos.class, id);
                ingresos.getIdingreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ingresos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleIngresos> detalleIngresosListOrphanCheck = ingresos.getDetalleIngresosList();
            for (DetalleIngresos detalleIngresosListOrphanCheckDetalleIngresos : detalleIngresosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ingresos (" + ingresos + ") cannot be destroyed since the DetalleIngresos " + detalleIngresosListOrphanCheckDetalleIngresos + " in its detalleIngresosList field has a non-nullable idingreso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Personas idproveedor = ingresos.getIdproveedor();
            if (idproveedor != null) {
                idproveedor.getIngresosList().remove(ingresos);
                idproveedor = em.merge(idproveedor);
            }
            em.remove(ingresos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ingresos> findIngresosEntities() {
        return findIngresosEntities(true, -1, -1);
    }

    public List<Ingresos> findIngresosEntities(int maxResults, int firstResult) {
        return findIngresosEntities(false, maxResults, firstResult);
    }

    private List<Ingresos> findIngresosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ingresos.class));
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

    public Ingresos findIngresos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ingresos.class, id);
        } finally {
            em.close();
        }
    }

    public int getIngresosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ingresos> rt = cq.from(Ingresos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
