package material;

import objects.Shape;
import raytracer.Intersection;
import raytracer.Ray;
import utils.Vec3;
import utils.RgbColor;


/**
 * Created by Marius on 24.06.2016.
 */
public class Refraction extends RayHandling{
    // Jedes Material bricht Licht anders, repäsentiert durch die folgenden Brechungzahlen:
    public static float mCoffGlass = 1.7f;
    public static float mCoffDiamond = 2.4f;
    public static float mCoffWater = 1.33f;
    private float materialCoefficient;  // Brechzahl des instanziierten Materials wird hier gespeichert
    private Shape shape;

    public Refraction(float _d, float _mCoff) {
        super(_d);
        materialCoefficient = _mCoff;
    }

    public Ray getOutRay(Vec3 direction, Vec3 normal, Vec3 startPoint) {
        Vec3 outDirection = breakRayDirection(direction, normal, materialCoefficient);
        Ray tempRay = new Ray(startPoint, outDirection, 200);  //Reflektionstrahl: Strahl, der gebrochen durchs Innere der Kuegel geht
        Intersection tempInters = shape.intersect(tempRay); // Schnitttest mit Objelt, durch den er verläuft. Liegt ein Teil eines anderen Objektes darin, wird dieser ignoriert /TODO: Wenn wichtig, hier auch im refraktierenden Objekt befindliche Objekte berücksichtigen
        tempInters.normal = shape.getNormal(tempInters.interSectionPoint);

        outDirection = breakRayDirection(outDirection, tempInters.normal.negate(), materialCoefficient/1f);

        return new Ray(tempInters.interSectionPoint, outDirection, 200);  //Reflektionstrahl: Strahl, der vom Trefferpunkt in berechnete Richtung geht
    }

    private  Vec3 breakRayDirection(Vec3 direction, Vec3 normal, float _coff) {
        Vec3 inDirection = direction.negate();  //-R (R = einfallender Strahl)
        float nVScalar = normal.scalar(inDirection);
        float sqrt = 1-_coff*_coff*(1-nVScalar*nVScalar);
        if (sqrt < 0) sqrt = 0;
        float tempF = nVScalar*_coff-(float)Math.sqrt(sqrt);
        Vec3 outDirection = normal.multScalar(tempF).sub(inDirection.multScalar(_coff));
        return outDirection;
    }

    public void setShape(Shape _s) {
        this.shape = _s;
    }
}
