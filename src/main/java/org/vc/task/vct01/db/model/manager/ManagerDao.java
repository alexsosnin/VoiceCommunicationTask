package org.vc.task.vct01.db.model.manager;

import java.util.List;

import org.vc.task.vct01.db.model.client.Client;

public interface ManagerDao {

	String ID           = "id";
	String NAME         = "name";
	String PATRONYMIC   = "patronymic";
	String SURNAME      = "surname";
	String PHONE_NUMBER = "phone_number";
	String REMOVAL_DATE = "removal_date";

	String DEPUTY_ID           = "deputy_id";
	String DEPUTY_NAME         = "deputy_name";
	String DEPUTY_PATRONYMIC   = "deputy_patronymic";
	String DEPUTY_SURNAME      = "deputy_surname";
	String DEPUTY_PHONE_NUMBER = "deputy_phone_number";

	List<Manager> getManagerList();
	Manager getManagerById(long id);
	List<Client> getManagerClientsList(long id);
	Manager insertManager(String name, String patronymic, String surname, String phoneNumber, Integer deputyId);
	Manager updateManager(Integer id, String name, String patronymic, String surname, String phoneNumber, Integer deputyId);
	boolean deleteManager(Integer id);

}
