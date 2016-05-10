package objects;

import light.Light;
import material.Material;
import raytracer.Ray;
import raytracer.Raytracer;
import utils.Log;
import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Sphere extends Shape {
    private float radius;

    public Sphere(float _radius, Vec3 _pos, Material _material) {
        super(_pos, _material);
        this.radius = _radius;
    }

    public float[]  intersect(Ray _ray) {
        Vec3 start = _ray.getStartPoint();
        Vec3 dir = _ray.getDirection();
        float b = 2*(start.x*dir.x+start.y*dir.y+start.z*dir.z);
        float c = start.x*start.x+start.y*start.y+start.z*start.z-this.radius*this.radius;

        float t0 = (float) ((   -b - Math.sqrt(b*b-4*c)   )/2);
        float t1 = (float) ((   -b + Math.sqrt(b*b-4*c)   )/2);
        t0=t1;
        float d = b*b-4*c;

        Vec3 intersectP = dir.multScalar(t0);           // Schnittpunkt von gesendeten Strahl mit der Kugel
        Vec3 normal = intersectP.sub(super.position).normalize();   // Normale berechnen vom Mittelpunkt der Kugel zum Schnittpunkt

        Light l = Raytracer.lightList.get(0);           // aktuell betrachtete Lichtquelle
        Vec3 lPos = l.getPosition();                    // Position des aktuell betrachteten Lichts
        Vec3 lVector = lPos.sub(intersectP).normalize();            // Vektor von Schnittpunkt zu Lichtquelle

        RgbColor RgbAtIntersect = material.getColor(l.getColor(), normal, lVector, dir);

        float[] out = new float[4];
        out[0] = d;
        out[1] = RgbAtIntersect.red();
        out[2] = RgbAtIntersect.green();
        out[3] = RgbAtIntersect.blue();

        if(d >= 0) {
            Log.print(this, d+":  "+intersectP);
        }

        return out;
    }
}
