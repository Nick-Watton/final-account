package com.qa.service.business;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.qa.service.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = Logger.getLogger(AccountService.class); //Creates log file for AccountService.class

	@Inject
	private AccountRepository repo;	//Looks for default implementation of AccountRepository interface

	public String getAllAccounts() {
		LOGGER.info("In AccountServiceImpl getAllAccounts "); //Inputs into log on method execution
		return repo.getAllAccounts();
	}

	@Override
	public String addAccount(String account) {
		LOGGER.info("In AccountServiceImpl createAccounts ");
		return repo.createAccount(account);
	}

	@Override
	public String updateAccount(Long id, String account) {
		LOGGER.info("In AccountServiceImpl updateAccount ");
		return repo.updateAccount(id, account);
	}

	@Override
	public String deleteAccount(Long id) {
		LOGGER.info("In AccountServiceImpl deleteAccount ");
		return repo.deleteAccount(id);

	}

	public void setRepo(AccountRepository repo) {
		this.repo = repo;
	}
}
