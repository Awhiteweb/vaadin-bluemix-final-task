package org.vaadin.backend.data;


public class ISO
{
	private String id;
	private Number value;
	
	public ISO(){}
	public ISO( String id, Number value )
	{
		this.id = id;
		this.value = value;
	}
	
	public String getId()
	{
		return id;
	}
	public Number getValue()
	{
		return value;
	}
	public void setValue( Number value )
	{
		this.value = value;
	}
	
}
