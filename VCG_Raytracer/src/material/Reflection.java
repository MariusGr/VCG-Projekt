package material;

import raytracer.Ray;
import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 21.06.2016.
 */
public class Reflection extends RayHandling{
    public Reflection(float _d) {
        super(_d);
    }

    public Ray getOutRay(Vec3 direction, Vec3 normal, Vec3 startPoint) {
        //"direction" ist die Richtung des einfallenden Strahls, hier wird die Richtung "refDirection" des reflektierten Strahls berechnent:
        Vec3 inDirection = direction.negate();  //-R (R = einfallender Strahl der Kamera bzw. refliektierter, ausfallender Lichstrahl)
        float skalarNI = normal.scalar(inDirection) * 2;          //N*(-R)*2
        Vec3 zweiSkalarNIN = normal.multScalar(skalarNI);    //N*(N*(-R)*2)
        Vec3 refDirection = zweiSkalarNIN.sub(inDirection);          //N*(N*(-R)*2)-Rref (Rref = reflektierter, ausfallender Strahl der Kamera bzw. einfallender STrahl des Lichts)
        return new Ray(startPoint, refDirection, 200);  //Reflektionstrahl: Strahl, der vom Trefferpunkt in berechnete Richtung geht (relektierter Strahl der Kamera)
    }
}
