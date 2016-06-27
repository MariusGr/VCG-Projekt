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
    protected float epsilon = 0.0001f;    //Verhindert Schnittpunkte in der Nähe des Strahlanfangs (aufgrund von Ungenauigkeit von floats)
                                            // Alle t-Werte darunter weden ignoriert --> kein Treffer. Bei Fraktalen höher einstellen

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
