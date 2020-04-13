package org.vc.task.vct01.rs;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vc.task.vct01.db.model.client.Client;
import org.vc.task.vct01.db.model.client.ClientDao;

@RestController
@RequestMapping (RestConst.API + RestConst.CLIENTS)
public class ClientRestController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final ClientDao clientDao;

	@Autowired
	public ClientRestController(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	@GetMapping ()
	public List<Client> getClientList() {
		logger.info("getClientList()");
		return clientDao.getClientList();
	}

	@GetMapping (RestConst.ID)
	public Client getClientById(@PathVariable long id) {
		logger.info("getClientById(" + id + ")");
		return clientDao.getClientById(id);
	}

	@PostMapping (RestConst.CREATE)
	public Client insertClient(@RequestBody Client client) {
		logger.info("insertClient(" + client + ")");
		return clientDao.insertClient(client.getName(), client.getLegalAddress(), client.getManagerId());
	}

	@PutMapping (RestConst.UPDATE_ID)
	public Client updateClient(@PathVariable int id, @RequestBody Client client) {
		logger.info("updateClient(" + id + ", " + client + ")");
		return clientDao.updateClient(id, client.getName(), client.getLegalAddress(), client.getManagerId());
	}

	@PutMapping (RestConst.DELETE_ID)
	public boolean deleteClient(@PathVariable int id) {
		logger.info("deleteClient(" + id + ")");
		return clientDao.deleteClient(id);
	}

}
