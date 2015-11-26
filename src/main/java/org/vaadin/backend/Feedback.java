package org.vaadin.backend;

class Feedback
{
	private String[] name;
	private Double value;
	public Feedback( String name, Double score )
	{
		this.name = name.split( "/" );
		this.value = score.doubleValue();
	}
	public double getValue()	
	{
		return value;
	}
	public String getParent()
	{
		return name[0];
	}
	public String getChild()
	{
		return name.length > 1 ? name[0] : name[1];
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
