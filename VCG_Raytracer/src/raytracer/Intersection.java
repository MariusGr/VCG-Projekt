package raytracer;

import objects.Shape;
import raytracer.Ray;
import utils.RgbColor;
import utils.Vec3;
/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Intersection {
    public Vec3 interSectionPoint;
    public Vec3 normal;
    public Ray inRay;
    public Ray outRay;
    public Shape shape;
    public float distance;
    public Boolean incoming;
    public Boolean hit;
    public RgbColor rgb;

    public Intersection() {
        interSectionPoint = null;
        normal = null;
        inRay = null;
        outRay= null;
        shape = null;
        distance = -1;
        incoming = false;
        hit = false;
        rgb = new RgbColor(0,0,0);
    }


    public Ray calculateReflecionRay() {
        return null;
    }

    public Ray calculateRefractionRay() {
        return null;
    }

    public Boolean isOutOfDistance() {
        return null;
    }
}

