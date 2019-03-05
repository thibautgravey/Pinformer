import java.awt.*;

public abstract class Personnage extends Entite {

    protected int vie;
    protected int vitesse;
    protected int depX;
    protected int depY;

    public Personnage(int x, int y, int largeur, int hauteur, int vie, int vitesse){
        super(x, y, largeur, hauteur);
        this.vie=vie;
        this.vitesse=vitesse;
        this.depX=0;
        this.depY=0;
    }

    public void deplacement(){
        super.x+=this.depX;
        super.y+=this.depY;
    }
    
      public void setVie(int v){
        this.vie=v;
    }

    public int getVie(){
        return this.vie;
    }
    
    
      public void setVitesse(int vitesse){
        this.vitesse=vitesse;
    }

    public int getVitesse(){
        return this.vitesse;
    }
}
