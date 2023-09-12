/*
 * This file is part of hephaestus-engine, licensed under the MIT license
 *
 * Copyright (c) 2021-2023 Unnamed Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package team.unnamed.hephaestus.util;

import org.jetbrains.annotations.Contract;
import team.unnamed.creative.base.Vector3Float;

import java.util.Objects;

/**
 * Immutable implementation of Quaternions, a mathematical
 * number system that extends the complex numbers
 *
 * <p>Quaternions are particularly applied for calculations
 * involving three-dimensional rotations, can be used with,
 * and as alternative of, euler angles and rotation matrices</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Quaternion">Quaternion on Wikipedia</a>
 * @since 1.0.0
 */
public final class Quaternion {

    private final double x;
    private final double y;
    private final double z;
    private final double w;

    public Quaternion(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * Returns the real part of the first component
     * of this quaternion
     *
     * @return The first component real part
     * @since 1.0.0
     */
    public double x() {
        return x;
    }

    /**
     * Returns the real part of the second component
     * of this quaternion
     *
     * @return The second component real part
     * @since 1.0.0
     */
    public double y() {
        return y;
    }

    /**
     * Returns the real part of the third component
     * of this quaternion
     *
     * @return The third component real part
     * @since 1.0.0
     */
    public double z() {
        return z;
    }

    /**
     * Returns the fourth component of this quaternion
     *
     * @return The fourth component
     * @since 1.0.0
     */
    public double w() {
        return w;
    }

    /**
     * Converts this quaternion to a {@code double} array
     * with fixed-size of 4, containing all the real parts
     * of the quaternion, in order
     *
     * @return The created array representing this quaternion
     * @since 1.0.0
     */
    @Contract("-> new")
    public double[] toArray() {
        return new double[] { x, y, z, w };
    }

    /**
     * Converts this quaternion to a {@code float} array
     * with fixed-size of 4, containing all the real parts
     * of the quaternion, in order, cast to float
     *
     * @return The created array representing this quaternion
     * @since 1.0.0
     */
    @Contract("-> new")
    public float[] toFloatArray() {
        return new float[] { (float) x, (float) y, (float) z, (float) w };
    }

    /**
     * Returns this quaternion represented as an
     * Euler Angle (in ZXY order) in radians.
     *
     * <p>See https://www.euclideanspace.com/maths/
     * geometry/rotations/conversions/quaternionToEuler
     * /indexLocal.htm</p>
     */
    public Vector3Float toEuler() {

        double test = x * z + y * w;

        // singularity at north pole
        if (test > 0.499F) {
            return new Vector3Float(
                    (float) Math.atan2(x, w),
                    (float) (Math.PI / 2F),
                    0F
            );
        }

        // singularity at south pole
        if (test < -0.499F) {
            return new Vector3Float(
                    (float) -Math.atan2(x, w),
                    (float) (-Math.PI / 2F),
                    0F
            );
        }

        double sqx = x * x;
        double sqy = y * y;
        double sqz = z * z;

        double x2 = x + x;
        double y2 = y + y;
        double z2 = z + z;

        return Vectors.toDegrees(new Vector3Float(
                (float) Math.atan2(w * x2 - y * z2, 1 - 2 * (sqx + sqy)),
                (float) -Math.asin(2 * test),
                (float) Math.atan2(w * z2 - x * y2, 1 - 2 * (sqz + sqy))
        ));
    }

    /**
     * Multiplies this quaternion rotations with the given
     * {@code other} quaternion rotations
     *
     * <p>This method doesn't modify the current {@code this}
     * quaternion instance, since it's immutable, it returns
     * a new fresh quaternion instance as a result of the
     * operation</p>
     *
     * <p>See 'Multiplication of basis elements' section in
     * <a href="https://en.wikipedia.org/wiki/Quaternion">
     *     Wikipedia</a></p>
     */
    public Quaternion multiply(Quaternion other) {
        return new Quaternion(
                x * other.w + w * other.x + y * other.z - z * other.y,
                y * other.w + w * other.y + z * other.x - x * other.z,
                z * other.w + w * other.z + x * other.y - y * other.x,
                w * other.w - x * other.x - y * other.y - z * other.z
        );
    }

    /**
     * Creates a {@link Quaternion} representation of the given
     * {@code euler} angles (in radians)
     */
    public static Quaternion fromEuler(Vector3Float euler) {
        euler = Vectors.toRadians(euler);

        // common values
        double halfX = euler.x() * 0.5D;
        double halfY = euler.y() * 0.5D;
        double halfZ = euler.z() * 0.5D;

        // compute cos
        double cosX = Math.cos(halfX);
        double cosY = Math.cos(halfY);
        double cosZ = Math.cos(halfZ);

        // compute sin
        double sinX = Math.sin(halfX);
        double sinY = Math.sin(halfY);
        double sinZ = Math.sin(halfZ);

        // common products
        double sinXCosY = sinX * cosY;
        double cosXSinY = cosX * sinY;
        double cosXCosY = cosX * cosY;
        double sinXSinY = sinX * sinY;

        return new Quaternion(
                sinXCosY * cosZ - cosXSinY * sinZ,
                cosXSinY * cosZ + sinXCosY * sinZ,
                cosXCosY * sinZ - sinXSinY * cosZ,
                cosXCosY * cosZ + sinXSinY * sinZ
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quaternion that = (Quaternion) o;
        return Double.compare(that.x, x) == 0
                && Double.compare(that.y, y) == 0
                && Double.compare(that.z, z) == 0
                && Double.compare(that.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    @Override
    public String toString() {
        return "Quaternion (" + x + ", " + y + ", " + z + ", " + w + ')';
    }

}
