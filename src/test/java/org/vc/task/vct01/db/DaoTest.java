package org.vc.task.vct01.db;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.vc.task.vct01.db.config.DbConfig;
import org.vc.task.vct01.db.model.client.Client;
import org.vc.task.vct01.db.model.client.ClientDao;
import org.vc.task.vct01.db.model.manager.Manager;
import org.vc.task.vct01.db.model.manager.ManagerDao;

import static org.junit.Assert.*;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (classes = DbConfig.class)
@Transactional (transactionManager = "jdbcTransactionManager")
@Rollback ()
//@Commit ()
public class DaoTest {

	private static final String MANAGER_NAME_01         = "Пётр";
	private static final String MANAGER_PATRONYMIC_01   = "Пётрович";
	private static final String MANAGER_SURNAME_01      = "Пётров";
	private static final String MANAGER_PHONE_NUMBER_01 = "222-222";

	private static final String MANAGER_NAME_02         = "Alan";
	private static final String MANAGER_PATRONYMIC_02   = "Bob";
	private static final String MANAGER_SURNAME_02      = "Mask";
	private static final String MANAGER_PHONE_NUMBER_02 = "777-777";

	private static final String CLIENT_NAME_01          = "Первый клиент";
	private static final String CLIENT_LEGAL_ADDRESS_01 = "Москва, ...";

	private static final String CLIENT_NAME_02          = "First client";
	private static final String CLIENT_LEGAL_ADDRESS_02 = "London, ...";

	@Autowired
	private ManagerDao managerDao;

	@Autowired
	private ClientDao clientDao;

	@Test
	public void daoTest() {
		try {
			List<Manager> initManagerList;

			initManagerList = managerDao.getManagerList();

			Manager m1 = managerDao.insertManager("Иван", "Иванович", "Иванов", "111-111", null);
			Manager m2 = managerDao.insertManager(MANAGER_NAME_01, MANAGER_PATRONYMIC_01, MANAGER_SURNAME_01, MANAGER_PHONE_NUMBER_01, m1.getId());

			Manager m3 = managerDao.insertManager("Александр", "Александрович", "Александров", "333-333", null);
			Manager m4 = managerDao.insertManager("Роман", "Романович", "Романов", "444-444", null);
			m3 = managerDao.updateManager(m3.getId(), m3.getName(), m3.getPatronymic(), m3.getSurname(), m3.getPhoneNumber(), m4.getId());

			Manager m5 = managerDao.insertManager("Азат", "Азатович", "Азатов", "555-555", null);
			Manager m6 = managerDao.insertManager("Асхат", "Асхатович", "Асхатов", "666-666", null);

			List<Manager> newManagerList = Arrays.asList(m1, m2, m3, m4, m5, m6);
			List<Manager> managerList = managerDao.getManagerList();
			assertTrue(initManagerList.isEmpty() || managerList.removeAll(initManagerList));
			assertEquals(managerList.size(), newManagerList.size());
			assertTrue(managerList.containsAll(newManagerList));

			assertEquals(new Manager(m2.getId(), MANAGER_NAME_01, MANAGER_PATRONYMIC_01, MANAGER_SURNAME_01, MANAGER_PHONE_NUMBER_01, m2.getDeputyId()),
						 managerDao.getManagerById(m2.getId()));

			m2 = managerDao.updateManager(m2.getId(), MANAGER_NAME_02, MANAGER_PATRONYMIC_02, MANAGER_SURNAME_02, MANAGER_PHONE_NUMBER_02, m2.getDeputyId());
			assertEquals(m2, new Manager(m2.getId(), MANAGER_NAME_02, MANAGER_PATRONYMIC_02, MANAGER_SURNAME_02, MANAGER_PHONE_NUMBER_02, m2.getDeputyId()));


			List<Client> initClientList = clientDao.getClientList();

			Client c1 = clientDao.insertClient(CLIENT_NAME_01, CLIENT_LEGAL_ADDRESS_01, m2.getId());
			Client c2 = clientDao.insertClient("Второй клиент", "Питер, ...", m5.getId());
			Client c3 = clientDao.insertClient("Третий клиент", "Москва, ...", m2.getId());
			Client c4 = clientDao.insertClient("Четвёртый клиент", "Питер, ...", m2.getId());
			Client c5 = clientDao.insertClient("Пятый клиент", "Москва, ...", m5.getId());
			Client c6 = clientDao.insertClient("Шестой клиент", "Питер, ...", m5.getId());
			Client c7 = clientDao.insertClient("Седьмой клиент", "Питер, ...", m5.getId());

			List<Client> newClientList = Arrays.asList(c1, c2, c3, c4, c5, c6, c7);
			List<Client> clientList = clientDao.getClientList();
			assertTrue(initClientList.isEmpty() || clientList.removeAll(initClientList));
			assertEquals(clientList.size(), newClientList.size());
			assertTrue(clientList.containsAll(newClientList));

			assertEquals(new Client(c1.getId(), c1.getCode(), CLIENT_NAME_01, CLIENT_LEGAL_ADDRESS_01, c1.getManagerId()),
						 clientDao.getClientById(c1.getId()));

			c1 = clientDao.updateClient(c1.getId(), CLIENT_NAME_02, CLIENT_LEGAL_ADDRESS_02, c1.getManagerId());
			assertEquals(c1, new Client(c1.getId(), c1.getCode(), CLIENT_NAME_02, CLIENT_LEGAL_ADDRESS_02, c1.getManagerId()));


			List<Client> m2AssignedClientsList = Arrays.asList(c1, c3, c4);
			List<Client> m2ClientsList = managerDao.getManagerClientsList(m2.getId());
			assertEquals(m2AssignedClientsList.size(), m2ClientsList.size());
			assertTrue(m2AssignedClientsList.containsAll(m2ClientsList));

			List<Client> m5AssignedClientsList = Arrays.asList(c2, c5, c6, c7);
			List<Client> m5ClientsList = managerDao.getManagerClientsList(m5.getId());
			assertEquals(m5AssignedClientsList.size(), m5ClientsList.size());
			assertTrue(m5AssignedClientsList.containsAll(m5ClientsList));


			assertTrue(clientDao.getClientList().contains(c4));
			assertTrue(clientDao.deleteClient(c4.getId()));
			assertFalse(clientDao.getClientList().contains(c4));
			assertNull(clientDao.getClientById(c4.getId()));


			assertTrue(managerDao.getManagerList().containsAll(Arrays.asList(m2, m5, m6)));

			assertTrue(managerDao.deleteManager(m6.getId()));
			//assertTrue(managerDao.deleteManager(m6.getId())); //re-delete produces exception: "Rollback because of incorrect manager delete operation result"
			assertFalse(managerDao.getManagerList().contains(m6));

			assertFalse(managerDao.deleteManager(m5.getId()));
			assertTrue(managerDao.getManagerList().contains(m5));


			assertEquals(m2.getId(), clientDao.getClientById(c1.getId()).getManagerId());
			assertEquals(m2.getId(), clientDao.getClientById(c3.getId()).getManagerId());

			assertTrue(managerDao.deleteManager(m2.getId()));
			assertFalse(managerDao.getManagerList().contains(m2));

			assertEquals(m2.getDeputy().getId(), clientDao.getClientById(c1.getId()).getManagerId());
			assertEquals(m2.getDeputy().getId(), clientDao.getClientById(c3.getId()).getManagerId());
		}
		catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}

	}

}
