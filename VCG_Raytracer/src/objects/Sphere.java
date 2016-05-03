package objects;

import material.Material;
import raytracer.Ray;
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

    public float  intersect(Ray _ray) {
        Vec3 start = _ray.getStartPoint();
        Vec3 dir = _ray.getDirection();
        float b = 2*(start.x*dir.x+start.y*dir.y+start.z*dir.z);
        float c = start.x*start.x+start.y*start.y+start.z*start.z-this.radius*this.radius;

        float t0 = (-b-((float) Math.sqrt(b*b-4*c)/2));
        float d = b*b-4*c;

        Vec3 intersectP = dir.multScalar(t0);
        Vec3 normal = intersectP.sub(super.position);



        return d;
    }
}
