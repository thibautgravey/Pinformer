import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
public class MonstreDistance extends Monstre {
	
    private int compteur = 0;
    private int intervalleTire;
    private int positionInitialeX;
    private int positionFinaleX;
    private int vitesse;
    boolean sens = false;
    private int xb;
    private int yb;
    private int choixB;
    private int enl;
    private int venl;
    Monde monde;
	
	public MonstreDistance(Jeu jeu, int intervalleTire, int x, int y, int largeur, int hauteur, int vie, int vieE, int vitesse, int vitesseR, Color couleur, int positionFinaleX, int choixB, Monde monde) {
		super(jeu, x, y, largeur, hauteur, vieE, vie, vitesseR, couleur);
        this.intervalleTire=intervalleTire;
        this.vitesse=vitesse;
        this.positionFinaleX=positionFinaleX;
        this.positionInitialeX=this.x;
        this.choixB=choixB;
        this.enl = vieE;
        this.venl = vitesseR;
        this.monde=monde;
        
	}
	
	
    public void tick(){
        
        this.deplacement();
   
        if (this.compt()) {
			Balle balle1 = this.creationBalle();
            monde.lesBalles.add(balle1);
        }
        
        
    }
    
    public Balle creationBalle() {
        //System.out.println("a creee'");
        xb =this.x;
        yb =  this.y;
        Balle balle = new Balle(jeu, xb, yb, 30, 30, this.sens, choixB, enl, venl);
        return balle;
    }
    

    public void aff(Graphics g){
		//g.setColor(this.couleur);
		//g.fillOval(super.x,super.y,super.largeur,super.hauteur);
        g.drawImage(super.jeu.textures.monstre_contact,x,y,null);
	}
		
		
	 // condition de déplacement de balle, voir si elle touche
	 // méthode qui renvoie si la balle a touché de le personnage ou pas
		
 
	public void aTouche(Joueur j){
		//if (this.balleAtouche(j) == true) {
			this.perdVie(j);
			this.perdVitesse(j);
		
	}
    
    public void monstreDistanceEstTouche(Joueur j) {
        // du coup ici pas besoin du fait que c'est l'un ou l'autre qui attaque, parce que si le joueur et le monstre se touche ca veut
        // direct dire que le joueur attaque le monstre, vu que ce monstre ne peut pas se battre en corps à corps
        // et ses balles iront trop loin pour toucher le personnage auquel il est collé
        if ((this.getX() == j.getX())&&(this.getY() == j.getY())) {
            this.monstrePerdvie();
        }
    }
    
      
 
    public void deplacement () {
       //à faire en boucle
       
        if(!sens){
            super.x+=vitesse;
        }
        
        if(super.x>=positionFinaleX){
			sens=true;
		}

        if(sens){
            super.x-=vitesse;
        }
        
        if(super.x<=positionInitialeX){
			sens=false;
		}
		
    }
    
    public boolean compt() {
        compteur = compteur +1;
        //System.out.println(compteur);
        
        if (compteur > intervalleTire+200 ) {
        compteur = 0;
        return true;
        } else{
        return false;
    }
    }
    
    public boolean renvoiSens() {
        return sens;
    }
}

