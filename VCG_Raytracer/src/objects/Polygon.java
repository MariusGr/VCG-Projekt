package objects;

import material.Material;
import material.RayHandling;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Polygon extends Triangle {
    public Polygon(Vec3 _position, Material _material, RayHandling _rh) {
        super(_position, _material, _rh);
    }
}
