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

        Camera myCam = new Camera(new Vec3(0 ,0, 0), new Vec3(0, 0, -1), new Vec3(0, 1, 0), 1.0f, 90.0f);
        Vec3 start = myCam.getPosition();

        Vec3 dest = myCam.calculateDestination(mBufferedImage.getWidth(), mBufferedImage.getHeight());
        Ray r = new Ray(start, dest);
        Vec3 rEndP = r.getEndpoint();
        float rDist = r.getDistance();
        float vDiffMax = rDist-myCam.getFocalLength();
        Vec3 rRef = new Vec3(start.x+1, start.y, start.z).sub(start);


        for (int j = 0; j < mBufferedImage.getHeight(); j ++) {
            for (int i = 0; i < mBufferedImage.getWidth(); i++) {
                dest = myCam.calculateDestination(i, j);
                r = new Ray(start, dest);
                rEndP = r.getEndpoint();
                rDist = r.getDistance();
                float vDiff = rDist-myCam.getFocalLength();
                vDiff = vDiff/vDiffMax;

                Vec3 rV = r.rayVector();
                Vec3 rP = new Vec3 (rV.x, rV.y, start.z);

                double angle = rP.angle(rRef)*(360/(2*Math.PI));

                if (rP.y < rRef.y) {
                    angle = 360-angle;
                }

                //Log.print(this, "--   "+rP.x+", "+rP.y+", "+rP.z+", AA:"+angle);

                float[] rgb = HSV.hsvToRgb((float) angle, vDiff*100, 100f);
                float red = rgb[0];
                float blue = rgb[1];
                float green = rgb[2];

                mRenderWindow.setPixel(mBufferedImage, new RgbColor(red, green, blue), new Vec2(i, j));
            }
        }
        IO.saveImageToPng(mBufferedImage, "raytracing"+System.currentTimeMillis()+".png");
    }
}
