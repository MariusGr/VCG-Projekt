import utils.Vec3;

/**
 * Created by PraktikumCG on 19.04.2016.
 */
public class Shape extends SceneObject {
    protected Material material;

    public Shape(Vec3 _position, Material _material) {
        super(_position);
        material = _material;
    }

    public void intersect()
        {

        }
}
