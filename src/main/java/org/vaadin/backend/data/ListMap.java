package org.vaadin.backend.data;

import java.util.LinkedList;
import java.util.List;

public class ListMap
{
	private List<Feedback> feedback;
	private String parent;
	private int quantity;
	public ListMap( Feedback feedback )
	{
		this.feedback = new LinkedList<Feedback>();
		this.parent = feedback.getParent();
		this.feedback.add( feedback );
		this.quantity = 1;
	}
	public void addFeedback( Feedback feedback )
	{
		this.feedback.add( feedback );
		quantity++;
	}
	public String getParent()
	{
		return parent;
	}
	public int getQuantity()
	{
		return quantity;
	}
	public List<Feedback> getFeedback()
	{
		return feedback;
	}
}