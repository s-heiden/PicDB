package BIF.SWE2.interfaces.controllers;

/**
 * Interface that needs to be implemented by classes that use a PictureNavigationControl.
 * 
 * Needed to be notified when and which navigation pictures are clicked.
 */
public interface Notifiable {
    
    public void notifiedOf(int value);
    
}
