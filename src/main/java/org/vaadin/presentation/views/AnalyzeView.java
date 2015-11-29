package org.vaadin.presentation.views;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.backend.data.ImageData;
import org.vaadin.backend.session.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.presentation.components.DataChart;
import org.vaadin.presentation.components.DataGrid;
import org.vaadin.presentation.components.ImageGrid;
import org.vaadin.presentation.components.RoseChart;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MMarginInfo;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.TabSheet;

/**
 * An example view that just make some simple analysis for the data and displays
 * it in various charts.
 */
@CDIView( "analyze" )
@ViewMenuItem( icon = FontAwesome.BAR_CHART_O, order = 2 )
public class AnalyzeView extends MVerticalLayout implements View
{
	private static final long serialVersionUID = 1L;
	@Inject
	UserSession userSession;
	private List<ImageData> images;

	@PostConstruct
	void init()
	{
		loadData();
	}

	private void loadData()
	{
		images = userSession.getImages();
		removeAllComponents();
		setMargin( new MMarginInfo( false, true ) );
		setSizeFull();
		addComponent( createSheet() );
	}
	
	private TabSheet createSheet()
	{
		TabSheet sheet = new TabSheet();
		sheet.setSizeFull();
		sheet.addTab( tabOne(), "Images" );
		sheet.addTab( tabTwo(), "Data table" );
		sheet.addTab( tabThree(), "Data charts" );
		return sheet;
	}

	private MVerticalLayout tabOne()
	{
		MVerticalLayout layout = new MVerticalLayout(); 
		layout.setMargin( new MMarginInfo( true, false ) );
		layout.add( new Header( "Viewing the data" ).setHeaderLevel( 2 ) );
		layout.add( new ImageGrid( images ) );
		return layout;
	}
	
	private MVerticalLayout tabTwo()
	{
		MVerticalLayout layout = new MVerticalLayout(); 
		layout.setMargin( new MMarginInfo( true, false ) );
		layout.add( new DataGrid( images ) );
		return layout;
	}

	private MVerticalLayout tabThree()
	{
		MVerticalLayout layout = new MVerticalLayout(); 
		layout.setMargin( new MMarginInfo( true, false ) );
		layout.add( new DataChart( images ) );
		layout.add(new RoseChart( images ) );
		return layout;
	}
	
	
	@Override
	public void enter( ViewChangeListener.ViewChangeEvent viewChangeEvent )
	{
	}
	
}
