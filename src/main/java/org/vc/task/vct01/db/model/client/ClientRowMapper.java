package org.vc.task.vct01.db.model.client;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.vc.task.vct01.db.model.manager.Manager;

@Component
public class ClientRowMapper implements RowMapper<Client> {

	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {

		Client client = new Client(
			rs.getInt(ClientDao.ID),
			rs.getString(ClientDao.CODE),
			rs.getString(ClientDao.NAME),
			rs.getString(ClientDao.LEGAL_ADDRESS),
			rs.getInt(ClientDao.MANAGER_ID)
		);

		if ( client.getManagerId() != null ) {

			Object deputyIdObject = rs.getObject(ClientDao.DEPUTY_MANAGER_ID);

			Manager manager = new Manager(
				client.getManagerId(),
				rs.getString(ClientDao.MANAGER_NAME),
				rs.getString(ClientDao.MANAGER_PATRONYMIC),
				rs.getString(ClientDao.MANAGER_SURNAME),
				rs.getString(ClientDao.MANAGER_PHONE_NUMBER),
				deputyIdObject == null ? null : rs.getInt(ClientDao.DEPUTY_MANAGER_ID)
			);

			if ( manager.getDeputyId() != null ) {
				Manager deputy = new Manager(
					manager.getDeputyId(),
					rs.getString(ClientDao.DEPUTY_MANAGER_NAME),
					rs.getString(ClientDao.DEPUTY_MANAGER_PATRONYMIC),
					rs.getString(ClientDao.DEPUTY_MANAGER_SURNAME),
					rs.getString(ClientDao.DEPUTY_MANAGER_PHONE_NUMBER),
					null
				);
				manager.setDeputy(deputy);
			}

			client.setManager(manager);
		}

		return client;
	}

}


