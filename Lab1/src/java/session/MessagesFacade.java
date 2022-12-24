/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Messages;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MessagesFacade extends AbstractFacade<Messages> {
    @PersistenceContext(unitName = "myblogPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MessagesFacade() {
        super(Messages.class);
    }
    
}
