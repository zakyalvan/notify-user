package com.innovez.core.notif.commons;

@SuppressWarnings("serial")
public class SimpleRecipientDetails implements RecipientDetails {
	private String name;
	private String address;
		
	public SimpleRecipientDetails(String name, String address) {
		this.name = name;
		this.address = address;
	}

	@Override
	public String getName() {		
		return name;
	}
	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleRecipientDetails other = (SimpleRecipientDetails) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimpleRecipientDetails [name=" + name + ", address=" + address
				+ "]";
	}
}
