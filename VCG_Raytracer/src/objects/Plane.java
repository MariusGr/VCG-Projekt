package objects;

import light.Light;
import material.Material;
import raytracer.Ray;
import raytracer.Raytracer;
import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Plane extends Shape {
    private Vec3 normal;
    private float q;        //Abstand zum Ursprung

    public Plane(Vec3 _position, Material _material, Vec3 _normal) {
        super(_position, _material);
        this.normal = _normal.normalize();

        // Berechnung des Abstands zum Ursprung
        Vec3 origin = new Vec3(0,0,0);
        float a = this.normal.scalar(super.position);
        this.q = this.normal.scalar(origin)-a;
    }

    @Override
    public float[]  intersect(Ray _ray) {
        Vec3 start = _ray.getStartPoint().sub(super.position);
        Vec3 dir = _ray.getDirection();

        float f1 = this.normal.scalar(start)+q;
        float f2 = this.normal.scalar(dir);

        float t0 = -(f1/f2);

        Vec3 intersectP = dir.multScalar(t0).add(super.position);           // Schnittpunkt von gesendeten Strahl mit der Kugel
        Vec3 normal = intersectP.sub(super.position).normalize();   // Normale berechnen vom Mittelpunkt der Kugel zum Schnittpunkt

        float distance = intersectP.sub(_ray.getStartPoint()).length();

        Light l = Raytracer.lightList.get(0);           // aktuell betrachtete Lichtquelle
        Vec3 lPos = l.getPosition();                    // Position des aktuell betrachteten Lichts
        Vec3 lVector = lPos.sub(intersectP).normalize();            // Vektor von Schnittpunkt zu Lichtquelle

        RgbColor RgbAtIntersect = material.getColor(l.getColor(), normal, lVector, dir);

        float[] out = new float[8];
        out[0] = 1;
        out[1] = RgbAtIntersect.red();
        out[2] = RgbAtIntersect.green();
        out[3] = RgbAtIntersect.blue();
        out[4] = intersectP.x;
        out[5] = intersectP.y;
        out[6] = intersectP.z;
        out[7] = distance;

        /*if(d >= 0) {
            Log.print(this, d+":  "+intersectP);
        }*/

        return out;
    }
}
