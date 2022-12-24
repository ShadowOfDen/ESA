/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package session;

import entity.Contacts;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ShadowUsurper
 */
@Stateless
public class ContactsFacade extends AbstractFacade<Contacts> {

    @PersistenceContext(unitName = "myblogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContactsFacade() {
        super(Contacts.class);
    }
    
}
