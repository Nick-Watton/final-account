package com.qa.service.repository;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import java.util.Collection;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.qa.domain.Account;
import com.qa.util.JSONUtil;

@Transactional(SUPPORTS)
@Default //Default method used by compiler, instead of AccountMapRepository
public class AccountDBRepository implements AccountRepository {

	@PersistenceContext(unitName = "primary")
	private EntityManager manager; //Interfaces with database -> persistence.xml

	@Inject	//Tells bean container to take over life-cycle management of object
	private JSONUtil util;

	@Override
	public String getAllAccounts() {
		Query query = manager.createQuery("Select a FROM Account a"); //New query created with manager, selects * from accounts
		Collection<Account> accounts = (Collection<Account>) query.getResultList(); //Executes query object and returns collection of objects
		return util.getJSONForObject(accounts); //Returns JSON for accounts collection
	}

	@Override
	@Transactional(REQUIRED)
	public String createAccount(String accout) {
		Account anAccount = util.getObjectForJSON(accout, Account.class);	//Gets JSON object for account
		manager.persist(anAccount);	//Adds account
		return "{\"message\": \"account has been sucessfully added\"}";
	}

	@Override
	@Transactional(REQUIRED)
	public String updateAccount(Long id, String accountToUpdate) {
		Account updatedAccount = util.getObjectForJSON(accountToUpdate, Account.class); //Gets JSON for account and puts in object
		Account accountFromDB = findAccount(id); //Finds account based on ID
		if (accountToUpdate != null) {
			accountFromDB = updatedAccount;		//Checks to make sure account is not null, updates account
			manager.merge(accountFromDB);
		}
		return "{\"message\": \"account sucessfully updated\"}";
	}

	@Override
	@Transactional(REQUIRED)
	public String deleteAccount(Long id) {
		Account accountInDB = findAccount(id);	//Takes in ID of account to be deleted
		if (accountInDB != null) {
			manager.remove(accountInDB);	//If object found, account deleted
		}
		return "{\"message\": \"account sucessfully deleted\"}";
	}

	private Account findAccount(Long id) {
		return manager.find(Account.class, id);  //Finds account from ID
	}

	public void setManager(EntityManager manager) {		//Setter for EntityManager
		this.manager = manager;
	}

	public void setUtil(JSONUtil util) {	//Setter for util
		this.util = util;
	}

}
