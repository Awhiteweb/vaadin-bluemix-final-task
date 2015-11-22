package org.vaadin.presentation.views;

import javax.annotation.PostConstruct;

import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.presentation.ScreenSize;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

/**
 * A view that lists Customers in a Table and lets user to choose one for
 * editing. There is also RIA features like on the fly filtering.
 */
@CDIView( "instagram" )
@ViewMenuItem( icon = FontAwesome.USERS, order = ViewMenuItem.BEGINNING )
public class InstagramView extends MVerticalLayout implements View
{

	private static final long serialVersionUID = 1L;

	Label output = new Label();
	Button connect = new Button();

	Header header = new Header( "Picture Resonse" ).setHeaderLevel( 2 );

	Button addButton = new MButton( FontAwesome.INSTAGRAM, new Button.ClickListener() 
	{
		private static final long serialVersionUID = 1L;

		@Override
		public void buttonClick( Button.ClickEvent event )
		{
		}
	} );

	@PostConstruct
	public void init()
	{
		addButton.setDescription( "This will connect you to instagram" );

		/*
		 * "Responsive Web Design" can be done with plain Java as well. Here we
		 * e.g. do selective layouting and configure visible columns in table
		 * based on available width
		 */
		layout();
		
		/*
		 * If you wish the UI to adapt on window resize/page orientation change,
		 * hook to BrowserWindowResizeEvent
		 */
		UI.getCurrent().setResizeLazy( true );
		Page.getCurrent().addBrowserWindowResizeListener( new Page.BrowserWindowResizeListener() 
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void browserWindowResized( Page.BrowserWindowResizeEvent browserWindowResizeEvent )
			{
				layout();
			}
		} );
	}

	/**
	 * Do the application layout that is optimized for the screen size.
	 * <p>
	 * Like in Java world in general, Vaadin developers can modularize their
	 * helpers easily and re-use existing code. E.g. In this method we are using
	 * extended versions of Vaadins basic layout that has "fluent API" and this
	 * way we get bit more readable code. Check out vaadin.com/directory for a
	 * huge amount of helper libraries and custom components. They might be
	 * valuable for your productivity.
	 * </p>
	 */
	private void layout()
	{
		removeAllComponents();
		if ( ScreenSize.getScreenSize() == ScreenSize.LARGE )
		{
			addComponents( );
		}
		else
		{
			addComponents( );
		}
		setMargin( true );
		expand( );
	}

	@Override
	public void enter( ViewChangeListener.ViewChangeEvent event )
	{

	}

}
