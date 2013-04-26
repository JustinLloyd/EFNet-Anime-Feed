import java.io.* ;
import java.net.* ;
import java.awt.* ;
import java.applet.* ;
import java.lang.Runtime ;

public class Anime extends Applet implements Runnable
    {
	static	final	String	sSatName[] = {	"California INTELSAT",
											"Texas GEOSAT",
											"Florida NETSAT",
											"Peru COMSAT",
											"Barbados WETSAT",
											"Western Europe INTSAT",
											"Eastern Europe INTELSAT",
											"Taiwan GEOSAT",
											"Tokyo NETSAT"
											} ;

	static	final	String	sFailed = "...FAILED" ;

	static final int	nGitSFrameList[] =
			{     0,   0,   0,   0,   0,   0,   1,   1,   1,   2,   2,   2,   3,   4,   4,   5,
				  5,   5,   6,   7,   7,   8,   8,   8,   9,   9,   9,   9,  10,  10,  11,  11,
				 11,  12,  12,  12,  13,  14,  14,  15,  15,  15,  16,  17,  17,  18,  18,  18,
				 19,  19,  19,  20,  21,  21,  22,  22,  22,  23,  24,  24,  25,  25,  25,  26,
				 27,  27,  28,  28,  28,  29,  29,  29,  30,  31,  31,  32,  32,  32,  33,  34,
				 34,  35,  35,  35,  36,  36,  36,  37,  38,  38,  39,  39,  39,  40,  41,  41,
				 42,  42,  42,  43,  43,  43,  44,  45,  45,  46,  46,  46,  47,  48,  48,  49,
				 49,  49,  50,  50,  50,  51,  52,  52,  53,  53,  53,  54,  55,  55,  56,  56,
				 56,  57,  57,  57,  58,  59,  59,  60,  60,  60,  61,  62,  62,  63,  63,  63,
				 64,  64,  64,  65,  66,  66,  67,  67,  67,  68,  69,  69,  70,  70,  70,  71,
				 71,  71,  71,  72,  72,  73,  73,  73,  74,  75,  75,  76,  76,  76,  77,  77,
				 77,  78,  79,  79,  80,  80,  80,  81,  82,  82,  83,  83,  83,  84,  84,  84,
				 85,  86,  86,  87,  87,  87,  88,  88,  88,  89,  90,  90,  91,  91,  91,  91,
				 92,  92,  93,  93,  93,  94,  94,  94,  95,  96,  96,  97,  97,  97,  98,  99,
				 99, 100, 100, 100, 101, 102, 102, 103, 103, 103, 104, 104, 104, 104, 105, 105,
				106, 106, 106, 107, 108, 108, 109, 109, 109, 110, 110, 110, 111, 112, 112, 113,
				113, 113, 114, 115, 115, 116, 116, 116, 117, 117, 117, 118, 119, 119, 120, 120,
				120, 121, 121, 121, 122, 123, 123 } ;

	// <monitor #>, <frame #>, <delay to next step>
	// frame # has the following meaning
	// 0 = video snow
	// 1 - 200 = advert
	// 201 = YUA video
	// 202 = GitS video
	static final int	nSequenceList[][] = {
{0, 0,  0},{1, 0,  0},{2, 0,  0},{3, 0,  0},{4, 0,  0},{5, 0,   0},{6, 0,  0},{7, 0,  0},{8, 0, 50},
{0, 1,  5},{1, 1,  5},{2, 1,  5},{3, 1,  5},{4, 1,  5},{5, 1,   5},{6, 1,  5},{7, 1,  5},{8, 1,  5},
{8, 3,  5},{5, 3,  5},{2, 3,  5},{1, 3,  5},{0, 3,  5},{3, 3,   5},{6, 3,  5},{7, 3,  5},{4, 3,  5}, 
{3, 0,  2},{5, 0,  2},{1, 0,  2},{6, 0,  2},{7, 0,  2},{8, 0,   2},{2, 0,  2},{0, 0,  2},{4, 0,  2},
{0, 2,  3},{4,10,  3},{7,16,  3},{1, 4,  3},{3, 8,  3},{5,12,   3},{2, 6,  3},{8,18,  3},{6,14, 20},
{4, 5, 10},{0, 5,  0},{2, 5,  0},{6, 5,  0},{8, 5, 10},{1, 5,   0},{3, 5,  0},{5, 5,  0},{7, 5, 20},
{0, 7,  3},{3, 7,  3},{6, 7,  3},{7, 7,  3},{4, 7,  3},{1, 7,   3},{2, 7,  3},{5, 7,  3},{8, 7,  3},

{7, 0,  5},{7, 9,  3},{4, 0,  5},{4, 9,  3},{1, 0,  5},{1, 9,   3},{3, 0,  0},{5, 0,  5},{3, 9,  0},
{5, 9,  3},{0, 0,  0},{2, 0,  0},{6, 0,  0},{8, 0,  5},{0, 9,   0},{2, 9,  0},{6, 9,  0},{8, 9,  5},

{2, 0,  4},{6, 0,  4},{4, 0,  4},{7, 0,  4},{3, 0,  4},{1, 0,   4},{0, 0,  4},{5, 0,  4},{8, 0,  4},

{0,11,  8},{0, 0,  4},{0,13,  8},{0, 0,  4},{0,20, 10},
{1,15,  8},{1, 0,  4},{1,17,  8},{1, 0,  4},{1,22, 10},
{2,19,  8},{2, 0,  4},{2,17,  8},{2, 0,  4},{2,21,  8},{2, 0,  4},{2,24, 10},
{3,13,  8},{3, 0,  4},{3,19,  8},{3,26, 10},
{4,11,  8},{4, 0,  4},{4,15,  8},{4, 0,  4},{4,28, 10},
{5,21,  8},{5, 0,  4},{5,17,  8},{5, 0,  4},{5,13,  8},{5, 0,  4},{5,30, 10},
{6,15,  8},{6, 0,  4},{6,25,  8},{6, 0,  4},{6,32, 10},
{7,27,  8},{7, 0,  4},{7,34, 10},
{8,23,  8},{8, 0,  4},{8,36, 10},
		{ 0, 202, 20 },
		{ -1, -1, -1 }
		} ;

	static final int	TOTAL_AD_FRAMES	= 52,
						TOTAL_MONITORS	= 9 ;

	// video states/types
	static	final	int	vtStatic	= 0,
						vtAdvert	= 1,
						vtVideoGitS	= 2 ;

	// blink states
	static final	int	bsClosing	= 0,
						bsOpening	= 1,
						bsOpen		= 2 ;

	// Playback states
	static	final	int	psConnecting	= 0,
						psConnectOK		= 1,
						psConnectFailed	= 2,
						psVideoPrep		= 3,
						psPlayback		= 100 ;

	// download states
	static	final	int	dsDownloadingYuri		= 0,
						dsDownloadingAdverts	= 1,
						dsDownloadingVideoGitS	= 2,
						dsFinished				= 3 ;

	int	nPlayState,
		nDownloadState,
		nAdvertLoaded = 0,
		nSequenceTimeout = 0,
		nSequenceStep = 0,
		nMonitorState[] = new int[TOTAL_MONITORS],
		nVideoFrame = 0,
		nVideoState = vtStatic,
		nCursorRow = 0,
		nBlinkSpeed = 2,
		nBlinkStep = 0,
		nBlinkFrame = 0,
		nBlinkPeriod = 19,
		nBlinkState = bsOpen ;


	boolean	bAudio = true,
			bRepaint = false,
			bYuriLoaded = false,
			bAdvertsLoaded = false,
			bVideoGitSLoaded = false,
			bFlipMsg = true ;


	long	lFrame = 0,
			lDownloadCount = 0 ;

	AudioClip	audioExplode ;

    Thread  threadAnime ;

	Graphics	gVideoMonitor,
				gYuriEyes,
				gBackground,
				gText,
				gOnScreenText,
				gVideoWall,
				gYuriTop ;

	Image	imageText,
			imageOnScreenText,
			imageBackground,
			imageDigits,
			imageYuriEyes,
			imageYuriMouth,
			imageAdvertParts,
			imageVideoParts,
			imageYuriParts,
			imageYuriLegs,
			imageYuriTop,
			imageWarningBar,
			imageVideoMonitor,
			imageVideoWall ;

	MediaTracker	mtDownload ;

	IRCListen	ircConnection ;


    public void init()
        {
		System.out.println(Runtime.getRuntime().freeMemory()) ;
        setBackground(Color.black) ;

		// create all purpose background image
		imageBackground = createImage(size().width, size().height) ;
		gBackground = imageBackground.getGraphics() ;
		gBackground.setColor(Color.black) ;
		gBackground.fillRect(0, 0, imageBackground.getWidth(this), imageBackground.getHeight(this)) ;
		repaint() ;
		gBackground.setColor(Color.white) ;
		gBackground.setFont(new Font("Dialog", Font.PLAIN, 12)) ;

//		ircConnection = new IRCListen("game1.virtualvegas.com", 6667) ;
//		ircConnection = new IRCListen(getCodeBase().getHost(), 6667) ;
		mtDownload = new MediaTracker(this) ;						// create a media tracker

		// retrieve Yuri
		imageYuriParts = getImage(getCodeBase(), "Yuri.GIF") ;		// maybe this should be document base
    	mtDownload.addImage(imageYuriParts, 0) ;
		prepareImage(imageYuriParts, this) ;
		mtDownload.checkAll(true) ;

		// build some graphic components
		imageVideoWall = createImage(75 * 3 + 9, 43 * 3 + 9) ;
		gVideoWall= imageVideoWall.getGraphics() ;
		gVideoWall.setColor(Color.black) ;
		gVideoWall.fillRect(0, 0, imageVideoWall.getWidth(this), imageVideoWall.getHeight(this)) ;

		imageYuriEyes = createImage(41, 19) ;
		gYuriEyes = imageYuriEyes.getGraphics() ;
		gYuriEyes.setColor(Color.black) ;
		gYuriEyes.fillRect(0, 0, imageYuriEyes.getWidth(this), imageYuriEyes.getHeight(this)) ;

		imageYuriTop = createImage(147, 199) ;						// @ 0x0
		gYuriTop = imageYuriTop.getGraphics() ;
		gYuriTop.setColor(Color.black) ;
		gYuriTop.fillRect(0, 0, imageYuriTop.getWidth(this), imageYuriTop.getHeight(this)) ;

		imageOnScreenText = createImage(size().width, 172) ;
		gOnScreenText = imageOnScreenText.getGraphics() ;

		imageText = createImage(size().width, 172) ;
		gText = imageText.getGraphics() ;
		gText.setColor(Color.black) ;
		gText.fillRect(0, 0, imageText.getWidth(this), imageText.getHeight(this)) ;
		gText.setColor(Color.green) ;

		imageWarningBar = createImage(10, 10) ;						// @ 178x55
		imageYuriMouth = createImage(23, 12) ;

		imageVideoMonitor = createImage(72, 43) ;					// @ 188x41
		gVideoMonitor = imageVideoMonitor.getGraphics() ;

		// video wall state machine
		for (int i = 0; i < TOTAL_MONITORS; i++)
			nMonitorState[i] = vtStatic ;

		nDownloadState = dsDownloadingYuri ;
		nPlayState = psConnecting ;
		System.out.println(Runtime.getRuntime().freeMemory()) ;
		}


    public void start()
        {
    	if (threadAnime == null)
    		{
    		threadAnime = new Thread(this) ;
			threadAnime.setPriority(Thread.MAX_PRIORITY) ;
			}
    		
	    threadAnime.start() ;
        }


    public void stop()
        {
		if (threadAnime != null && threadAnime.isAlive())
			{
			threadAnime.stop() ;
			threadAnime = null ;									// never gets here?
			}
			
        }


	private void UpdateEyes(Graphics g)
		{
		switch (nBlinkState)
			{
			case bsOpen :
				nBlinkPeriod-- ;
				if (nBlinkPeriod <= 0)
					{
					nBlinkState = bsClosing ;
					nBlinkSpeed = (int)(Math.random() * 4) ;
					nBlinkStep = 0 ;
					}

				break ;

			case bsClosing :
				nBlinkStep-- ;
				if (nBlinkStep < 0)
					{
					nBlinkStep = nBlinkSpeed ;
					nBlinkFrame++ ;
					gYuriEyes.drawImage(imageYuriParts, -147, -113 - 19 * nBlinkFrame, this) ;
					g.drawImage(imageYuriEyes, 39, 34, this) ;
					if (nBlinkFrame >= 3)
						nBlinkState = bsOpening ;

					}

				break ;

			case bsOpening :
				nBlinkStep-- ;
				if (nBlinkStep < 0)
					{
					nBlinkStep = nBlinkSpeed ;
					nBlinkFrame-- ;
					gYuriEyes.drawImage(imageYuriParts, -147, -113 - 19 * nBlinkFrame, this) ;
					g.drawImage(imageYuriEyes, 39, 34, this) ;
					if (nBlinkFrame <= 0)
						{
						nBlinkState = bsOpen ;
						nBlinkPeriod = (int)(Math.random() * 19) + 6 ;
						}

					}

				break ;


			}

		}


	private void UpdateMonitor(int nVideoType, int nFrame, int nMonitor)
		{
		nMonitorState[nMonitor] = nVideoType ;
		if (nVideoType == vtVideoGitS)
			{
			if (nFrame >= 0 && nFrame <= 500)
				{
				// draw video image
				nFrame = nGitSFrameList[nFrame] ;
				gVideoMonitor.drawImage(imageVideoParts, 0, -43 * nFrame, null) ;
				}

			}
		else if (nVideoType == vtAdvert)
			{
			if (nFrame >= 0 && nFrame <= 199)
				gVideoMonitor.drawImage(imageAdvertParts, 0, -43 * nFrame, this) ;

			}
		else if (nVideoType == vtStatic)
			{
			// draw static
			gVideoMonitor.drawImage(imageYuriParts, -188 - (int)(Math.random() * 28), -((int)(Math.random() * 156)), this) ;
			gVideoMonitor.drawImage(imageYuriParts, -188 - (int)(Math.random() * 28), -((int)(Math.random() * 156)), this) ;
			}
		else
			{
			gVideoMonitor.setColor(Color.black) ;
			gVideoMonitor.fillRect(0, 0, 72, 43) ;
			}

		gVideoWall.drawImage(imageVideoMonitor, (nMonitor % 3) * 75, (nMonitor / 3) * 46, this) ;
		}


	private void UpdateMonitors(Graphics g)
		{
		int	nMonitor ;

		if (nVideoState == vtVideoGitS)
			{
			if (nVideoFrame == 0 && bAudio)
				audioExplode.play() ;

			UpdateMonitor(vtVideoGitS, nVideoFrame, 0) ;
			for (nMonitor = 1; nMonitor < TOTAL_MONITORS; nMonitor++)
				UpdateMonitor(vtVideoGitS, -1, nMonitor) ;

			nVideoFrame++ ;
			if (nVideoFrame > 277)
				{
				nVideoFrame = 0 ;
				nVideoState = vtAdvert ;
				}

			}
		else if (nVideoState == vtStatic)
			{
			for (nMonitor = 0; nMonitor < TOTAL_MONITORS; nMonitor++)
				UpdateMonitor(vtStatic, 0, nMonitor) ;

			}
		else
			{
			if ((nSequenceList[nSequenceStep][1] <= nAdvertLoaded) || (nSequenceList[nSequenceStep][1] == 202 && bVideoGitSLoaded))
				{
				while (nSequenceTimeout == 0)
					{
//					if (nSequenceList[nSequenceStep][0] == 0)
					if (nSequenceList[nSequenceStep][1] == 0)
						UpdateMonitor(vtStatic, 0, nSequenceList[nSequenceStep][0]) ;
					else if (nSequenceList[nSequenceStep][1] == 202)
						nVideoState = vtVideoGitS ;
					else if (nSequenceList[nSequenceStep][1] >=1 && nSequenceList[nSequenceStep][1] <= 200)
						UpdateMonitor(vtAdvert, nSequenceList[nSequenceStep][1] - 1, nSequenceList[nSequenceStep][0]) ;

					nSequenceTimeout = nSequenceList[nSequenceStep][2] ;
					nSequenceStep++ ;
					if (nSequenceList[nSequenceStep][0] == -1)
						nSequenceStep = 0 ;

					}

				nSequenceTimeout-- ;
				}

			for (nMonitor = 0; nMonitor < TOTAL_MONITORS; nMonitor++)
				{
				if (nMonitorState[nMonitor] == vtStatic)
					UpdateMonitor(vtStatic, 0, nMonitor) ;

				}

			}

		g.drawImage(imageVideoWall, 100, 20, null) ;
		}


	private void UpdateMessageArea(Graphics g)
		{
		gOnScreenText.drawImage(imageText, 0, 0, null) ;
		gOnScreenText.drawImage(imageYuriParts, 0, -199, null) ;
		g.drawImage(imageOnScreenText, 0, 199, null) ;
		}


	private void IncomingMessage()
		{
		String	sMsg ;

		sMsg = ircConnection.RetrieveLine() ;
		System.out.println(sMsg) ;
		gText.setColor(Color.green) ;
		gText.drawString(sMsg, 0, imageText.getHeight(this) - 4) ;
		UpdateMessageArea(getGraphics()) ;
		gText.drawImage(imageText, 0, -10, null) ;
		gText.setColor(Color.black) ;
		gText.fillRect(0, imageText.getHeight(this) - 10, imageText.getWidth(this), 10) ;
		}


	private void HandleState()
		{
//		Graphics g = gBackground ;
//		System.out.println("Handle state " + lFrame) ;
		switch (nDownloadState)
			{
			case dsDownloadingVideoGitS :
				if (mtDownload.checkAll())
					{
					System.out.println(Runtime.getRuntime().freeMemory()) ;
					System.out.println("Video Loaded") ;
					bVideoGitSLoaded = true ;
					nDownloadState = dsFinished ;
					System.out.println("Finished at Frame #" + lFrame + " & DownloadCount #" + lDownloadCount) ;
					}

				break ;

			case dsDownloadingAdverts :
				if (mtDownload.checkAll())
					{
					System.out.println(Runtime.getRuntime().freeMemory()) ;
					System.out.println("Adverts Loaded") ;
					bAdvertsLoaded = true ;
					imageVideoParts = getImage(getCodeBase(), "Explode.JPG") ;
					mtDownload.addImage(imageVideoParts, 0) ;
					prepareImage(imageVideoParts, this) ;
					mtDownload.checkAll(true) ;
					nDownloadState = dsDownloadingVideoGitS ;
					}

				break ;

			case dsDownloadingYuri :
				if (mtDownload.checkAll())
					{
					System.out.println(Runtime.getRuntime().freeMemory()) ;
					System.out.println("Yuri Loaded") ;
			   		audioExplode = getAudioClip(getCodeBase(), "explode.au") ;
					imageAdvertParts = getImage(getCodeBase(), "AdStrip.JPG") ;
    				mtDownload.addImage(imageAdvertParts, 0) ;
					prepareImage(imageAdvertParts, this) ;
					mtDownload.checkAll(true) ;
					gYuriTop.drawImage(imageYuriParts, 0, 0, null) ;
					imageWarningBar.getGraphics().drawImage(imageYuriParts, -178, -55, null) ;
					bYuriLoaded = true ;
					nDownloadState = dsDownloadingAdverts ;
					}

				break ;

			}


/*
		frame, color, action (display message), message #, x, y, 
		frame, color, action (clear background)

		action0 = clear background to colour
		action1 = message1
		action2 = message2

  */
		switch (nPlayState)
			{
			case psConnecting :
//				System.out.println("Connect State") ;
				if (lFrame < 120)
					{
					if ((lFrame % 3) == 0)
						{
						if (!bFlipMsg)
							gBackground.setColor(Color.black) ;
						else
							gBackground.setColor(Color.green) ;

						gBackground.drawString("Attempting to connect to sattelite uplink...", 0, 50) ;
						bFlipMsg = !bFlipMsg ;
						}

					}

				gBackground.setColor(Color.green) ;
				if (lFrame > 30 && lFrame <= 120 && (lFrame % 10) == 0)
					{
					int nTemp = (int)((lFrame - 40) / 10) ;
					if (nTemp > 0)
						gBackground.drawString(sFailed, 150, (nTemp * 10 + 50)) ;

					gBackground.drawString(sSatName[nTemp], 0, nTemp * 10 + 60) ;
					}

				if (lFrame == 130)
					{
					if (ircConnection.IsConnected())
						{
						gBackground.drawString("...CONNECT", 150, 140) ;
						nPlayState = psConnectOK ;
						}
					else
						{
						gBackground.drawString(sFailed, 150, 140) ;
						nPlayState = psConnectFailed ;
						}

					}

				break ;

			case psConnectOK :
				if (lFrame >= 135 && lFrame <= 145 && (lFrame % 2) == 0)
					{
					gBackground.setColor(Color.black) ;
					int nTemp = (int)((lFrame - 40) / 10) ;
					gBackground.drawString(sSatName[nTemp], 0, (nTemp * 10 + 60)) ;
					gBackground.drawString(sFailed, 150, (nTemp * 10 + 60)) ;
					}

				if (lFrame == 150)
					{
					gBackground.setColor(Color.green) ;
					gBackground.drawString("Establishing information download", 0, 160) ;
					}

				if (lFrame > 170 && bYuriLoaded)
					nPlayState = psVideoPrep ;

				break ;

			case psConnectFailed :
				if (lFrame == 131)
					{
					gBackground.setColor(Color.red) ;
					gBackground.drawString("Security Violation Detected", 0, 160) ;
					gBackground.drawString("Line trace begins...", 0, 175) ;
					}

				if (lFrame >= 140 && lFrame <= 144)
					ClearBackground(gBackground, size().width, size().height, (int)(lFrame - 140) * 50, (int)(lFrame - 140) * 50, (int)(lFrame - 140) * 50) ;

				if (lFrame == 144)
					nPlayState = psVideoPrep ;

				break ;

			case psVideoPrep :
				ClearBackground(gBackground, size().width, size().height, 0, 0, 0) ;
				gBackground.fillRect(0, 0, imageBackground.getWidth(this), imageBackground.getHeight(this)) ;
				DrawYuri() ;
				bRepaint = true ;
				nPlayState = psPlayback ;
				nVideoState = vtAdvert ;
				break ;

			case psPlayback :
				UpdateEyes(getGraphics()) ;
				UpdateMonitors(getGraphics()) ;
				break ;

			}

		}

	public void DrawYuri()
		{
		int	i ;

		if (bYuriLoaded)
			{
			for (i = 0; i < 36; i++)
				gBackground.drawImage(imageWarningBar, 143 + i * 10, 0, null) ;

			for (i = 0; i < 36; i++)
				gBackground.drawImage(imageWarningBar, 144 + i * 10, 189, null) ;

			gBackground.drawImage(imageYuriTop, 0, 0, null) ;			// draw top part of yuri
			UpdateEyes(gBackground) ;									// draw yuri's eyes
			UpdateMonitors(gBackground) ;								// draw monitors
			UpdateMessageArea(gBackground) ;							// draw text & legs

			for (i = 0; i < 50; i++)
				gBackground.drawImage(imageWarningBar, i * 10, 370, null) ;

			}

		}

	public void update(Graphics g)
		{
//		System.out.println("UPDATE") ;
		if (nPlayState != psPlayback || bRepaint)
			g.drawImage(imageBackground, 0, 0, null) ;

		bRepaint = false ;
//		System.out.println("UPDATE ENDS") ;
		}
		

  public void run()
        {
		String	sMsg ;

		int	i ;

//		if (!ircConnection.IsConnected())
//			stop() ;

//		System.out.println("RUN") ;
		while (true)
			{
			HandleState() ;
			// use a sleep period of 65 for IE & 12 for NS
			if (nVideoState == vtVideoGitS)
				{
				try
					{
					Thread.currentThread().sleep(60) ;
					}

				catch (InterruptedException e)
					{
					}

				}
			else
				{
				try
					{
					Thread.currentThread().sleep(50) ;
					}

				catch (InterruptedException e)
					{
					}

				}


			if (ircConnection.LineAvailable())
				IncomingMessage() ;

			lFrame++ ;
			if ((lFrame % 250) == 0)
				{
				Runtime.getRuntime().gc() ;
				System.out.println(Runtime.getRuntime().freeMemory()) ;
				}

			update(getGraphics()) ;
			}

		}


	public void paint(Graphics g)
		{
		int	i ;

		System.out.println("Paint") ;
		// complete refresh of Yuri required
		if (nPlayState == psPlayback)
			DrawYuri() ;

		bRepaint = true ;
		update(g) ;
		}


	public void ClearBackground(Graphics graf, int w, int h, int r, int g, int b)
		{
		graf.setColor(new Color(r, g, b)) ;
		graf.fillRect(0, 0, w, h) ;
		}


    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height)
        {

		if ((lDownloadCount % 25) == 0)
			{
			Graphics	g = getGraphics() ;

			g.setColor(Color.black) ;
			g.fillRect(350, 26, 28, 12) ;
			g.setColor(Color.white) ;
			g.drawString("" + (7875 - lDownloadCount), 350, 36) ;
			g.dispose() ;
			}

		if (img == imageAdvertParts)
			{
			if ((y % 43) == 0)
				{
				nAdvertLoaded++ ;
//				System.out.println("Advert " + nAdvertLoaded) ;
				}

			}

		lDownloadCount++ ;

		return (true) ;
		}


	public boolean mouseUp(Event evt, int x, int y)
		{
		System.out.println("Mouse UP") ;
		if (x < 60 & y < 60)
			{
			bAudio = !bAudio ;
			}

		return (false) ;
		}

    }
