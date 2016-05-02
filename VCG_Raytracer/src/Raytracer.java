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

import ui.Window;
import utils.*;

import java.awt.*;
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

        Camera c = new Camera(new Vec3(0 ,0, 0), new Vec3(0, 0, -1), new Vec3(0, 1, 0), 1.0f, 90.0f);
        Vec3 start = c.getPosition();

        for (int j = 0; j < mBufferedImage.getHeight(); j ++) {
            for (int i = 0; i < mBufferedImage.getWidth(); i++) {
                Vec3 dest = c.calculateDestination(i, j);
                Ray r = new Ray(start, dest.normalize(), 1.0f);
                Vec3 eP = r.getEndpoint();

                Color col = Color.getHSBColor(eP.x, eP.y, eP.z);
                float red = (float) col.getRed()/255.0f;
                float blue = (float)col.getBlue()/255.0f;
                float green = (float)col.getGreen()/255.0f;

                mRenderWindow.setPixel(mBufferedImage, new RgbColor(red, green, blue), new Vec2(i, j));
            }
        }
        IO.saveImageToPng(mBufferedImage, "raytracing"+System.currentTimeMillis()+".png");
    }
}
