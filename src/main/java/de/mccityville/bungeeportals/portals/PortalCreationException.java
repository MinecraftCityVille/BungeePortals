package de.mccityville.bungeeportals.portals;

public class PortalCreationException extends RuntimeException {
	
	private static final long serialVersionUID = -3347414554498153401L;
	
	public PortalCreationException() {
		super();
	}
	
	public PortalCreationException(String msg) {
		super(msg);
	}
	
	public PortalCreationException(Throwable cause) {
		super(cause);
	}
	
	public PortalCreationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
