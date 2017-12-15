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
import entidades.Categorias;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author User
 */
public class CategoriasJpaController implements Serializable {

    public CategoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categorias categorias) {
        if (categorias.getArticulosList() == null) {
            categorias.setArticulosList(new ArrayList<Articulos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Articulos> attachedArticulosList = new ArrayList<Articulos>();
            for (Articulos articulosListArticulosToAttach : categorias.getArticulosList()) {
                articulosListArticulosToAttach = em.getReference(articulosListArticulosToAttach.getClass(), articulosListArticulosToAttach.getIdarticulo());
                attachedArticulosList.add(articulosListArticulosToAttach);
            }
            categorias.setArticulosList(attachedArticulosList);
            em.persist(categorias);
            for (Articulos articulosListArticulos : categorias.getArticulosList()) {
                Categorias oldIdcategoriaOfArticulosListArticulos = articulosListArticulos.getIdcategoria();
                articulosListArticulos.setIdcategoria(categorias);
                articulosListArticulos = em.merge(articulosListArticulos);
                if (oldIdcategoriaOfArticulosListArticulos != null) {
                    oldIdcategoriaOfArticulosListArticulos.getArticulosList().remove(articulosListArticulos);
                    oldIdcategoriaOfArticulosListArticulos = em.merge(oldIdcategoriaOfArticulosListArticulos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categorias categorias) throws IllegalOrphanException, NonexistentEntityException, Exception {
        if (categorias.getArticulosList() == null) {
            categorias.setArticulosList(new ArrayList<Articulos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categorias persistentCategorias = em.find(Categorias.class, categorias.getIdcategoria());
            List<Articulos> articulosListOld = persistentCategorias.getArticulosList();
            List<Articulos> articulosListNew = categorias.getArticulosList();
            List<String> illegalOrphanMessages = null;
            for (Articulos articulosListOldArticulos : articulosListOld) {
                if (!articulosListNew.contains(articulosListOldArticulos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Articulos " + articulosListOldArticulos + " since its idcategoria field is not nullable.");
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
            categorias.setArticulosList(articulosListNew);
            categorias = em.merge(categorias);
            for (Articulos articulosListNewArticulos : articulosListNew) {
                if (!articulosListOld.contains(articulosListNewArticulos)) {
                    Categorias oldIdcategoriaOfArticulosListNewArticulos = articulosListNewArticulos.getIdcategoria();
                    articulosListNewArticulos.setIdcategoria(categorias);
                    articulosListNewArticulos = em.merge(articulosListNewArticulos);
                    if (oldIdcategoriaOfArticulosListNewArticulos != null && !oldIdcategoriaOfArticulosListNewArticulos.equals(categorias)) {
                        oldIdcategoriaOfArticulosListNewArticulos.getArticulosList().remove(articulosListNewArticulos);
                        oldIdcategoriaOfArticulosListNewArticulos = em.merge(oldIdcategoriaOfArticulosListNewArticulos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categorias.getIdcategoria();
                if (findCategorias(id) == null) {
                    throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.");
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
            Categorias categorias;
            try {
                categorias = em.getReference(Categorias.class, id);
                categorias.getIdcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categorias with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Articulos> articulosListOrphanCheck = categorias.getArticulosList();
            for (Articulos articulosListOrphanCheckArticulos : articulosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categorias (" + categorias + ") cannot be destroyed since the Articulos " + articulosListOrphanCheckArticulos + " in its articulosList field has a non-nullable idcategoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categorias> findCategoriasEntities() {
        return findCategoriasEntities(true, -1, -1);
    }

    public List<Categorias> findCategoriasEntities(int maxResults, int firstResult) {
        return findCategoriasEntities(false, maxResults, firstResult);
    }

    private List<Categorias> findCategoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            //CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
           //cq.select(cq.from(Categorias.class));
            Query q = em.createQuery("SELECT c FROM Categorias c where c.condicion = 1");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Categorias findCategorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categorias.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categorias> rt = cq.from(Categorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
