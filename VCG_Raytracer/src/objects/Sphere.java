package objects;

import light.Light;
import material.Material;
import raytracer.Ray;
import raytracer.Raytracer;
import utils.Matrix4;
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
        Matrix4 translateM = new Matrix4().translate(super.position);
        Matrix4 invM = translateM.invert();

        start = invM.multVec3(start, false);

        Vec3 dir = _ray.getDirection();                             // Richtung kann dieselbe bleiben
        float b = 2*(start.x*dir.x+start.y*dir.y+start.z*dir.z);
        float c = start.x*start.x+start.y*start.y+start.z*start.z-this.radius*this.radius;

        float t0 = (float) ((   -b - Math.sqrt(b*b-4*c)   )/2);
        float t1 = (float) ((   -b + Math.sqrt(b*b-4*c)   )/2);

        float[] out = new float[7];
        out[0] = -1;    // Wenn der Block (s. unten) nicht ausgeführt wird, weil die Kugel hinter der Kamera liegt, wird dieser Wert dazu führen, dass sie nicht gezeichznet wird
        out[1] = 0;
        out[2] = 0;
        out[3] = 0;
        out[4] = 0;
        out[5] = 0;
        out[6] = 0;

        if(t0 >= 0 || t1 >= 0) {    //TODO: Performance prüfen
            if(t1 < t0 && t1 >= 0) t0 = t1; // Welches t ist näher an der Kamera?

            float d = b * b - 4 * c;

            Vec3 intersectP = start.add(dir.multScalar(t0));           // Schnittpunkt von gesendeten Strahl mit der Kugel
            intersectP = translateM.multVec3(intersectP, false);
            Vec3 normal = intersectP.sub(super.position).normalize();   // Normale berechnen vom Mittelpunkt der Kugel zum Schnittpunkt

            Light l = Raytracer.lightList.get(0);           // aktuell betrachtete Lichtquelle
            Vec3 lPos = l.getPosition();                    // Position des aktuell betrachteten Lichts
            Vec3 lVector = lPos.sub(intersectP).normalize();            // Vektor von Schnittpunkt zu Lichtquelle

            RgbColor RgbAtIntersect = material.getColor(l.getColor(), normal, lVector, dir);

            out[0] = d;
            out[1] = RgbAtIntersect.red()+0.2f;
            out[2] = RgbAtIntersect.green()+0.2f;
            out[3] = RgbAtIntersect.blue()+0.2f;
            out[4] = intersectP.x;
            out[5] = intersectP.y;
            out[6] = intersectP.z;

        }

        return out;
    }
}
