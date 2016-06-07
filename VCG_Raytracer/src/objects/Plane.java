package objects;

import light.Light;
import material.Material;
import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Raytracer;
import utils.Matrix4;
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


    }



    public Intersection intersect(Ray _ray) {

        Intersection inters = new Intersection();

        // Berechnung des Abstands zum Ursprung
        Vec3 origin = new Vec3(0,0,0);
        float a = this.normal.scalar(super.position);
        this.q = this.normal.scalar(origin)-a;

        Vec3 start = _ray.getStartPoint().sub(super.position);
        Vec3 dir = _ray.getDirection();

        float f1 = this.normal.scalar(start)+q; //Pn * P0 +Q
        float f2 = this.normal.scalar(dir); //Pn * D

        float t0 = -(f1/f2);

        Vec3 intersectP = dir.multScalar(t0).add(super.position);           // Schnittpunkt von gesendeten Strahl mit der Kugel
        Vec3 normal = intersectP.sub(super.position).normalize();   // Normale berechnen vom Mittelpunkt der Kugel zum Schnittpunkt

        Light l = Raytracer.lightList.get(0);           // aktuell betrachtete Lichtquelle
        Vec3 lPos = l.getPosition();                    // Position des aktuell betrachteten Lichts
        Vec3 lVector = lPos.sub(intersectP).normalize();            // Vektor von Schnittpunkt zu Lichtquelle

        RgbColor RgbAtIntersect = material.getColor(l.getColor(), normal, lVector, dir);

        inters.shape = this;
        inters.hit = (f2 != 0);
        inters.interSectionPoint = intersectP;
        inters.normal = normal;
        inters.rgb = RgbAtIntersect;


       /* Vec3 start = _ray.getStartPoint();

        start = invM.multVec3(start, true);

        Vec3 dir = _ray.getDirection();                             // Richtung kann dieselbe bleiben
        float b = 2*(start.x*dir.x+start.y*dir.y+start.z*dir.z);
        float c = start.x*start.x+start.y*start.y+start.z*start.z-this.radius*this.radius;

        Intersection inters = new Intersection();
        float d = b * b - 4 * c;

        if(d < 0) return inters;

        float t0 = (float) ((   -b - Math.sqrt(d)   )/2f);
        float t1 = (float) ((   -b + Math.sqrt(d)   )/2f);

        if(t0 >= 0 || t1 >= 0) {    //TODO: Performance prüfen
            if(t1 < t0 && t1 >= 0) t0 = t1; // Welches t ist näher an der Kamera?

            Matrix4 translateMStart = new Matrix4().translate(start);
            Vec3 intersectP = start.add(dir.multScalar(t0));           // Schnittpunkt von gesendeten Strahl mit der Kugel
            intersectP = translateM.multVec3(intersectP, true);
            Vec3 normal = intersectP.sub(super.position).normalize();   // Normale berechnen vom Mittelpunkt der Kugel zum Schnittpunkt

            Light l = Raytracer.lightList.get(0);           // aktuell betrachtete Lichtquelle
            Vec3 lPos = l.getPosition();                    // Position des aktuell betrachteten Lichts
            Vec3 lVector = lPos.sub(intersectP).normalize();            // Vektor von Schnittpunkt zu Lichtquelle

            RgbColor RgbAtIntersect = material.getColor(l.getColor(), normal, lVector, dir);

            inters.shape = this;
            inters.hit = (d >= 0);
            inters.interSectionPoint = intersectP;
            inters.normal = normal;
            inters.rgb = RgbAtIntersect;

        }*/

        return inters;
    }


   /* public float[]  intersect(Ray _ray) {
        Vec3 start = _ray.getStartPoint().sub(super.position);
        Vec3 dir = _ray.getDirection();

        float f1 = this.normal.scalar(start)+q;
        float f2 = this.normal.scalar(dir);

        float t0 = -(f1/f2);

        Vec3 intersectP = dir.multScalar(t0).add(super.position);           // Schnittpunkt von gesendeten Strahl mit der Kugel
        Vec3 normal = intersectP.sub(super.position).normalize();   // Normale berechnen vom Mittelpunkt der Kugel zum Schnittpunkt

        Light l = Raytracer.lightList.get(0);           // aktuell betrachtete Lichtquelle
        Vec3 lPos = l.getPosition();                    // Position des aktuell betrachteten Lichts
        Vec3 lVector = lPos.sub(intersectP).normalize();            // Vektor von Schnittpunkt zu Lichtquelle

        RgbColor RgbAtIntersect = material.getColor(l.getColor(), normal, lVector, dir);

        float[] out = new float[4];
        out[0] = 1;
        out[1] = RgbAtIntersect.red();
        out[2] = RgbAtIntersect.green();
        out[3] = RgbAtIntersect.blue();
        out[4] = intersectP.x;
        out[5] = intersectP.y;
        out[6] = intersectP.z;

        /*if(d >= 0) {
            Log.print(this, d+":  "+intersectP);
        }

        return out;
    }*/
}
