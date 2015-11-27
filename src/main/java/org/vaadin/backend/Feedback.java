package org.vaadin.backend;

public class Feedback
{
	private String parent;
	private String child;
	private String[] name;
	private Double value;
	public Feedback( String name, Double score )
	{
		this.name = name.split( "/" );
		this.value = score.doubleValue();
		setFamily();
	}
	private void setFamily()
	{
		this.parent = name[0];
		this.child = name.length > 1 ? name[1] : name[0];
	}
	
	public double getValue()	
	{
		return value;
	}
	public String getParent()
	{
		return parent;
	}
	public String getChild()
	{
		return child;
	}
	public String getName()
	{
		return name.length > 1 ? name[0] + "/" + name[1] : name[0];
	}
	@Override
	public String toString()
	{
		return name.length > 1 ? name[1] : name[0];
	}
}
