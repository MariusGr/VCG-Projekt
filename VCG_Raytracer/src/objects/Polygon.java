package objects;

import material.Material;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Polygon extends Triangle {
<<<<<<< HEAD
    public Polygon(Vec3 _position, Material _material) {
        super(_position, _material);
=======
    public Polygon(Vec3 _position, Material _material, RayHandling _rh) {
        super(_position, _material, _rh);
        super.rayHandling.setShape(this);
>>>>>>> origin/branch_1
    }
}
