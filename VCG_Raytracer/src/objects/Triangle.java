package objects;

import material.Material;
import raytracer.Ray;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Triangle extends Shape {
    public Triangle(Vec3 _position, Material _material) {
        super(_position, _material);
    }

    @Override
    public float[] intersect(Ray _ray) {
        return new float[0];
    }
}
