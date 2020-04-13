package org.vc.task.vct01.db.model.client;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.vc.task.vct01.db.model.manager.Manager;

@JsonIgnoreProperties (ignoreUnknown = true)
public class Client {

	private Integer id;
	private String  code;
	private String  name;
	private String  legalAddress;
	private Integer managerId;
	private Manager manager;

	public Client() {
	}

	public Client(Integer id, String code, String name, String legalAddress, Integer managerId) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.legalAddress = legalAddress;
		this.managerId = managerId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(String legalAddress) {
		this.legalAddress = legalAddress;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Client client = (Client)o;
		return Objects.equals(id, client.id) &&
			   Objects.equals(code, client.code) &&
			   Objects.equals(name, client.name) &&
			   Objects.equals(legalAddress, client.legalAddress) &&
			   Objects.equals(managerId, client.managerId);
	}

	public int hashCode() {
		return Objects.hash(id, code, name, legalAddress, managerId);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append(" {id=").append(id);
		sb.append(", code=\"").append(code).append("\"");
		sb.append(", name=\"").append(name).append("\"");
		sb.append(", legalAddress=\"").append(legalAddress).append("\"");
		sb.append(", managerId=").append(managerId);
		sb.append(", manager=").append(manager);
		sb.append("}");
		return sb.toString();
	}

}
