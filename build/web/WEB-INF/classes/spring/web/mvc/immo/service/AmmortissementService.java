/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.web.mvc.immo.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.web.mvc.immo.dao.HibernateDAO;
import spring.web.mvc.immo.model.Ammortissement;

/**
 *
 * @author UCER
 */

@Service
public class AmmortissementService {
    @Autowired
    private HibernateDAO hibernateDao;

    public void insertAmmortissement(Ammortissement ammortissement) throws Exception {
        hibernateDao.save(ammortissement);
    }
    
    public List<Ammortissement> getListeAmmortissement(Ammortissement ammortissement) throws Exception {
        List<Ammortissement> liste = new ArrayList();
        liste = hibernateDao.findAll(ammortissement);
        return liste;
    }
    
    public List<Ammortissement> getAmmortissementByAnnee(Ammortissement ammortissement) throws Exception {
        List<Ammortissement> liste = new ArrayList();
        liste = hibernateDao.findByAnnee(ammortissement);
        return liste;
    }
}
