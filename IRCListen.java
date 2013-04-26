import java.io.* ;
import java.net.* ;
import java.awt.* ;
import java.applet.* ;
import java.lang.Runtime ;


public class IRCListen implements Runnable
	{
	private final static int	MAX_LINES	= 10 ;

	private	Socket  socketBridge = null ;

	private	DataOutputStream	dsOutput = null ;

	private	DataInputStream dsInput = null ;

	private	String	sLine[] = new String[MAX_LINES] ;

	private	int	nLinesAvailable = 0 ;

	private boolean	bConnected = false ;

	Thread	threadIRC ;


	public void run()
		{
		System.out.println("Run Begins") ;
		while (true)
			{
//			System.out.println("Listening") ;
			try
				sLine[nLinesAvailable] = dsInput.readLine() ;
			
			catch(IOException e) ;

			nLinesAvailable++ ;
//			try
//				dsOutput.writeChars("PING" + (char)13 + (char)10) ;

//			catch (IOException e) ;
//			dsOutput.println

//			System.out.println("Got a line") ;
//			System.out.println(sMsg) ;
			}

		}


	public boolean IsConnected()
		{
		return (bConnected) ;
		}


	public String RetrieveLine()
		{
		nLinesAvailable-- ;

		return (sLine[nLinesAvailable]) ;
		}


	public boolean LineAvailable()
		{
		return (nLinesAvailable > 0) ;
		}


	public IRCListen(String sHost, int nPort)
		{
		// connect to socket
	    try
   			{
			socketBridge = new Socket(sHost, nPort) ;
  	        dsInput = new DataInputStream(socketBridge.getInputStream()) ;
			dsOutput = new DataOutputStream(socketBridge.getOutputStream()) ;
		    }

    	catch (Exception e)
   	    	{
    		if (socketBridge != null)
        		{
           		try
	           		socketBridge.close() ;
           		    		
	           	catch (IOException e2) ;
    	       	}

			if (dsInput != null)
       			{
           		try
	           		dsInput.close() ;
	           		    		
				catch (IOException e2) ;
		        }
	           		    	
			if (dsOutput != null)
				{
				try
					dsOutput.close() ;

				catch (IOException e2) ;
				}

			}

		if (socketBridge != null)
			{
			bConnected = true ;
			if (threadIRC == null)
    			threadIRC = new Thread(this) ;
    		
			threadIRC.setPriority(Thread.MIN_PRIORITY) ;
			threadIRC.start() ;
			}

		}

	}
