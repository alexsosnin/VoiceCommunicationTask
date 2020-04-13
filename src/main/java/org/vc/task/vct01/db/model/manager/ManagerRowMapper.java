package org.vc.task.vct01.db.model.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ManagerRowMapper implements RowMapper<Manager> {

	@Override
	public Manager mapRow(ResultSet rs, int rowNum) throws SQLException {

		Object deputyIdObject = rs.getObject(ManagerDao.DEPUTY_ID);

		Manager manager = new Manager(
			rs.getInt(ManagerDao.ID),
			rs.getString(ManagerDao.NAME),
			rs.getString(ManagerDao.PATRONYMIC),
			rs.getString(ManagerDao.SURNAME),
			rs.getString(ManagerDao.PHONE_NUMBER),
			deputyIdObject == null ? null : rs.getInt(ManagerDao.DEPUTY_ID)
		);

		if ( manager.getDeputyId() != null ) {
			Manager deputy = new Manager(
				manager.getDeputyId(),
				rs.getString(ManagerDao.DEPUTY_NAME),
				rs.getString(ManagerDao.DEPUTY_PATRONYMIC),
				rs.getString(ManagerDao.DEPUTY_SURNAME),
				rs.getString(ManagerDao.DEPUTY_PHONE_NUMBER),
				null
			);
			manager.setDeputy(deputy);
		}

		return manager;

	}

}
