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
import entidades.Articulos;
import entidades.Units;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author User
 */
public class UnitsJpaController implements Serializable {

    public UnitsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Units units) {
        if (units.getArticulosList() == null) {
            units.setArticulosList(new ArrayList<Articulos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Articulos> attachedArticulosList = new ArrayList<Articulos>();
            for (Articulos articulosListArticulosToAttach : units.getArticulosList()) {
                articulosListArticulosToAttach = em.getReference(articulosListArticulosToAttach.getClass(), articulosListArticulosToAttach.getIdarticulo());
                attachedArticulosList.add(articulosListArticulosToAttach);
            }
            units.setArticulosList(attachedArticulosList);
            em.persist(units);
            for (Articulos articulosListArticulos : units.getArticulosList()) {
                Units oldUnitIdOfArticulosListArticulos = articulosListArticulos.getUnitId();
                articulosListArticulos.setUnitId(units);
                articulosListArticulos = em.merge(articulosListArticulos);
                if (oldUnitIdOfArticulosListArticulos != null) {
                    oldUnitIdOfArticulosListArticulos.getArticulosList().remove(articulosListArticulos);
                    oldUnitIdOfArticulosListArticulos = em.merge(oldUnitIdOfArticulosListArticulos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Units units) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Units persistentUnits = em.find(Units.class, units.getId());
            List<Articulos> articulosListOld = persistentUnits.getArticulosList();
            List<Articulos> articulosListNew = units.getArticulosList();
            List<String> illegalOrphanMessages = null;
            for (Articulos articulosListOldArticulos : articulosListOld) {
                if (!articulosListNew.contains(articulosListOldArticulos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Articulos " + articulosListOldArticulos + " since its unitId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Articulos> attachedArticulosListNew = new ArrayList<Articulos>();
            for (Articulos articulosListNewArticulosToAttach : articulosListNew) {
                articulosListNewArticulosToAttach = em.getReference(articulosListNewArticulosToAttach.getClass(), articulosListNewArticulosToAttach.getIdarticulo());
                attachedArticulosListNew.add(articulosListNewArticulosToAttach);
            }
            articulosListNew = attachedArticulosListNew;
            units.setArticulosList(articulosListNew);
            units = em.merge(units);
            for (Articulos articulosListNewArticulos : articulosListNew) {
                if (!articulosListOld.contains(articulosListNewArticulos)) {
                    Units oldUnitIdOfArticulosListNewArticulos = articulosListNewArticulos.getUnitId();
                    articulosListNewArticulos.setUnitId(units);
                    articulosListNewArticulos = em.merge(articulosListNewArticulos);
                    if (oldUnitIdOfArticulosListNewArticulos != null && !oldUnitIdOfArticulosListNewArticulos.equals(units)) {
                        oldUnitIdOfArticulosListNewArticulos.getArticulosList().remove(articulosListNewArticulos);
                        oldUnitIdOfArticulosListNewArticulos = em.merge(oldUnitIdOfArticulosListNewArticulos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = units.getId();
                if (findUnits(id) == null) {
                    throw new NonexistentEntityException("The units with id " + id + " no longer exists.");
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
            Units units;
            try {
                units = em.getReference(Units.class, id);
                units.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The units with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Articulos> articulosListOrphanCheck = units.getArticulosList();
            for (Articulos articulosListOrphanCheckArticulos : articulosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Units (" + units + ") cannot be destroyed since the Articulos " + articulosListOrphanCheckArticulos + " in its articulosList field has a non-nullable unitId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(units);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Units> findUnitsEntities() {
        return findUnitsEntities(true, -1, -1);
    }

    public List<Units> findUnitsEntities(int maxResults, int firstResult) {
        return findUnitsEntities(false, maxResults, firstResult);
    }

    private List<Units> findUnitsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Units.class));
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

    public Units findUnits(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Units.class, id);
        } finally {
            em.close();
        }
    }

    public int getUnitsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Units> rt = cq.from(Units.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
