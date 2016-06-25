package material;

import raytracer.Ray;
import utils.Vec3;
import utils.RgbColor;


/**
 * Created by Marius on 24.06.2016.
 */
public class Refraction extends RayHandling{
    public Refraction(float _d) {
        super(_d);
    }

    public Ray getOutRay(Vec3 direction, Vec3 normal, Vec3 startPoint) {
        //vectorIn ist der einfallende Strahl "ray", hier wird die Richrung des reflektierten Strahls berechnent:
        Vec3 vectorOut = direction.multScalar(-1);  //-R (R = einfallender Strahl)
        float skalarNI = normal.scalar(vectorOut) * 2;          //N*(-R)*2
        Vec3 zweiSkalarNIN = normal.multScalar(skalarNI);    //N*(N*(-R)*2)
        Vec3 refDirection = zweiSkalarNIN.sub(vectorOut);          //N*(N*(-R)*2)-Rref (Rref = reflektierter, ausfallender Strahl)
        return new Ray(startPoint, refDirection, 200);  //Reflektionstrahl: Strahl, der vom Trefferpunkt in berechnete Richtung geht
    }
}
