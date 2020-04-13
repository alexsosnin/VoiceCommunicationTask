package org.vc.task.vct01.db.model.manager;

import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vc.task.vct01.db.model.client.Client;
import org.vc.task.vct01.db.model.client.ClientDao;
import org.vc.task.vct01.db.model.client.ManagerClientRowMapper;
import org.vc.task.vct01.util.ResourceUtil;

@Repository
@Transactional (rollbackFor = Exception.class)
public class ManagerDaoImpl implements ManagerDao {

	private static final Class<?> CLAZZ        = MethodHandles.lookup().lookupClass();
	private static final String   CHARSET_NAME = StandardCharsets.UTF_8.name();

	private static final String GET_MANAGER_LIST        = ResourceUtil.readResource(CLAZZ, "GET_MANAGER_LIST.sql", CHARSET_NAME);
	private static final String GET_MANAGER_BY_ID       = ResourceUtil.readResource(CLAZZ, "GET_MANAGER_BY_ID.sql", CHARSET_NAME);
	private static final String GET_MANAGER_CLIENT_LIST = ResourceUtil.readResource(CLAZZ, "GET_MANAGER_CLIENT_LIST.sql", CHARSET_NAME);
	private static final String INSERT_MANAGER          = ResourceUtil.readResource(CLAZZ, "INSERT_MANAGER.sql", CHARSET_NAME);
	private static final String UPDATE_MANAGER          = ResourceUtil.readResource(CLAZZ, "UPDATE_MANAGER.sql", CHARSET_NAME);
	private static final String DELETE_MANAGER          = ResourceUtil.readResource(CLAZZ, "DELETE_MANAGER.sql", CHARSET_NAME);
	private static final String UPDATE_CLIENTS_MANAGER  = ResourceUtil.readResource(CLAZZ, "UPDATE_CLIENTS_MANAGER.sql", CHARSET_NAME);

	private final NamedParameterJdbcOperations npJdbcTemplate;
	private final ManagerRowMapper             managerRowMapper;
	private final ManagerClientRowMapper       managerClientRowMapper;

	@Autowired
	public ManagerDaoImpl(NamedParameterJdbcOperations npJdbcTemplate, ManagerRowMapper managerRowMapper, ManagerClientRowMapper managerClientRowMapper) {
		this.npJdbcTemplate = npJdbcTemplate;
		this.managerRowMapper = managerRowMapper;
		this.managerClientRowMapper = managerClientRowMapper;
	}

	@Override
	public List<Manager> getManagerList() {
		return npJdbcTemplate.query(GET_MANAGER_LIST, managerRowMapper);
	}

	@Override
	public Manager getManagerById(long id) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue(ID, id);
		List<Manager> list = npJdbcTemplate.query(GET_MANAGER_BY_ID, params, managerRowMapper);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<Client> getManagerClientsList(long id) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue(ClientDao.MANAGER_ID, id);
		return npJdbcTemplate.query(GET_MANAGER_CLIENT_LIST, params, managerClientRowMapper);
	}

	@Override
	public Manager insertManager(String name, String patronymic, String surname, String phoneNumber, Integer deputyId) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue(NAME, name)
			.addValue(PATRONYMIC, patronymic)
			.addValue(SURNAME, surname)
			.addValue(PHONE_NUMBER, phoneNumber)
			.addValue(DEPUTY_ID, deputyId);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		npJdbcTemplate.update(INSERT_MANAGER, sqlParameterSource, keyHolder, new String[] {ID});
		long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
		return getManagerById(id);
	}

	@Override
	public Manager updateManager(Integer id, String name, String patronymic, String surname, String phoneNumber, Integer deputyId) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue(ID, id)
			.addValue(NAME, name)
			.addValue(PATRONYMIC, patronymic)
			.addValue(SURNAME, surname)
			.addValue(PHONE_NUMBER, phoneNumber)
			.addValue(DEPUTY_ID, deputyId);
		npJdbcTemplate.update(UPDATE_MANAGER, sqlParameterSource);
		return getManagerById(id);
	}

	@Override
	public boolean deleteManager(Integer id) {
		boolean res = false;
		boolean deletionIsAllowed = false;

		List<Client> managerClientsList = getManagerClientsList(id);

		if ( managerClientsList.isEmpty() ) {
			deletionIsAllowed = true;
		}
		else {
			Integer deputyId = getManagerById(id).getDeputyId();
			if ( deputyId != null ) {
				if (
					npJdbcTemplate.update(UPDATE_CLIENTS_MANAGER,
										  new MapSqlParameterSource()
											  .addValue(ClientDao.MANAGER_ID, id)
											  .addValue(ClientDao.NEW_MANAGER_ID, deputyId)
					) == managerClientsList.size()
				) {
					deletionIsAllowed = true;
				}
				else {
					throw new RuntimeException("Rollback because of incorrect clients update operation result");
				}
			}
		}

		if ( deletionIsAllowed ) {
			if (
				npJdbcTemplate.update(DELETE_MANAGER,
									  new MapSqlParameterSource()
										  .addValue(ID, id)
										  .addValue(REMOVAL_DATE, new Timestamp((new Date()).getTime()))
				) == 1
			) {
				res = true;
			}
			else {
				throw new RuntimeException("Rollback because of incorrect manager delete operation result");
			}
		}

		return res;
	}

}

