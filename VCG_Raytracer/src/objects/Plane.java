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

    private Matrix4 translateM;
    private Matrix4 invM;

    public Plane(Vec3 _position, Material _material, Vec3 _normal) {
        super(_position, _material);
        this.normal = _normal.normalize();

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
        float f2 = this.normal.scalar(dir); //Pn * D

        float t0 = -(f1/f2);

        Matrix4 translateMStart = new Matrix4().translate(start);
        Vec3 intersectP = start.add(dir.multScalar(t0));           // Schnittpunkt von gesendeten Strahl mit der Kugel
        intersectP = translateM.multVec3(intersectP, true);

        Light l = Raytracer.lightList.get(0);           // aktuell betrachtete Lichtquelle
        Vec3 lPos = l.getPosition();                    // Position des aktuell betrachteten Lichts
        Vec3 lVector = lPos.sub(intersectP).normalize();// Vektor von Schnittpunkt zu Lichtquelle






        RgbColor RgbAtIntersect = material.getColor(l.getColor(), normal, lVector, dir);

        inters.distance = t0;
        inters.shape = this;
        inters.hit = (f2 > 0);
        inters.interSectionPoint = intersectP;
        inters.normal = normal;
        inters.rgb = RgbAtIntersect;


        return inters;
    }
}
