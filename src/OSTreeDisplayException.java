
/**
 * An exception thrown by the OSTreeDisplay class.
 *
 * Date: May 19, 2012
 *
 * @author Ran Eshel.
 */
public class OSTreeDisplayException extends Exception {

   /**
    * Constructor with no parameters.
    */
   public OSTreeDisplayException () {
      super();
   }

   /**
    * Constructor with message.
    *
    * @param message a detailed description of the exception.
    */
   public OSTreeDisplayException (String message) {
      super (message);
   }
}
