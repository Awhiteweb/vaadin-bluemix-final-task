package org.vaadin.backend;

import java.util.List;

public class ImageData
{
	private Image thumbnail;
	private Image lowResolution;
	private Image standardResolution;
	private double[] location;
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
	public double[] getLocation()
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
	public List<Feedback> getTopFiveFeedback()
	{
		return insight.getFeedback( 5 );
	}
	public List<Feedback> getTopTenFeedback()
	{
		return insight.getFeedback( 10 );
	}
	
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
		this.location = location;
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
