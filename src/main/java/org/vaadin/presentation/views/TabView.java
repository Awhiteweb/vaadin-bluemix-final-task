package org.vaadin.presentation.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.backend.session.UserSession;
import org.vaadin.presentation.components.DataGrid;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;

public class TabView extends MVerticalLayout
{
	private static final long serialVersionUID = 1L;
	
	@Inject
	UserSession userSession;
	private TabSheet tabSheet;
	
	@PostConstruct
	void init()
	{
		tabSheet = new TabSheet();
		add( tabSheet );
		tabSheet.addTab( createTabOne() ).setCaption( "Instagram Details" );
		tabSheet.addTab( createTabTwo() ).setCaption( "Analysis" );
		tabSheet.addTab( createTabThree() ).setCaption( "Your Map" );
	}

	private Component createTabOne()
	{
		return new DataGrid();
	}

	private Component createTabTwo()
	{
		return new AnalyzeView().getComponent( 0 );
	}
	
	private Component createTabThree()
	{
		return new MapView().getComponent( 0 );
	}
}
