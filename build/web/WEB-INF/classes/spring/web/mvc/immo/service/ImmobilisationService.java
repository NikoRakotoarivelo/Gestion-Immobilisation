package spring.web.mvc.immo.service;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.web.mvc.immo.dao.HibernateDAO;

/**
 *
 * @author UCER
 */
import spring.web.mvc.immo.model.Immobilisation;

@Service
public class ImmobilisationService {

    @Autowired
    private HibernateDAO hibernateDao;

    public int insertImmobilisation(Immobilisation immobilisation) throws Exception {
        int id = hibernateDao.save(immobilisation);
        return id;
    }

    public List<Immobilisation> getListeImmobilisation(Immobilisation immobilisation) throws Exception {
        List<Immobilisation> liste = new ArrayList();
        liste = hibernateDao.findAll(immobilisation);
        return liste;
    }

    public Immobilisation getImmobilisationById(Immobilisation immobilisation) throws Exception {
        immobilisation = (Immobilisation) hibernateDao.findById(immobilisation);
        return immobilisation;
    }
    
    public List<Immobilisation> getImmobilisationBySearch(Immobilisation immobilisation) throws Exception {
        List<Immobilisation> liste = new ArrayList();
        liste = hibernateDao.findByAnnee(immobilisation);
        return liste;
    }
    
//    public Immobilisation paginationImmobilisatsion(Immobilisation immobilisation, int first, int max) throws Exception {
//        immobilisation = (Immobilisation) hibernateDao.pagination(immobilisation, first, max);
//        return immobilisation;
//    }
}
