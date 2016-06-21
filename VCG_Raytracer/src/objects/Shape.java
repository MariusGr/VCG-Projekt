package objects;

import material.Material;
import raytracer.Intersection;
import raytracer.Ray;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Shape extends SceneObject {
    protected Material material;
    protected Vec3 normal;
    protected boolean reflection;

    public Shape(Vec3 _position, Material _material) {
        super(_position);
        material = _material;
        normal = null;
        reflection = false;
    }

    public boolean getReflection() {return this.reflection;}

    public void setReflection(boolean b) {reflection = b;}

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
}
