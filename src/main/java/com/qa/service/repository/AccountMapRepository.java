package com.qa.service.repository;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import com.qa.domain.Account;
import com.qa.util.JSONUtil;

@Alternative //Alternative method used by compiler, instead of AccountDBRepository
@ApplicationScoped //Bean and objects will live for as long as the application is running
public class AccountMapRepository implements AccountRepository {

	private final Long INITIAL_COUNT = 1L;
	private Map<Long, Account> accountMap;
	private Long ID;

	@Inject //Tells bean container to take over life-cycle management of object
	private JSONUtil util;

	public AccountMapRepository() {
		this.accountMap = new HashMap<Long, Account>(); //Creates new HashMap
		ID = INITIAL_COUNT;
		initAccountMap();
	}
//Some methods such as this one have the same names as AccountDBRepository. Allows AccountRepository to remain the same when using different methods
	@Override	//Calls to new map-based methods are contained within overridden methods e.g. initAccountMap called within AccountMapRepository
	public String getAllAccounts() {
		return util.getJSONForObject(accountMap.values());
	}

	@Override
	public String createAccount(String account) {
		ID++;
		Account newAccount = util.getObjectForJSON(account, Account.class);
		accountMap.put(ID, newAccount);
		return account;
	}

	@Override
	public String updateAccount(Long id, String accountToUpdate) {
		Account newAccount = util.getObjectForJSON(accountToUpdate, Account.class);
		accountMap.put(id, newAccount);
		return accountToUpdate;
	}

	@Override
	public String deleteAccount(Long id) {
		accountMap.remove(id);
		return "{\"message\": \"accout sucessfully removed\"}";
	}

	private void initAccountMap() {
		Account account = new Account("Joe", "Bloggs", "1234");
		accountMap.put(1L, account);
	}

}
