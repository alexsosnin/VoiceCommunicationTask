package org.vc.task.vct01.db.model.client;

import java.util.List;

public interface ClientDao {

	String ID            = "id";
	String CODE          = "code";
	String NAME          = "name";
	String LEGAL_ADDRESS = "legal_address";

	String REMOVAL_DATE = "removal_date";

	String MANAGER_ID           = "manager_id";
	String MANAGER_NAME         = "manager_name";
	String MANAGER_PATRONYMIC   = "manager_patronymic";
	String MANAGER_SURNAME      = "manager_surname";
	String MANAGER_PHONE_NUMBER = "manager_phone_number";

	String DEPUTY_MANAGER_ID           = "deputy_manager_id";
	String DEPUTY_MANAGER_NAME         = "deputy_manager_name";
	String DEPUTY_MANAGER_PATRONYMIC   = "deputy_manager_patronymic";
	String DEPUTY_MANAGER_SURNAME      = "deputy_manager_surname";
	String DEPUTY_MANAGER_PHONE_NUMBER = "deputy_manager_phone_number";

	String NEW_MANAGER_ID = "new_manager_id";

	List<Client> getClientList();
	Client getClientById(long id);
	Client insertClient(String name, String legalAddress, Integer managerId);
	Client updateClient(Integer id, String name, String legalAddress, Integer managerId);
	boolean deleteClient(Integer id);

}
