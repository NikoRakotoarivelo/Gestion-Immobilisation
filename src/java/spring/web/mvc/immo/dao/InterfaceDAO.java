/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.web.mvc.immo.dao;

import java.io.Serializable;
import java.util.List;
import spring.web.mvc.immo.model.BaseModel;

/**
 *
 * @author UCER
 */
public interface InterfaceDAO {
    
    int save(BaseModel baseModel) throws Exception;
    List findAll(BaseModel baseModel) throws Exception;
    BaseModel findById(BaseModel baseModel) throws Exception;
    List findByAnnee(BaseModel baseModel) throws Exception;
    List pagination(BaseModel baseModel, int first, int max) throws Exception;
    
    BaseModel findByRehetra(BaseModel baseModel) throws Exception;
}

