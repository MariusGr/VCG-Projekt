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
<<<<<<< HEAD
    protected boolean reflection;
=======
    protected RayHandling rayHandling;
    protected float epsilon = 0.0001f;    //Verhindert Schnittpunkte in der Nähe des Strahlanfangs (aufgrund von Ungenauigkeit von floats)
                                            // Alle t-Werte darunter weden ignoriert --> kein Treffer. Bei Fraktalen höher einstellen

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
<<<<<<< HEAD
=======

    public RayHandling getRayHandling() {return rayHandling;}

    protected void setRayHandlingShape(Shape s) {
        if (rayHandling != null) rayHandling.setShape(s);
    }
>>>>>>> origin/branch_1
}
