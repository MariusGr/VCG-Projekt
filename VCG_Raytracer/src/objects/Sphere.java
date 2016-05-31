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
public class Sphere extends Shape {
    private float radius;
    private Matrix4 translateM;
    private Matrix4 invM;

    public Sphere(float _radius, Vec3 _pos, Material _material) {
        super(_pos, _material);
        this.radius = _radius;
        this.translateM = new Matrix4().translate(super.position);
        this.invM = translateM.invert();
    }

    public Intersection  intersect(Ray _ray) {
        Vec3 start = _ray.getStartPoint();

        start = invM.multVec3(start, false);

        Vec3 dir = _ray.getDirection();                             // Richtung kann dieselbe bleiben
        float b = 2*(start.x*dir.x+start.y*dir.y+start.z*dir.z);
        float c = start.x*start.x+start.y*start.y+start.z*start.z-this.radius*this.radius;

        float t0 = (float) ((   -b - Math.sqrt(b*b-4*c)   )/2);
        float t1 = (float) ((   -b + Math.sqrt(b*b-4*c)   )/2);

        Intersection inters = new Intersection();

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

            inters.shape = this;
            inters.hit = (d >= 0);
            inters.interSectionPoint = intersectP;
            inters.normal = normal;
            inters.rgb = RgbAtIntersect;

        }

        return inters;
    }
}
