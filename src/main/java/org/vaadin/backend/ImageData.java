package org.vaadin.backend;

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
	public void setThumbnail( String url, int width, int height )
	{
		this.thumbnail = new Image( url, width, height );
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
	public void setLowResolution( String url, int width, int height )
	{
		this.lowResolution = new Image( url, width, height );
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
	public void setStandardResolution( String url, int width, int height )
	{
		this.standardResolution = new Image( url, width, height );
	}
	
	/**
	 * @return double [lat, long]
	 */
	public double[] getLocation()
	{
		return location;
	}
	/**
	 * @param location double [lat, long]
	 */
	public void setLocation( double[] location )
	{
		this.location = location;
	}
	public String getId()
	{
		return id;
	}
	public void setId( String id )
	{
		this.id = id;
	}
	public String getLocationId()
	{
		return locationId;
	}
	public void setLocationId( String locationId )
	{
		this.locationId = locationId;
	}
	public String getLocationName()
	{
		return locationName;
	}
	public void setLocationName( String locationName )
	{
		this.locationName = locationName;
	}
	public String getCaptionId()
	{
		return captionId;
	}
	public void setCaptionId( String captionId )
	{
		this.captionId = captionId;
	}
	public String getCaptionText()
	{
		return captionText;
	}
	public void setCaptionText( String captionText )
	{
		this.captionText = captionText;
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
