package org.vc.task.vct01.db.model.client;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ManagerClientRowMapper implements RowMapper<Client> {

	@Override
	public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Client(
			rs.getInt(ClientDao.ID),
			rs.getString(ClientDao.CODE),
			rs.getString(ClientDao.NAME),
			rs.getString(ClientDao.LEGAL_ADDRESS),
			rs.getInt(ClientDao.MANAGER_ID)
		);
	}

}


