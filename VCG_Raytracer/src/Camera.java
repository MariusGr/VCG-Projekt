/**
 * Created by PraktikumCG on 19.04.2016.
 */

import utils.Vec2;
import utils.Vec3;

public class Camera extends SceneObject{
    private Vec3 cameraPosition;
    private Vec3 lookAt;
    private Vec3 upVector;
    private float focalLength; //Brennweite
    private float viewAngle;
    private float ratio = (float) Main.IMAGE_WIDTH/(float) Main.IMAGE_HEIGHT;
    private float viewPlaneH; // Höhe Viewplane
    private float viewPlaneW; // Breite Viewplane
    private Vec3 vVector; //V-Vektor -> Entfernung Kamera zur Viewplane

    public Camera(Vec3 _pos, Vec3 _locA, Vec3 _up, float _localL, float _v) {
        this.cameraPosition = _pos; // Kamera Position im Globalen Koordinatensystem
        this.lookAt = _locA.normalize(); //Lookat Vektor
        this.upVector = _up;  //upVektor
        this.focalLength = _localL;
        this.viewAngle = _v; //VielAnlge Kamera -> Öffnungswinkel der Kamera

        this.viewPlaneH = (float) (2*Math.tan(viewAngle/2)); // Berechnung Höhe ViewPlane
        this.viewPlaneW = ratio*viewPlaneH;                 // Wird noch gebraucht?

        focalLength = (viewPlaneH/2)/((float) Math.tan(viewAngle/2));
        vVector = lookAt.multScalar(focalLength);
    }

    public void getPixelColor(Vec2 _p) {
        Vec2 pN = pNorm(_p);

        //Vec3
    }

    public Vec3 calculateDestination() {
        return null;
    }

    private Vec2 pNorm(Vec2 _v2) {
        float x = _v2.x;
        float y = _v2.y;
        float newX = pNormCalculate(x, (float) Main.IMAGE_WIDTH);
        float newY = pNormCalculate(y, (float) Main.IMAGE_HEIGHT);

        return new Vec2(newX, newY);
    }

    private float pNormCalculate(float _f, float _max) {
        return (2*((_f+0.5f)/_max)-1);
    }
}
