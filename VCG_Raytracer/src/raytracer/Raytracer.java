/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    1. Send primary ray
    2. intersection test with all shapes
    3. if hit:
    3a: send secondary ray to the light source
    3b: 2
        3b.i: if hit:
            - Shape is in the shade
            - Pixel color = ambient value
        3b.ii: in NO hit:
            - calculate local illumination
    4. if NO hit:
        - set background color

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package raytracer;

import ui.Window;
import utils.*;

import java.awt.image.BufferedImage;

public class Raytracer {

    private BufferedImage mBufferedImage;
    private Window mRenderWindow;

    public Raytracer(Window renderWindow){
        mBufferedImage = renderWindow.getBufferedImage();
        mRenderWindow = renderWindow;
    }

    public void renderScene(){
        Log.print(this, "Start rendering");


        for (int j = 0; j < mBufferedImage.getHeight(); j ++) {
            for (int i = 0; i < mBufferedImage.getWidth(); i++) {
                mRenderWindow.setPixel(mBufferedImage, new RgbColor((float) i / mBufferedImage.getWidth(), (float) j / mBufferedImage.getHeight(), (float) 0.7), new Vec2(i, j));
            }
        }
        IO.saveImageToPng(mBufferedImage, "raytracing.png");
    }
}
