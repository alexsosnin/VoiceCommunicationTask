package org.vc.task.vct01.db.model.client;

import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vc.task.vct01.util.ResourceUtil;

@Repository
@Transactional (rollbackFor = Exception.class)
public class ClientDaoImpl implements ClientDao {

	private static final Class<?> CLAZZ        = MethodHandles.lookup().lookupClass();
	private static final String   CHARSET_NAME = StandardCharsets.UTF_8.name();

	private static final String GET_CLIENT_LIST  = ResourceUtil.readResource(CLAZZ, "GET_CLIENT_LIST.sql", CHARSET_NAME);
	private static final String GET_CLIENT_BY_ID = ResourceUtil.readResource(CLAZZ, "GET_CLIENT_BY_ID.sql", CHARSET_NAME);
	private static final String INSERT_CLIENT    = ResourceUtil.readResource(CLAZZ, "INSERT_CLIENT.sql", CHARSET_NAME);
	private static final String UPDATE_CLIENT    = ResourceUtil.readResource(CLAZZ, "UPDATE_CLIENT.sql", CHARSET_NAME);
	private static final String DELETE_CLIENT    = ResourceUtil.readResource(CLAZZ, "DELETE_CLIENT.sql", CHARSET_NAME);

	private final NamedParameterJdbcOperations npJdbcTemplate;
	private final ClientRowMapper              rowMapper;

	@Autowired
	public ClientDaoImpl(NamedParameterJdbcOperations npJdbcTemplate, ClientRowMapper rowMapper) {
		this.npJdbcTemplate = npJdbcTemplate;
		this.rowMapper = rowMapper;
	}

	@Override
	public List<Client> getClientList() {
		return npJdbcTemplate.query(GET_CLIENT_LIST, rowMapper);
	}

	@Override
	public Client getClientById(long id) {
		SqlParameterSource params = new MapSqlParameterSource()
			.addValue(ID, id);
		List<Client> list = npJdbcTemplate.query(GET_CLIENT_BY_ID, params, rowMapper);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Client insertClient(String name, String legalAddress, Integer managerId) {
		SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue(CODE, UUID.randomUUID())
			.addValue(NAME, name)
			.addValue(LEGAL_ADDRESS, legalAddress)
			.addValue(MANAGER_ID, managerId);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		npJdbcTemplate.update(INSERT_CLIENT, sqlParameterSource, keyHolder, new String[] {ID});
		long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
		return getClientById(id);
	}

	@Override
	public Client updateClient(Integer id, String name, String legalAddress, Integer managerId) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue(ID, id)
			.addValue(NAME, name)
			.addValue(LEGAL_ADDRESS, legalAddress)
			.addValue(MANAGER_ID, managerId);
		npJdbcTemplate.update(UPDATE_CLIENT, sqlParameterSource);
		return getClientById(id);
	}

	@Override
	public boolean deleteClient(Integer id) {
		MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource()
			.addValue(ID, id)
			.addValue(REMOVAL_DATE, new Timestamp((new Date()).getTime()));
		return npJdbcTemplate.update(DELETE_CLIENT, sqlParameterSource) == 1;
	}

}
