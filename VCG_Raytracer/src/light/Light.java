package light;

import objects.SceneObject;
import utils.RgbColor;
import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Light extends SceneObject {
    protected RgbColor color;

    public Light(RgbColor _color, Vec3 _position) {
        super(_position);
        this.color = _color;
    }

    public RgbColor getColor()
        {
            return color;

        }
}
