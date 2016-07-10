package scene; /**
 * Created by PraktikumCG on 19.04.2016.
 */

import objects.SceneObject;
import utils.Vec2;
import utils.Vec3;

public class Camera extends SceneObject {
    private Vec3 lookAt;
    private Vec3 userUpVector;
    private float focalLength; //Brennweite
    private float alpha;
    private float ratio = (float) Main.IMAGE_WIDTH/(float) Main.IMAGE_HEIGHT;
    private float viewPlaneH; // Höhe scene.Viewplane
    private float viewPlaneW; // Breite scene.Viewplane
    private Vec3 vVector; //V-Vektor -> Entfernung Kamera zur scene.Viewplane
    private Vec3 uVector;
    private Vec3 sVector;

    public Camera(Vec3 _pos, Vec3 _lokA, Vec3 _up, float _alpha, Vec3 _centerOI) {
        super(_pos); // Kamera Position im Globalen Koordinatensystem
        this.lookAt = _lokA.normalize(); //Lookat Vektor
        this.userUpVector = _up;  //UserupVektor
        this.alpha = (float) Math.toRadians(_alpha); //ViewAngle Kamera -> Öffnungswinkel der Kamera
        this.vVector = _centerOI.sub(_pos);
        this.sVector = vVector.cross(_up).normalize();
        this.uVector = sVector.cross(vVector).normalize(); //uVektor

        this.viewPlaneH = (float) (2f*Math.tan(alpha/2f)); // Berechnung Höhe ViewPlane
        this.viewPlaneW = ratio*viewPlaneH;                 // TODO: Wird noch gebraucht?***************

        //this.focalLength = (viewPlaneH/2)/((float) Math.tan(alpha/2));   // h = viewPlaneH, alpha = Öffnungswinkel, Ergebnis: Länge einer Dreiecksseite bzw. Länge von viewVector
        this.focalLength = (float) ((viewPlaneW/2f)/Math.tan(alpha/2f));    // Braucht man noch? ****************************************
        //this.vVector = lookAt.multScalar(focalLength);       // Skalarprodukt aus lookAt und Länge von v ergibt ViewVector, damit sind view und up Vector gegeben
    }


    private Vec3 viewPlaneCenter() {
        return super.position.add(vVector.multScalar(focalLength));
    }

    public Vec3 calculateDestination(int _x, int _y) {
        Vec2 pN = pNorm(new Vec2(_x, _y));
        Vec3 horizontal = sVector.multScalar(pN.x*viewPlaneW);
        Vec3 vertical = uVector.multScalar(-pN.y*viewPlaneH);


        //Vec3 dest = new Vec3(vC.x+(xN*viewPlaneW/2), vC.y+(yN*viewPlaneH/2), vC.z);
        Vec3 dest = viewPlaneCenter().add(horizontal.add(vertical));

        return dest;
    }

    /**
     * Normiert einen zweidimensionalen Vektor auf die scene.Viewplane mit einem Wertebereich zwisachen -1 und +1 für x bzw y.
     * @param _v2 Position in px auf der attsächlichen Bildfläche
     * @return normierte Position auf der scene.Viewplane
     */
    private Vec2 pNorm(Vec2 _v2) {  //LookUpTable **********************************************
        float x = _v2.x;
        float y = _v2.y;
        float newX = pNormCalculate(x, (float) Main.IMAGE_WIDTH);
        float newY = pNormCalculate(y, (float) Main.IMAGE_HEIGHT);

        return new Vec2(newX, newY);
    }

    /**
     * Hilfsfunktiojn für pNorm() mit der bekannten Formel aus der Vorlesung
     * @param _f x bzw. y Koordinate
     * @param _max Maximum der jeweiligen Koordinate, sprich Breite bzw. Höhe
     * @return normirter Koordinatenwert
     */
    private float pNormCalculate(float _f, float _max) {
        return (2*((_f+0.5f)/_max)-1);
    }

    public Vec3 getPosition() {
        return super.position;
    }

}
