package org.robotmessenger.application;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.Map;
import java.util.Vector;
import org.robotmessenger.exberry.ExberryOrderManager;
import org.robotmessenger.exberry.ExberrySession;
import org.robotmessenger.exberry.IExberrySessionListener;
import org.robotmessenger.exberry.dto.Enums.OrderType;
import org.robotmessenger.exberry.dto.Enums.Side;
import org.robotmessenger.exberry.dto.Enums.TimeInForce;
import org.robotmessenger.exberry.dto.request.CancelOrderRequest;
import org.robotmessenger.exberry.dto.request.ModifyOrderRequest;
import org.robotmessenger.exberry.dto.request.PlaceOrderRequest;
import org.robotmessenger.exberry.dto.request.ReplaceOrderRequest;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.gui2.TextBox.Style;
import com.googlecode.lanterna.gui2.menu.Menu;
import com.googlecode.lanterna.gui2.menu.MenuBar;
import com.googlecode.lanterna.gui2.menu.MenuItem;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;



/**
 * Session adapter used to handle data received from Exberry server printing to
 * user interface;
 * 
 * @author popolony2k
 *
 */
class ExberrySessionAdapter implements IExberrySessionListener {
	
	private TextBox                      textArray[];
	private Vector <ExberryOrderManager> sessions;
	
	
		
	/**
	 * Constructor. Initialize all class data.
	 * @param textArray TextArray with all TextBoxes used to print data;
	 * @param sessions Session list with all configured sessions;
	 */
	public ExberrySessionAdapter( TextBox textArray[], Vector <ExberryOrderManager> sessions )  {
	
		this.textArray = textArray;
		this.sessions  = sessions;
	}
	
	/**
	 * Print message to corresponding textbox;
	 * 
	 * @param textArray Allocated textarray with all textboxes to print data;
	 * @param sessions Allocated sessions;
	 * @param session Current session;
	 * @param message Message to print;
	 */
	public void printText( TextBox[] textArray, Vector <ExberryOrderManager> sessions, 
                            ExberrySession session, String message )  {
		
		TextBox text = textArray[sessions.indexOf( session )];
		
	    text.addLine( message );					
	    text.setCaretPosition( Integer.MAX_VALUE, 0 );
	}
	
	@Override
	public void onOpen( ExberrySession session ) {
		
		printText( textArray, sessions, session, "Connected to Exberry matching engine" );
	}
	
	@Override
	public void onMessage( ExberrySession session, String message ) {
		
		printText( textArray, sessions, session, message );
	}
	
	@Override
	public void onError( ExberrySession session, String error ) {
		
		printText( textArray, sessions, session, error );
	}
	
	@Override
	public void onClose( ExberrySession session, int reasonCode ) {
		
		printText( textArray, sessions, session, "Disconnected from Exberry matching engine" );
	}
};  // ExberrySessionAdapter


/**
 * 
 * Main application entry-point.
 */
public class Main {

	/**
	 * Shows a dialog with all order data to send to Exberry;
	 * 
	 * @param session session whose order will be placed;
	 * @return true on success or false otherwise;
	 */
	private static boolean showPlaceOrderDialog( Screen screen, final MultiWindowTextGUI textGUI, final ExberryOrderManager session )  {
		
		if( session.isConnected() )  {
			
			final PlaceOrderRequest.PlaceOrder  order = PlaceOrderRequest.newInstance();
        	
	        Panel                             panel     = new Panel();
	        final Label                       lblOutput = new Label( "" );
	        final BasicWindow                 window    = new BasicWindow( "Place order" );
	        final Map.Entry<Boolean, Boolean> pair      = new AbstractMap.SimpleEntry<Boolean, Boolean>( false, false );


	        window.setCloseWindowWithEscape( true );
	        window.setComponent( panel );
	        
	        panel.setLayoutManager(new GridLayout(2));
	        panel.addComponent( new Label( "Order Type" ) );
	        
	        final ComboBox<String> orderTypeCombo = new ComboBox<String>();
	        
	        orderTypeCombo.addItem( "Limit" );
	        orderTypeCombo.addItem( "Market" );
	        panel.addComponent( orderTypeCombo );
	        
	        panel.addComponent( new Label( "Side" ) );
	        final ComboBox<String> orderSideCombo = new ComboBox<String>();
	        
	        orderSideCombo.addItem( "Buy" );
	        orderSideCombo.addItem( "Sell" );
	        panel.addComponent( orderSideCombo );

	        panel.addComponent( new Label( "Quantity" ) );
	        final TextBox quantityTxt = new TextBox( "1.3" ).addTo( panel );

	        panel.addComponent( new Label( "Price" ) );
	        final TextBox priceTxt = new TextBox( "100.33" ).addTo( panel );
	        
	        panel.addComponent( new Label( "Instrument" ) );
	        final TextBox instrumentTxt = new TextBox( "INS3" ).addTo( panel );

	        panel.addComponent( new Label( "MpOrderId" ) );
	        final TextBox mpOrderIdTxt = new TextBox( "5004" ).addTo( panel );
	        
	        panel.addComponent( new Label( "Time in force" ) );
	        final ComboBox<String> timeInForceCombo = new ComboBox<String>();
	        timeInForceCombo.addItem( "GTC" );
	        timeInForceCombo.addItem( "GTD" );
	        timeInForceCombo.addItem( "FOK" );
	        timeInForceCombo.addItem( "IOC" );
	        timeInForceCombo.addItem( "GAA" );
	        panel.addComponent( timeInForceCombo );
	        
	        panel.addComponent( new Label( "UserId" ) );
	        final TextBox userIdTxt = new TextBox( "UATUserTest10" ).addTo( panel );

	        panel.addComponent( new EmptySpace( new TerminalSize( 0, 0 ) ) );
	        panel.addComponent( lblOutput );
	        
	        new Button( "Send!", new Runnable() {
	            @Override
	            public void run() {
	            	
	                order.orderType   = OrderType.values()[orderTypeCombo.getSelectedIndex()];
	                order.side        = Side.values()[orderSideCombo.getSelectedIndex()];
	                order.quantity    = Double.parseDouble( quantityTxt.getText() );
	                order.price       = ( !priceTxt.getText().isEmpty() ? Double.parseDouble( priceTxt.getText() ) : null );
	                order.instrument  = instrumentTxt.getText();
	                order.mpOrderId   = Long.parseLong( mpOrderIdTxt.getText() );
	                order.timeInForce = TimeInForce.values()[timeInForceCombo.getSelectedIndex()]; 
	                order.userId      = userIdTxt.getText();

	                pair.setValue( true );
	                window.close();
	            }
	        } ).addTo( panel );
	        
	        new Button( "Cancel!", new Runnable() {
	            @Override
	            public void run() {
	            	
	                window.close();
	            }
	        } ).addTo( panel );
	        
	        textGUI.addWindowAndWait( window );
			
	        if( pair.getValue() )
	        	return session.placeOrder( order );
	        
	        return true;
		}
		
		return false;
	}
	
	/**
	 * Shows a dialog with data to cancel an order at Exberry engine;
	 * 
	 * @param session session whose order will be canceled;
	 * @return true on success or false otherwise;
	 */
	private static boolean showCancelOrderDialog( Screen screen, final MultiWindowTextGUI textGUI, final ExberryOrderManager session )  {
		
		if( session.isConnected() )  {
			
			final CancelOrderRequest.CancelOrder  order = CancelOrderRequest.newInstance();
        	
	        Panel                             panel     = new Panel();
	        final Label                       lblOutput = new Label( "" );
	        final BasicWindow                 window    = new BasicWindow( "Cancel order" );
	        final Map.Entry<Boolean, Boolean> pair      = new AbstractMap.SimpleEntry<Boolean, Boolean>( false, false );


	        window.setCloseWindowWithEscape( true );
	        window.setComponent( panel );
	        
	        panel.setLayoutManager(new GridLayout(2));

	        panel.addComponent( new Label( "Order Id" ) );
	        final TextBox orderIdTxt = new TextBox( "1004" ).addTo( panel );

	        panel.addComponent( new Label( "MpOrderId" ) );
	        final TextBox mpOrderIdTxt = new TextBox( "5004" ).addTo( panel );

	        panel.addComponent( new Label( "Instrument" ) );
	        final TextBox instrumentTxt = new TextBox( "INS3" ).addTo( panel );

	        panel.addComponent( new EmptySpace( new TerminalSize( 0, 0 ) ) );
	        panel.addComponent( lblOutput );

	        new Button( "Send!", new Runnable() {
	            @Override
	            public void run() {
	            	
	                order.instrument  = instrumentTxt.getText();
	                order.orderId     = ( !orderIdTxt.getText().isEmpty() ? Long.parseLong( orderIdTxt.getText() ) : null );
	                order.mpOrderId   = ( !mpOrderIdTxt.getText().isEmpty() ? Long.parseLong( mpOrderIdTxt.getText() ) : null );
	                
	                pair.setValue( true );
	                window.close();
	            }
	        } ).addTo( panel );

	        new Button( "Cancel!", new Runnable() {
	            @Override
	            public void run() {
	            	
	                window.close();
	            }
	        } ).addTo( panel );

	        textGUI.addWindowAndWait( window );
			
	        if( pair.getValue() )
	        	return session.cancelOrder( order );
	        
	        return true;
		}
		
		return false;
	}
	
	/**
	 * Shows a dialog with data to mass cancel orders at Exberry engine;
	 * 
	 * @param session session whose order will be canceled;
	 * @return true on success or false otherwise;
	 */
	private static boolean showMassCancelOrderDialog( Screen screen, final MultiWindowTextGUI textGUI, final ExberryOrderManager session )  {
		
		if( session.isConnected() )  {
			        	
	        Panel                             panel     = new Panel();
	        final Label                       lblOutput = new Label( "" );
	        final BasicWindow                 window    = new BasicWindow( "Mass cancel order" );
	        final Map.Entry<Boolean, Boolean> pair      = new AbstractMap.SimpleEntry<Boolean, Boolean>( false, false );

	        window.setCloseWindowWithEscape( true );
	        window.setComponent( panel );
	        
	        panel.setLayoutManager(new GridLayout(2));

	        panel.addComponent( new Label( "Instrument" ) );
	        final TextBox instrumentTxt = new TextBox( "INS3" ).addTo( panel );
	        
	        panel.addComponent( new EmptySpace( new TerminalSize( 0, 0 ) ) );
	        panel.addComponent( lblOutput );

	        new Button( "Send!", new Runnable() {
	            @Override
	            public void run() {

	            	pair.setValue( true );
	                window.close();
	            }
	        } ).addTo( panel );

	        new Button( "Cancel!", new Runnable() {
	            @Override
	            public void run() {
	            	
	                window.close();
	            }
	        } ).addTo( panel );

	        textGUI.addWindowAndWait( window );
			
	        if( pair.getValue() )
	        	return session.massCancel( instrumentTxt.getText() );
	        
	        return true;
		}
		
		return false;
	}
	
	/**
	 * Shows a dialog with order data to be modified at Exberry engine;
	 * 
	 * @param session session whose order will be placed;
	 * @return true on success or false otherwise;
	 */
	private static boolean showModifyOrderDialog( Screen screen, final MultiWindowTextGUI textGUI, final ExberryOrderManager session )  {
		
		if( session.isConnected() )  {
			
			final ModifyOrderRequest.ModifyOrder  order = ModifyOrderRequest.newInstance();
        	
	        Panel                             panel     = new Panel();
	        final Label                       lblOutput = new Label( "" );
	        final BasicWindow                 window    = new BasicWindow( "Modify order" );
	        final Map.Entry<Boolean, Boolean> pair      = new AbstractMap.SimpleEntry<Boolean, Boolean>( false, false );
	        

	        window.setCloseWindowWithEscape( true );
	        window.setComponent( panel );
	        
	        panel.setLayoutManager(new GridLayout(2));

	        panel.addComponent( new Label( "Order Id" ) );
	        final TextBox orderIdTxt = new TextBox( "1001" ).addTo( panel );

	        panel.addComponent( new Label( "Instrument" ) );
	        final TextBox instrumentTxt = new TextBox( "INS3" ).addTo( panel );

	        panel.addComponent( new Label( "Quantity" ) );
	        final TextBox quantityTxt = new TextBox( "1.3" ).addTo( panel );

	        panel.addComponent( new EmptySpace( new TerminalSize( 0, 0 ) ) );
	        panel.addComponent( lblOutput );
	        
	        new Button( "Send!", new Runnable() {
	            @Override
	            public void run() {
	            	
	                order.quantity   = Double.parseDouble( quantityTxt.getText() );
	                order.instrument = instrumentTxt.getText();
	                order.orderId    = Long.parseLong( orderIdTxt.getText() );
	                
	                pair.setValue( true );
	                window.close();
	            }
	        } ).addTo( panel );
	        
	        new Button( "Cancel!", new Runnable() {
	            @Override
	            public void run() {
	            	
	                window.close();
	            }
	        } ).addTo( panel );
	        
	        textGUI.addWindowAndWait( window );
	        
	        if( pair.getValue() )			
	        	return session.modifyOrder( order );
	        
	        return true;
		}
		
		return false;
	}
	
	/**
	 * Shows a dialog with all order data to send to Exberry;
	 * 
	 * @param session session whose order will be placed;
	 * @return true on success or false otherwise;
	 */
	private static boolean showReplaceOrderDialog( Screen screen, final MultiWindowTextGUI textGUI, final ExberryOrderManager session )  {
		
		if( session.isConnected() )  {
			
			final ReplaceOrderRequest.ReplaceOrder  order = ReplaceOrderRequest.newInstance();
        	
	        Panel                             panel     = new Panel();
	        final Label                       lblOutput = new Label( "" );
	        final BasicWindow                 window    = new BasicWindow( "Replace order" );
	        final Map.Entry<Boolean, Boolean> pair      = new AbstractMap.SimpleEntry<Boolean, Boolean>( false, false );
	        

	        window.setCloseWindowWithEscape( true );
	        window.setComponent( panel );
	        
	        panel.setLayoutManager(new GridLayout( 2 ) );
	        
	        panel.addComponent( new Label( "Order Id" ) );
	        final TextBox orderIdTxt = new TextBox( "1004" ).addTo( panel );

	        panel.addComponent( new Label( "Instrument" ) );
	        final TextBox instrumentTxt = new TextBox( "INS3" ).addTo( panel );

	        panel.addComponent( new Label( "Quantity" ) );
	        final TextBox quantityTxt = new TextBox( "1.3" ).addTo( panel );

	        panel.addComponent( new Label( "Price" ) );
	        final TextBox priceTxt = new TextBox( "100.33" ).addTo( panel );
	        	        
	        panel.addComponent( new Label( "Time In Force" ) );
	        final ComboBox<String> timeInForceCombo = new ComboBox<String>();
	        timeInForceCombo.addItem( "NONE" );
	        timeInForceCombo.addItem( "GTC" );
	        timeInForceCombo.addItem( "GTD" );
	        timeInForceCombo.addItem( "FOK" );
	        timeInForceCombo.addItem( "IOC" );
	        timeInForceCombo.addItem( "GAA" );
	        panel.addComponent( timeInForceCombo );
	        
	        panel.addComponent( new Label( "Expiry date (GTD)" ) );
	        final TextBox expiryDateTxt = new TextBox(new SimpleDateFormat( "yyyy-MM-dd" ).format( Calendar.getInstance().getTime() ) ).addTo( panel );

	        panel.addComponent( new EmptySpace( new TerminalSize( 0, 0 ) ) );
	        panel.addComponent( lblOutput );
	        
	        new Button( "Send!", new Runnable() {
	            @Override
	            public void run() {
	            	
	            	try  {
	            		int  selectedIndex = timeInForceCombo.getSelectedIndex();
	            		
		                order.quantity    = Double.parseDouble( quantityTxt.getText() );
		                order.price       = Double.parseDouble( priceTxt.getText() );
		                order.instrument  = instrumentTxt.getText();
		                order.timeInForce = ( selectedIndex > 0 ? TimeInForce.values()[selectedIndex - 1] : null ); 
		                order.orderId     = Long.parseLong( orderIdTxt.getText() );
		                
		                if( order.timeInForce == TimeInForce.GTD )  {		                	
		                	order.expiryDate = new SimpleDateFormat( "yyyy-MM-dd" ).parse( expiryDateTxt.getText() );
		                }
	            	} 
	            	catch( Exception e )  {
	            		e.printStackTrace();
	            	}
	            	
	            	pair.setValue( true );
	                window.close();
	            }
	        } ).addTo( panel );
	        
	        new Button( "Cancel!", new Runnable() {
	            @Override
	            public void run() {
 
	                window.close();
	            }
	        } ).addTo( panel );
	        
	        textGUI.addWindowAndWait( window );
	        
	        if( pair.getValue() )
	        	return session.replaceOrder( order );
	        
	        return true;
		}
		
		return false;
	}
		
	/**
	 * Main program entry point.
	 * @param args command line arguments
	 */
    public static void main( String[] args ) {
    	
    	/*
    	 *  For those who are trying to connect through a proxy server
    	 *  uncomment below
    	 */
    	//System.setProperty( "java.net.useSystemProxies", "true" );


    	try {
    		final Vector <ExberryOrderManager> sessions = new Vector<ExberryOrderManager>();
    		ExberryOrderManager         session;
    		int                         columns;
    		int                          rows;
    		
    		boolean                      started      = true;
    		String                       strSecretKey = "8e46b743e9cfa5c3ec1dfdfec2efe14b04d8f72dc574ea50582edf8ce5dbe792";
    		String                       strApiKey    = "a31a75f4-0bf7-4264-8842-473fb77f7bca";

    		// Setup terminal and screen layers
    		MenuBar                      menuBar      = new MenuBar();
    		Menu                         menuFile     = new Menu( "File" );
    		GridLayout                   layout       = new GridLayout( 1 );
    		Panel                        outputPanel  = new Panel( layout );
    		Terminal                     terminal     = new DefaultTerminalFactory().createTerminal();
    		final BasicWindow            outputWindow = new BasicWindow();
    		final Screen                 screen       = new TerminalScreen( terminal );
    		final TextBox                output       = new TextBox( "", Style.MULTI_LINE );
    		final TextBox                orderStream  = new TextBox( "", Style.MULTI_LINE );
    		final TextBox                erStream     = new TextBox( "", Style.MULTI_LINE );
    		final TextBox                tradesStream = new TextBox( "", Style.MULTI_LINE );
    		final TextBox[]              textArray    = { output, orderStream, erStream, tradesStream };
    		final ExberrySessionAdapter  adapter      = new ExberrySessionAdapter( textArray, sessions );
    		final MultiWindowTextGUI     textGUI      = new MultiWindowTextGUI( screen, new DefaultWindowManager(),
                                                                                new EmptySpace( TextColor.ANSI.BLUE ));
						
    		/*
    		 * Menu items setup
    		 */
    		menuBar.add( menuFile );
    		menuFile.add( new MenuItem( "Authenticate", new Runnable() {

				@Override
				public void run() {
					
					for( ExberryOrderManager session : sessions )  {
			            if( !session.isConnected() || !session.sendAuth() )
			            	adapter.printText( textArray, sessions, session, "Error sending authentication" );
					}
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Order book state", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 0 );
					
		            if( !session.isConnected() || !session.requestOrderBookState() )
		            	adapter.printText( textArray, sessions, session, "Error sending requestOrderBookState()" );
				}
			} ) );

    		menuFile.add( new MenuItem( "Order book depth", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 1 );

		            if( !session.isConnected() || !session.requestOrderBookDepth( 0L ) )
		            	adapter.printText( textArray, sessions, session, "Error sending requestOrderBookDepth()" );
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Mass order status", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 0 );
					
		            if( !session.isConnected() || !session.requestMassOrderStatus() )
		            	adapter.printText( textArray, sessions, session, "Error sending requestMassOrderStatus()" );
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Execution Reports", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 2 );
					
		            if( !session.isConnected() || !session.requestExecutionReports( 0L ) )
		            	adapter.printText( textArray, sessions, session, "Error sending requestExecutionReports()" );
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Trades", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 3 );
					
		            if( !session.isConnected() || !session.requestTrades( 0L ) )
		            	adapter.printText( textArray, sessions, session, "Error sending requestTrades()" );
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Place order...", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 0 );
					
		            if( !showPlaceOrderDialog( screen, textGUI, session ) )
		            	adapter.printText( textArray, sessions, session, "Error placeOrder()" );
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Cancel order...", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 0 );
					
		            if( !showCancelOrderDialog( screen, textGUI, session ) )
		            	adapter.printText( textArray, sessions, session, "Error cancelOrder()" );
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Mass cancel order...", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 0 );
					
		            if( !showMassCancelOrderDialog( screen, textGUI, session ) )
		            	adapter.printText( textArray, sessions, session, "Error massCancelOrder()" );
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Modify order...", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 0 );
					
		            if( !showModifyOrderDialog( screen, textGUI, session ) )
		            	adapter.printText( textArray, sessions, session, "Error modifyOrder()" );
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Replace order...", new Runnable() {

				@Override
				public void run() {
					ExberryOrderManager session = sessions.get( 0 );
					
		            if( !showReplaceOrderDialog( screen, textGUI, session ) )
		            	adapter.printText( textArray, sessions, session, "Error replaceOrder()" );
				}
			} ) );
    		
    		menuFile.add( new MenuItem( "Exit", new Runnable() {

				@Override
				public void run() {
					for( ExberryOrderManager session : sessions )  {
			            if( !session.isConnected() || session.isRunning() )
			            	session.stop();
					}
		            
					outputWindow.close();
				}
			} ) );
    		
    		/**
    		 * Main window and user interface elements setup.
    		 */
    		layout.setVerticalSpacing( 1 );
 
    		screen.startScreen();
    		outputWindow.setFixedSize( new TerminalSize( 109, 23 ) );
    		outputWindow.setPosition( TerminalPosition.TOP_LEFT_CORNER );
    		outputWindow.setMenuBar( menuBar );
    		outputWindow.setComponent( outputPanel );
    		
    		columns = ( outputWindow.getSize().getColumns() - 1 );
    		rows    = ( outputWindow.getSize().getRows() / textArray.length );
    		
    		for( int count = 0; count < textArray.length; count++ )  {
        		textArray[count].setPreferredSize( new TerminalSize( columns, rows ) );
        		outputPanel.addComponent( textArray[count] );
        		
				session = new ExberryOrderManager( ExberryOrderManager.EXBERRY_STAGING_URI, strApiKey, strSecretKey );
				session.addExberrySessionListener( adapter );			
				sessions.add( session );
    		}
    		
    		for( ExberryOrderManager s : sessions )
    			started &= s.start();
    		
			if( started )  {
				textGUI.addWindowAndWait( outputWindow );
				
	    		for( ExberryOrderManager s : sessions )
	    			s.stop();
	    		
	    		screen.close();
			}
			else  {
				System.out.println( "Failed to start application" );
				System.exit( 0 );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}    	
     }
}
