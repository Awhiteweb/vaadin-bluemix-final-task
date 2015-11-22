package org.vaadin.backend.domain;

public class PictureResponse
{
	private Meta meta;
	private Data data;
	private Pagination pagination;

	public Meta getMeta()
	{
		return meta;
	}

	public void setMeta( Meta meta )
	{
		this.meta = meta;
	}

	public Data getData()
	{
		return data;
	}

	public void setData( Data data )
	{
		this.data = data;
	}

	public Pagination getPagination()
	{
		return pagination;
	}

	public void setPagination( Pagination pagination )
	{
		this.pagination = pagination;
	}
	
	class Meta
	{
		private String error_type;
		private String code;
		private String error_message;

		public String getError_type()
		{
			return error_type;
		}
		public void setError_type( String error_type )
		{
			this.error_type = error_type;
		}
		public String getCode()
		{
			return code;
		}
		public void setCode( String code )
		{
			this.code = code;
		}
		public String getError_message()
		{
			return error_message;
		}
		public void setError_message( String error_message )
		{
			this.error_message = error_message;
		}
	}
	
	class Data
	{
		
	}
	
	class Pagination
	{
		private String next_url;
		private String next_max_id;
		
		public String getNext_max_id()
		{
			return next_max_id;
		}
		public void setNext_max_id( String next_max_id )
		{
			this.next_max_id = next_max_id;
		}
		public String getNext_url()
		{
			return next_url;
		}
		public void setNext_url( String next_url )
		{
			this.next_url = next_url;
		}		
	}
}
