package endlessRunner;

import javafx.geometry.Point3D;
import javafx.scene.Group;

public class Player extends Group {
    private Main.Side side;
    private double jumpTime;
    private Group model;

    private boolean squashing;
    private boolean falling;
    private double fallingSpeed;
    private double fallingInertia;

    /**
     * Zisti ci je hrac skrceny.
     * @return Je hrac skrceny?
     */
    public boolean isSquashing() {
        return squashing;
    }


    /**
     * Zisti na ktorej strane sa hrac nachadza.
     * @return Vrati enum na ktorej strane sa hrac nachadza
     */
    public Main.Side getSide() {
        return side;
    }

    Player(){
        side = Main.Side.MIDDLE;
        jumpTime = 0;
        squashing = true;
        falling = false;
        fallingSpeed = 5;
        model = new Group(Utils.loadModel("src/models/Scooter-alien.obj"));
        model.setTranslateY(-30);
        this.getChildren().addAll(model);
        this.setTranslateZ(0);
        this.setRotationAxis(new Point3D(0,1,0));
        this.setRotate(180);
        this.setTranslateY(0);
        this.setTranslateX(0);
        this.setScaleX(70);
        this.setScaleY(70);
        this.setScaleZ(70);




    }

    /**
     * Hrac zacne padat smerom dole.
     *
     * @param fallingSpeed Rychlost padania
     * @param fallingInertia Rychlost akou ide hrac dopredu pri padani
     */
    public void fall (double fallingSpeed, double fallingInertia){
        this.fallingSpeed = fallingSpeed;
        this.fallingInertia = fallingInertia;
        falling = true;
    }


    /**
     * Urobi z hraca palaciku, napr. ak narazi do steny
     */
    public void wallSquash(){
        this.setScaleZ(20);
        this.setTranslateZ(-20);

        this.setScaleX(120);
    }

    /**
     * Hrac sa skrci napr. pri podliezani
     */
    public void squash(){
        squashing = true;
        this.setScaleY(1);
        model.setTranslateY(0);

    }

    /**
     * Hrac sa vystrie pokial bol skrceny.
     */
    public void unsquash(){
        squashing = false;
        this.setScaleY(70);
        model.setTranslateY(-30);
    }

    /**
     * Hrac sa pohne dolava ak je to mozne.
     */
    public void moveLeft(){
        if(side == Main.Side.LEFT){
            return;
        }

        if(side == Main.Side.MIDDLE){
            side = Main.Side.LEFT;
        }

        if(side == Main.Side.RIGHT){
            side = Main.Side.MIDDLE;
        }


    }



    /**
     * Hrac sa pohne doprava ak je to mozne.
     */
    public void moveRight(){
        if(side == Main.Side.RIGHT){
            return;
        }

        if(side == Main.Side.MIDDLE){
            side = Main.Side.RIGHT;
        }

        if(side == Main.Side.LEFT){
            side = Main.Side.MIDDLE;
        }
    }



    /**
     * @return je hrac v skoku? (vo vzduchu pri skakani)
     */
    public boolean isJumping(){
        return jumpTime > 0;
    }


    /**
     * hrac vyskoci
     */
    public void jump(){
        if(jumpTime > 0){
            return;
        }
        jumpTime = 1.0;
    }


    /**
     * Update pohybu.
     */
    public void update(){
        if(jumpTime > 0){
            jumpTime -= 2.0/ Main.fps;
        }else{
            jumpTime = 0;
        }
        if(falling){
            this.setTranslateY(this.getTranslateY()+(1.0/ Main.fps)*fallingSpeed*100);
            this.setTranslateZ(this.getTranslateZ()+(1.0/ Main.fps)*fallingInertia*100);
            return;
        }
        this.setTranslateY(-Math.sin(Math.PI*jumpTime)*70);

        lerp(0.7);
    }

    private void lerp(double lerpSpeed){
        switch (side){
            case LEFT:
                this.setTranslateX(Utils.lerp(this.getTranslateX(), -100, lerpSpeed));
                break;
            case MIDDLE:
                this.setTranslateX(Utils.lerp(this.getTranslateX(), 0, lerpSpeed));
                break;
            case RIGHT:
                this.setTranslateX(Utils.lerp(this.getTranslateX(), 100, lerpSpeed));
                break;
        }

    }

}