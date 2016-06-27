package objects;

import material.Material;
import material.RayHandling;
import raytracer.Intersection;
import raytracer.Ray;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Shape extends SceneObject {
    protected Material material;
    protected Vec3 normal;
    protected RayHandling rayHandling;

    public Shape(Vec3 _position, Material _material, RayHandling _rayHandling) {
        super(_position);
        this.material = _material;
        this.normal = null;
        this.rayHandling = _rayHandling;
    }

    public Intersection intersect(Ray r)
        {
            return null;
        }

    public Vec3 getNormal(Vec3 pointOnSurface) {
        return this.normal;
    }

    public Material getMaterial() {
        return this.material;
    }

    public RayHandling getRayHandling() {return rayHandling;}

    protected void setRayHandlingShape(Shape s) {
        if (rayHandling != null) rayHandling.setShape(s);
    }
}
