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
import org.vc.task.vct01.db.model.manager.Manager;
import org.vc.task.vct01.db.model.manager.ManagerDao;

@RestController
@RequestMapping (RestConst.API + RestConst.MANAGERS)
public class ManagerRestController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final ManagerDao managerDao;

	@Autowired
	public ManagerRestController(ManagerDao managerDao) {
		this.managerDao = managerDao;
	}

	@GetMapping ()
	public List<Manager> getManagerList() {
		logger.info("getManagerList()");
		return managerDao.getManagerList();
	}

	@GetMapping (RestConst.ID)
	public Manager getManagerById(@PathVariable long id) {
		logger.info("getManagerById(" + id + ")");
		return managerDao.getManagerById(id);
	}

	@GetMapping (RestConst.ID + RestConst.CLIENTS)
	public List<Client> getManagerClientsList(@PathVariable long id) {
		logger.info("getManagerClientsList(" + id + ")");
		return managerDao.getManagerClientsList(id);
	}

	@PostMapping (RestConst.CREATE)
	public Manager insertManager(@RequestBody Manager manager) {
		logger.info("insertManager(" + manager + ")");
		return managerDao.insertManager(manager.getName(), manager.getPatronymic(), manager.getSurname(), manager.getPhoneNumber(), manager.getDeputyId());
	}

	@PutMapping (RestConst.UPDATE_ID)
	public Manager updateManager(@PathVariable int id, @RequestBody Manager manager) {
		logger.info("updateManager(" + id + ", " + manager + ")");
		return managerDao.updateManager(id, manager.getName(), manager.getPatronymic(), manager.getSurname(), manager.getPhoneNumber(), manager.getDeputyId());
	}

	@PutMapping (RestConst.DELETE_ID)
	public boolean deleteManager(@PathVariable int id) {
		logger.info("deleteManager(" + id + ")");
		return managerDao.deleteManager(id);
	}

}
