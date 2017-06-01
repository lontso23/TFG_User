package views;


	import java.awt.Graphics;
	import java.awt.Image;
	import java.net.URL;
	 
	import javax.swing.ImageIcon;
	import javax.swing.JPanel;

	public class Fondo extends JPanel {

		private Image bgImage;
		 
		 public Fondo() {
		  super();
		  this.setOpaque(false);
		 }
		 
		 /**
		  * Lo utilizaremos para establecerle su imagen de fondo.
		  * @param bgImage La imagen en cuestion
		  */
		 public void setBackgroundImage(Image bgImage) {
		  this.bgImage = bgImage;
		 }
		 
		 /**
		  * Para recuperar una imagen de un archivo...
		  * @param path Ruta de la imagen relativa al proyecto
		  * @return una imagen
		  */
		 public ImageIcon createImage(String path) {
		  URL imgURL = getClass().getResource(path);
		     if (imgURL != null) {
		         return new ImageIcon(imgURL);
		     } else {
		         System.err.println("Couldn't find file: " + path);
		         return null;
		     }
		 }
		 
		 @Override
		 public void paint(Graphics g) {

		  if(bgImage != null) {
		   g.drawImage(bgImage, 0, 0, 1000, 650, null);
		  }

		  super.paint(g);
		 
		 }
	}



