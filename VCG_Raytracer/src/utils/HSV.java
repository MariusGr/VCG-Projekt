package utils;

public class HSV {

    /**
     * utils.HSV to RGB color conversion
     *
     * H runs from 0 to 360 degrees
     * S and V run from 0 to 100
     *
     * Ported from the excellent java algorithm by Eugene Vishnevsky at:
     * http://www.cs.rit.edu/~ncs/color/t_convert.html
     */
    public static float[] hsvToRgb(float _h, float _s, float _v) {
        float r, g, b;
        int i;
        float f, p, q, t;

        // Make sure our arguments stay in-range
        float h = Math.max(0, Math.min(360, _h));
        float s = Math.max(0, Math.min(100, _s));
        float v = Math.max(0, Math.min(100, _v));


        // We accept saturation and value arguments from 0 to 100 because that's
        // how Photoshop represents those values. Internally, however, the
        // saturation and value are calculated from a range of 0 to 1. We make
        // That conversion here.
        s /= 100;
        v /= 100;

        if (s == 0) {
            // Achromatic (grey)
            r = g = b = v;

            float[] out = {r, g,(b)};
            return out;
        }

        h /= 60; // sector 0 to 5
        i = (int) Math.floor(h);
        f = h - i; // factorial part of h
        p = v * (1 - s);
        q = v * (1 - s * f);
        t = v * (1 - s * (1 - f));

        switch(i) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;

            case 1:
                r = q;
                g = v;
                b = p;
                break;

            case 2:
                r = p;
                g = v;
                b = t;
                break;

            case 3:
                r = p;
                g = q;
                b = v;
                break;

            case 4:
                r = t;
                g = p;
                b = v;
                break;

            default: // case 5:
                r = v;
                g = p;
                b = q;
        }

        float[] out = {r, g,(b)};
        return out;
    }
}
