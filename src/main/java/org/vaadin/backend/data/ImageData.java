package org.vaadin.backend.data;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.vaadin.addon.leaflet.shared.Point;

public class ImageData
{
	private Image thumbnail;
	private Image lowResolution;
	private Image standardResolution;
	private Point location;
	private String id;
	private String locationId;
	private String locationName;
	private String captionId;
	private String captionText;
	private Insight insight;
	
	public String getThumbnailUrl()
	{
		return thumbnail.getUrl();
	}
	/**
	 * @return int[ width, height ]
	 */
	public int[] getThumbnailSize()
	{
		return thumbnail.getSize();
	}
	public String getLowResolutionUrl()
	{
		return lowResolution.getUrl();
	}
	/**
	 * @return int[ width, height ]
	 */
	public int[] getLowResolutionSize()
	{
		return lowResolution.getSize();
	}
	public String getStandardResolution()
	{
		return standardResolution.getUrl();
	}
	/**
	 * @return int[ width, height ]
	 */
	public int[] getStandardResolutionSize()
	{
		return standardResolution.getSize();
	}
	
	/**
	 * @return double [lat, long]
	 */
	public Point getLocation()
	{
		return location;
	}
	public String getId()
	{
		return id;
	}
	public String getLocationId()
	{
		return locationId;
	}
	public String getLocationName()
	{
		return locationName;
	}
	public String getCaptionId()
	{
		return captionId;
	}
	public String getCaptionText()
	{
		return captionText;
	}
	/**
	 * returns a list of the top 5 rated feedback items
	 * if less than 5 exist then the total available
	 * @return
	 */	public List<Feedback> getTopFiveFeedback()
	{
		return insight.getFeedback( 5 );
	}
	/**
	 * returns a list of the top 10 rated feedback items
	 * if less than 10 exist then the total available
	 * @return
	 */	public List<Feedback> getTopTenFeedback()
	{
		return insight.getFeedback( 10 );
	}
	/**
	 * returns full list of feedback that has a value greater than 0
	 * @return
	 */
	public List<Feedback> getAllFeedback()
	{
		return insight.getAllFeedback();
	}
	public Set<String> getParents()
	{
		return insight.getParents();
	}
	public int countParents()
	{
		return getParents().size();
	}
	/**
	 * returns the average % value for a parent
	 * String = parent
	 * Number = double as a percentage
	 * @return HashMap<String, Number>
	 */
	public HashMap<String, Number> getParentPercent()
	{
		return insight.getParentPercentage();
	}
	
	public HashMap<String, Number> getChildren( String parent )
	{
		return insight.getChildren( parent );
	}
	
	
	
	
	// setters
	
	public void setThumbnail( String url, int width, int height )
	{
		this.thumbnail = new Image( url, width, height );
	}
	public void setLowResolution( String url, int width, int height )
	{
		this.lowResolution = new Image( url, width, height );
	}
	public void setStandardResolution( String url, int width, int height )
	{
		this.standardResolution = new Image( url, width, height );
	}
	/**
	 * @param location double [lat, long]
	 */
	public void setLocation( double[] location )
	{
		this.location = new Point( location[0], location[1] );
	}
	public void setId( String id )
	{
		this.id = id;
	}
	public void setLocationId( String locationId )
	{
		this.locationId = locationId;
	}
	public void setLocationName( String locationName )
	{
		this.locationName = locationName;
	}
	public void setCaptionId( String captionId )
	{
		this.captionId = captionId;
	}
	public void setCaptionText( String captionText )
	{
		this.captionText = captionText;
	}
	public void setInsights( Insight insight )
	{
		this.insight = insight;
	}

	class Image
	{
		private String url;
		private int[] size;
		
		Image( String url, int width, int height )
		{
			this.url = url;
			this.size = new int[]{ width, height };
		}
		
		public String getUrl()
		{
			return url;
		}
		
		public int[] getSize()
		{
			return size;
		}
	}
	
}
