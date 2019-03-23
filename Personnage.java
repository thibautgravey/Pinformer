import java.awt.*;

public abstract class Personnage extends Entite {

    protected int vie;
    protected double vitesse;
    protected double depX;
    protected double depY;
    protected Jeu jeu;
    protected final double GRAVITE = 2;
    protected final double FROTTEMENTS = 1;
    protected final int VMAX = 25;
    protected boolean glissade = false;
    protected boolean falling = true;
    protected boolean jumping = true;

    public Personnage(Jeu jeu, int id, int x, int y, int largeur, int hauteur, int vie, double vitesse){
        super(jeu,id, x, y, largeur, hauteur);
        this.vie=vie;
        this.vitesse=vitesse;
        this.depX=0;
        this.depY=0;
        this.jeu=jeu;
    }

    public void action(Personnage p, String s){}

    public void deplacement(){
        this.deplacementX();
        this.deplacementY();
    }

    public void chute(){
        if(falling){
            this.depY+=GRAVITE;
        }
    }

    public void frottements(){
        if(glissade && this.depX>0){
            if((this.depX-FROTTEMENTS)>0){
                this.depX-=FROTTEMENTS;
            } else {
                this.depX=0;
            }
        } else if (glissade && this.depX<0){
            if((this.depX+FROTTEMENTS)<0){
                this.depX+=FROTTEMENTS;
            } else {
                this.depX=0;
            }
        }
    }

    public void saut(double hauteurSaut){
        jumping = true;
        this.depY-=hauteurSaut;
    }

    public void deplacementX() {
        mapCollisionX();
        blocCollisionX();
        objetCollisionX();
    }

    public void deplacementY(){
        mapCollisionY();
        blocCollisionY();
        objetCollisionY();
    }

    public boolean mapCollisionY(){
        int testY = 0;
        if(this.depY>0) { //Déplacement vers le bas
            testY = (int) (y + hauteur + depY);
            if (testY  > 720) {
                //ENDGAME
                depY=0;
                super.y += 720-y-hauteur;
                jumping=false;
                jeu.finish();
                return true;
            }
        }
        return false;
    }

    public boolean mapCollisionX(){
        int testX = 0;
        if (this.depX > 0){ //Déplacement droite
            testX = (int) (x + largeur + depX);
            if(testX>1280){
                this.depX=0;
                super.x += 1280-x-largeur;
                glissade = false;
                return true;
            }
        } else if (this.depX < 0) { //Déplacement gauche
            testX = (int)(x+depX);
            if(testX<0){
                this.depX = 0;
                super.x += -x;
                glissade = false;
                return true;
            }
        }
        return false;
    }

    public boolean objetCollisionX() {
        int testX = 0;
        if (this.depX > 0){ //Déplacement droite
            testX = (int) (x + largeur + depX);
            if(this.jeu.getMonde().objetDetectionX(testX,y,hauteur, this)){
                return true;
            }
        } else if (this.depX < 0) { //Déplacement gauche
            testX = (int)(x+depX);
            if(this.jeu.getMonde().objetDetectionX(testX,y,hauteur,this)){
                return true;
            }
        }
        return false;
    }

    public boolean objetCollisionY(){
        int testY = 0;
        double dY = depY;
        if(this.depY>0){ //Déplacement vers le bas
            if(dY>VMAX) dY=VMAX;
            testY=(int)(y+hauteur+dY);
            if(this.jeu.getMonde().objetDetectionY(testY,x,largeur,this)){
                return true;
            }
        } else if(this.depY<0){ //Déplacement vers le haut
            if(dY<-VMAX) dY=-VMAX;
            testY=(int)(y+dY);
            if(this.jeu.getMonde().objetDetectionY(testY,x,largeur,this)){
                return true;
            }
        }
        return false;
    }

    public boolean blocCollisionX(){
        int testX=0;
        if(this.depX>0){ //Déplacement droite
            testX = (int) (x+largeur+depX);
            if(!this.jeu.getMonde().blocDetectionX(testX, y, hauteur)){ //pas de collision
                super.x+=(int)depX;
                glissade = true;
                return false;
            } else { //collision
                this.depX=0;
                super.x += this.jeu.getMonde().c.x-x-largeur;
                glissade = false;
                return true;
            }
        } else if(this.depX<0){ //Déplacement gauche
            testX = (int)(x+depX);
            if(!this.jeu.getMonde().blocDetectionX(testX, y, hauteur)){
                super.x+=(int)depX;
                glissade=true;
                return false;
            } else { //collision
                this.depX = 0;
                super.x += (this.jeu.getMonde().c.x+this.jeu.getMonde().c.largeur)-x;
                glissade = false;
                return true;
            }
        }
        return false;
    }

    public boolean blocCollisionY(){
        int testY=0;
        if(depY>0) { //Déplacement vers le bas
            double dY = depY;
            if(dY>VMAX) dY=VMAX;
            testY = (int) (y + hauteur + dY);
            if (!this.jeu.getMonde().blocDetectionY(testY, x, largeur)) { //Pas de collision
                super.y += (int) this.depY;
                return false;
            } else { //collision --> On avance au max
                this.depY=0;
                super.y += this.jeu.getMonde().c.y-y-hauteur;
                jumping=false;

                int id = this.jeu.getMonde().c.getId();
                int vit = this.jeu.getMonde().c.getVitesse();
                if(id==5 || id==6 || id==7 || id==8){
                    super.x+=vit;
                }

                return true;
            }
        } else if(this.depY<0){ //Déplacement vers le haut
            double dY = depY;
            if(dY<-VMAX) dY=-VMAX;
            testY = (int) (y + dY);
            if (!this.jeu.getMonde().blocDetectionY(testY, x, largeur)) { //Pas de collision
                super.y += (int) this.depY;
                return false;
            } else { //collision --> On avance au max
                this.depY = 0;
                super.y += (this.jeu.getMonde().c.y+this.jeu.getMonde().c.hauteur)-y;
                return true;
            }
        }
        return false;
    }

    public void setVie(int v){
        this.vie=v;
    }

    public int getVie(){
        return this.vie;
    }

    public void setVitesse(double vitesse){
        this.vitesse=vitesse;
    }

    public double getVitesse(){
        return this.vitesse;
    }
    
    public int getLargeur() {
		return this.largeur;
	}
	
	public int getHauteur() {
		return this.hauteur;
	}
}
