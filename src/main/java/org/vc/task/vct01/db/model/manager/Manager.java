package org.vc.task.vct01.db.model.manager;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public class Manager {

	private Integer id;
	private String  name;
	private String  patronymic;
	private String  surname;
	private String  phoneNumber;
	private Integer deputyId;
	private Manager deputy;

	public Manager() {
	}

	public Manager(Integer id, String name, String patronymic, String surname, String phoneNumber, Integer deputyId) {
		this.id = id;
		this.name = name;
		this.patronymic = patronymic;
		this.surname = surname;
		this.phoneNumber = phoneNumber;
		this.deputyId = deputyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getDeputyId() {
		return deputyId;
	}

	public void setDeputyId(Integer deputyId) {
		this.deputyId = deputyId;
	}

	public Manager getDeputy() {
		return deputy;
	}

	public void setDeputy(Manager deputy) {
		this.deputy = deputy;
	}

	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Manager manager = (Manager)o;
		return Objects.equals(id, manager.id) &&
			   Objects.equals(name, manager.name) &&
			   Objects.equals(patronymic, manager.patronymic) &&
			   Objects.equals(surname, manager.surname) &&
			   Objects.equals(phoneNumber, manager.phoneNumber) &&
			   Objects.equals(deputyId, manager.deputyId);
	}

	public int hashCode() {
		return Objects.hash(id, name, patronymic, surname, phoneNumber, deputyId);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append(" {id=").append(id);
		sb.append(", name=\"").append(name).append("\"");
		sb.append(", patronymic=\"").append(patronymic).append("\"");
		sb.append(", surname=\"").append(surname).append("\"");
		sb.append(", phoneNumber=\"").append(phoneNumber).append("\"");
		sb.append(", deputyId=").append(deputyId);
		sb.append(", deputy=").append(deputy);
		sb.append("}");
		return sb.toString();
	}

}
