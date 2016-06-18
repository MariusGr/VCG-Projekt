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
    private float q;        //Abstand zum Ursprung

    private Matrix4 translateM;
    private Matrix4 invM;

    public Plane(Vec3 _position, Material _material, Vec3 _normal) {
        super(_position, _material);
        super.normal = _normal.normalize();

        this.translateM = new Matrix4().translate(super.position);
        this.invM = translateM.invert();
    }

    public Intersection intersect(Ray _ray) {

        Intersection inters = new Intersection();

        // Berechnung des Abstands zum Ursprung
        //Vec3 origin = new Vec3(0,0,0);
        //float a = this.normal.scalar(super.position); // q ist immer 0
        //this.q = this.normal.scalar(origin)-a;

        Vec3 start = _ray.getStartPoint();

        start = invM.multVec3(start, true);
        Vec3 dir = _ray.getDirection();


        float f1 = this.normal.scalar(start);//+q; //Pn * P0 +Q
        float f2 = this.normal.scalar(dir); //Pn * D // Skalarprodukt aus Normale und Ray-Richtung. Bei 0 sind beide senkrecht. Bei groesser 0 zeigen beide in die gleiche Richtung

        if (f2 >= 0) //wir koennen uns weitere Berechnungen sparen wenn PlaneNormale und Ray in die selbe Richtung oder senkrecht zeigen.
        {
            inters.distance = 0;
            inters.shape = this;
            inters.hit = false;
            return inters;
        }

        else {
            float t0 = -(f1 / f2);

            Matrix4 translateMStart = new Matrix4().translate(start);
            Vec3 intersectP = start.add(dir.multScalar(t0));           // Schnittpunkt von gesendeten Strahl mit der Plane
            intersectP = translateM.multVec3(intersectP, true);

            // Ausrechung der Farbwerte erfolgt erst in Intersection, wenn durch die Raytracer Punkt zeichnet
            inters.distance = t0;
            inters.shape = this;
            inters.inRay = _ray;
            inters.hit = true;
            inters.interSectionPoint = intersectP;

            return inters;
        }
    }
}
