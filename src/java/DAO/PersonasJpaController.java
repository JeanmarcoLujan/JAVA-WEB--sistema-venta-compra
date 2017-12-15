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
import entidades.Ingresos;
import entidades.Personas;
import java.util.ArrayList;
import java.util.List;
import entidades.Ventas;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author User
 */
public class PersonasJpaController implements Serializable {

    public PersonasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personas personas) {
        if (personas.getIngresosList() == null) {
            personas.setIngresosList(new ArrayList<Ingresos>());
        }
        if (personas.getVentasList() == null) {
            personas.setVentasList(new ArrayList<Ventas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Ingresos> attachedIngresosList = new ArrayList<Ingresos>();
            for (Ingresos ingresosListIngresosToAttach : personas.getIngresosList()) {
                ingresosListIngresosToAttach = em.getReference(ingresosListIngresosToAttach.getClass(), ingresosListIngresosToAttach.getIdingreso());
                attachedIngresosList.add(ingresosListIngresosToAttach);
            }
            personas.setIngresosList(attachedIngresosList);
            List<Ventas> attachedVentasList = new ArrayList<Ventas>();
            for (Ventas ventasListVentasToAttach : personas.getVentasList()) {
                ventasListVentasToAttach = em.getReference(ventasListVentasToAttach.getClass(), ventasListVentasToAttach.getIdventa());
                attachedVentasList.add(ventasListVentasToAttach);
            }
            personas.setVentasList(attachedVentasList);
            em.persist(personas);
            for (Ingresos ingresosListIngresos : personas.getIngresosList()) {
                Personas oldIdproveedorOfIngresosListIngresos = ingresosListIngresos.getIdproveedor();
                ingresosListIngresos.setIdproveedor(personas);
                ingresosListIngresos = em.merge(ingresosListIngresos);
                if (oldIdproveedorOfIngresosListIngresos != null) {
                    oldIdproveedorOfIngresosListIngresos.getIngresosList().remove(ingresosListIngresos);
                    oldIdproveedorOfIngresosListIngresos = em.merge(oldIdproveedorOfIngresosListIngresos);
                }
            }
            for (Ventas ventasListVentas : personas.getVentasList()) {
                Personas oldIdclienteOfVentasListVentas = ventasListVentas.getIdcliente();
                ventasListVentas.setIdcliente(personas);
                ventasListVentas = em.merge(ventasListVentas);
                if (oldIdclienteOfVentasListVentas != null) {
                    oldIdclienteOfVentasListVentas.getVentasList().remove(ventasListVentas);
                    oldIdclienteOfVentasListVentas = em.merge(oldIdclienteOfVentasListVentas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personas personas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        if (personas.getIngresosList() == null) {
            personas.setIngresosList(new ArrayList<Ingresos>());
        }
        if (personas.getVentasList() == null) {
            personas.setVentasList(new ArrayList<Ventas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas persistentPersonas = em.find(Personas.class, personas.getIdpersona());
            List<Ingresos> ingresosListOld = persistentPersonas.getIngresosList();
            List<Ingresos> ingresosListNew = personas.getIngresosList();
            List<Ventas> ventasListOld = persistentPersonas.getVentasList();
            List<Ventas> ventasListNew = personas.getVentasList();
            List<String> illegalOrphanMessages = null;
            for (Ingresos ingresosListOldIngresos : ingresosListOld) {
                if (!ingresosListNew.contains(ingresosListOldIngresos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ingresos " + ingresosListOldIngresos + " since its idproveedor field is not nullable.");
                }
            }
            for (Ventas ventasListOldVentas : ventasListOld) {
                if (!ventasListNew.contains(ventasListOldVentas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Ventas " + ventasListOldVentas + " since its idcliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Ingresos> attachedIngresosListNew = new ArrayList<Ingresos>();
            for (Ingresos ingresosListNewIngresosToAttach : ingresosListNew) {
                ingresosListNewIngresosToAttach = em.getReference(ingresosListNewIngresosToAttach.getClass(), ingresosListNewIngresosToAttach.getIdingreso());
                attachedIngresosListNew.add(ingresosListNewIngresosToAttach);
            }
            ingresosListNew = attachedIngresosListNew;
            personas.setIngresosList(ingresosListNew);
            List<Ventas> attachedVentasListNew = new ArrayList<Ventas>();
            for (Ventas ventasListNewVentasToAttach : ventasListNew) {
                ventasListNewVentasToAttach = em.getReference(ventasListNewVentasToAttach.getClass(), ventasListNewVentasToAttach.getIdventa());
                attachedVentasListNew.add(ventasListNewVentasToAttach);
            }
            ventasListNew = attachedVentasListNew;
            personas.setVentasList(ventasListNew);
            personas = em.merge(personas);
            for (Ingresos ingresosListNewIngresos : ingresosListNew) {
                if (!ingresosListOld.contains(ingresosListNewIngresos)) {
                    Personas oldIdproveedorOfIngresosListNewIngresos = ingresosListNewIngresos.getIdproveedor();
                    ingresosListNewIngresos.setIdproveedor(personas);
                    ingresosListNewIngresos = em.merge(ingresosListNewIngresos);
                    if (oldIdproveedorOfIngresosListNewIngresos != null && !oldIdproveedorOfIngresosListNewIngresos.equals(personas)) {
                        oldIdproveedorOfIngresosListNewIngresos.getIngresosList().remove(ingresosListNewIngresos);
                        oldIdproveedorOfIngresosListNewIngresos = em.merge(oldIdproveedorOfIngresosListNewIngresos);
                    }
                }
            }
            for (Ventas ventasListNewVentas : ventasListNew) {
                if (!ventasListOld.contains(ventasListNewVentas)) {
                    Personas oldIdclienteOfVentasListNewVentas = ventasListNewVentas.getIdcliente();
                    ventasListNewVentas.setIdcliente(personas);
                    ventasListNewVentas = em.merge(ventasListNewVentas);
                    if (oldIdclienteOfVentasListNewVentas != null && !oldIdclienteOfVentasListNewVentas.equals(personas)) {
                        oldIdclienteOfVentasListNewVentas.getVentasList().remove(ventasListNewVentas);
                        oldIdclienteOfVentasListNewVentas = em.merge(oldIdclienteOfVentasListNewVentas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personas.getIdpersona();
                if (findPersonas(id) == null) {
                    throw new NonexistentEntityException("The personas with id " + id + " no longer exists.");
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
            Personas personas;
            try {
                personas = em.getReference(Personas.class, id);
                personas.getIdpersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Ingresos> ingresosListOrphanCheck = personas.getIngresosList();
            for (Ingresos ingresosListOrphanCheckIngresos : ingresosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Ingresos " + ingresosListOrphanCheckIngresos + " in its ingresosList field has a non-nullable idproveedor field.");
            }
            List<Ventas> ventasListOrphanCheck = personas.getVentasList();
            for (Ventas ventasListOrphanCheckVentas : ventasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Ventas " + ventasListOrphanCheckVentas + " in its ventasList field has a non-nullable idcliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(personas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personas> findPersonasEntities() {
        return findPersonasEntities(true, -1, -1);
    }

    public List<Personas> findPersonasEntities(int maxResults, int firstResult) {
        return findPersonasEntities(false, maxResults, firstResult);
    }

    private List<Personas> findPersonasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personas.class));
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

    public Personas findPersonas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personas> rt = cq.from(Personas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Personas> findClientesEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT p FROM Personas p WHERE p.tipoPersona = 'cliente'");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Personas> findProveedoresEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT p FROM Personas p WHERE p.tipoPersona = 'proveedor'");
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
