/* 
 *  Copyright (c) 2010-2011, Michael Bedward. All rights reserved. 
 *   
 *  Redistribution and use in source and binary forms, with or without modification, 
 *  are permitted provided that the following conditions are met: 
 *   
 *  - Redistributions of source code must retain the above copyright notice, this  
 *    list of conditions and the following disclaimer. 
 *   
 *  - Redistributions in binary form must reproduce the above copyright notice, this 
 *    list of conditions and the following disclaimer in the documentation and/or 
 *    other materials provided with the distribution.   
 *   
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
 *  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 *  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR 
 *  ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
 *  (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
 *  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */   

package it.geosolutions.jaiext.vectorbin.utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.GeometryFactory;
import javax.media.jai.ROI;

import it.geosolutions.jaiext.testclasses.TestBase;
import it.geosolutions.jaiext.vectorbin.utils.CoordinateSequence2D;
import it.geosolutions.jaiext.vectorbin.utils.ROIGeometry;

import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for ROIGeometry.
 * 
 * @author Michael Bedward
 * @since 1.1
 * @version $Id$
 */
public class ROIGeometrySimpleTests extends TestBase{
    
    private static GeometryFactory gf = new GeometryFactory();

    public ROIGeometrySimpleTests() {
    }
    
    @Ignore
    @Test
    public void testAdd() {
        fail("not implemented");
    }

    @Test
    public void testContains_Point() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        Point p0 = new Point(-1, 2);
        Point p1 = new Point(-2, 1);
        
        assertTrue(roi.contains(p0));
        assertFalse(roi.contains(p1));
    }

    @Test
    public void testContains_Point2D() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        Point2D p0 = new Point2D.Double(-1.0, 2.5);
        Point2D p1 = new Point2D.Double(-2.5, 1.0);
        
        assertTrue(roi.contains(p0));
        assertFalse(roi.contains(p1));
    }

    @Test
    public void testContains_int_int() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        assertTrue(roi.contains(-1, 2));
        assertFalse(roi.contains(-2, 1));
    }

    @Test
    public void testContains_double_double() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        assertTrue(roi.contains(-1.0, 2.5));
        assertFalse(roi.contains(-2.5, 1.0));
    }

    @Test
    public void testContains_Rectangle() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        Rectangle r = new Rectangle(-1, -2, 4, 6);
        assertTrue(roi.contains(r));
        
        r.width += 1;
        assertFalse(roi.contains(r));
    }

    @Test
    public void testContains_Rectangle2D() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        Rectangle2D r = new Rectangle2D.Double(-1.0, -2.0, 4.0, 6.0);
        assertTrue(roi.contains(r));
        
        r = new Rectangle2D.Double(-1.2, -2.0, 4.0, 6.0);
        assertFalse(roi.contains(r));
    }

    @Test
    public void testContains_intRectArgs() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        assertTrue(roi.contains(-1, -2, 4, 6));
        assertFalse(roi.contains(-1, -2, 5, 6));
    }

    @Test
    public void testContains_doubleRectArgs() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        assertTrue(roi.contains(-1.0, -2.0, 4.0, 6.0));
        assertFalse(roi.contains(-1.0, -2.0, 5.0, 6.0));
    }

    @Ignore
    @Test
    public void testExclusiveOr() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testGetAsBitmask() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testGetAsImage() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testGetAsRectangleList_4args() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testGetAsRectangleList_5args() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testGetAsShape() {
        fail("not implemented");
    }

    @Test
    public void testGetBounds() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        Rectangle expected = new Rectangle(-1, -2, 4, 6);
        assertTrue( expected.equals(roi.getBounds()) );
    }

    @Test
    public void testGetBounds2D() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        Rectangle2D expected = new Rectangle2D.Double(-1.1, -2.2, 4.4, 6.6);
        Rectangle2D result = roi.getBounds2D();

        assertEquals(expected.getMinX(), result.getMinX(), 0.0001);
        assertEquals(expected.getMinY(), result.getMinY(), 0.0001);
        assertEquals(expected.getWidth(), result.getWidth(), 0.0001);
        assertEquals(expected.getHeight(), result.getHeight(), 0.0001);
    }

    @Ignore
    @Test
    public void testGetThreshold() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testIntersect() {
        fail("not implemented");
    }

    @Test
    public void testIntersects_Rectangle() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        Rectangle r = new Rectangle(0, 0, 10, 10);
        assertTrue(roi.intersects(r));
        
        r.x = r.y = 5;
        assertFalse(roi.intersects(r));
    }

    @Test
    public void testIntersects_Rectangle2D() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        Rectangle2D r = new Rectangle2D.Double(0.0, 0.0, 10.0, 10.0);
        assertTrue(roi.intersects(r));
        
        r = new Rectangle2D.Double(-10.0, -10.0, 5.0, 5.0);
        assertFalse(roi.intersects(r));
    }

    @Test
    public void testIntersects_intRectArgs() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        assertTrue(roi.intersects(0, 0, 10, 10));
        assertFalse(roi.intersects(-10, -10, 5, 5));
    }

    @Test
    public void testIntersects_doubleRectArgs() {
        ROIGeometry roi = createRectROI(-1.1, -2.2, 3.3, 4.4);
        assertTrue(roi.intersects(-5.0, -5.0, 5.0, 5.0));
        assertFalse(roi.intersects(-10.0, -10.0, 5.0, 5.0));
    }
    
    @Test
    public void canCreateFromEmptyGeometry() {
        createEmptyROI();
    }
    
    @Test
    public void emptyROIContainsPoint() {
        ROIGeometry roi = createEmptyROI();
        assertFalse(roi.contains(0, 0));
    }
    
    @Test
    public void emptyROIContainsRect() {
        ROIGeometry empty = createEmptyROI();
        assertFalse(empty.contains(0, 0, 1, 1));
    }
    
    @Test 
    public void addNonEmptyROIToEmpty() {
        ROIGeometry nonEmpty = createRectROI(0, 0, 10, 10);
        ROIGeometry empty = createEmptyROI();
        ROI result = empty.add(nonEmpty);
        
        assertTrue( result.getBounds().equals(nonEmpty.getBounds()) );
    }
    
    @Test
    public void addEmptyROIToNonEmptyROI() {
        ROIGeometry nonEmpty = createRectROI(0, 0, 10, 10);
        ROIGeometry empty = createEmptyROI();
        ROI result = nonEmpty.add(empty);
        
        assertTrue( result.getBounds().equals(nonEmpty.getBounds()) );
    }

    @Ignore
    @Test
    public void testPerformImageOp_4args_1() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testPerformImageOp_4args_2() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testSetThreshold() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testSubtract() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testTransform_AffineTransform_Interpolation() {
        fail("not implemented");
    }

    @Ignore
    @Test
    public void testTransform_AffineTransform() {
        fail("not implemented");
    }

    private ROIGeometry createRectROI(double x0, double y0, double x1, double y1) {
        CoordinateSequence2D cs = new CoordinateSequence2D(
                x0, y0, x0, y1, x1, y1, x1, y0, x0, y0);
        
        Polygon poly = gf.createPolygon(gf.createLinearRing(cs), null);
        return new ROIGeometry(poly, false);
    }

    private ROIGeometry createEmptyROI() {
        Polygon poly = gf.createPolygon(null, null);
        return new ROIGeometry(poly, false);
    }
}