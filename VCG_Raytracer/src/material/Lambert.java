package material;

import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Lambert extends Material {

    public Lambert(RgbColor _color) {
        super(_color);
    }

    public RgbColor getColor(RgbColor _iP, Vec3 _normal, Vec3 _lightV) {
        return _iP.multRGB(super.color.multScalar( _normal.normalize().scalar(_lightV.normalize())));
    }
}
