package objects;

import light.Light;
import material.Material;
import material.RayHandling;
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
    private float epsilon = Float.MIN_VALUE;

    public Sphere(float _radius, Vec3 _pos, Material _material, RayHandling _rh) {
        super(_pos, _material, _rh);
        this.radius = _radius;
        this.translateM = new Matrix4().translate(super.position);
        this.invM = translateM.invert();
        super.setRayHandlingShape(this);
    }

    public Intersection  intersect(Ray _ray) {
        Vec3 start = _ray.getStartPoint();

        start = invM.multVec3(start, true);

        Vec3 dir = _ray.getDirection();                             // Richtung kann dieselbe bleiben
        float b = 2f*(start.x*dir.x+start.y*dir.y+start.z*dir.z);
        float c = start.x*start.x+start.y*start.y+start.z*start.z-this.radius*this.radius;

        Intersection inters = new Intersection();
        float d = b * b - 4f * c;

        if(d < 0) return inters;

        float t0 =  ((   -b - (float) Math.sqrt(d)   )/2f);
        float t1 =  ((   -b +(float)  Math.sqrt(d)   )/2f);

        if(t0 >= 0 || t1 >= 0) {    //TODO: Performance prüfen******************************
            if ((t1 < t0 && t1 >= 0) || t0 <= epsilon) t0 = t1; // Welches t ist näher an der Kamera?

            Matrix4 translateMStart = new Matrix4().translate(start);
            Vec3 intersectP = start.add(dir.multScalar(t0));           // Schnittpunkt von gesendeten Strahl mit der Kugel
            intersectP = translateM.multVec3(intersectP, true);

            // Ausrechung der Farbwerte erfolgt erst in Intersection, wenn durch die Raytracer Punkt zeichnet
            inters.distance = t0;
            inters.shape = this;
            inters.inRay = _ray;
            inters.hit = (d >= 0);
            inters.interSectionPoint = intersectP;
        }
        return inters;
    }

    public Vec3 getNormal(Vec3 pointOnSurface) {
        Vec3 normal = pointOnSurface.sub(super.position).normalize();   // Normale geht vom Ursprung der Kugel durch den Punkt auf ihrer Oberfläche
        return normal;
    }
}
