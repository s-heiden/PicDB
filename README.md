# PicDB
a simple app to organize your pictures
## Doku

### Benutzerhandbuch
![UI Picture](https://picload.org/image/riwpodpa/picexplain.png)
#### Menu Bar (1):
Files:
  * Save ITPC info: Used to modify the IPTC info of the current picture.
  * Quit: will close the program

Edit
  * Photographers: Opens a dialog to add, edit or delete photographers.

Options
  * Generate image report: Will generate a report of the selectet image with all information.
  * Generate tags report: Will generate a report of all tags and the number of images per tag.

Help
  * Help: Opens a help dialog.
  * About: Opens an about dialog.

#### Search Bar (2):
Enter your search string here. This is a basic search.

#### Image View (3):
Shows the selected image in the image viewer (upper-left corner).

#### Info View (4):
Shows all IPTC and EXIF information of the selected image. It also allows to edit the ITPC info.

#### Image Navigation Pane (5):
Shows all saved images in a slider and let select one by clicking at it.
  
### Lösungsbeschreibung
Supported by the given structured interfaces we implemented a small image viewing app which allows creating image and tag pdfs as well as editing IPTC info.
The main theme of this project was the application of the MVVM model. At first, it was a challenge to understand the deeper connections of this models and we had trash more than one version of the project before we understood the inner workings of the MVVM pattern.

### Worauf bin ich stolz?
We are proud about the picture navigation pane. We implemented an observer pattern intuitively without ever having learned about it. Only some weeks later we heard about it in the course Software Paradigms.

### Was würde ich anders machen?
We did not start soon enough and had no clear boundaries as to which part of the interface is whose responsibility in the project team. In the future, we want to make time to sit together and to define clearly who is responsible for which part of the interface.


