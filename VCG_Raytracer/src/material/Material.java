package material;

import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Material {
    protected RgbColor color;
    protected float materialCoff;

    public Material(RgbColor _color, float _materialCoff) {
        this.color = _color;
        this.materialCoff = _materialCoff;
    }

    public RgbColor getColor(RgbColor _iP, Vec3 _normal, Vec3 _lightV) {
        return null;
    }
}
