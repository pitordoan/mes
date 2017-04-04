/************************************************************************************
 * Copyright (c) 2011 CIMPoint.  All rights reserved. 
 * This source is subjected to CIMPoint license as described in the License.txt file.
 * 
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY KIND, 
 * EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND/OR FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Contributors:
 *     pitor - initial implementation
 ***********************************************************************************/
package com.cimpoint.mes.server.repositories;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cimpoint.common.server.repositories.JpaRepository;
import com.cimpoint.mes.client.MESConfigurations;
import com.cimpoint.mes.common.entities.EComponent;
import com.cimpoint.mes.common.entities.ERecipe;

@Repository("recipeRepository")
public class RecipeRepository extends JpaRepository<Long, ERecipe> {
	
	@Autowired
	private EntityManager entityManager;

	public RecipeRepository() {
		super(ERecipe.class);
	}
	
	@PostConstruct
	public void init() {
		super.setEntityManager(entityManager);
		super.setDatabaseType(MESConfigurations.getDatabaseType());
	}
	
	@Transactional
	public ERecipe createRecipe(String name, Set<EComponent> components) throws Exception {
		ERecipe equipment = new ERecipe(name, components);
		getEntityManager().persist(equipment);
		return equipment;
	}
	
	@Transactional
	public ERecipe updateRecipe(ERecipe equipment) {
		getEntityManager().merge(equipment);
		return equipment;
	}
		
	public ERecipe findRecipeById(long id) {
		return (ERecipe) getEntityManager().find(ERecipe.class, id);
	}
	
	public ERecipe findRecipeByName(String equipmentName) throws Exception {
		try {
			ERecipe equipment = (ERecipe) getEntityManager()
					.createQuery("select o from ERecipe o where o.number = ?1")
					.setParameter(1, equipmentName)
					.getSingleResult();
			return equipment;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	public EComponent findComponentById(Long componentId) {
		return (EComponent) getEntityManager().find(EComponent.class, componentId);
	}

	public EComponent findComponentByNameAndRevision(String componentName, String componentRevision) throws Exception {
		try {
			EComponent c = (EComponent) getEntityManager()
					.createQuery("select o from EComponent o where o.name = ?1 and o.revision = ?2")
					.setParameter(1, componentName)
					.setParameter(2, componentRevision)
					.getSingleResult();
			return c;
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}
}
