/**
	 * Saves an clam message with a timestamp
	 */
	public class clamMessage
	{
		private String timeStamp;
		private String errorMessage;
		/**
		 * Constructor for clam message (no parameters)
		 */
		public clamMessage(){
			timeStamp = null;
			errorMessage = null;
		}
		/**
		 * Constructor for error message
		 * @param ts timestamp
		 * @param em string of error message
		 */
		public clamMessage(String ts, String em){
			timeStamp = ts;
			errorMessage = em;
		}
		/**
		 * set time stamp
		 * @param ts timestamp
		 */
		public void setTimeStamp(String ts)
		{
			timeStamp = ts;
		}
		/**
		 * set error message
		 * @param em errormessage
		 */
		public void setErrorMessage(String em)
		{
			errorMessage = em;
		}
		
		/**
		 * gets time stamp
		 * @return timestamp
		 */
		public String getTimeStamp(){
			return timeStamp;
		}
		/**
		 * gets error message
		 * @return virus
		 */
		public String getErrorMessage(){
			return errorMessage;
		}
	}