package it.geosolutions.jaiext.rescale;

import java.awt.Rectangle;
import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.ROI;
import javax.media.jai.ROIShape;
import javax.media.jai.RenderedOp;
import org.junit.BeforeClass;
import org.junit.Test;
import it.geosolutions.jaiext.range.Range;
import it.geosolutions.jaiext.range.RangeFactory;
import it.geosolutions.jaiext.testclasses.TestBase;

/**
 * This test class is used for compare the timing between the new and the old versions of the Rescale operator. If the user wants to change the number
 * of the benchmark cycles or of the not benchmark cycles, should only pass the new values to the JAI.Ext.BenchmarkCycles or
 * JAI.Ext.NotBenchmarkCycles parameters. The selection of the old or new descriptor must be done by setting to true or false the JVM parameter
 * JAI.Ext.OldDescriptor. For the old descriptor it is possible to use the MediaLibAcceleration by setting to true the JVM parameter
 * JAI.Ext.Acceleration. ROI or No Data Range can be used by simply setting to true the JAI.Ext.ROIUsed and JAI.Ext.RangeUsed JVM parameters. The
 * results are printed to the screen.
 */
public class ComparisonTest extends TestBase {

    /** Number of benchmark iterations (Default 1) */
    private final static Integer BENCHMARK_ITERATION = Integer.getInteger(
            "JAI.Ext.BenchmarkCycles", 1);

    /** Number of not benchmark iterations (Default 0) */
    private final static int NOT_BENCHMARK_ITERATION = Integer.getInteger(
            "JAI.Ext.NotBenchmarkCycles", 0);

    /** Boolean indicating if the old descriptor must be used */
    private final static boolean OLD_DESCRIPTOR = Boolean.getBoolean("JAI.Ext.OldDescriptor");

    /** Boolean indicating if the native acceleration must be used */
    private final static boolean NATIVE_ACCELERATION = Boolean.getBoolean("JAI.Ext.Acceleration");

    /** Boolean indicating if a No Data Range must be used */
    private final static boolean RANGE_USED = Boolean.getBoolean("JAI.Ext.RangeUsed");

    /** Boolean indicating if a ROI must be used */
    private final static boolean ROI_USED = Boolean.getBoolean("JAI.Ext.ROIUsed");

    /** Image to elaborate */
    private static RenderedImage image;

    /** ROI parameter */
    private static ROI roi;

    /** No Data Range parameter */
    private static Range range;

    /** Scale factors */
    private static double[] scales;

    /** Offset values */
    private static double[] offsets;

    /** Destination No Data value used when an input data is a No Data value */
    private static double destNoData;

    @BeforeClass
    public static void initialSetup() {

        // Setting of the image filler parameter to true for a better image creation
        IMAGE_FILLER = true;
        // Images initialization
        byte noDataB = 100;
        short noDataUS = 100;
        short noDataS = 100;
        int noDataI = 100;
        float noDataF = 100;
        double noDataD = 100;
        // Image creations
        switch (TEST_SELECTOR) {
        case DataBuffer.TYPE_BYTE:
            image = createTestImage(DataBuffer.TYPE_BYTE, DEFAULT_WIDTH, DEFAULT_HEIGHT, noDataB,
                    false);
            break;
        case DataBuffer.TYPE_USHORT:
            image = createTestImage(DataBuffer.TYPE_USHORT, DEFAULT_WIDTH, DEFAULT_HEIGHT,
                    noDataUS, false);
            break;
        case DataBuffer.TYPE_SHORT:
            image = createTestImage(DataBuffer.TYPE_SHORT, DEFAULT_WIDTH, DEFAULT_HEIGHT, noDataS,
                    false);
            break;
        case DataBuffer.TYPE_INT:
            image = createTestImage(DataBuffer.TYPE_INT, DEFAULT_WIDTH, DEFAULT_HEIGHT, noDataI,
                    false);
            break;
        case DataBuffer.TYPE_FLOAT:
            image = createTestImage(DataBuffer.TYPE_FLOAT, DEFAULT_WIDTH, DEFAULT_HEIGHT, noDataF,
                    false);
            break;
        case DataBuffer.TYPE_DOUBLE:
            image = createTestImage(DataBuffer.TYPE_DOUBLE, DEFAULT_WIDTH, DEFAULT_HEIGHT, noDataD,
                    false);
            break;
        default:
            throw new IllegalArgumentException("Wrong data type");
        }
        // Image filler must be reset
        IMAGE_FILLER = false;

        // ROI creation
        if (ROI_USED) {
            Rectangle rect = new Rectangle(0, 0, DEFAULT_WIDTH / 4, DEFAULT_HEIGHT / 4);
            roi = new ROIShape(rect);
        } else {
            roi = null;
        }

        // Range creation if selected
        if (RANGE_USED && !OLD_DESCRIPTOR) {
            switch (TEST_SELECTOR) {
            case DataBuffer.TYPE_BYTE:
                range = RangeFactory.create(noDataB, true, noDataB, true);
                break;
            case DataBuffer.TYPE_USHORT:
                range = RangeFactory.createU(noDataUS, true, noDataUS, true);
                break;
            case DataBuffer.TYPE_SHORT:
                range = RangeFactory.create(noDataS, true, noDataS, true);
                break;
            case DataBuffer.TYPE_INT:
                range = RangeFactory.create(noDataI, true, noDataI, true);
                break;
            case DataBuffer.TYPE_FLOAT:
                range = RangeFactory.create(noDataF, true, noDataF, true, true);
                break;
            case DataBuffer.TYPE_DOUBLE:
                range = RangeFactory.create(noDataD, true, noDataD, true, true);
                break;
            default:
                throw new IllegalArgumentException("Wrong data type");
            }
        }

        // Rescale Factors
        scales = new double[] { 2, 4, 8 };
        offsets = new double[] { 1, 2, 3 };

        // Destination No Data
        destNoData = 0.0d;
    }

    @Test
    public void testRescale() {

        // Image dataType
        int dataType = TEST_SELECTOR;

        // Descriptor string
        String description = "Rescale";

        // Control if the acceleration should be used for the old descriptor
        if (OLD_DESCRIPTOR) {

            description = "Old " + description;
            if (NATIVE_ACCELERATION) {
                description += " accelerated ";
                System.setProperty("com.sun.media.jai.disableMediaLib", "false");
            } else {
                System.setProperty("com.sun.media.jai.disableMediaLib", "true");
            }
            // Control if the Range should be used for the new descriptor
        } else {
            description = "New " + description;
            System.setProperty("com.sun.media.jai.disableMediaLib", "true");
        }
        // Data type string
        String dataTypeString = "";

        switch (dataType) {
        case DataBuffer.TYPE_BYTE:
            dataTypeString += "Byte";
            break;
        case DataBuffer.TYPE_USHORT:
            dataTypeString += "UShort";
            break;
        case DataBuffer.TYPE_SHORT:
            dataTypeString += "Short";
            break;
        case DataBuffer.TYPE_INT:
            dataTypeString += "Integer";
            break;
        case DataBuffer.TYPE_FLOAT:
            dataTypeString += "Float";
            break;
        case DataBuffer.TYPE_DOUBLE:
            dataTypeString += "Double";
            break;
        default:
            throw new IllegalArgumentException("Wrong data type");
        }

        // Total cycles number
        int totalCycles = BENCHMARK_ITERATION + NOT_BENCHMARK_ITERATION;
        // Image
        PlanarImage imageRescale = null;

        long mean = 0;
        long max = Long.MIN_VALUE;
        long min = Long.MAX_VALUE;

        // Cycle for calculating the mean, maximum and minimum calculation time
        for (int i = 0; i < totalCycles; i++) {

            // creation of the image
            if (OLD_DESCRIPTOR) {
                imageRescale = javax.media.jai.operator.RescaleDescriptor.create(image, scales,
                        offsets, null);
            } else {
                imageRescale = RescaleDescriptor.create(image, scales, offsets, roi, range, false,
                        destNoData, null);
            }

            // Total calculation time
            long start = System.nanoTime();
            imageRescale.getTiles();
            long end = System.nanoTime() - start;

            // If the the first NOT_BENCHMARK_ITERATION cycles has been done, then the mean, maximum and minimum values are stored
            if (i > NOT_BENCHMARK_ITERATION - 1) {
                if (i == NOT_BENCHMARK_ITERATION) {
                    mean = end;
                } else {
                    mean = mean + end;
                }

                if (end > max) {
                    max = end;
                }

                if (end < min) {
                    min = end;
                }
            }
            // For every cycle the cache is flushed such that all the tiles must be recalculates
            JAI.getDefaultInstance().getTileCache().flush();
        }
        // Mean values
        double meanValue = mean / BENCHMARK_ITERATION * 1E-6;

        // Max and Min values stored as double
        double maxD = max * 1E-6;
        double minD = min * 1E-6;
        System.out.println(dataTypeString);
        // Comparison between the mean times
        // Output print of the
        System.out.println("\nMean value for " + description + "Descriptor : " + meanValue
                + " msec.");
        System.out.println("Maximum value for " + description + "Descriptor : " + maxD + " msec.");
        System.out.println("Minimum value for " + description + "Descriptor : " + minD + " msec.");

        // Final Image disposal
        if (imageRescale instanceof RenderedOp) {
            ((RenderedOp) imageRescale).dispose();
        }

    }
}
